package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import br.com.tercom.Adapter.ProductAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProductControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductList;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.Util.toast;

public class ProductListActivity extends AbstractAppCompatActivity {

    private ProductTask productTask;

    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;


    @OnClick(R.id.btn_add_product) void add(){
        createIntentAbs(ProductAddActivity.class);
    }


    EditText.OnEditorActionListener ON_EDITOR_CLICK = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                if(!textView.getText().toString().trim().isEmpty())
                initProductTask(textView.getText().toString());
                else
                    toast(ProductListActivity.this,"Digite algum valor antes de buscar!");
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        createToolbarWithNavigation(2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        txtSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txtSearch.setOnEditorActionListener(ON_EDITOR_CLICK);

    }


    private void initProductTask(String value){
        if(productTask == null || productTask.getStatus() != AsyncTask.Status.RUNNING){
            productTask = new ProductTask(value);
            productTask.execute();
        }
    }


    private void createListProviders(final ProductList result) {
        ProductAdapter productAdapter = new ProductAdapter(this,result.getList());
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ProductListActivity.this,ProductDetailsActivity.class);
                intent.putExtra("product",new Gson().toJson(result.getList().get(position)));
                startActivity(intent);
            }
        });
        rvProducts.setLayoutManager(llmanager);
        rvProducts.setAdapter(productAdapter);
    }



    private class ProductTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductList> apiResponse;
        private String value;
        private DialogLoading dialogLoading;


        public ProductTask(String value) {
            dialogLoading = new DialogLoading(ProductListActivity.this);
            this.value = value;
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
            ProductControl productControl = new ProductControl(ProductListActivity.this);
            apiResponse = productControl.search(value, EnumREST.NAME);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                createListProviders(apiResponse.getResult());
            }
        }
    }



}
