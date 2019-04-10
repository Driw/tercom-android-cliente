package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import br.com.tercom.Entity.LastUpdate;
import br.com.tercom.Entity.Manufacture;
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

    private static final int actionEdit = 1;
    private static final int actionFinalize = 2;
    private int actionType;

    OrderRequest orderRequest;
    ArrayList<ProductValue> produtos;
    ArrayList<ServicePrice> servicos;
    ArrayList<iNewOrderItem> orderItems = new ArrayList<>();
    ArrayList<OrderRequest> orderRequests = new ArrayList<>();

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
    @BindView(R.id.btnOrderAcceptaneMainFinalize)
    Button btnOrderAcceptaneMainFinalize;


    @OnClick(R.id.btnOrderAcceptaneMainFinalize) void actionManager(){

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_main_list);
//        createToolbar();
        ButterKnife.bind(this);
//        populate();
        OrderAcceptanceMainAdapter orderAcceptanceMainAdapter = new OrderAcceptanceMainAdapter(this, orderItems, orderRequests);
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


    /*
    private void populate(){
        servicos = new ArrayList<ServicePrice>();
        produtos = new ArrayList<ProductValue>();
        for(int i = 0; i < 5; i++){
            ProductValue p = new ProductValue();
            Product pr = new Product();
            Manufacture m = new Manufacture();
            Provider pro = new Provider();
            ProductPackage p2 = new ProductPackage();
            ProductType p3 = new ProductType();
            LastUpdate l = new LastUpdate();
            p.setLastUpdate(l);
            p.setType(p3);
            p.setPackage(p2);
            p.setProduct(pr);
            p.setManufacture(m);
            p.setProvider(pro);
            p.setName("Teste");
            produtos.add(p);
        }
    }
    */

}
