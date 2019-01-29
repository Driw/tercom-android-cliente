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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import br.com.tercom.Adapter.NewOrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.OrderRequestControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewOrderList extends AbstractAppCompatActivity {



    @BindView(R.id.rvNewOrderList)
    RecyclerView rvNewOrderList;

    String extra1 = "Teste";
    String extra2 = "Teste";

    @OnClick(R.id.btn_addNewOrderItemmenu_service)
    void addNewOrderItemService () {
        Intent addService = new Intent(NewOrderList.this, NewOrderItem.class);
        addService.putExtra("typeAdd", extra1);
        addService.putExtra("orderRequestId", extra2);
        startActivity(addService);
    }

    @OnClick(R.id.btn_addNewOrderItemmenu_product)
    void addNewOrderItemProduct() {
        Intent addProduct = new Intent(NewOrderList.this, NewOrderItem.class);
        addProduct.putExtra("typeAdd", extra1);
        addProduct.putExtra("orderRequestId", extra2);
        startActivity(addProduct);
    }

    @OnClick(R.id.btnCompleteOrder) void completeOrder() {
        //TODO
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);
        createToolbar();
        ButterKnife.bind(this);
    }

    private void createListOrder(ArrayList<OrderItemProduct> orders) {
        NewOrderListAdapter newOrderitemAdapter = new NewOrderListAdapter(this, orders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNewOrderList.setLayoutManager(layoutManager);
        rvNewOrderList.setAdapter(newOrderitemAdapter);
    }


}
