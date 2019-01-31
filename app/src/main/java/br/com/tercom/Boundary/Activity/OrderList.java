package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tercom.Adapter.OrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.OrderRequestControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Order;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.OrderRequestList;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderList extends AbstractAppCompatActivity {

    private NewOrderTask newOrderTask;
    private OrderRequest selectORderRequest;
    private Dialog dialog;

    @BindView(R.id.rv_OrderList)
    RecyclerView rv_OrderList;

    @OnClick(R.id.btnNewOrder) void newOrder(){
        initDialog();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        createToolbar();
    }


    private void initDialog() {
        Dialog dialog = new Dialog(OrderList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_new_order);
        final EditText txtMaxBudget = dialog.findViewById(R.id.txtMaxBudget);
        final EditText txtExpirationDate = dialog.findViewById(R.id.txt_expiration_date);
        Button btnInit = dialog.findViewById(R.id.btn_init);
        txtExpirationDate.addTextChangedListener( Mask.insert("##/##/####",txtExpirationDate));
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomPair<String> pair = verifyData(txtMaxBudget.getText().toString(),txtExpirationDate.getText().toString());
                if (pair.first){
                    initRequest(txtMaxBudget.getText().toString(),txtExpirationDate.getText().toString());
                }else{
                    Util.toast(OrderList.this,pair.second);
                }
            }
        });
        dialog.show();

    }

    private void initRequest(String budget, String date) {
        if(newOrderTask == null || newOrderTask.getStatus() != AsyncTask.Status.RUNNING){
            newOrderTask = new NewOrderTask(Double.parseDouble(budget),date);
            newOrderTask.execute();
        }
    }

    private CustomPair<String> verifyData(String budget, String date) {
        if(TextUtils.isEmpty(budget))
            return new CustomPair<>(false,"Valor não informado");

        if(TextUtils.isEmpty(date))
            return new CustomPair<>(false,"data não informada");

        return new CustomPair<>(true,"ok");
    }


    private class NewOrderTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<OrderRequest> apiResponse;
        private double budget;
        private String expirationDate;

        public NewOrderTask(double budget, String expirationDate) {
            this.budget = budget;
            this.expirationDate = convertDateFormat(expirationDate);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            OrderRequestControl orderRequestControl = new OrderRequestControl(OrderList.this);
            apiResponse = orderRequestControl.add(budget,expirationDate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                Intent intent = new Intent();
                intent.setClass(OrderList.this,NewOrderList.class);
                intent.putExtra("orderRequest",new Gson().toJson(apiResponse.getResult()));
                startActivity(intent);
            }else{
                DialogConfirm dialogConfirm = new DialogConfirm(OrderList.this);
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

    private String convertDateFormat(String expirationDate){
        try {
            SimpleDateFormat finalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return finalDateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(expirationDate));
        }catch (Exception e){
            e.printStackTrace();
            return expirationDate;
        }
    }

    private void createOrderRequestList(final OrderRequestList result){
        OrderListAdapter categoryAdapter = new OrderListAdapter(this, result.getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                selectORderRequest = result.getList().get(position);
                dialog.dismiss();
            }
        });
        rv_OrderList.setLayoutManager(layoutManager);
        rv_OrderList.setAdapter(categoryAdapter);
    }

    private class getOrderInQueueTask extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderRequestList> apiResponse;
        private int mode = 4;

        public getOrderInQueueTask (int mode) {
            this.mode = mode;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (Looper.myLooper() == null){
                Looper.prepare();
            }
            OrderRequestControl orderRequestControl = new OrderRequestControl(OrderList.this);
            apiResponse = orderRequestControl.getAll(mode);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createOrderRequestList(apiResponse.getResult());
            } else {
                DialogConfirm dialogConfirm = new DialogConfirm(OrderList.this);
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

}
