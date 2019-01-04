package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import java.util.ArrayList;

import br.com.tercom.Adapter.CategoryAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProductControl;
import br.com.tercom.Control.ProductFamilyControl;
import br.com.tercom.Control.ProductGroupControl;
import br.com.tercom.Control.ProductSectorControl;
import br.com.tercom.Control.ProductSubGroupControl;
import br.com.tercom.Control.ProductUnitControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductFamily;
import br.com.tercom.Entity.ProductFamilyList;
import br.com.tercom.Entity.ProductGroup;
import br.com.tercom.Entity.ProductSector;
import br.com.tercom.Entity.ProductSend;
import br.com.tercom.Entity.ProductSubGroup;
import br.com.tercom.Entity.ProductUnitList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.IProductCategory;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.GsonUtil.getItem;
import static br.com.tercom.Util.Util.toast;

public class ProductCategoryAddActivity extends AbstractAppCompatActivity {

    private int step = 0 ;
    private UnitTask unitTask;
    private FamilyTask familyTask;
    private GroupTask groupTask;
    private SubGroupTask subGroupTask;
    private SectorTask sectorTask;
    private ProductTask productTask;
    private AddFamilyTask addFamilyTask;
    private AddGroupTask addGroupTask;
    private AddSubGroupTask addSubGroupTask;
    private AddSectorTask addSectorTask;
    private ProductSend product;
    private String[] categories = {"Unidade","Familia","Grupo","Subgrupo","Setor","Adicionar Produto"};

    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.cardFilter)
    CardView cardFilter;
    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.rv_category)
    RecyclerView rv_category;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_add)
    Button btnAdd;

    EditText.OnEditorActionListener ON_EDITOR_CLICK = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                if(step == 0 || step == 1) {
                    executeStep(step, textView.getText().toString());
                }else{

                }
            }
            return false;
        }
    };

    @OnClick(R.id.btn_next) void next(){
        if(step == 3 || step == 4)
            changeStep();
        else
            initProductTask(product);
    }

    @OnClick(R.id.btn_add) void add(){
        openDialogAdd(step);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_add);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        createToolbar();
        ButterKnife.bind(this);
        init();

    }

    private void init(){
        txtSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txtSearch.setOnEditorActionListener(ON_EDITOR_CLICK);
        try{
            product = getItem(getIntent().getExtras().getString("product"),ProductSend.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        executeStep(0,null);
    }

    private void openDialogAdd(final int step){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add_category);
        TextView txtTitle =  dialog.findViewById(R.id.txtTitle);
        final EditText editName =  dialog.findViewById(R.id.editName);
        Button btnAddCategory =  dialog.findViewById(R.id.btnAddCategory);
        txtTitle.setText(getTitleStep(step));
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editName.getText().toString().trim().isEmpty()) {
                    String value = editName.getText().toString();
                    switch (step) {
                        case 1:
                            initAddFamilyTask(value);
                            dialog.dismiss();
                            break;
                        case 2:
                            initAddGroupTask(product.getIdProductFamily(), value);
                            dialog.dismiss();
                            break;
                        case 3:
                            initAddSubGroupTask(product.getIdProductGroup(),value);
                            dialog.dismiss();
                            break;
                        case 4:
                            initAddSectorTask(product.getIdProductSubGroup(),value);
                            dialog.dismiss();
                            break;
                    }
                }
            }
        });
        dialog.show();

    }

    public String getTitleStep(int step) {
        switch(step){
            case 1:
                return "Adicionar Família";
            case 2:
                return "Adicionar Grupo";
            case 3:
                return "Adicionar Subgrupo";
            case 4:
                return "Adicionar Setor";
            default:
                return "Adicionar";
        }
    }




    private void executeStep(int step, String value) {
        switch (step){
            case 0:
                initUnitTask();
                break;
            case 1:
                initFamilyTask(value);
            default:


        }

    }

    private void changeStep(){
        txtSearch.setText("", TextView.BufferType.EDITABLE);
        rv_category.setAdapter(null);
        step++;
        txtType.setText(categories[step]);
        if(step == 1)
            btnAdd.setVisibility(View.VISIBLE);
        if(step == 2 )
            txtSearch.setVisibility(View.GONE);
        if(step == 3 || step == 4 || step == 5) {
            btnNext.setVisibility(View.VISIBLE);

            if (step == 5) {
                btnAdd.setVisibility(View.GONE);
                txtSearch.setVisibility(View.GONE);
                btnNext.setBackgroundColor(this.getResources().getColor(R.color.colorGreenLogin));
                btnNext.setText("Adicionar Produto");
            }
        }
        else
            btnNext.setVisibility(View.GONE);

    }

    public void createList(ArrayList<? extends  IProductCategory> categories, RecyclerViewOnClickListenerHack rvClick){
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        categoryAdapter.setmRecyclerViewOnClickListenerHack(rvClick);
        rv_category.setLayoutManager(layoutManager);
        rv_category.setAdapter(categoryAdapter);

    }

    private void initUnitTask(){
        if(unitTask == null || unitTask.getStatus() != AsyncTask.Status.RUNNING){
            unitTask = new UnitTask();
            unitTask.execute();
        }
    }

    private void initFamilyTask(String value){
        if(familyTask == null || familyTask.getStatus() != AsyncTask.Status.RUNNING){
            familyTask = new FamilyTask(value);
            familyTask.execute();
        }
    }

    private void initGroupTask(int id){
        if(groupTask == null || groupTask.getStatus() != AsyncTask.Status.RUNNING){
            groupTask = new GroupTask(id);
            groupTask.execute();
        }
    }

    private void initSubGrouptTask(int value){
        if(subGroupTask == null || subGroupTask.getStatus() != AsyncTask.Status.RUNNING){
            subGroupTask = new SubGroupTask(value);
            subGroupTask.execute();
        }
    }

    private void initSectorTask(int value){
        if(sectorTask == null || sectorTask.getStatus() != AsyncTask.Status.RUNNING){
            sectorTask = new SectorTask(value);
            sectorTask.execute();
        }
    }

    private void initProductTask(ProductSend product){
        if(productTask == null || productTask.getStatus() != AsyncTask.Status.RUNNING){
            productTask = new ProductTask(product);
            productTask.execute();
        }
    }

    private void initAddFamilyTask(String name){
        if(addFamilyTask == null || addFamilyTask.getStatus() != AsyncTask.Status.RUNNING){
            addFamilyTask = new AddFamilyTask(name);
            addFamilyTask.execute();
        }
    }

    private void initAddGroupTask(int id, String name){
        if(addGroupTask == null || addGroupTask.getStatus() != AsyncTask.Status.RUNNING){
            addGroupTask = new AddGroupTask(name,id);
            addGroupTask.execute();
        }
    }

    private void initAddSubGroupTask(int id, String name){
        if(addSubGroupTask == null || addSubGroupTask.getStatus() != AsyncTask.Status.RUNNING){
            addSubGroupTask = new AddSubGroupTask(name,id);
            addSubGroupTask.execute();
        }
    }

    private void initAddSectorTask(int id, String name){
        if(addSectorTask == null || addSectorTask.getStatus() != AsyncTask.Status.RUNNING){
            addSectorTask = new AddSectorTask(name,id);
            addSectorTask.execute();
        }
    }

    private void selectAdd(int step, Integer id, String name){
        switch (step){
            case 1:
                initAddFamilyTask(name);
                break;
            case 2:
                initAddGroupTask(id,name);
                break;
            case 3:
                initAddSubGroupTask(id,name);
                break;
            case 4:
                initAddSectorTask(id,name);
                break;
        }
    }


    private class UnitTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductUnitList> apiResponse;
        private DialogLoading dialogLoading;

        public UnitTask() {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
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
            ProductUnitControl productUnitControl = new ProductUnitControl(ProductCategoryAddActivity.this);
            apiResponse = productUnitControl.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean())
                createList(apiResponse.getResult().getList(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        cardFilter.setVisibility(View.VISIBLE);
                        product.setIdProductUnit(apiResponse.getResult().getList().get(position).getId());
                        changeStep();
                    }
                });
        }
    }




    private class FamilyTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductFamilyList> apiResponse;
        private String value;
        private DialogLoading dialogLoading;


        public FamilyTask(String value) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
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
            ProductFamilyControl productFamilyControl = new ProductFamilyControl(ProductCategoryAddActivity.this);
            apiResponse = productFamilyControl.search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getList().size() != 0){
                createList(apiResponse.getResult().getList(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        product.setIdProductFamily(apiResponse.getResult().getList().get(position).getId());
                        changeStep();
                        initGroupTask(apiResponse.getResult().getList().get(position).getId());
                    }
                });

                }else{
                    toast(ProductCategoryAddActivity.this, "Não foi encontrado nenhuma família. Adicione uma!");
                }
            }
        }
    }




    private class GroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductFamily> apiResponse;
        private int id;
        private DialogLoading dialogLoading;


        private GroupTask(int id) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
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
            ProductFamilyControl productFamilyControl = new ProductFamilyControl(ProductCategoryAddActivity.this);
            apiResponse = productFamilyControl.getGroups(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductGroups().size() != 0){
                createList(apiResponse.getResult().getProductGroups(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        product.setIdProductGroup(apiResponse.getResult().getProductGroups().get(position).getId());
                        changeStep();
                        initSubGrouptTask(apiResponse.getResult().getProductGroups().get(position).getId());
                    }
                });
                }else{
                    toast(ProductCategoryAddActivity.this, "Não foi encontrado nenhum Grupo. Adicione um!");
                }
            }
        }
    }



    private class SubGroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductGroup> apiResponse;
        private int id;
        private DialogLoading dialogLoading;


        public SubGroupTask(int value) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
            this.id = value;
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
            ProductGroupControl productGroupControl = new ProductGroupControl(ProductCategoryAddActivity.this);
            apiResponse = productGroupControl.getSubGroups(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductSubGroups().size() != 0){
                createList(apiResponse.getResult().getProductSubGroups(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        product.setIdProductSubGroup(apiResponse.getResult().getProductSubGroups().get(position).getId());
                        changeStep();
                        initSectorTask(apiResponse.getResult().getProductSubGroups().get(position).getId());
                    }
                });
                }else{
                    toast(ProductCategoryAddActivity.this, "Não foi encontrado nenhuma família. Adicione uma!");
                }
            }
        }
    }




    private class SectorTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductSubGroup> apiResponse;
        private int id;
        private DialogLoading dialogLoading;


        public SectorTask(int id) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
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
            ProductSubGroupControl productSubGroupControl = new ProductSubGroupControl(ProductCategoryAddActivity.this);
            apiResponse = productSubGroupControl.getSectors(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductSectores().size() != 0){
                createList(apiResponse.getResult().getProductSectores(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        product.setIdProductSector(apiResponse.getResult().getProductSectores().get(position).getId());
                        changeStep();
                    }
                });
                }else{
                    toast(ProductCategoryAddActivity.this, "Não foi encontrado nenhuma família. Adicione uma!");
                }
            }
        }
    }

    private class ProductTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<Product> apiResponse;
        private ProductSend product;
        private DialogLoading dialogLoading;

        public ProductTask(ProductSend product) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
            this.product = product;
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
            ProductControl productControl = new ProductControl(ProductCategoryAddActivity.this);
            apiResponse = productControl.add(product);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductCategoryAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirm.dismissD();
                        createIntentAbs(ProductListActivity.class);
                    }
                });
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

    private class AddFamilyTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductFamily> apiResponse;
        private String name;
        private DialogLoading dialogLoading;


        public AddFamilyTask(String name) {
            this.name = name;
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);

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
            ProductFamilyControl productFamilyControl = new ProductFamilyControl(ProductCategoryAddActivity.this);
            apiResponse = productFamilyControl.add(name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductCategoryAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        product.setIdProductFamily(apiResponse.getResult().getId());
                        changeStep();
                        initSectorTask(apiResponse.getResult().getId());
                        dialogConfirm.dismissD();
                    }
                });
            }
        }
    }


    private class AddGroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductGroup> apiResponse;
        private String name;
        private int id;
        private DialogLoading dialogLoading;


        public AddGroupTask(String name, int id) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
            this.name = name;
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
            ProductGroupControl productGroupControl = new ProductGroupControl(ProductCategoryAddActivity.this);
            apiResponse = productGroupControl.add(id,name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductCategoryAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        product.setIdProductGroup(apiResponse.getResult().getId());
                        changeStep();
                        initSectorTask(apiResponse.getResult().getId());
                        dialogConfirm.dismissD();
                    }
                });
            }
        }
    }


    private class AddSubGroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductSubGroup> apiResponse;
        private String name;
        private int id;
        private DialogLoading dialogLoading;


        public AddSubGroupTask(String name, int id) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
            this.name = name;
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
            ProductSubGroupControl productSubGroupControl = new ProductSubGroupControl(ProductCategoryAddActivity.this);
            apiResponse = productSubGroupControl.add(id,name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductCategoryAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        product.setIdProductSubGroup(apiResponse.getResult().getId());
                        changeStep();
                        initSectorTask(apiResponse.getResult().getId());
                        dialogConfirm.dismissD();
                    }
                });
            }
        }
    }


    private class AddSectorTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductSector> apiResponse;
        private String name;
        private int id;
        private DialogLoading dialogLoading;


        public AddSectorTask(String name, int id) {
            dialogLoading = new DialogLoading(ProductCategoryAddActivity.this);
            this.name = name;
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
            ProductSectorControl productSectorControl = new ProductSectorControl(ProductCategoryAddActivity.this);
            apiResponse = productSectorControl.add(id,name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductCategoryAddActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        product.setIdProductSector(apiResponse.getResult().getId());
                        changeStep();
                        initSectorTask(apiResponse.getResult().getId());
                        dialogConfirm.dismissD();
                    }
                });
            }
        }
    }



}
