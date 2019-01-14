package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.tercom.Adapter.NewOrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.Provider;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewOrderList extends AbstractAppCompatActivity {

    ArrayList<OrderItemProduct> orders;

    @BindView(R.id.rvNewOrderList)
    RecyclerView rvNewOrderList;

    @OnClick(R.id.btn_addNewOrderItem) void addNewOrderItem() {
        createIntentAbs(NewOrderItem.class);
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
        populate();
        NewOrderListAdapter newOrderitemAdapter = new NewOrderListAdapter(this, orders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNewOrderList.setLayoutManager(layoutManager);
        rvNewOrderList.setAdapter(newOrderitemAdapter);
    }

    private void populate(){
        orders = new ArrayList<OrderItemProduct>();
        for (int i = 0; i < 5; i++){
            OrderItemProduct o = new OrderItemProduct();
            Product p = new Product();
            Manufacture m = new Manufacture();
            Provider pr = new Provider();
            p.setName("Teste");
            m.setFantasyName("Teste");
            pr.setFantasyName("Teste");
            o.setId(i);
            o.setProduct(p);
            o.setManufacturer(m);
            o.setProvider(pr);
            orders.add(o);
        }
    }

}
