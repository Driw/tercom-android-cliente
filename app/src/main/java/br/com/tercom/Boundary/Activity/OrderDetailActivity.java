package br.com.tercom.Boundary.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.OrderAcceptanceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends AbstractAppCompatActivity {

    private static int selectedItemType;
    private static final int typeProduct = 1;
    private static final int typeService = 2;

    private OrderRequest orderRequest;
    private ArrayList<iNewOrderItem> list;
    private getAllProductsTask getAllProductsTask;
    private getAllServicesTask getAllServicesTask;

    private ArrayList<ProductValue> produtos;
    private ArrayList<ServicePrice> services;

    @BindView(R.id.rv_OrderDetailListExpanded)
    RecyclerView rv_OrderDetailListExpanded;
    @BindView(R.id.tbtnOrderDetailItemType)
    ToggleButton tbtnOrderDetailItemType;
    @BindView(R.id.txtOrderRequestedProducts)
    TextView txtOrderRequestedProducts;

    @OnClick(R.id.tbtnOrderDetailItemType) void switchItemType(){
        if(changeType() == typeProduct){
            txtOrderRequestedProducts.setText("Produtos Solicitados:");
            selectedItemType = typeProduct;
            initGetAllProductsTask();
        } else {
            txtOrderRequestedProducts.setText("Serviços Solicitados:");
            selectedItemType = typeService;
            initGetAllServicesTask();
        }
    }

    private int changeType(){
        if (selectedItemType == typeProduct){
            return typeService;
        } else {
            return typeProduct;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        createToolbar();
        if (produtos.size() == 0){
            selectedItemType = typeService;
        } else {
            selectedItemType = typeProduct;
        }
        initGetAllProductsTask();
    }

    private void initGetAllProductsTask(){
        if(getAllProductsTask == null || getAllProductsTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllProductsTask = new getAllProductsTask(orderRequest.getId());
            getAllProductsTask.execute();
        }
    }

    private void initGetAllServicesTask(){
        if(getAllServicesTask == null || getAllServicesTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllServicesTask = new getAllServicesTask(orderRequest.getId());
            getAllServicesTask.execute();
        }
    }

    private void createOrderDetailList(ArrayList<? extends iNewOrderItem> list){
        if (rv_OrderDetailListExpanded.getAdapter() != null){
            rv_OrderDetailListExpanded.setAdapter(null);
        }
        switch (selectedItemType){
            case typeProduct:
                ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, produtos);
                GridLayoutManager layoutManagerProducts = new GridLayoutManager(this, 2);
                rv_OrderDetailListExpanded.setLayoutManager(layoutManagerProducts);
                rv_OrderDetailListExpanded.setAdapter(productValueAdapter);
                break;
            case typeService:
                ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this, services);
                GridLayoutManager layoutManagerServices = new GridLayoutManager(this, 2);
                rv_OrderDetailListExpanded.setLayoutManager(layoutManagerServices);
                rv_OrderDetailListExpanded.setAdapter(servicePriceAdapter);
        }
    }

    private class getAllProductsTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private int id;

        public getAllProductsTask(int id) {
            list = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderDetailActivity.this);
            apiResponseProduct = orderAcceptanceControl.getAllProducts(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseProduct.getStatusBoolean()){
                list.addAll(apiResponseProduct.getResult().getList());
            }
            if(list.size() > 0){
                createOrderDetailList(list);
            }
        }
    }

    private class getAllServicesTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int id;

        public getAllServicesTask(int id) {
            list = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderDetailActivity.this);
            apiResponseService = orderAcceptanceControl.getAllServices(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseService.getStatusBoolean()){
                list.addAll(apiResponseService.getResult().getList());
            }
            if(list.size() > 0){
                createOrderDetailList(list);
            }
        }
    }

}
