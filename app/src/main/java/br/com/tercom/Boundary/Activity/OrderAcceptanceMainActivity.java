package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderAcceptanceMainAdapter;
import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.OrderAcceptanceControl;
import br.com.tercom.Control.OrderItemControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.LastUpdate;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderAcceptance;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemServiceList;
import br.com.tercom.Entity.OrderQuote;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductPackage;
import br.com.tercom.Entity.ProductType;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.Util.toast;

public class OrderAcceptanceMainActivity extends AbstractAppCompatActivity {

    private OrderAcceptance orderAcceptance;
    private OrderQuote orderQuote;
    private GetAllItemsListTask getAllItemsListTask;
    private acceptanceAdd acceptanceAdd;
    private ArrayList<iNewOrderItem> orderItems;
    private FinalizeTask finalizeTask;

    @BindView(R.id.txtOrderAcceptanceMainOrderID)
    TextView txtOrderAcceptanceMainOrderID;
    @BindView(R.id.txtOrderAcceptanceMainStatus)
    TextView txtOrderAcceptanceMainStatus;
    @BindView(R.id.txtOrderAcceptanceMainReceivedBy)
    TextView txtOrderAcceptanceMainReceivedBy;
    @BindView(R.id.txtOrderAcceptanceMainExpDate)
    TextView txtOrderAcceptanceMainExpDate;
    @BindView(R.id.rvOrderAcceptanceMainList)
    RecyclerView rvOrderAcceptanceMainList;
    @BindView(R.id.btnOrderAcceptaneMainFinalize)
    Button btnOrderAcceptaneMainFinalize;

    @OnClick(R.id.btnOrderAcceptaneMainFinalize) void initFinalizeTask(){
        if(finalizeTask == null || finalizeTask.getStatus() != AsyncTask.Status.RUNNING){
            finalizeTask = new FinalizeTask(orderAcceptance.getId());
            finalizeTask.execute();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_main_list);
        createToolbar();
        ButterKnife.bind(this);
        populateLabels();
        initAcceptanceAdd();
        initGetAllItemListTask();
    }

    private void populateLabels() {
        orderQuote =  new Gson().fromJson(getIntent().getExtras().getString("orderquote"),OrderQuote.class);
        txtOrderAcceptanceMainOrderID.setText(String.valueOf(orderQuote.getId()));
        txtOrderAcceptanceMainStatus.setText(String.valueOf(orderQuote.getStatus()));
        txtOrderAcceptanceMainReceivedBy.setText(orderQuote.getOrderRequest().getCustomerEmployee().getName());
        txtOrderAcceptanceMainExpDate.setText(String.valueOf(orderQuote.getOrderRequest().getExpiration().getDate()));
    }

    private void initGetAllItemListTask(){
        if(getAllItemsListTask == null || getAllItemsListTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllItemsListTask = new GetAllItemsListTask(orderQuote.getOrderRequest().getId());
            getAllItemsListTask.execute();
        }
    }

    private void initAcceptanceAdd(){
        if(acceptanceAdd == null || acceptanceAdd.getStatus() != AsyncTask.Status.RUNNING){
            acceptanceAdd = new acceptanceAdd(orderQuote.getId(), getIntent().getExtras().getInt("idAddress"), "OBSERVATIONS");
            acceptanceAdd.execute();
        }
    }

    private void createOrderAcceptanceList(final ArrayList<? extends iNewOrderItem> list){
        OrderAcceptanceMainAdapter orderAcceptanceMainAdapter = new OrderAcceptanceMainAdapter(this, orderItems, orderQuote);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderAcceptanceMainList.setLayoutManager(layoutManager);
        rvOrderAcceptanceMainList.setAdapter(orderAcceptanceMainAdapter);
        orderAcceptanceMainAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(OrderAcceptanceMainActivity.this, OrderAcceptancePriceActivity.class);
                intent.putExtra("type", list.get(position).isProduct());
                intent.putExtra("orderquote",new Gson().toJson(orderQuote));
                intent.putExtra("orderAcceptance",new Gson().toJson(orderAcceptance));
                intent.putExtra("idProduct",list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private class GetAllItemsListTask extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderItemProductList> apiResponseProduct;
        private ApiResponse<OrderItemServiceList> apiResponseService;
        private int id;

        public GetAllItemsListTask (int id){
            orderItems = new ArrayList<>();
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderItemControl orderItemControl = new OrderItemControl(OrderAcceptanceMainActivity.this);
            apiResponseProduct = orderItemControl.getAllProducts(id);
            apiResponseService = orderItemControl.getAllServices(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseProduct.getStatusBoolean()){
                orderItems.addAll(apiResponseProduct.getResult().getList() );
            }
            if(apiResponseService.getStatusBoolean()){
                orderItems.addAll(apiResponseService.getResult().getList());
            }
            if(orderItems.size() > 0)
                createOrderAcceptanceList(orderItems);

        }
    }

    private  class acceptanceAdd extends AsyncTask<Void, Void, Void> {
        private ApiResponse<OrderAcceptance> apiResponseAcceptance;
        private int idOrderQuote;
        private int idAddress;
        private String observations;

        public acceptanceAdd(int idOrderQuote, int idAddress, String observations){
            this.idOrderQuote = idOrderQuote;
            this.idAddress = idAddress;
            this.observations = observations;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptanceMainActivity.this);
            apiResponseAcceptance = orderAcceptanceControl.add(idOrderQuote, idAddress, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponseAcceptance.getStatusBoolean()){
                orderAcceptance = apiResponseAcceptance.getResult();
            }else{
                toast(OrderAcceptanceMainActivity.this, apiResponseAcceptance.getMessage());
                createIntentAbs(OrderListActivity.class);
                finish();
            }
            if(orderAcceptance != null) {

            }
        }
    }

    private class FinalizeTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<OrderAcceptance> apiResponse;
        private int id;

        public FinalizeTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();

            apiResponse = new OrderAcceptanceControl(OrderAcceptanceMainActivity.this).approve(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                toast(OrderAcceptanceMainActivity.this, apiResponse.getMessage());
                createIntentAbs(MenuActivity.class);
            }else{
                toast(OrderAcceptanceMainActivity.this, apiResponse.getMessage());
            }
        }
    }

}
