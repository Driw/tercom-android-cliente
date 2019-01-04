package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProductValueControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ProductValueList;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductValueListActivity extends AbstractAppCompatActivity {

    private GetAllProductValueTask getAll;

    @BindView(R.id.rv_productvalue)
    RecyclerView rv_productValues;

    @OnClick(R.id.fab_AddProductValue) void click()
    {
        Intent intent = new Intent();
        ProductValue productValue = new ProductValue();
        productValue.getProduct().setId(getIntent().getExtras().getInt("idProduct"));
        intent.putExtra("productValue", new Gson().toJson(productValue));
        //intent.putExtra("idProduct", getIntent().getExtras().getInt("idProduct"));
        intent.setClass(ProductValueListActivity.this, ProductValueDetails.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_value_list);
        ButterKnife.bind(this);
        createToolbar();
        int idProduct = getIntent().getExtras().getInt("idProduct");
        initTask(idProduct);
    }

    private void initTask(int idProduct){
        if(getAll == null || getAll.getStatus() !=  AsyncTask.Status.RUNNING){
            getAll = new GetAllProductValueTask(idProduct);
            getAll.execute();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initTask(getIntent().getExtras().getInt("idProduct"));
    }

    private void createList(final ProductValueList productValue){

        ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, productValue.getList());
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this, 2);
        productValueAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("productValue",new Gson().toJson(productValue.getList().get(position)));
                intent.setClass(ProductValueListActivity.this, ProductValueDetails.class);
                startActivity(intent);
            }
        });
        rv_productValues.setLayoutManager(gridLayoutManager);
        rv_productValues.setAdapter(productValueAdapter);

    }

    private class GetAllProductValueTask extends AsyncTask<Void,Void,Void> {

        private ApiResponse<ProductValueList> apiResponse;
        private int idProduct;
        private DialogLoading dialogLoading;


        public GetAllProductValueTask(int idProduct){
            dialogLoading = new DialogLoading(ProductValueListActivity.this);
            this.idProduct = idProduct;
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
            ProductValueControl pValueControl= new ProductValueControl(ProductValueListActivity.this);
            apiResponse = pValueControl.getAll(idProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                createList(apiResponse.getResult());
            }
        }
    }
}
