package br.com.tercom.Boundary.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderDetailAdapter;
import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.LastUpdate;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductPackage;
import br.com.tercom.Entity.ProductType;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends AbstractAppCompatActivity {

    private static int selectedItemType;
    private static final int typeProduct = 1;
    private static final int typeService = 2;

    ArrayList<ProductValue> produtos;
    ArrayList<ServicePrice> services;

    @BindView(R.id.rv_OrderDetailListExpanded)
    RecyclerView rv_OrderDetailListExpanded;
    @BindView(R.id.tbtnOrderDetailItemType)
    ToggleButton tbtnOrderDetailItemType;
    @BindView(R.id.txtOrderRequestedProducts)
    TextView txtOrderRequestedProducts;

    @OnClick(R.id.tbtnOrderDetailItemType) void switchItemType(){
        if(changeType() == typeProduct){
            txtOrderRequestedProducts.setText("Produtos Solicitados:");
            selectedItemType = typeProduct;
            setAdapter(typeProduct);
        } else {
            txtOrderRequestedProducts.setText("Serviços Solicitados:");
            selectedItemType = typeService;
            setAdapter(typeService);
        }
    }

    private int changeType(){
        if (selectedItemType == typeProduct){
            return typeService;
        } else {
            return typeProduct;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        createToolbar();
        populate();
        if (produtos.size() == 0){
            selectedItemType = typeService;
        } else {
            selectedItemType = typeProduct;
        }
        setAdapter(selectedItemType);
    }

    private void setAdapter(int type) {
        if (rv_OrderDetailListExpanded.getAdapter() != null){
            rv_OrderDetailListExpanded.setAdapter(null);
        }
        switch (type){
            case typeProduct:
                ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, produtos);
                GridLayoutManager layoutManagerProducts = new GridLayoutManager(this, 2);
                rv_OrderDetailListExpanded.setLayoutManager(layoutManagerProducts);
                rv_OrderDetailListExpanded.setAdapter(productValueAdapter);
                break;
            case typeService:
                ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this, services);
                GridLayoutManager layoutManagerServices = new GridLayoutManager(this, 2);
                rv_OrderDetailListExpanded.setLayoutManager(layoutManagerServices);
                rv_OrderDetailListExpanded.setAdapter(servicePriceAdapter);
        }
    }

    private void populate(){
        services = new ArrayList<ServicePrice>();
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

}
