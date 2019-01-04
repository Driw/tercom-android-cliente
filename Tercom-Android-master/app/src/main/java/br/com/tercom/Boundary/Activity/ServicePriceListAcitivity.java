package br.com.tercom.Boundary.Activity;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ServicePriceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductValueList;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Entity.ServicePriceList;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Boundary.Activity.ServicePriceDetailsActivity.ACTION_ADD;
import static br.com.tercom.Boundary.Activity.ServicePriceDetailsActivity.ACTION_UPDATE;
import static br.com.tercom.Util.Util.toast;

public class ServicePriceListAcitivity extends AbstractAppCompatActivity {

    private GetAllServicePrices getAll;
    private int idService;

    @BindView(R.id.rv_service_price)
    RecyclerView rvServicePrice;


    @OnClick(R.id.addServicePrice) void  addServicePrice(){
        Intent intent = new Intent();
        intent.putExtra("idService",idService);
        intent.putExtra("action",ACTION_ADD);
        intent.setClass(ServicePriceListAcitivity.this, ServicePriceDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_price_list_acitivity);
        ButterKnife.bind(this);
        createToolbar();
        idService = getIntent().getExtras().getInt("idService");
        initTask(idService);
    }

    private void initTask(int idService) {
        if(getAll == null || getAll.getStatus() != AsyncTask.Status.RUNNING){
            getAll = new GetAllServicePrices(idService);
            getAll.execute();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initTask(idService);
    }

    private void createList(final ArrayList<ServicePrice> servicePriceArrayList){

        ServicePriceAdapter productValueAdapter = new ServicePriceAdapter(this, servicePriceArrayList);
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this, 2);
        productValueAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("price",new Gson().toJson(servicePriceArrayList.get(position)));
                intent.putExtra("idService",idService);
                intent.putExtra("action",ACTION_UPDATE);
                intent.setClass(ServicePriceListAcitivity.this, ServicePriceDetailsActivity.class);
                startActivity(intent);
            }
        });
        rvServicePrice.setLayoutManager(gridLayoutManager);
        rvServicePrice.setAdapter(productValueAdapter);

    }


    private class GetAllServicePrices extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ServicePriceList> apiResponse;
        private int id;

        public GetAllServicePrices(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ServicePriceControl servicePriceControl = new ServicePriceControl(ServicePriceListAcitivity.this);
            apiResponse = servicePriceControl.getService(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createList(apiResponse.getResult().getList());
            }else{
                toast(ServicePriceListAcitivity.this,apiResponse.getMessage());
            }
        }
    }


}
