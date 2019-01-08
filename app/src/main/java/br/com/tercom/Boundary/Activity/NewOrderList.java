package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewOrderList extends AbstractAppCompatActivity {

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
        ButterKnife.bind(this);
    }
}
