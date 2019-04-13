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
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderAcceptance;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.ProductValue;
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

    private addPrice addPrice;
    private addProductValue addProductValue;
    private addServicePrice addServicePrice;
    private OrderAcceptance orderAcceptance;
    private OrderRequest orderRequest;
    private ArrayList<iNewOrderItem> list;
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
        setAdapter(setType());
    }

    private int setType(){
        if(produtos.size() == 0){
            return typeService;
        } else {
            return typeProduct;
        }
    }

    private void setAdapter(int type){
        if(rvOrderAcceptancePriceList.getAdapter() != null){
            rvOrderAcceptancePriceList.setAdapter(null);
        }
        switch (type){
            case typeProduct:
                final ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, produtos);
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
                break;
            case typeService:
                final ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this, servicos);
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
                            initDialog(position);
                        }
                        servicePriceAdapter.notifyItemChanged(position);
                    }
                });
        }
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
            }
        });
        dialog.show();
    }

    private void initOrderAcceptanceProductTask(){
        if(addProductValue == null || addProductValue.getStatus() != AsyncTask.Status.RUNNING){
            addProductValue = new addProductValue(0);
            addProductValue.execute();
        }
    }

    private void initOrderAcceptanceServideTask(){
        if(addServicePrice == null || addServicePrice.getStatus() != AsyncTask.Status.RUNNING){
            addServicePrice = new addServicePrice(0);
            addServicePrice.execute();
        }
    }

    private void createOrderAcceptanceProductList(ArrayList<? extends iNewOrderItem> list) {
        if(rvOrderAcceptancePriceList.getAdapter() != null){
            rvOrderAcceptancePriceList.setAdapter(null);
        }
        final ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, produtos);
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
        final ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this, servicos);
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
                initDialog(position);
            }
            servicePriceAdapter.notifyItemChanged(position);
            }
        });
    }

    private  class addPrice extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderAcceptance> apiResponseAcceptance;
        private int id;

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseAcceptance = orderAcceptanceControl.add(0,0,"teste");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseAcceptance.getStatusBoolean()){

            }
        }
    }

    private class addProductValue extends AsyncTask<Void,Void,Void> {
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private int id;

        public addProductValue (int id) {
            produtos = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseProduct = orderAcceptanceControl.productAdd(0,0,0,0, "teste");
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
        private int id;

        public addServicePrice(int id){
            list = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptancePriceActivity.this);
            apiResponseService = orderAcceptanceControl.serviceAdd(0,0,0,0, "teste");
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

}
