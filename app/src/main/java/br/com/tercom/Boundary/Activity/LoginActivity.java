package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.LoginCustomerControl;
import br.com.tercom.Control.ProductGroupControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.LoginCustomer;
import br.com.tercom.Entity.LoginTercom;
import br.com.tercom.Entity.ProductGroup;
import br.com.tercom.Entity.ProductList;
import br.com.tercom.Entity.User;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Application.AppTercom.USER_STATIC;
import static br.com.tercom.Util.Util.toast;

public class LoginActivity extends AbstractAppCompatActivity {

    private static final String STRING_REFERENCE = "login_costumer";
    private static final String STRING_LOGIN = "login";

    private LoginTask loginTask;
    private SharedPreferences sharedPreferences;

    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @BindView(R.id.txtPass)
    EditText txtPass;

    @OnClick(R.id.btn_login) void login(){
        boolean result = true;
        if(TextUtils.isEmpty(txtEmail.getText().toString())){
            result = false;
            txtEmail.setError("Email inválido");
        }

        if(TextUtils.isEmpty(txtPass.getText().toString())){
            result = false;
            txtPass.setError("Senha inválido");
        }

        if(result){
            if(loginTask == null || loginTask.getStatus() != AsyncTask.Status.RUNNING){
                loginTask = new LoginTask(txtEmail.getText().toString(),txtPass.getText().toString());
                loginTask.execute();
            }
        }




    }

    /*@OnClick(R.id.btn_login) void login() {
        createIntentAbs(MenuActivity.class);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_boundary);
        ButterKnife.bind(this);
        sharedPreferences =  getSharedPreferences(STRING_REFERENCE, Context.MODE_PRIVATE);
        try {
            String getJson = sharedPreferences.getString(STRING_LOGIN,"");
            if(!TextUtils.isEmpty(getJson)) {
                USER_STATIC = new Gson().fromJson(getJson, LoginCustomer.class);
                createIntentAbs(MenuActivity.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class LoginTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<LoginCustomer> apiResponse;
        private String login;
        private String senha;
        private DialogLoading dialogLoading;



        public LoginTask(String login, String senha) {
            dialogLoading = new DialogLoading(LoginActivity.this);
            this.login = login;
            this.senha = senha;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoading.init();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            LoginCustomerControl loginTercomControl = new LoginCustomerControl(LoginActivity.this);
            apiResponse = loginTercomControl.login(login,senha);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(LoginActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,"Bem vindo(a), "+ apiResponse.getResult().getCustomerEmployee().getName());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        USER_STATIC = apiResponse.getResult();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(STRING_LOGIN, new Gson().toJson(USER_STATIC));
                        editor.apply();
                        createIntentAbs(MenuActivity.class);
                    }
                });

            }else {
                dialogConfirm.init(EnumDialogOptions.FAIL, apiResponse.getMessage());
            }
        }
    }

}
