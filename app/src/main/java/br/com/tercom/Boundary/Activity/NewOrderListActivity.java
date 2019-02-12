package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.NewOrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.OrderItemControl;
import br.com.tercom.Control.OrderRequestControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.CustomerProfile;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemService;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.TercomProfile;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Application.AppTercom.USER_STATIC;
import static br.com.tercom.Util.Util.toast;

public class NewOrderListActivity extends AbstractAppCompatActivity {


    private OrderRequest orderRequest;
    private Dialog dialog;
    private OrderItemProduct selectedProduct;
    private ArrayList<iNewOrderItem> list;
    private GetAllProductListTask getAllProductListTask;
    private CompleteOrderTask completeOrderTask;

    @BindView(R.id.rvNewOrderList)
    RecyclerView rvNewOrderList;

    @OnClick(R.id.btn_addNewOrderItemmenu_service)
    void addNewOrderItemService () {
        Intent addService = new Intent(NewOrderListActivity.this, NewOrderItemActivity.class);
        addService.putExtra("typeAdd", NewOrderItemActivity.ADD_SERVICE);
        addService.putExtra("orderRequestId", orderRequest.getId());
        startActivityForResult(addService, NewOrderItemActivity.ADD_SERVICE );
    }

    @OnClick(R.id.btn_addNewOrderItemmenu_product)
    void addNewOrderItemProduct() {
        Intent addProduct = new Intent(NewOrderListActivity.this, NewOrderItemActivity.class);
        addProduct.putExtra("typeAdd", NewOrderItemActivity.ADD_PRODUCT);
        addProduct.putExtra("orderRequestId", orderRequest.getId());
        startActivityForResult(addProduct,NewOrderItemActivity.ADD_PRODUCT);
    }

    @OnClick(R.id.btnCompleteOrder) void completeOrder() {
        initCompleteOrderTask();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);
        orderRequest = new Gson().fromJson(getIntent().getExtras().getString("orderRequest"),OrderRequest.class);
        createToolbar();
        ButterKnife.bind(this);
        initGetAllProducts();


    }

    private void createNewOrderList(ArrayList<? extends iNewOrderItem> list) {
        NewOrderListAdapter newOrderitemAdapter = new NewOrderListAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNewOrderList.setLayoutManager(layoutManager);
        rvNewOrderList.setAdapter(newOrderitemAdapter);
    }


    private void initGetAllProducts(){
        if(getAllProductListTask == null || getAllProductListTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllProductListTask = new GetAllProductListTask(orderRequest.getId());
            getAllProductListTask.execute();
        }
    }

    private void initCompleteOrderTask(){
        if(completeOrderTask == null || completeOrderTask.getStatus() != AsyncTask.Status.RUNNING){
            completeOrderTask = new CompleteOrderTask(orderRequest.getId());
            completeOrderTask.execute();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK)
            initGetAllProducts();
    }

    private class GetAllProductListTask extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int id;

        public GetAllProductListTask (int id){
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

    private class CompleteOrderTask extends AsyncTask<Void,Void,Void>{
        ApiResponse<OrderRequest> apiResponse;
        private int id;

        public CompleteOrderTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            OrderRequestControl orderRequestControl = new OrderRequestControl(NewOrderListActivity.this);
            apiResponse = orderRequestControl.setQueued(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            toast(NewOrderListActivity.this,apiResponse.getMessage());
            finish();
        }
    }


}
