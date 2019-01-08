package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetail extends AbstractAppCompatActivity {

    @OnClick(R.id.btnRemoveOrder) void removeOrder() {

    }
    @OnClick(R.id.btnOrderEdit) void editOrder() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
    }
}
