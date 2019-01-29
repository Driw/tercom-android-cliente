package br.com.tercom.Boundary.Activity;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.security.Permissions;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.PermissionsAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ManagePermissionsControl;
import br.com.tercom.Control.PermissionControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Permission;
import br.com.tercom.Entity.PermissionList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
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

    private PermissionTask permissionTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        ButterKnife.bind(this);
        populate();
    }

    private void populate() {
        txtName.setText(USER_STATIC.getCustomerEmployee().getName());
        txtLevel.setText(String.valueOf(USER_STATIC.getCustomerEmployee().getCustomerProfile().getAssignmentLevel()));
        txtProfileType.setText(USER_STATIC.getCustomerEmployee().getCustomerProfile().getName());
        initTask();
    }

    private void createList(ArrayList<Permission> permissions){
        PermissionsAdapter permissionsAdapter = new PermissionsAdapter(this,permissions);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPermissions.getContext(),
                llmanager.getOrientation());
        rvPermissions.addItemDecoration(dividerItemDecoration);
        rvPermissions.setLayoutManager(llmanager);
        rvPermissions.setAdapter(permissionsAdapter);

    }

    private void initTask(){
        if(permissionTask == null || permissionTask.getStatus() != AsyncTask.Status.RUNNING){
            permissionTask = new PermissionTask();
            permissionTask.execute();
        }

    }

    private class PermissionTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<PermissionList> apiResponse;
        private DialogLoading dialogLoading;

        @Override
        protected void onPreExecute() {
            dialogLoading = new DialogLoading(PermissionsActivity.this);
            dialogLoading.init();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ManagePermissionsControl permissionControl = new ManagePermissionsControl(PermissionsActivity.this);
            apiResponse = permissionControl.getAll(USER_STATIC.getCustomerEmployee().getCustomerProfile().getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                createList(apiResponse.getResult().getList());
            }else{
                DialogConfirm dialogConfirm = new DialogConfirm(PermissionsActivity.this);
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }


}
