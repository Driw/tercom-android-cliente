package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.tercom.Adapter.ManufacturerAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ManufactureControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ManufactureList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.Util.toast;

public class ManufacturerActivity extends AbstractAppCompatActivity {

    private ManufacturerTask manufacturerTask;
    private AddManufacturerTask addManufacturerTask;
    private UpdateManufacturerTask updateManufacturerTask;

    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.rv_manufacturer)
    RecyclerView rvManufacturer;


    @OnClick(R.id.btn_add_manufacturer) void add(){
        openDialog(EnumActionManufacturer.ADD);
    }

    EditText.OnEditorActionListener ON_EDITOR_CLICK = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                if(!textView.getText().toString().trim().isEmpty())
                    initManufacturerTask(textView.getText().toString());
                else
                    toast(ManufacturerActivity.this,"Digite algum valor antes de buscar!");
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer);
        ButterKnife.bind(this);
        createToolbarWithNavigation(5);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        txtSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txtSearch.setOnEditorActionListener(ON_EDITOR_CLICK);
    }


    private void openDialog(EnumActionManufacturer actionManufacturer){
        openDialog(actionManufacturer, null,null);
    }

    private void openDialog(final EnumActionManufacturer action, @Nullable  String valueToUpdate,@Nullable final Integer id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add_category);
        TextView txtTitle =  dialog.findViewById(R.id.txtTitle);
        final EditText editName =  dialog.findViewById(R.id.editName);
        Button btnAddCategory =  dialog.findViewById(R.id.btnAddCategory);
        if(valueToUpdate != null)
            editName.setText(valueToUpdate, TextView.BufferType.EDITABLE);
        txtTitle.setText(getTitleDialog(action));
        btnAddCategory.setText(getTextButton(action));
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editName.getText().toString().trim().isEmpty()) {
                    String value = editName.getText().toString();
                    if(action == EnumActionManufacturer.ADD){
                        initAddManufacturerTask(value);
                        dialog.dismiss();
                    }else{
                        initUpdateManufacturerTask(id,value);
                        dialog.dismiss();
                    }

                }
            }
        });
        dialog.show();

    }

    private String getTitleDialog(EnumActionManufacturer action) {
        return action == EnumActionManufacturer.ADD ? "Adicionar Fabricante" : "Atualizar Fabricante";
    }
    private String getTextButton(EnumActionManufacturer action) {
        return action == EnumActionManufacturer.ADD ? "Adicionar" : "Atualizar";
    }



    private void createListProviders(final ManufactureList result) {
        ManufacturerAdapter manufacturerAdapter = new ManufacturerAdapter(this,result.getList());
        LinearLayoutManager llmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manufacturerAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                openDialog(EnumActionManufacturer.UPDATE, result.getList().get(position).getFantasyName(), result.getList().get(position).getId());
            }
        });
        rvManufacturer.setLayoutManager(llmanager);
        rvManufacturer.setAdapter(manufacturerAdapter);
    }



    private void initManufacturerTask(String value){
        if(manufacturerTask == null || manufacturerTask.getStatus() != AsyncTask.Status.RUNNING){
            manufacturerTask = new ManufacturerTask(value);
            manufacturerTask.execute();
        }
    }

    private void initAddManufacturerTask(String value){
        if(addManufacturerTask == null || addManufacturerTask.getStatus() != AsyncTask.Status.RUNNING){
            addManufacturerTask = new AddManufacturerTask(value);
            addManufacturerTask.execute();
        }
    }

    private void initUpdateManufacturerTask(int id,String value){
        if(updateManufacturerTask == null || updateManufacturerTask.getStatus() != AsyncTask.Status.RUNNING){
            updateManufacturerTask = new UpdateManufacturerTask(value, id);
            updateManufacturerTask.execute();
        }
    }

    private enum EnumActionManufacturer{
        ADD,
        UPDATE
    }


    private class ManufacturerTask extends AsyncTask<Void,Void,Void> {

        private ApiResponse<ManufactureList> apiResponse;
        private String value;
        private DialogLoading dialogLoading;

        public ManufacturerTask(String value) {
            dialogLoading = new DialogLoading(ManufacturerActivity.this);
            this.value = value;
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
            ManufactureControl manufactureControl = new ManufactureControl(ManufacturerActivity.this);
            apiResponse = manufactureControl.search(value);
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

    private class AddManufacturerTask extends AsyncTask<Void,Void,Void> {

        private ApiResponse<ManufactureList> apiResponse;
        private String value;
        private DialogLoading dialogLoading;

        public AddManufacturerTask(String value) {
            dialogLoading = new DialogLoading(ManufacturerActivity.this);
            this.value = value;
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
            ManufactureControl manufactureControl = new ManufactureControl(ManufacturerActivity.this);
            apiResponse = manufactureControl.add(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ManufacturerActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

    private class UpdateManufacturerTask extends AsyncTask<Void,Void,Void> {

        private ApiResponse<ManufactureList> apiResponse;
        private String value;
        private int id;
        private DialogLoading dialogLoading;

        public UpdateManufacturerTask(String value, int id) {
            dialogLoading = new DialogLoading(ManufacturerActivity.this);
            this.value = value;
            this.id = id;
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
            ManufactureControl manufactureControl = new ManufactureControl(ManufacturerActivity.this);
            apiResponse = manufactureControl.update(id,value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ManufacturerActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }



}
