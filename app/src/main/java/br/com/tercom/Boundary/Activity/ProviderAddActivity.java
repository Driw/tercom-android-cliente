package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.ProviderControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Price;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Enum.EnumFont;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.CustomTypeFace.overrideFonts;
import static br.com.tercom.Util.GsonUtil.getItem;
import static br.com.tercom.Util.TextUtil.emptyValidator;
import static br.com.tercom.Util.Util.toast;

public class ProviderAddActivity extends AbstractAppCompatActivity {

    private AddProviderTask addProviderTask;

    //BUTTONS
    @BindView(R.id.btn_finalize)
    Button btnSubmit;

    @BindView(R.id.txtCompanyName)
    EditText txtCompanyName;
    @BindView(R.id.txtFantasyName)
    EditText txtFantasyName;
    @BindView(R.id.txtCNPJ)
    EditText txtCNPJ;
    @BindView(R.id.txtSpokesMan)
    EditText txtSpokesMan;
    @BindView(R.id.txtSite)
    EditText txtSite;

    @OnClick(R.id.btn_finalize) void next(){
        CustomPair<String> result = verifyData(txtCompanyName.getText().toString(),txtFantasyName.getText().toString(),
                txtCNPJ.getText().toString(),txtSite.getText().toString(),txtSpokesMan.getText().toString());
        if(result.first){
            initAddTask(txtCompanyName.getText().toString(),txtFantasyName.getText().toString(),txtCNPJ.getText().toString(),txtSite.getText().toString(),txtSpokesMan.getText().toString());
        }else{
            toast(ProviderAddActivity.this,result.second);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provider);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        createToolbar();
        overrideFonts(this,getWindow().getDecorView().getRootView(), EnumFont.FONT_ROBOTO_REGULAR);
        txtCNPJ.addTextChangedListener(Mask.insert("##.###.###/####-##",txtCNPJ));
    }

    private CustomPair<String> verifyData(String companyName, String fantasyName, String cnpj, String site,String spokesman){

        if(!emptyValidator(companyName))
            return new CustomPair<>(false,"Razão Social inválida");

        if(!emptyValidator(fantasyName))
            return new CustomPair<>(false,"Nome Fantasia Inválido");

        if(!emptyValidator(cnpj))
            return new CustomPair<>(false,"CNPJ Inválido");

        if(!emptyValidator(site))
            return new CustomPair<>(false,"Site inválido");

        if(!emptyValidator(spokesman))
            return new CustomPair<>(false,"Nome do responsável inválido");

        return new CustomPair<>(true,"Ok");
    }

    private void initAddTask(String companyName, String fantasyName, String cnpj, String site, String spokesman){
        if(addProviderTask == null || addProviderTask.getStatus() != AsyncTask.Status.RUNNING){
            addProviderTask = new AddProviderTask(companyName,fantasyName,cnpj,site,spokesman);
            addProviderTask.execute();
        }
    }

    private class AddProviderTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<Provider> apiResponse;
        private String companyName;
        private String fantasyName;
        private String cnpj;
        private String site;
        private String spokesman;
        private DialogLoading dialogLoading;

        public AddProviderTask(String companyName, String FantasyName, String cnpj, String site, String spokesman){
            dialogLoading = new DialogLoading(ProviderAddActivity.this);
            this.companyName = companyName;
            fantasyName = FantasyName;
            this.cnpj = cnpj;
            this.site = site;
            this.spokesman = spokesman;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoading.init();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProviderControl providerControl = new ProviderControl(ProviderAddActivity.this);
            apiResponse = providerControl.addProvider(companyName,fantasyName,cnpj,site,spokesman);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ProviderAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage(),"Adicionar Contatos");
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(ProviderAddActivity.this,ProviderContactAddActivity.class);
                        intent.putExtra("idProvider",apiResponse.getResult().getId());
                        startActivity(intent);
                    }
                });
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
               Price price = getItem(getIntent().getExtras().getString("a"),Price.class);
            }
        }
    }



}