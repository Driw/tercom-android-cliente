package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.TagAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ServiceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Services;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.GsonUtil.getItem;
import static br.com.tercom.Util.Util.toast;

public class ServiceDetailsActivity extends AbstractAppCompatActivity {

    private ArrayList<String> tags;
    private TagAdapter tagAdapter;
    private UpdateServiceTask updateServiceTask;
    private Services selectedService;
    private boolean isEnable;

    @BindView(R.id.rv_tag)
    RecyclerView rvTag;
    @BindView(R.id.editTag)
    EditText editTag;
    @BindView(R.id.editNameService)
    EditText editNameService;
    @BindView(R.id.editDescriptionService)
    EditText editDescriptionService;
    @BindView(R.id.btnAddService)
    Button btnUpdate;
    @BindView(R.id.btnAddTag)
    Button btnAddTag;

    @OnClick(R.id.btnAddTag) void addTag(){
        hideKeyboard();
        if(!editTag.getText().toString().trim().isEmpty()){
            tags.add(editTag.getText().toString());
            tagAdapter.notifyDataSetChanged();
            editTag.setText("", TextView.BufferType.EDITABLE);
            toast(ServiceDetailsActivity.this,String.format(Locale.US,"%s adicionado com sucesso.",editTag.getText().toString()));
        }else{
            toast(ServiceDetailsActivity.this,"Digite algum valor antes de inserir uma tag.");
        }
    }

    @OnClick(R.id.btnAddService) void updateService(){
        if(isEnable) {
            if (verifyEmptyFields().first) {
                initUpdateTask(editNameService.getText().toString(), editDescriptionService.getText().toString(), tags);
            } else {
                toast(ServiceDetailsActivity.this, verifyEmptyFields().second);
            }
        }else{
            setEnable(true);
        }
    }

    @OnClick(R.id.btn_option) void goToPrices(){

        Intent intent = new Intent();
        intent.setClass(this,ServicePriceListAcitivity.class);
        intent.putExtra("idService",selectedService.getId());
        startActivity(intent);

    }

    private CustomPair<String> verifyEmptyFields() {
        CustomPair<String> verified = new CustomPair<>(true,"ok");
        if(TextUtils.isEmpty(editNameService.getText().toString()))
            verified = new CustomPair<>(false,"Nome inválido");
        if(TextUtils.isEmpty(editDescriptionService.getText().toString()))
            verified = new CustomPair<>(false,"Descrição inválida");
        return verified;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        createToolbar();
        configureActivity();
        initList();
    }

    private void configureActivity() {
        try {
            selectedService = getItem(getIntent().getExtras().getString("selectedService"), Services.class);
        }catch (Exception e){
            e.printStackTrace();
            toast(this, "Não foi possível ver os detalhes deste serviço.");
            finish();
        }
        btnUpdate.setText("Atualizar Serviço");
        tags = new ArrayList<>();
        setEnable(false);
        populate();

    }

    private void populate(){
        editNameService.setText(selectedService.getName());
        editDescriptionService.setText(selectedService.getDescription());

    }

    private void setEnable(boolean enable){
        isEnable = enable;
        editNameService.setEnabled(enable);
        editDescriptionService.setEnabled(enable);
        rvTag.setClickable(enable);
        editTag.setVisibility(enable? View.VISIBLE : View.GONE);
        btnAddTag.setVisibility(enable? View.VISIBLE : View.GONE);

    }


    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initList() {
        tags.addAll(selectedService.getTags().getList());
        tagAdapter = new TagAdapter(this,tags);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tagAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDetailsActivity.this);
                builder.setTitle("Excluir tag");
                builder.setMessage(String.format(Locale.US,"Deseja realmente exluir a tag '%s'?", tags.get(position)));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        tags.remove(tags.get(position));
                        tagAdapter.notifyDataSetChanged();
                        arg0.dismiss();
                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
                builder.show();
            }
        });
        rvTag.setLayoutManager(llmanager);
        rvTag.setAdapter(tagAdapter);
    }

    private void initUpdateTask(String name, String description, ArrayList<String> tags){
        if(updateServiceTask == null || updateServiceTask.getStatus() == AsyncTask.Status.RUNNING){
            updateServiceTask = new UpdateServiceTask(tags,name,description, selectedService.getId());
            updateServiceTask.execute();
        }
    }

    private class UpdateServiceTask extends AsyncTask<Void,Void,Void>{

        private ArrayList<String> tag;
        private String name;
        private String description;
        private ApiResponse<Services> apiResponse;
        private int idService;
        private DialogLoading dialogLoading;


        public UpdateServiceTask(ArrayList<String> tag, String name, String description, int idService) {
            dialogLoading = new DialogLoading(ServiceDetailsActivity.this);
            this.tag = tag;
            this.name = name;
            this.description = description;
            this.idService = idService;
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
            ServiceControl serviceControl = new ServiceControl(ServiceDetailsActivity.this);
            apiResponse = serviceControl.update(idService,name,description,tags);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ServiceDetailsActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirm.dismissD();
                        finish();
                    }
                });
            }
            else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

}
