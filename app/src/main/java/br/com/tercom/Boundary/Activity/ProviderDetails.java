package br.com.tercom.Boundary.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.com.tercom.Adapter.ContactAdapter;
import br.com.tercom.Boundary.BoundaryUtil.Mask;
import br.com.tercom.Control.ProviderControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.ProviderContact;
import br.com.tercom.Entity.ProviderContactList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Enum.EnumFont;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.CustomTypeFace.overrideFonts;
import static br.com.tercom.Util.GsonUtil.getItem;
import static br.com.tercom.Util.TextUtil.emptyValidator;
import static br.com.tercom.Util.Util.toast;

public class ProviderDetails extends AppCompatActivity {

    private Provider selectedProvider;
    private boolean isEnable;
    private UpdateProviderTask updateProviderTask;
    private ContactsTask contactsTask;
    private ProviderControl providerControl;

    @BindView(R.id.txtCnpj)
    EditText txtCnpj ;

    @BindView(R.id.txtCompanyName)
    EditText txtCompanyName;

    @BindView(R.id.txtFantasyName)
    EditText txtFantasyName ;

    @BindView(R.id.txtSpokesMan)
    EditText txtSpokesMan;

    @BindView(R.id.txtSite)
    EditText txtSite;

    @BindView(R.id.rv_contacts)
    RecyclerView rv_contacts;

    @OnClick(R.id.btn_update) void update(){
        if(isEnable){
            CustomPair<String> result = verifyData(txtCompanyName.getText().toString(),txtFantasyName.getText().toString(),
                    Mask.unmask(txtCnpj.getText().toString()),txtSite.getText().toString(),txtSpokesMan.getText().toString());
            if(result.first){
                initAddTask(txtCompanyName.getText().toString(),txtFantasyName.getText().toString(),Mask.unmask(txtCnpj.getText().toString()),
                        txtSite.getText().toString(),txtSpokesMan.getText().toString(),selectedProvider.getId());
                setEnable(false);
            }else{
                toast(ProviderDetails.this,result.second);
            }
        }else{
            setEnable(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        overrideFonts(this,getWindow().getDecorView().getRootView(),EnumFont.FONT_ROBOTO_REGULAR);
        selectedProvider = getItem(getIntent().getExtras().getString("details"),Provider.class);
        populate(selectedProvider);
        initContactTask(selectedProvider.getId());
    }

    private void populate(Provider selectedProvider) {
        txtCnpj.addTextChangedListener(Mask.insert("##.###.###/####-##",txtCnpj));
        txtCnpj.setText(selectedProvider.getCnpj(), TextView.BufferType.EDITABLE);
        txtCompanyName.setText(selectedProvider.getCompanyName(), TextView.BufferType.EDITABLE);
        txtFantasyName.setText(selectedProvider.getFantasyName(), TextView.BufferType.EDITABLE);
        txtSpokesMan.setText(selectedProvider.getSpokesman(), TextView.BufferType.EDITABLE);
        txtSite.setText(selectedProvider.getSite(), TextView.BufferType.EDITABLE);
        setEnable(false);
    }

    private void setEnable(boolean enable){
        isEnable = enable;
        txtCnpj.setEnabled(enable);
        txtCompanyName.setEnabled(enable);
        txtFantasyName.setEnabled(enable);
        txtSpokesMan.setEnabled(enable);
        txtSite.setEnabled(enable);
    }


    private CustomPair<String> verifyData(String companyName, String fantasyName, String cnpj, String site,String spokesman){

        if(!emptyValidator(companyName))
            return new CustomPair<>(false,"Razão Social inválida");

        if(!emptyValidator(fantasyName))
            return new CustomPair<>(false,"Nome Fantasia Inválido");

        if(!emptyValidator(cnpj))
            return new CustomPair<>(false,"CNPJ Inválido");

        if(!emptyValidator(site))
            return new CustomPair<>(false,"Site inválido");

        if(!emptyValidator(spokesman))
            return new CustomPair<>(false,"Nome do responsável inválido");

        return new CustomPair<>(true,"Ok");
    }


    private void createListContacts(final ArrayList<ProviderContact> list) {
        ContactAdapter contactAdapter = new ContactAdapter(this,list);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
               Intent intent = new Intent();
               intent.setClass(ProviderDetails.this, ProviderContactUpdateActivity.class);
               intent.putExtra("details",new Gson().toJson(list.get(position)));
               intent.putExtra("idProvider", selectedProvider.getId());
               startActivity(intent);
            }
        });
        rv_contacts.setLayoutManager(llmanager);
        rv_contacts.setAdapter(contactAdapter);
    }

    private void initAddTask(String companyName, String fantasyName, String cnpj, String site, String spokesman, int idProvider){
        if(updateProviderTask == null || updateProviderTask.getStatus() != AsyncTask.Status.RUNNING){
            updateProviderTask = new UpdateProviderTask(companyName,fantasyName,cnpj,site,spokesman, idProvider);
            updateProviderTask.execute();
        }
    }


    private void initContactTask(int id){
        if(contactsTask == null || contactsTask.getStatus() != AsyncTask.Status.RUNNING){
            contactsTask = new ContactsTask(id);
            contactsTask.execute();
        }
    }


    private class UpdateProviderTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<Provider> apiResponse;
        private String companyName;
        private String fantasyName;
        private String cnpj;
        private String site;
        private String spokesman;
        private int idProvider;

        public UpdateProviderTask(String companyName, String fantasyName, String cnpj, String site, String spokesman, int idProvider){

            this.companyName = companyName;
            this.fantasyName = fantasyName;
            this.cnpj = cnpj;
            this.site = site;
            this.spokesman = spokesman;
            this.idProvider = idProvider;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            providerControl = new ProviderControl(ProviderDetails.this);
            apiResponse = providerControl.updateProvider(companyName,fantasyName,cnpj,site,spokesman,idProvider);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DialogConfirm dialogConfirm = new DialogConfirm(ProviderDetails.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

    private class ContactsTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProviderContactList> apiResponse;
        private int id;
        private DialogLoading dialogLoading;


        public ContactsTask(int id) {
            dialogLoading = new DialogLoading(ProviderDetails.this);
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoading.init();
        }


        @Override
        protected Void doInBackground(Void... voids){
            if(Looper.myLooper() == null)
                Looper.prepare();
            providerControl = new ProviderControl(ProviderDetails.this);
            apiResponse = providerControl.getContacts(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                createListContacts(apiResponse.getResult().getList());
            }
        }
    }



}
