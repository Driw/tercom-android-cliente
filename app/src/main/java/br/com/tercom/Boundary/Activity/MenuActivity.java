package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AbstractAppCompatActivity {

    @OnClick(R.id.cardContato) void sendToMessages (){
        createIntentAbs(MessageListActivity.class);
    }
    @OnClick(R.id.cardOrder) void sendToOrder (){
        createIntentAbs(OrderListActivity.class);
    }
    @OnClick(R.id.cardPermission) void sendToPermission (){
        createIntentAbs(PermissionsActivity.class);
    }

    @BindView(R.id.textView4)
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        //createToolbarWithNavigation(1);
        //txtWelcome.setText("Bem vindo, " + AppTercom.USER_STATIC.getCustomerEmployee().getName() + "!");
    }
}