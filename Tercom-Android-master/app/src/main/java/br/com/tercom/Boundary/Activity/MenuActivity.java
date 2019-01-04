package br.com.tercom.Boundary.Activity;

import android.os.Bundle;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AbstractAppCompatActivity {

    @OnClick(R.id.cardProduct) void sendToProduct (){
         createIntentAbs(ProductListActivity.class);
    }
    @OnClick(R.id.cardService) void sendToService (){
        createIntentAbs(ServiceListActivity.class);
    }
    @OnClick(R.id.cardProvider) void sendToProvider (){
        createIntentAbs(ProviderListActivity.class);
    }
    @OnClick(R.id.cardManufacturer) void sendToManufacturer (){
        createIntentAbs(ManufacturerActivity.class);
    }
    @OnClick(R.id.cardPermissions) void sendToPermissions (){
        createIntentAbs(PermissionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        createToolbarWithNavigation(1);
    }
}
