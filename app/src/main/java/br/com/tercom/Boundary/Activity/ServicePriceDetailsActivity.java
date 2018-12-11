package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.ProductValueItensAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProviderControl;
import br.com.tercom.Control.ServicePriceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProviderList;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.IProductValueItem;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.GsonUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.PriceMask.unmaskPrice;
import static br.com.tercom.Util.TextUtil.emptyValidator;
import static br.com.tercom.Util.Util.toast;

public class ServicePriceDetailsActivity extends AbstractAppCompatActivity {

    public static final int ACTION_ADD = 1;
    public static final int ACTION_UPDATE = 2;

    private IProductValueItem selectedProvider;
    private AddPriceTask addPriceTask;
    private UpdatePriceTask updatePriceTask;
    private int idService;
    private ServicePrice servicePrice;
    private int action;
    private boolean enable;
    private RecyclerView rvSearch;
    private EditText editSearch;
    private ProviderTask providerTask;
    private DeleteTask deleteTask;

    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editPrice)
    EditText editPrice;
    @BindView(R.id.editObs)
    EditText editObs;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnProvider)
    Button btnProvider;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    @OnClick(R.id.btn_delete) void delete(){
        initDeleteTask();
    }

    private void initDeleteTask() {
        if(deleteTask ==null || deleteTask.getStatus() != AsyncTask.Status.RUNNING){
            deleteTask = new DeleteTask(idService);
            deleteTask.execute();
        }
    }

    @OnClick(R.id.btnProvider) void getProvider(){
        initDialog();
    }

    @OnClick(R.id.btnAdd)
    void action() {
        CustomPair<String> result = verifyData(editName.getText().toString(), editPrice.getText().toString(), editObs.getText().toString(),  selectedProvider != null? selectedProvider.getId() : 0);
        switch (action) {

            case ACTION_ADD:
                if (result.first) {
                    initAddTask(editName.getText().toString(), (float) unmaskPrice(editPrice.getText().toString()), editObs.getText().toString(), selectedProvider.getId());
                } else {
                    toast(ServicePriceDetailsActivity.this, result.second);
                }
                break;

            case ACTION_UPDATE:
                if (!enable) {
                    setEnable(true);
                } else {
                    if (result.first) {
                        initUpdateTask(editName.getText().toString(), (float) unmaskPrice(editPrice.getText().toString()), editObs.getText().toString(), selectedProvider.getId());
                    } else {
                        toast(ServicePriceDetailsActivity.this, result.second);
                    }
                }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_price);
        ButterKnife.bind(this);
        action = getIntent().getExtras().getInt("action");
        idService = getIntent().getExtras().getInt("idService");

        if (action == ACTION_UPDATE)
            configureUpdate();

        createToolbar();
    }

    private void configureUpdate() {
        btnAdd.setText("Atualizar");
        servicePrice = GsonUtil.getItem(getIntent().getExtras().getString("price"), ServicePrice.class);
        populate(servicePrice);
        setEnable(enable);
    }

    private void populate(ServicePrice servicePrice) {
        editName.setText(servicePrice.getName());
        editPrice.setText(String.valueOf(servicePrice.getPrice()));
        editObs.setText(servicePrice.getAdditionalDescription());
        btnProvider.setText(servicePrice.getProvider().getFantasyName());
    }

    private void setEnable(boolean enable) {
        this.enable = enable;
        editName.setEnabled(enable);
        editPrice.setEnabled(enable);
        editObs.setEnabled(enable);
        btnProvider.setEnabled(enable);

    }


    private CustomPair<String> verifyData(String name, String price, String additionalDescription, int id) {

        if (!emptyValidator(name))
            return new CustomPair<>(false, "Nome inválido");

        if (!emptyValidator(price))
            return new CustomPair<>(false, "Preço Inválido");

        if (id == 0)
            return new CustomPair<>(false, "Fornecedor inválido");

        return new CustomPair<>(true, "Ok");
    }

    private void initAddTask(String name, float price, String additionalDescription, int id) {
        if (addPriceTask == null || addPriceTask.getStatus() != AsyncTask.Status.RUNNING) {
            addPriceTask = new AddPriceTask(idService, name, additionalDescription, price, id);
            addPriceTask.execute();
        }
    }

    private void initUpdateTask(String name, float price, String additionalDescription, int id) {
        if (updatePriceTask == null || updatePriceTask.getStatus() != AsyncTask.Status.RUNNING) {
            updatePriceTask = new UpdatePriceTask(idService, name, additionalDescription, price, id);
            updatePriceTask.execute();
        }
    }


    private void initDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_search_info_price);
        rvSearch = dialog.findViewById(R.id.rv_search);
        editSearch = dialog.findViewById(R.id.editSearch);
        editSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == 0){//EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(editSearch.getText().toString()))
                        initProviderTask(editSearch.getText().toString(), dialog);

                    return true;
                }
                return false;
            }
        });
        dialog.show();

    }


    private void initProviderTask(String value, Dialog dialog){
        if(providerTask == null || providerTask.getStatus() != AsyncTask.Status.RUNNING){
            providerTask = new ProviderTask(value, dialog);
            providerTask.execute();
        }
    }

    private void createList( final ArrayList<? extends IProductValueItem> itens, final Dialog dialog){
        rvSearch.setAdapter(null);
        ProductValueItensAdapter productValueItensAdapter = new ProductValueItensAdapter(ServicePriceDetailsActivity.this,itens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        productValueItensAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                btnProvider.setText(getStringFormated("Fornecedor",itens.get(position).getName()));
                selectedProvider = itens.get(position);
                dialog.cancel();
            }
        });
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(productValueItensAdapter);

    }

    private String getStringFormated(String type, String value){
        return String.format(Locale.getDefault(),"%s: %s", type, value);
    }



    private class AddPriceTask extends AsyncTask<Void, Void, Void> {

        private ApiResponse<ServicePrice> apiResponse;
        int idService;
        private String name;
        private String observations;
        private float price;
        private int idProvider;


        public AddPriceTask(int idService, String name, String observations, float price, int idProvider) {
            this.name = name;
            this.observations = observations;
            this.price = price;
            this.idProvider = idProvider;
            this.idService = idService;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (Looper.myLooper() == null)
                Looper.prepare();
            ServicePriceControl servicePriceControl = new ServicePriceControl(ServicePriceDetailsActivity.this);
            apiResponse = servicePriceControl.add(idService, idProvider, price, name, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DialogConfirm dialogConfirm = new DialogConfirm(ServicePriceDetailsActivity.this);
            if (apiResponse.getStatusBoolean()) {
                dialogConfirm.init(EnumDialogOptions.CONFIRM, apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                dialogConfirm.init(EnumDialogOptions.FAIL, apiResponse.getMessage());
            }
        }
    }

    private class UpdatePriceTask extends AsyncTask<Void, Void, Void> {

        private ApiResponse<ServicePrice> apiResponse;
        int idService;
        private String name;
        private String observations;
        private float price;
        private int idProvider;

        public UpdatePriceTask(int idService, String name, String observations, float price, int idProvider) {
            this.idService = idService;
            this.name = name;
            this.observations = observations;
            this.price = price;
            this.idProvider = idProvider;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (Looper.myLooper() == null)
                Looper.prepare();
            ServicePriceControl servicePriceControl = new ServicePriceControl(ServicePriceDetailsActivity.this);
            apiResponse = servicePriceControl.set(servicePrice.getId(), name,price, observations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final DialogConfirm dialogConfirm = new DialogConfirm(ServicePriceDetailsActivity.this);
            if (apiResponse.getStatusBoolean()) {
                dialogConfirm.init(EnumDialogOptions.CONFIRM, apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirm.dismissD();
                        setEnable(false);
                    }
                });
            } else {
                dialogConfirm.init(EnumDialogOptions.FAIL, apiResponse.getMessage());
            }
        }
    }

    private class DeleteTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ServicePrice> apiResponse;
        private int idService;

        public DeleteTask(int idService) {
            this.idService = idService;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ServicePriceControl servicePriceControl = new ServicePriceControl(ServicePriceDetailsActivity.this);
            apiResponse = servicePriceControl.remove(idService);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DialogConfirm dialogConfirm = new DialogConfirm(ServicePriceDetailsActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

    private class ProviderTask extends AsyncTask<Void, Void, Void>
    {
        private ApiResponse<ProviderList> apiResponse;
        private String value;
        final private Dialog dialog;

        public ProviderTask(String value, Dialog dialog)
        {
            this.value = value;
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            apiResponse = new ProviderControl(ServicePriceDetailsActivity.this).search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(apiResponse.getStatusBoolean())
                createList(apiResponse.getResult().getList(), dialog);
            else
                toast(ServicePriceDetailsActivity.this, "Nenhum item encontrado");
        }
    }




}
