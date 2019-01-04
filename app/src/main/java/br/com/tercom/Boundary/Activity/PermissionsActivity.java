package br.com.tercom.Boundary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.PermissionsAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Permission;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.tercom.Application.AppTercom.USER_STATIC;

public class PermissionsActivity extends AbstractAppCompatActivity {

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtProfileType)
    TextView txtProfileType;
    @BindView(R.id.txtLevel)
    TextView txtLevel;
    @BindView(R.id.rv_permissions)
    RecyclerView rvPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        ButterKnife.bind(this);
        populate();
    }

    private void populate() {
        txtName.setText(USER_STATIC.getTercomEmployee().getName());
        txtLevel.setText(String.valueOf(USER_STATIC.getTercomEmployee().getTercomProfile().getAssignmentLevel()));
        txtProfileType.setText(USER_STATIC.getTercomEmployee().getTercomProfile().getName());
        ArrayList<Permission> permissions = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            Permission permission = new Permission();
            try {
                permission.setAction(String.format(Locale.US,"Permissão %d",i));
                permission.setAssignmentLevel(1);
                permission.setId(i);
                permission.setPacket(String.valueOf(i));
                permissions.add(permission);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        PermissionsAdapter permissionsAdapter = new PermissionsAdapter(this,permissions);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPermissions.setLayoutManager(llmanager);
        rvPermissions.setAdapter(permissionsAdapter);

    }


}
