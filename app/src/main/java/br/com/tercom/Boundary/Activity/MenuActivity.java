package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.CustomerControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Customer;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Application.AppTercom.CUSTOMER_STATIC;
import static br.com.tercom.Application.AppTercom.USER_STATIC;
import static br.com.tercom.Boundary.Activity.LoginActivity.STRING_LOGIN;
import static br.com.tercom.Boundary.Activity.LoginActivity.STRING_REFERENCE;
import static br.com.tercom.Util.Util.toast;

public class MenuActivity extends AbstractAppCompatActivity {

    private CustomerTask customerTask;

    @OnClick(R.id.cardContato) void sendToMessages (){
        createIntentAbs(MessageListActivity.class);
    }
    @OnClick(R.id.cardOrder) void sendToOrder (){
        createIntentAbs(OrderListActivity.class);
    }
    @OnClick(R.id.cardPermission) void sendToPermission (){
        createIntentAbs(PermissionsActivity.class);
    }
    @OnClick(R.id.cardProduct) void sendToProduct (){
        toast(MenuActivity.this,"Funcionalidade não ativada");
    }

    @OnClick(R.id.btnLogoff) void logoff (){
        SharedPreferences sharedPreferences  =  getSharedPreferences(STRING_REFERENCE, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        createIntentAbs(LoginActivity.class);
    }


    @BindView(R.id.textView4)
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        initCustomerTask();
        //createToolbarWithNavigation(1);
        //txtWelcome.setText("Bem vindo, " + AppTercom.USER_STATIC.getCustomerEmployee().getName() + "!");
    }

    private void initCustomerTask(){
        if(customerTask == null || customerTask.getStatus() != AsyncTask.Status.RUNNING){
            customerTask = new CustomerTask(USER_STATIC.getCustomerEmployeeId());
            customerTask.execute();
        }
    }


    private class CustomerTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<Customer> apiResponse;
        private int id;

        public CustomerTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            apiResponse = new CustomerControl(MenuActivity.this).get(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                CUSTOMER_STATIC = apiResponse.getResult();
            }else{
                CUSTOMER_STATIC = new Customer();
            }
        }
    }

}