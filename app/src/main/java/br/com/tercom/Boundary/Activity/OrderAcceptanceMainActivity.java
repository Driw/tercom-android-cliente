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

    private OrderRequest orderRequest;
    private GetAllItemsListTask getAllItemsListTask;
    private ArrayList<ProductValue> produtos;
    private ArrayList<ServicePrice> servicos;
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
        initGetAllItemListTask();
    }

    private void initGetAllItemListTask(){
        if(getAllItemsListTask == null || getAllItemsListTask.getStatus() != AsyncTask.Status.RUNNING){
            getAllItemsListTask = new GetAllItemsListTask(orderRequest.getId());
            getAllItemsListTask.execute();
        }
    }

    private void createOrderAcceptanceList(final ArrayList<? extends iNewOrderItem> list){
        OrderAcceptanceMainAdapter orderAcceptanceMainAdapter = new OrderAcceptanceMainAdapter(this, orderItems, orderRequest);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderAcceptanceMainList.setLayoutManager(layoutManager);
        rvOrderAcceptanceMainList.setAdapter(orderAcceptanceMainAdapter);
        orderAcceptanceMainAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(OrderAcceptanceMainActivity.this, OrderAcceptancePriceActivity.class);
                intent.putExtra("type", list.get(position).isProduct());
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

}
