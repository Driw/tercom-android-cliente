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

public class NewOrderList extends AbstractAppCompatActivity {

    private OrderRequest orderRequest;
    private Dialog dialog;
    private OrderItemProduct selectedProduct;
    private ArrayList<iNewOrderItem> list;

    @BindView(R.id.rvNewOrderList)
    RecyclerView rvNewOrderList;

    @OnClick(R.id.btn_addNewOrderItemmenu_service)
    void addNewOrderItemService () {
        Intent addService = new Intent(NewOrderList.this, NewOrderItem.class);
        addService.putExtra("typeAdd", NewOrderItem.ADD_SERVICE);
        addService.putExtra("orderRequestId", orderRequest.getId());
        startActivity(addService);
    }

    @OnClick(R.id.btn_addNewOrderItemmenu_product)
    void addNewOrderItemProduct() {
        Intent addProduct = new Intent(NewOrderList.this, NewOrderItem.class);
        addProduct.putExtra("typeAdd", NewOrderItem.ADD_PRODUCT);
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
        CustomerProfile tercomProfile = USER_STATIC.getCustomerEmployee().getCustomerProfile();
        Log.i("tag",tercomProfile.getName());

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
            OrderItemControl orderItemControl = new OrderItemControl(NewOrderList.this);
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
