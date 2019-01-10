package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderListAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Order;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderList extends AbstractAppCompatActivity {

    private ArrayList<Order> orders;
    private ArrayList<ProdutoGenerico> produtos;

    @BindView(R.id.rv_OrderList)
    RecyclerView rv_OrderList;

    @OnClick(R.id.btnNewOrder) void newOrder(){
        createIntentAbs(NewOrderList.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        createToolbar();
        populate();
        OrderListAdapter orderListAdapter = new OrderListAdapter(this, orders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_OrderList.setLayoutManager(layoutManager);
        rv_OrderList.setAdapter(orderListAdapter);
        orderListAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                createIntentAbs(MessageList.class);
            }
        });
    }

    private void populate(){

        orders = new ArrayList<Order>();
        produtos = new ArrayList<ProdutoGenerico>();

        for (int i = 0; i < 5; i++) {
            Order o = new Order();
            o.setOrderNumber(i);
            o.setProdutos(produtos);
            orders.add(o);
        }
    }

}
