package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAcceptancePriceActivity extends AbstractAppCompatActivity {

    private static final int typeProduct = 1;
    private static final int typeService = 2;

    ArrayList<ProductValue> produtos;
    ArrayList<ServicePrice> servicos;

    @BindView(R.id.txtOrderAcceptancePriceAddInfo)
    TextView txtOrderAcceptancePriceAddInfo;
    @BindView(R.id.rvOrderAcceptancePriceList)
    RecyclerView rvOrderAcceptancePriceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acceptance_price_check);
//        createToolbar();
        ButterKnife.bind(this);
        setAdapter(setType());
    }

    private int setType(){
        if(produtos.size() == 0){
            return typeService;
        } else {
            return typeProduct;
        }
    }

    private void setAdapter(int type){
        if(rvOrderAcceptancePriceList.getAdapter() != null){
            rvOrderAcceptancePriceList.setAdapter(null);
        }
        switch (type){
            case typeProduct:
                final ProductValueAdapter productValueAdapter = new ProductValueAdapter(this, produtos);
                GridLayoutManager layoutManagerProducts = new GridLayoutManager(this, 2);
                rvOrderAcceptancePriceList.setLayoutManager(layoutManagerProducts);
                rvOrderAcceptancePriceList.setAdapter(productValueAdapter);
                productValueAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        if (produtos.get(position).isSelected()){
                            produtos.get(position).setSelected(false);
                        } else {produtos.get(position).setSelected(true);}
                        productValueAdapter.notifyItemChanged(position);
                    }
                });
                break;
            case typeService:
                final ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this, servicos);
                GridLayoutManager layoutManagerServices = new GridLayoutManager(this, 2);
                rvOrderAcceptancePriceList.setLayoutManager(layoutManagerServices);
                rvOrderAcceptancePriceList.setAdapter(servicePriceAdapter);
                servicePriceAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        if (servicos.get(position).isSelected()){
                            servicos.get(position).setSelected(false);
                        } else {servicos.get(position).setSelected(true);}
                        servicePriceAdapter.notifyItemChanged(position);
                    }
                });
        }
    }

}
