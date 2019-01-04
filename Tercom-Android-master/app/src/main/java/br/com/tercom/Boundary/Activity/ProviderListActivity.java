package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.google.gson.Gson;

import br.com.tercom.Adapter.ProviderAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProviderControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProviderList;
import br.com.tercom.Entity.Providers;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderListActivity extends AbstractAppCompatActivity {

    private ProviderTask providerTask;

    @BindView(R.id.rv_providers)
    RecyclerView rv_providers;

    @OnClick(R.id.fabAddProvider) void sendToAddProvider(){
        createIntentAbs(ProviderAddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list_boundary);
        ButterKnife.bind(this);
        createToolbarWithNavigation(4);
        initProviderTask(1);
    }


    private void initProviderTask(int page){
        if(providerTask == null || providerTask.getStatus() == AsyncTask.Status.RUNNING){
            providerTask = new ProviderTask(page);
            providerTask.execute();
        }
    }


    private void createListProviders(final Providers result) {
        ProviderAdapter providerAdapter = new ProviderAdapter(this,result.getProviders().getList());
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        providerAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ProviderListActivity.this, ProviderDetails.class);
                intent.putExtra("details", new Gson().toJson(result.getProviders().getList().get(position)));
                startActivity(intent);

            }
        });
        rv_providers.setLayoutManager(llmanager);
        rv_providers.setAdapter(providerAdapter);
    }

    private class ProviderTask extends AsyncTask<Void, Void, Void>
    {

        private ApiResponse<Providers> apiResponse;
        private int page;
        private DialogLoading dialogLoading;



        public ProviderTask(int page){
            dialogLoading = new DialogLoading(ProviderListActivity.this);

            this.page = page;
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
            ProviderControl providerControl = new ProviderControl(ProviderListActivity.this);
            apiResponse = providerControl.getProviderList(page);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                createListProviders(apiResponse.getResult());
            }
        }
    }

}
