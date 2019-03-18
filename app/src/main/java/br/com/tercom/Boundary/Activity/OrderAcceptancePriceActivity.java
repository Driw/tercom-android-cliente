package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Adapter.ProductValueAdapter;
import br.com.tercom.Adapter.ServicePriceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.Util;
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
                        } else {
                            produtos.get(position).setSelected(true);
                            initDialog(position);
                        }
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
                        } else {
                            servicos.get(position).setSelected(true);
                            initDialog(position);
                        }
                        servicePriceAdapter.notifyItemChanged(position);
                    }
                });
        }
    }

    private void initDialog(final int position) {
        final Dialog dialog = new Dialog(OrderAcceptancePriceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_order_acceptance_qnt);
        final EditText txtOrderAcceptancePriceQnt = dialog.findViewById(R.id.txtOrderAcceptancePriceQnt);
        Button btnOrderAcceptanceDialog = dialog.findViewById(R.id.btnOrderAcceptanceDialog);
        btnOrderAcceptanceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtos.get(position).setAmount(Integer.parseInt(txtOrderAcceptancePriceQnt.getText().toString()));
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
