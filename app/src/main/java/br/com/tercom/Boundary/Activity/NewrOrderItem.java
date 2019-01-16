package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewrOrderItem extends AbstractAppCompatActivity {

    private OrderItemProduct orderItemProduct;

    @BindView(R.id.txtOrderProductName)
    EditText txtOrderProductName;
    @BindView(R.id.txtOrderManufacturerName)
    EditText txtOrderManufacturerName;
    @BindView(R.id.txtOrderProviderName)
    EditText txtOrderProviderName;

    @OnClick(R.id.btnOrderItemAdd) void addOrderItem() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_item);
        createToolbar();
        ButterKnife.bind(this);

    }
}
