package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderAddressListAdapter;
import br.com.tercom.Application.AppTercom;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Address;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.com.tercom.Application.AppTercom.CUSTOMER_STATIC;

public class OrderAddressListActivity extends AbstractAppCompatActivity {

    @BindView(R.id.rv_OrderAddressList)
    RecyclerView rv_OrderAddressList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address_list);
        ButterKnife.bind(this);
        setAdapter();
    }

    private void setAdapter(){
        OrderAddressListAdapter orderAddressListAdapter = new OrderAddressListAdapter(this, CUSTOMER_STATIC.getAddresses());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_OrderAddressList.setLayoutManager(layoutManager);
        rv_OrderAddressList.setAdapter(orderAddressListAdapter);
        orderAddressListAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(OrderAddressListActivity.this, OrderAcceptanceMainActivity.class);
                intent.putExtra("idAddress", CUSTOMER_STATIC.getAddresses().get(position).getId());
                intent.putExtra("idOrderRequest", getIntent().getExtras().getInt("idOrderRequest"));
                startActivity(intent);
            }
        });
    }

}
