package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.ManufacturerAdapter;
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
import br.com.tercom.Util.TextUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.Util.toast;

public class ServiceAddActivity extends AbstractAppCompatActivity {

    private ArrayList<String> tags;
    private TagAdapter tagAdapter;
    private AddServiceTask addServiceTask;

    @BindView(R.id.rv_tag)
    RecyclerView rvTag;
    @BindView(R.id.editTag)
    EditText editTag;
    @BindView(R.id.editNameService)
    EditText editNameService;
    @BindView(R.id.editDescriptionService)
    EditText editDescriptionService;

    @OnClick(R.id.btnAddTag) void addTag(){
            hideKeyboard();
            if(!editTag.getText().toString().trim().isEmpty()){
                tags.add(editTag.getText().toString());
                tagAdapter.notifyDataSetChanged();
                editTag.setText("", TextView.BufferType.EDITABLE);
                toast(ServiceAddActivity.this,String.format(Locale.US,"%s adicionado com sucesso.",editTag.getText().toString()));
            }else{
                toast(ServiceAddActivity.this,"Digite algum valor antes de inserir uma tag.");
            }
    }

    @OnClick(R.id.btnAddService) void addServices(){
        if(verifyEmptyFields().first){
            initAddTask(editNameService.getText().toString(),editDescriptionService.getText().toString(),tags);
        }else{
            toast(ServiceAddActivity.this,verifyEmptyFields().second);
        }
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
        tags = new ArrayList<>();
        initList();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initList() {
        tagAdapter = new TagAdapter(this,tags);
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tagAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceAddActivity.this);
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

    private void initAddTask(String name, String description, ArrayList<String> tags){
        if(addServiceTask == null || addServiceTask.getStatus() == AsyncTask.Status.RUNNING){
            addServiceTask = new AddServiceTask(tags,name,description);
            addServiceTask.execute();
        }
    }

    private class AddServiceTask extends AsyncTask<Void,Void,Void>{

        private ArrayList<String> tag;
        private String name;
        private String description;
        private ApiResponse<Services> apiResponse;
        private DialogLoading dialogLoading;


        public AddServiceTask(ArrayList<String> tag, String name, String description) {
            dialogLoading = new DialogLoading(ServiceAddActivity.this);
            this.tag = tag;
            this.name = name;
            this.description = description;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ServiceControl serviceControl = new ServiceControl(ServiceAddActivity.this);
            apiResponse = serviceControl.add(name,description,tags);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoading.init();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final DialogConfirm dialogConfirm = new DialogConfirm(ServiceAddActivity.this);
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
                dialogLoading.dismissD();
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

}
