package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.ContactAdapter;
import br.com.tercom.Adapter.ServiceAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ServiceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Services;
import br.com.tercom.Entity.ServicesList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.Util.toast;

public class ServiceListActivity extends AbstractAppCompatActivity {

    private SearchServiceTask searchServiceTask;

    @BindView(R.id.txtSearchService)
    EditText editSearch;
    @BindView(R.id.rv_services)
    RecyclerView rv_services;

    @OnClick(R.id.btn_add_product) void add(){
        createIntentAbs(ServiceAddActivity.class);
    }


    EditText.OnEditorActionListener ON_EDITOR_CLICK = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                if(!textView.getText().toString().trim().isEmpty())
                    initServiceTask(textView.getText().toString());
                else
                    toast(ServiceListActivity.this,"Digite algum valor antes de buscar!");
            }
            return false;
        }
    };

    private void createList(final ArrayList<Services> list) {
        ServiceAdapter adapter = new ServiceAdapter(this,list);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ServiceListActivity.this,ServiceDetailsActivity.class);
                intent.putExtra("selectedService",new Gson().toJson(list.get(position)));
                startActivity(intent);
            }
        });
        rv_services.setAdapter(adapter);
        rv_services.setLayoutManager(llmanager);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        ButterKnife.bind(this);
        createToolbarWithNavigation(3);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        editSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editSearch.setOnEditorActionListener(ON_EDITOR_CLICK);
    }


    private void initServiceTask(String value) {
        if(searchServiceTask == null || searchServiceTask.getStatus() != AsyncTask.Status.RUNNING){
            searchServiceTask = new SearchServiceTask(value);
            searchServiceTask.execute();
        }

    }


    private class SearchServiceTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ServicesList> apiResponse;
        private String name;
        private DialogLoading dialogLoading;


        public SearchServiceTask(String name) {
            dialogLoading = new DialogLoading(ServiceListActivity.this);
            this.name = name;
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
            ServiceControl serviceControl = new ServiceControl(ServiceListActivity.this);
            apiResponse = serviceControl.search(name, EnumREST.NAME);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if (apiResponse.getStatusBoolean()) {
                createList(apiResponse.getResult().getList());
            }else{
                DialogConfirm dialogConfirm = new DialogConfirm(ServiceListActivity.this);
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }



}
