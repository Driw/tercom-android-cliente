package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.OrderAcceptanceControl;
import br.com.tercom.Control.QuotedProductPriceControl;
import br.com.tercom.Control.QuotedServicePriceControl;
import br.com.tercom.Entity.Address;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderAcceptance;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.QuotedProductPrice;
import br.com.tercom.Entity.QuotedProductPriceList;
import br.com.tercom.Entity.QuotedServicePrice;
import br.com.tercom.Entity.QuotedServicePriceList;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAcceptancePriceActivity extends AbstractAppCompatActivity {

    private static final int typeProduct = 1;
    private static final int typeService = 2;

    private ArrayList<QuotedProductPrice> quotedProductPrices;

    private addPrice addPrice;
    private addProductValue addProductValue;
    private addServicePrice addServicePrice;
    private getProductValue getProductValue;
    private getServicePrice getServicePrice;
    private OrderAcceptance orderAcceptance;
    private ArrayList<iNewOrderItem> list;
    private ProductValueAdapter productValueAdapter;
    private ServicePriceAdapter servicePriceAdapter;

    private getQuotedServicePrice getQuotedServicePrice;
    private getQuotedProductPrice getQuotedProductPrice;
    private OrderRequest orderRequest;
    private ArrayList<QuotedProductPrice> quotedProductPriceList;
    private ArrayList<QuotedServicePrice> quotedServicePriceList;
    private ArrayList<ProductValue> produtos;
    private ArrayList<ServicePrice> servicos;

    @BindView(R.id.txtOrderAcceptancePriceAddInfo)
    TextView txtOrderAcceptancePriceAddInfo;
    @BindView(R.id.rvOrderAcceptancePriceList)
    RecyclerView rvOrderAcceptancePriceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_price_check);
//        createToolbar();
        ButterKnife.bind(this);
        switch (setType(getIntent().getExtras().getBoolean("type"))) {
            case typeProduct: initOrderAcceptanceGetProductTask();
            case typeService: initOrderAcceptanceGetServiceTask();
        }
    }

    private int setType(boolean type){
        if(!type){
            return typeService;
        } else {
            return typeProduct;
        }
    }

    private void createOrderAcceptanceProductList(ArrayList<? extends iNewOrderItem> list) {
        if(rvOrderAcceptancePriceList.getAdapter() != null){
            rvOrderAcceptancePriceList.setAdapter(null);
        }
        productValueAdapter = new ProductValueAdapter(this, produtos);
        GridLayoutManager layoutManagerProducts = new GridLayoutManager(this, 2);
        rvOrderAcceptancePriceList.setLayoutManager(layoutManagerProducts);
        rvOrderAcceptancePriceList.setAdapter(productValueAdapter);
        productValueAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                if (produtos.get(position).isSelected()){
                    produtos.get(position).setSelected(false);
                } else {
                    produtos.get(position).setSelected(true);
                    initDialog(position);
                }
                productValueAdapter.notifyItemChanged(position);
            }
        });
    }

    private void createOrderAcceptanceServiceList(ArrayList<? extends iNewOrderItem> list) {
        if(rvOrderAcceptancePriceList.getAdapter() != null){
            rvOrderAcceptancePriceList.setAdapter(null);
        }
        servicePriceAdapter = new ServicePriceAdapter(this, servicos);
        GridLayoutManager layoutManagerServices = new GridLayoutManager(this, 2);
        rvOrderAcceptancePriceList.setLayoutManager(layoutManagerServices);
        rvOrderAcceptancePriceList.setAdapter(servicePriceAdapter);
        servicePriceAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                if (servicos.get(position).isSelected()){
                    servicos.get(position).setSelected(false);
                } else {
                    servicos.get(position).setSelected(true);
                    initOrderAcceptanceServiceTask();
                }
                servicePriceAdapter.notifyItemChanged(position);
            }
        });
    }

    private void initDialog(final int position) {
        final Dialog dialog = new Dialog(OrderAcceptancePriceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_order_acceptance_qnt);
        final EditText txtOrderAcceptancePriceQnt = dialog.findViewById(R.id.txtOrderAcceptancePriceQnt);
        Button btnOrderAcceptanceDialog = dialog.findViewById(R.id.btnOrderAcceptanceDialog);
        btnOrderAcceptanceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtos.get(position).setAmount(Integer.parseInt(txtOrderAcceptancePriceQnt.getText().toString()));
                dialog.dismiss();
                initOrderAcceptanceProductTask(position);
                productValueAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private void initOrderAcceptanceProductTask(int position){
        if(addProductValue == null || addProductValue.getStatus() != AsyncTask.Status.RUNNING){
            addProductValue = new addProductValue(orderAcceptance.getId(), orderRequest.getId(), list.get(position).getAmount(), orderAcceptance.getObservations());
            addProductValue.execute();
        }
    }

    private void initOrderAcceptanceServiceTask(){
        if(addServicePrice == null || addServicePrice.getStatus() != AsyncTask.Status.RUNNING){
            addServicePrice = new addServicePrice(orderAcceptance.getId(), orderRequest.getId(), orderAcceptance.getObservations());
            addServicePrice.execute();
        }
    }

    private void initOrderAcceptanceGetProductTask(){
        if(getProductValue == null || getProductValue.getStatus() != AsyncTask.Status.RUNNING){
            getProductValue = new getProductValue(orderAcceptance.getId());
            getProductValue.execute();
        }
    }

    private void initOrderAcceptanceGetServiceTask(){
        if(getServicePrice == null || getServicePrice.getStatus() != AsyncTask.Status.RUNNING){
            getServicePrice = new getServicePrice(orderAcceptance.getId());
            getServicePrice.execute();
        }
    }

    private void initQuotedProductPriceTask(int position){
        if(getQuotedProductPrice == null || getQuotedProductPrice.getStatus() != AsyncTask.Status.RUNNING){
            getQuotedProductPrice = new getQuotedProductPrice(list.get(position).getId());
            getQuotedProductPrice.execute();
        }
    }

    private void initQuotedServicePriceTask(int position){
        if(getQuotedServicePrice == null || getQuotedServicePrice.getStatus() != AsyncTask.Status.RUNNING){
            getQuotedServicePrice = new getQuotedServicePrice(list.get(position).getId());
            getQuotedServicePrice.execute();
        }
    }

    private  class addPrice extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderAcceptance> apiResponseAcceptance;
        private int idAcceptance;
        private int idAddress;
        private String observations;

        public addPrice(int idAcceptance, int idAddress, String observations){
            this.idAcceptance = idAcceptance;
            this.idAddress = idAddress;
            this.observations = observations;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseAcceptance = orderAcceptanceControl.add(idAcceptance, idAddress, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseAcceptance.getStatusBoolean()){
                orderAcceptance = apiResponseAcceptance.getResult();
            }
            if(orderAcceptance != null) {

            }
        }
    }

    private class addProductValue extends AsyncTask<Void,Void,Void> {
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private int idAcceptance;
        private int idOrder;
        private int qtd;
        private String observations;

        public addProductValue (int idAcceptance, int idOrder, int qtd, String observations) {
            list = new ArrayList<>();
            this.idAcceptance = idAcceptance;
            this.idOrder = idOrder;
            this.qtd = qtd;
            this.observations = observations;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseProduct = orderAcceptanceControl.productAdd(idAcceptance,idOrder,qtd,0, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseProduct.getStatusBoolean()){
                list.addAll(apiResponseProduct.getResult().getList());
            }
            if(list.size() > 0) {
                createOrderAcceptanceProductList(list);
            }
        }
    }

    private class addServicePrice extends AsyncTask<Void,Void,Void> {
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int idAcceptance;
        private int idOrder;
        private String observations;

        public addServicePrice (int idAcceptance, int idOrder, String observations) {
            list = new ArrayList<>();
            this.idAcceptance = idAcceptance;
            this.idOrder = idOrder;
            this.observations = observations;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseService = orderAcceptanceControl.serviceAdd(idAcceptance,idOrder,0, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseService.getStatusBoolean()){
                list.addAll(apiResponseService.getResult().getList());
            }
            if(list.size() > 0) {
                createOrderAcceptanceServiceList(list);
            }
        }
    }

    private class getProductValue extends AsyncTask<Void,Void,Void> {
        private ApiResponse<OrderItemServiceList> apiResponseProduct;
        private int idAcceptance;

        public getProductValue (int idAcceptance) {
            list = new ArrayList<>();
            this.idAcceptance = idAcceptance;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseProduct = orderAcceptanceControl.getAllProducts(idAcceptance);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseProduct.getStatusBoolean()){
                list.addAll(apiResponseProduct.getResult().getList());
            }
            if(list.size() > 0) {
                createOrderAcceptanceServiceList(list);
            }
        }
    }

    private class getServicePrice extends AsyncTask<Void,Void,Void> {
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int idAcceptance;

        public getServicePrice (int idAcceptance) {
            list = new ArrayList<>();
            this.idAcceptance = idAcceptance;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseService = orderAcceptanceControl.getAllServices(idAcceptance);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseService.getStatusBoolean()){
                list.addAll(apiResponseService.getResult().getList());
            }
            if(list.size() > 0) {
                createOrderAcceptanceServiceList(list);
            }
        }
    }

    private  class getQuotedProductPrice extends AsyncTask<Void, Void, Void> {
        private ApiResponse<QuotedProductPriceList> apiResponseQuotedProduct;
        private int idProduct;

        public getQuotedProductPrice(int idProduct){
            quotedProductPriceList = new ArrayList<>();
            this.idProduct = idProduct;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            QuotedProductPriceControl quotedProductPriceControl = new QuotedProductPriceControl(OrderAcceptancePriceActivity.this);
            apiResponseQuotedProduct = quotedProductPriceControl.getAll(idProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseQuotedProduct.getStatusBoolean()){
                quotedProductPriceList = apiResponseQuotedProduct.getResult().getList();
            }
            if(quotedProductPriceList.size() > 0) {
                initOrderAcceptanceGetProductTask();
            }
        }
    }

    private  class getQuotedServicePrice extends AsyncTask<Void, Void, Void> {
        private ApiResponse<QuotedServicePriceList> apiResponseQuotedService;
        private int idService;

        public getQuotedServicePrice(int idService){
            quotedServicePriceList = new ArrayList<>();
            this.idService = idService;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            QuotedServicePriceControl quotedServicePriceControl = new QuotedServicePriceControl(OrderAcceptancePriceActivity.this);
            apiResponseQuotedService = quotedServicePriceControl.getAll(idService);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseQuotedService.getStatusBoolean()){
                quotedServicePriceList = apiResponseQuotedService.getResult().getList();
            }
            if(quotedServicePriceList.size() > 0) {
                initOrderAcceptanceServiceTask();
            }
        }
    }

}
