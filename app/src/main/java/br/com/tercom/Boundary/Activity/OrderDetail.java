package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderDetailAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetail extends AbstractAppCompatActivity {

    ArrayList<ProdutoGenerico> produtos;

    @BindView(R.id.rvOrderDetail)
    RecyclerView rvOrderDetail;
    @OnClick(R.id.btnRemoveOrder) void removeOrder() {

    }
    @OnClick(R.id.btnOrderEdit) void editOrder() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        createToolbar();
        populate();
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(this, produtos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderDetail.setLayoutManager(layoutManager);
        rvOrderDetail.setAdapter(orderDetailAdapter);
    }

    private void populate(){
        produtos = new ArrayList<ProdutoGenerico>();

        //produtos.add("Produto")
    }

}
