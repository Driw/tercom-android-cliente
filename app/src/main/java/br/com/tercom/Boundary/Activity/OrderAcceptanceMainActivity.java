package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderAcceptanceMainAdapter;
import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAcceptanceMainActivity extends AbstractAppCompatActivity {

//    ArrayList<ProductValue> produtos;
//    ArrayList<ServicePrice> servicos;
    ArrayList<iNewOrderItem> orderItems;

    @BindView(R.id.btnOrderAcceptanceMainEdit)
    Button btnOrderAcceptanceMainEdit;
    @BindView(R.id.btnOrderAcceptanceMainRemove)
    Button btnOrderAcceptanceMainRemove;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_main_list);
//        createToolbar();
        ButterKnife.bind(this);
        OrderAcceptanceMainAdapter orderAcceptanceMainAdapter = new OrderAcceptanceMainAdapter(this, orderItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderAcceptanceMainList.setLayoutManager(layoutManager);
        rvOrderAcceptanceMainList.setAdapter(orderAcceptanceMainAdapter);
        orderAcceptanceMainAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                createIntentAbs(OrderAcceptancePriceActivity.class);
            }
        });
    }





}
