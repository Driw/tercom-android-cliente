package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
import br.com.tercom.Control.ManufactureControl;
import br.com.tercom.Control.ProductPackageControl;
import br.com.tercom.Control.ProductTypeControl;
import br.com.tercom.Control.ProductValueControl;
import br.com.tercom.Control.ProviderControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ManufactureList;
import br.com.tercom.Entity.PackageList;
import br.com.tercom.Entity.ProductTypeList;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ProductValueSend;
import br.com.tercom.Entity.ProviderList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.IProductValueItem;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import br.com.tercom.Util.GsonUtil;
import br.com.tercom.Util.PriceMask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.PriceMask.unmaskPrice;
import static br.com.tercom.Util.TextUtil.emptyValidator;
import static br.com.tercom.Util.Util.toast;

public class ProductValueDetails extends AbstractAppCompatActivity {
    /**
     * Todas as classes de referencia devem implementar a interface IProductvalueItem para ser usada no adapter.
     * Tem os exemplos de cada coisa que deve ser feita: clique, dialog (genérico para qualquer tipo, só passar a referencia), método de search, taskSave e inicialização da taskSave.
     * Devem ser feitos isso para cada um dos tipos e depois um para o atualizar nessa classe e adicionar na outra.
     */

    private static final int REFERENCE_PROVIDER = 1;
    private static final int REFERENCE_MANUFACTURER = 2;
    private static final int REFERENCE_PACKAGE = 3;
    private static final int REFERENCE_TYPE = 4;

    private ProviderTask providerTask;
    private ManufactureTask manufactureTask;
    private PackageTask packageTask;
    private TypeTask typeTask;
    private ProductValueSend productValue;

    private ProductValue jsonProductValue;
    private DeleteTask deleteTask;
    private TaskSave taskSave;

    private boolean update = false;

    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtAmount)
    EditText txtAmount;
    @BindView(R.id.txtValue)
    EditText txtValue;
    @BindView(R.id.txtProvider)
    TextView txtProvider;
    @BindView(R.id.txtManufacturer)
    TextView txtManufacturer;
    @BindView(R.id.txtPackage)
    TextView txtPackage;
    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.btn_function)
    Button btn_function;
    private RecyclerView rvSearch;
    private EditText editSearch;


    @OnClick(R.id.txtManufacturer) void onClick(){ initDialog(REFERENCE_MANUFACTURER); }
    @OnClick(R.id.txtProvider) void onClickProvider() { initDialog(REFERENCE_PROVIDER); }
    @OnClick(R.id.txtPackage) void onClickPackage() { initDialog(REFERENCE_PACKAGE); }
    @OnClick(R.id.txtType) void onClickType() { initDialog(REFERENCE_TYPE); }


    @OnClick(R.id.btn_function) void click()
    {
        CustomPair<String> result = verifyData();
        if(result.first)
        {
            productValue.setAmount(Integer.parseInt(txtAmount.getText().toString()));
            productValue.setPrice(unmaskPrice(txtValue.getText().toString()));
            productValue.setName(txtName.getText().toString());
            if(taskSave == null || taskSave.getStatus() != AsyncTask.Status.RUNNING) {
                taskSave = new TaskSave(productValue);
                taskSave.execute();
            }
        }
        else
        {
            toast(ProductValueDetails.this,result.second);
        }
    }


    @OnClick(R.id.btn_delete) void delete(){
        if(deleteTask == null || deleteTask.getStatus() != AsyncTask.Status.RUNNING){
            deleteTask = new DeleteTask(jsonProductValue.getId());
            deleteTask.execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_value_generic);
        ButterKnife.bind(this);
        createToolbar();
        PriceMask mask = new PriceMask(txtValue);
        txtValue.addTextChangedListener(mask);
        productValue = new ProductValueSend();
        //TODO: Ao clicar no fab de Adiconar, passar um novo ProductPrice no intent, contendo apenas o ID do ProductPrice
        jsonProductValue = GsonUtil.getItem(getIntent().getExtras().get("productValue").toString(), ProductValue.class);
        //TODO: Converter de ProductValue para ProductValueSend.
        if(jsonProductValue.getId() != 0)
        {
            update = true;
            fillProductValueSend(jsonProductValue);
            fillViewFields();
        }else{
            btn_delete.setVisibility(View.INVISIBLE);
            btn_function.setBackgroundColor(getResources().getColor(R.color.colorGreenLogin));
            btn_function.setText("Adicionar");
        }
        productValue.setId(jsonProductValue.getId());
        productValue.setIdProduct(jsonProductValue.getProduct().getId());
    }

    private void fillProductValueSend(ProductValue jsonProductValue)
    {
        productValue.setAmount(jsonProductValue.getAmount());
        productValue.setIdManufacture(jsonProductValue.getManufacture().getId());
        productValue.setIdProductPackage(jsonProductValue.getPackage().getId());
        productValue.setIdProductType(jsonProductValue.getType().getId());
        productValue.setIdProvider(jsonProductValue.getProvider().getId());
        productValue.setName(jsonProductValue.getName());
        productValue.setPrice(jsonProductValue.getPrice());
    }

    private void fillViewFields()
    {
        txtAmount.setText(String.valueOf(productValue.getAmount()));
        txtName.setText(productValue.getName());
        txtValue.setText(String.valueOf(productValue.getPrice()));
        txtManufacturer.setText("Fabricante: " + jsonProductValue.getManufacture().getName());
        txtPackage.setText("Embalagem: " + jsonProductValue.getPackage().getName());
        txtProvider.setText("Fornecedor: " + jsonProductValue.getProvider().getName());
        txtType.setText("Tipo: " + jsonProductValue.getType().getName());
    }


    private void initDialog(final int typeReference) {
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
                        search(typeReference, editSearch.getText().toString(), dialog);

                    return true;
                }
                return false;
            }
        });
        dialog.show();

    }



    /**
     * para cada uma das tasks deve ter um desse para inicializa-la
     * @param  value receberá o valor que o usuário colocar no editSearch
     */

    private void initProviderTask(String value, Dialog dialog){
        if(providerTask == null || providerTask.getStatus() != AsyncTask.Status.RUNNING){
            providerTask = new ProviderTask(value, dialog);
            providerTask.execute();
        }
    }

    private void initManufacturerTask(String value, Dialog dialog){
        if(manufactureTask == null || manufactureTask.getStatus() != AsyncTask.Status.RUNNING){
            manufactureTask = new ManufactureTask(value, dialog);
            manufactureTask.execute();
        }
    }

    private void initPackageTask(String value, Dialog dialog){
        if(packageTask == null || packageTask.getStatus() != AsyncTask.Status.RUNNING){
            packageTask = new PackageTask(value, dialog);
            packageTask.execute();
        }
    }

    private void initTypeTask(String value, Dialog dialog){
        if(typeTask == null || typeTask.getStatus() != AsyncTask.Status.RUNNING){
            typeTask = new TypeTask(value, dialog);
            typeTask.execute();
        }
    }

    private void setReference(int reference, IProductValueItem value){
        switch (reference){
            case REFERENCE_PROVIDER:
                txtProvider.setText(getStringFormated("Fornecedor",value.getName()));
                productValue.setIdProvider(value.getId());
                break;
            case REFERENCE_MANUFACTURER:
                txtManufacturer.setText(getStringFormated("Fabricante",value.getName()));
                productValue.setIdManufacture(value.getId());
                break;
            case REFERENCE_PACKAGE:
                txtPackage.setText(getStringFormated("Embalagem",value.getName()));
                productValue.setIdProductPackage(value.getId());
                break;
            case REFERENCE_TYPE:
                txtType.setText(getStringFormated("Tipo",value.getName()));
                productValue.setIdProductType(value.getId());
                break;
        }

    }

    private String getStringFormated(String type, String value){
        return String.format(Locale.getDefault(),"%s: %s", type, value);
    }



    /**
     * Baseado na referencia, ele direciona para a taskSave necessária;
     * @param reference tipo da chamada, passará uma das constantes dessa classe.
     */
    private void search(int reference, String value, Dialog dialog) {
        // TODO(aqui devem ir as chamadas de async taskSave reference a cada uma das chamadas feitas no dialog, assim ela retornará a lista que irá no adapter.)
        switch (reference){
            case REFERENCE_PROVIDER:
                initProviderTask(value, dialog);
                break;
            case REFERENCE_MANUFACTURER:
                initManufacturerTask(value, dialog);
                break;
            case REFERENCE_PACKAGE:
                initPackageTask(value, dialog);
                break;
            case REFERENCE_TYPE:
                initTypeTask(value, dialog);
                break;
        }
    }

    /**
     * Cria a lista no dialog que quando clicada, irá adicionar o valor no product value e na view.
     * @param itens itens que preencherão a recyclerView
     * @param reference qual objeto deverá ser setado.
     */
    private void createList(final int reference, final ArrayList<? extends IProductValueItem> itens, final Dialog dialog){
        rvSearch.setAdapter(null);
        ProductValueItensAdapter productValueItensAdapter = new ProductValueItensAdapter(ProductValueDetails.this,itens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        productValueItensAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                setReference(reference, itens.get(position));
                dialog.cancel();
            }
        });
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(productValueItensAdapter);

    }

    /**
     * Task que fará download da lista do item desejado e setará no dialog através do método createList()
     */

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
            apiResponse = new ProviderControl(ProductValueDetails.this).search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(apiResponse.getStatusBoolean())
                createList(REFERENCE_PROVIDER, apiResponse.getResult().getList(), dialog);
            else
                toast(ProductValueDetails.this, "Nenhum item encontrado");
        }
    }

    private class ManufactureTask extends AsyncTask<Void,Void,Void>{
        private ApiResponse<ManufactureList> apiResponse;
        private String value;
        final private Dialog dialog;

        public ManufactureTask(String value, Dialog dialog) {
            this.value = value;
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ManufactureControl manufactureControl = new ManufactureControl(ProductValueDetails.this);
            apiResponse = manufactureControl.search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createList(REFERENCE_MANUFACTURER,apiResponse.getResult().getList(), dialog);
            }else{
                toast(ProductValueDetails.this,"Nenhum item encontrado.");
            }
        }

    }

    private class PackageTask extends AsyncTask<Void, Void, Void>
    {
        //TODO Colocar tipo certo de Package
        private ApiResponse<PackageList> apiResponse;
        private String value;
        final private Dialog dialog;

        public PackageTask(String value, Dialog dialog)
        {
            this.value = value;
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            apiResponse = new ProductPackageControl(ProductValueDetails.this).search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createList(REFERENCE_PACKAGE,apiResponse.getResult().getList(), dialog);
            }else{
                toast(ProductValueDetails.this,"Nenhum item encontrado.");
            }
        }
    }

    private class TypeTask extends AsyncTask<Void, Void, Void>
    {
        private ApiResponse<ProductTypeList> apiResponse;
        private String value;
        final private Dialog dialog;

        public TypeTask(String value, Dialog dialog)
        {
            this.value = value;
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            //TODO Fazer ProductTypeControl
            apiResponse = new ProductTypeControl(ProductValueDetails.this).search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createList(REFERENCE_TYPE,apiResponse.getResult().getList(), dialog);
            }else{
                toast(ProductValueDetails.this,"Nenhum item encontrado.");
            }
        }
    }


    private CustomPair<String> verifyData()
    {
        if(!emptyValidator(String.valueOf(productValue.getId())))
            return new CustomPair<>(false, "Produto inválido");
        if(!emptyValidator(String.valueOf(productValue.getIdProvider())))
            return new CustomPair<>(false, "Fornecedor inválido");
        if(!emptyValidator(String.valueOf(productValue.getIdProductPackage())))
            return new CustomPair<>(false, "Embalagem inválida");
        if(!emptyValidator(String.valueOf(productValue.getIdProductType())))
            return new CustomPair<>(false, "Tipo de Produto inválido");
        if(!emptyValidator(txtName.getText().toString()))
            return new CustomPair<>(false, "Nome do Produto inválido");
        if(!emptyValidator(txtAmount.getText().toString()))
            return new CustomPair<>(false, "Quantidade inválida");
        if(!emptyValidator(txtValue.getText().toString()))
            return new CustomPair<>(false, "Preço inválido");
        return new CustomPair<>(true,"Ok");
    }

    private class TaskSave extends AsyncTask<Void, Void, Void>
    {

        private ApiResponse<ProductValue> apiResponse;
        private ProductValueSend productValue;
        private DialogLoading dialogLoading;


        public TaskSave(ProductValueSend productValue)
        {
            dialogLoading = new DialogLoading(ProductValueDetails.this);
            this.productValue = productValue;
            control = new ProductValueControl(ProductValueDetails.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoading.init();
        }

        private ProductValueControl control;
        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            if(update)
                apiResponse = control.update(productValue.getId(), productValue.getIdProvider(), productValue.getIdManufacture(), productValue.getIdProductPackage(), productValue.getIdProductType(),
                        productValue.getName(), productValue.getAmount(), productValue.getPrice());
            else
                apiResponse = control.add(productValue.getIdProduct(), productValue.getId(), productValue.getIdProvider(), productValue.getIdProductPackage(), productValue.getIdProductType(), productValue.getAmount(),
                        productValue.getPrice(), productValue.getName(), productValue.getIdManufacture());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ProductValueDetails.this);
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


    private class DeleteTask extends AsyncTask<Void,Void,Void> {

        private ApiResponse<ProductValue> apiResponse;
        private int id;
        private DialogLoading dialogLoading;


        public DeleteTask(int id) {
            dialogLoading = new DialogLoading(ProductValueDetails.this);
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
            ProductValueControl productValueControl = new ProductValueControl(ProductValueDetails.this);
            apiResponse = productValueControl.remove(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            DialogConfirm dialogConfirm = new DialogConfirm(ProductValueDetails.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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