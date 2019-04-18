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

public class OrderAcceptanceMainActivity extends AbstractAppCompatActivity {

    private acceptanceAdd acceptanceAdd;
    private OrderAcceptance orderAcceptance;
    private OrderRequest orderRequest;
    private GetAllItemsListTask getAllItemsListTask;
    private ArrayList<iNewOrderItem> orderItems;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_main_list);
        createToolbar();
        ButterKnife.bind(this);
        initAcceptanceAdd();
        initGetAllItemListTask();
    }

    private void initGetAllItemListTask(){
        if(getAllItemsListTask == null || getAllItemsListTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllItemsListTask = new GetAllItemsListTask(getIntent().getExtras().getInt("idOrderRequest"));
            getAllItemsListTask.execute();
        }
    }

    private void initAcceptanceAdd(){
        //ORDER QUOTE ID NO LUGAR DO ACCEPTANCE
        if(acceptanceAdd == null || acceptanceAdd.getStatus() != AsyncTask.Status.RUNNING){
            acceptanceAdd = new acceptanceAdd(orderAcceptance.getId(), getIntent().getExtras().getInt("idAddress"), orderAcceptance.getObservations());
            acceptanceAdd.execute();
        }
    }

    private void createOrderAcceptanceList(final ArrayList<? extends iNewOrderItem> list){
        OrderAcceptanceMainAdapter orderAcceptanceMainAdapter = new OrderAcceptanceMainAdapter(this, orderItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderAcceptanceMainList.setLayoutManager(layoutManager);
        rvOrderAcceptanceMainList.setAdapter(orderAcceptanceMainAdapter);
        orderAcceptanceMainAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(OrderAcceptanceMainActivity.this, OrderAcceptancePriceActivity.class);
                intent.putExtra("type", list.get(position).isProduct());
                intent.putExtra("idAcceptance", orderAcceptance.getId());
                intent.putExtra("acceptanceObservations", orderAcceptance.getObservations());
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
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptanceMainActivity.this);
            apiResponseProduct = orderAcceptanceControl.getAllProducts(id);
            apiResponseService = orderAcceptanceControl.getAllServices(id);
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
        private int idAcceptance;
        private int idAddress;
        private String observations;

        public acceptanceAdd(int idAcceptance, int idAddress, String observations){
            this.idAcceptance = idAcceptance;
            this.idAddress = idAddress;
            this.observations = observations;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null) {
                Looper.prepare();
            }
            OrderAcceptanceControl orderAcceptanceControl = new OrderAcceptanceControl(OrderAcceptanceMainActivity.this);
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

}
