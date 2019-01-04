package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductCategory;
import br.com.tercom.Entity.ProductSend;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.TextUtil.emptyValidator;

public class ProductAddActivity extends AbstractAppCompatActivity {

    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtDescription)
    EditText txtDescription;
    @BindView(R.id.txtApplication)
    EditText txtApplication;

    @OnClick(R.id.btnNext) void next(){
        DialogLoading dialogLoading = new DialogLoading(ProductAddActivity.this);
        dialogLoading.init();

        String[] values ={txtName.getText().toString(),
                txtDescription.getText().toString(),
                txtApplication.getText().toString()} ;

        CustomPair<String> result = verify(values[0],values[1],values[2]);
        if(result.first){
            ProductSend product = new ProductSend();
            product.setName(values[0]);
            product.setDescription(values[1]);
            product.setUtility(values[2]);
            Intent intent = new Intent();
            intent.setClass(this, ProductCategoryAddActivity.class);
            intent.putExtra("product",new Gson().toJson(product));
            dialogLoading.dismissD();
            startActivity(intent);
        }else{
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ProductAddActivity.this);
            dialogConfirm.init(EnumDialogOptions.FAIL, result.second);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        createToolbar();
        ButterKnife.bind(this);
    }

    private CustomPair<String> verify(String name, String description, String application) {
        if(!emptyValidator(name))
            return new CustomPair<>(false,"Nome inválido");

        if(!emptyValidator(description))
            return new CustomPair<>(false,"Descrição Inválida");

        if(!emptyValidator(application))
            return new CustomPair<>(false,"Aplicação Inválida");


        return new CustomPair<>(true,"Ok");


    }
}
