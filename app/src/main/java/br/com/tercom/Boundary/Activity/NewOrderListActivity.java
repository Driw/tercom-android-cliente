package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.NewOrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.OrderItemControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewOrderListActivity extends AbstractAppCompatActivity {

    private OrderRequest orderRequest;
    private Dialog dialog;
    private OrderItemProduct selectedProduct;
    private ArrayList<iNewOrderItem> list;

    @BindView(R.id.rvNewOrderList)
    RecyclerView rvNewOrderList;

    @OnClick(R.id.btn_addNewOrderItemmenu_service)
    void addNewOrderItemService () {
        Intent addService = new Intent(NewOrderListActivity.this, NewOrderItemActivity.class);
        addService.putExtra("typeAdd", NewOrderItemActivity.ADD_SERVICE);
        addService.putExtra("orderRequestId", orderRequest.getId());
        startActivity(addService);
    }

    @OnClick(R.id.btn_addNewOrderItemmenu_product)
    void addNewOrderItemProduct() {
        Intent addProduct = new Intent(NewOrderListActivity.this, NewOrderItemActivity.class);
        addProduct.putExtra("typeAdd", NewOrderItemActivity.ADD_PRODUCT);
        addProduct.putExtra("orderRequestId", orderRequest.getId());
        startActivity(addProduct);
    }

    @OnClick(R.id.btnCompleteOrder) void completeOrder() {
        //TODO
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);
        orderRequest = new Gson().fromJson(getIntent().getExtras().getString("orderRequest"),OrderRequest.class);
        createToolbar();
        ButterKnife.bind(this);
    }

    private void createNewOrderList(ArrayList<? extends iNewOrderItem> list) {
        NewOrderListAdapter newOrderitemAdapter = new NewOrderListAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNewOrderList.setLayoutManager(layoutManager);
        rvNewOrderList.setAdapter(newOrderitemAdapter);
    }

    private class getAllProductListTask extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int id;

        public getAllProductListTask (int id){
            list = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderItemControl orderItemControl = new OrderItemControl(NewOrderListActivity.this);
            apiResponseProduct = orderItemControl.getAllProducts(id);
            apiResponseService = orderItemControl.getAllServices(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseProduct.getStatusBoolean()){
                list.addAll(apiResponseProduct.getResult().getList() );
            }
            if(apiResponseService.getStatusBoolean()){
                list.addAll(apiResponseService.getResult().getList());
            }
            if(list.size() > 0)
                createNewOrderList(list);
        }
    }

}
