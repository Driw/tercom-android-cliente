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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.tercom.Adapter.CategoryAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.ProductControl;
import br.com.tercom.Control.ProductFamilyControl;
import br.com.tercom.Control.ProductGroupControl;
import br.com.tercom.Control.ProductSubGroupControl;
import br.com.tercom.Control.ProductUnitControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductFamily;
import br.com.tercom.Entity.ProductFamilyList;
import br.com.tercom.Entity.ProductGroup;
import br.com.tercom.Entity.ProductSend;
import br.com.tercom.Entity.ProductSubGroup;
import br.com.tercom.Entity.ProductUnitList;
import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.Interface.IProductCategory;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import br.com.tercom.Util.DialogConfirm;
import br.com.tercom.Util.DialogLoading;
import br.com.tercom.Util.TextUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Util.GsonUtil.getItem;
import static br.com.tercom.Util.Util.toast;

public class ProductDetailsActivity extends AbstractAppCompatActivity {

    private static final int CATEGORY_UNIT = 1;
    private static final int CATEGORY_FAMILY = 2;
    private static final int CATEGORY_GROUP = 3;
    private static final int CATEGORY_SUBGROUP = 4;
    private static final int CATEGORY_SECTOR = 5;

    private Product product;
    private boolean enable;
    private UnitTask unitTask;
    private FamilyTask familyTask;
    private GroupTask groupTask;
    private SubGroupTask subGroupTask;
    private SectorTask sectorTask;
    private ProductTask productTask;
    private ProductSend productSend;
    private RecyclerView rvSearch;
    private EditText editSearch;
    private Dialog taskDialog;

    @BindView(R.id.txtUnit)
    TextView txtUnit;

    @BindView(R.id.txtName)
    EditText txtName;

    @BindView(R.id.txtDescription)
    EditText txtDescription;

    @BindView(R.id.txtUtility)
    EditText txtUtility;

    @BindView(R.id.txtFamily)
    TextView txtFamily;

    @BindView(R.id.txtGroup)
    TextView txtGroup;

    @BindView(R.id.txtSubGroup)
    TextView txtSubGroup;

    @BindView(R.id.txtSector)
    TextView txtSector;

    @OnClick(R.id.btn_prices) void seePrices(){
        Intent intent = new Intent(ProductDetailsActivity.this, ProductValueListActivity.class);
        intent.putExtra("idProduct", product.getId());
        startActivity(intent);
    }

    @OnClick(R.id.btn_update) void update(){
        if(!enable) {
            setEnable(true);
            productSend = new ProductSend();
            productSend.setName(product.getName());
            productSend.setDescription(product.getDescription());
            productSend.setUtility(product.getUtility());
            productSend.setIdProductFamily(product.getCategory().getFamily().getId());
            productSend.setIdProductGroup(product.getCategory().getGroup().getId());
            productSend.setIdProductSubGroup(product.getCategory().getSubgroup().getId());
            productSend.setIdProductSector(product.getCategory().getSector().getId());
            productSend.setIdProductUnit(product.getUnit().getId());
        }else{
            if(!TextUtils.isEmpty(txtName.getText().toString()) && !TextUtils.isEmpty(txtDescription.getText().toString()) && !TextUtils.isEmpty(txtUtility.getText().toString())) {
                productSend.setName(txtName.getText().toString());
                productSend.setDescription(txtDescription.getText().toString());
                productSend.setUtility(txtUtility.getText().toString());
                initProductTask(productSend);
            }
            else
                toast(ProductDetailsActivity.this,"Nome, descrição e utilidade devem ser preenchidos!");
        }

    }

    @OnClick(R.id.txtUnit) void clickUnit(){
        initDialog(CATEGORY_UNIT,0);
    }
    @OnClick(R.id.txtFamily) void clickFamily(){
        initDialog(CATEGORY_FAMILY,0);
    }
    @OnClick(R.id.txtGroup) void clickGroup(){
        initDialog(CATEGORY_GROUP,productSend.getIdProductFamily());
    }
    @OnClick(R.id.txtSubGroup) void clickSubgroup(){
        if(productSend.getIdProductGroup() != 0){
            initDialog(CATEGORY_SUBGROUP,productSend.getIdProductGroup());
        }else{
            DialogConfirm dialogConfirm = new DialogConfirm(ProductDetailsActivity.this);
            dialogConfirm.init(EnumDialogOptions.FAIL,"Não é possível selecionar um subgrupo sem um grupo");
        }
    }
    @OnClick(R.id.txtSector) void clickSector(){
        if(productSend.getIdProductSubGroup() != 0){
            initDialog(CATEGORY_SECTOR,productSend.getIdProductSubGroup());
        }else{
            DialogConfirm dialogConfirm = new DialogConfirm(ProductDetailsActivity.this);
            dialogConfirm.init(EnumDialogOptions.FAIL,"Não é possível selecionar um setor sem um subgrupo");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        createToolbar();
        product = getItem(getIntent().getExtras().getString("product"),Product.class);
        populate();
        setEnable(false);
    }

    private void populate() {
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtUtility.setText(product.getUtility());
        setText(CATEGORY_UNIT,product.getUnit().getName());
        setText(CATEGORY_FAMILY,product.getCategory().getFamily().getName());
        setText(CATEGORY_GROUP,product.getCategory().getGroup().getName());
        setText(CATEGORY_SUBGROUP,product.getCategory().getSubgroup().getName());
        setText(CATEGORY_SECTOR,product.getCategory().getSector().getName());


    }

    private void setEnable(boolean enable) {
        txtName.setEnabled(enable);
        txtDescription.setEnabled(enable);
        txtUtility.setEnabled(enable);
        txtUnit.setEnabled(enable);
        txtFamily.setEnabled(enable);
        txtGroup.setEnabled(enable);
        txtSubGroup.setEnabled(enable);
        txtSector.setEnabled(enable);
        this.enable = enable;
    }

    private void setText(int type, String value){
        switch (type){
            case CATEGORY_UNIT:
                txtUnit.setText(String.format(Locale.US,"Unidade: %s",value));
                break;
            case CATEGORY_FAMILY:
                txtFamily.setText(String.format(Locale.US,"Família: %s",value));
                break;
            case CATEGORY_GROUP:
                txtGroup.setText(String.format(Locale.US,"Grupo: %s",value));
                break;
            case CATEGORY_SUBGROUP :
                txtSubGroup.setText(String.format(Locale.US,"Subgrupo: %s",value));
                break;
            case CATEGORY_SECTOR :
                txtSector.setText(String.format(Locale.US,"Setor: %s",value));
                break;
        }

    }

    private void clear(int... type){
        for(int selectedType: type) {
            switch (selectedType) {
                case CATEGORY_UNIT:
                    txtUnit.setText("Unidade:");
                    productSend.setIdProductUnit(0);
                    break;
                case CATEGORY_FAMILY:
                    txtFamily.setText("Família: ");
                    productSend.setIdProductFamily(0);
                    break;
                case CATEGORY_GROUP:
                    txtGroup.setText("Grupo:");
                    productSend.setIdProductGroup(0);
                    break;
                case CATEGORY_SUBGROUP:
                    txtSubGroup.setText("Subgrupo:");
                    productSend.setIdProductSubGroup(0);
                    break;
                case CATEGORY_SECTOR:
                    txtSector.setText("Setor:");
                    productSend.setIdProductSector(0);
                    break;
            }
        }

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

    private void initProductTask(ProductSend productSend){
        if(productTask == null || productTask.getStatus() != AsyncTask.Status.RUNNING){
            productTask = new ProductTask(productSend,product.getId());
            productTask.execute();
        }
    }


    public void createList(ArrayList<? extends IProductCategory> categories, RecyclerViewOnClickListenerHack rvClick){
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        categoryAdapter.setmRecyclerViewOnClickListenerHack(rvClick);
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(categoryAdapter);

    }


    private void initDialog(final int typeReference, int id) {
        taskDialog = new Dialog(this);
        taskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        taskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        taskDialog.setContentView(R.layout.dialog_search_info_price);
        rvSearch = taskDialog.findViewById(R.id.rv_search);
        editSearch = taskDialog.findViewById(R.id.editSearch);
            editSearch.setVisibility(View.GONE);
            switch (typeReference){
                case CATEGORY_UNIT:
                    initUnitTask();
                    break;
                case CATEGORY_FAMILY:
                    editSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if (i == EditorInfo.IME_ACTION_SEARCH) {
                                if (!TextUtils.isEmpty(editSearch.getText().toString()))
                                    initFamilyTask(editSearch.getText().toString());
                                return true;
                            }
                            return false;
                        }
                    });
                    editSearch.setVisibility(View.VISIBLE);
                    break;
                case CATEGORY_GROUP:
                    initGroupTask(id);
                    break;
                case CATEGORY_SUBGROUP :
                    initSubGrouptTask(id);
                    break;
                case CATEGORY_SECTOR :
                    initSectorTask(id);
                    break;
            }
        taskDialog.show();

    }


    private class UnitTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductUnitList> apiResponse;

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductUnitControl productUnitControl = new ProductUnitControl(ProductDetailsActivity.this);
            apiResponse = productUnitControl.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean())
                createList(apiResponse.getResult().getList(), new RecyclerViewOnClickListenerHack() {
                    @Override
                    public void onClickListener(View view, int position) {
                        productSend.setIdProductUnit(apiResponse.getResult().getList().get(position).getId());
                        setText(CATEGORY_UNIT,apiResponse.getResult().getList().get(position).getName());
                        taskDialog.dismiss();

                    }
                });
        }
    }




    private class FamilyTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductFamilyList> apiResponse;
        private String value;

        public FamilyTask(String value) {
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductFamilyControl productFamilyControl = new ProductFamilyControl(ProductDetailsActivity.this);
            apiResponse = productFamilyControl.search(value);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getList().size() != 0){
                    createList(apiResponse.getResult().getList(), new RecyclerViewOnClickListenerHack() {
                        @Override
                        public void onClickListener(View view, int position) {
                            productSend.setIdProductFamily(apiResponse.getResult().getList().get(position).getId());
                            setText(CATEGORY_FAMILY,apiResponse.getResult().getList().get(position).getName());
                            clear(CATEGORY_GROUP,CATEGORY_SUBGROUP,CATEGORY_SECTOR);
                            taskDialog.dismiss();
                        }
                    });

                }else{
                    toast(ProductDetailsActivity.this, "Não foi encontrado nenhuma família. Adicione uma!");
                }
            }
        }
    }




    private class GroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductFamily> apiResponse;
        private int id;

        private GroupTask(int id) {
            this.id = id;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductFamilyControl productFamilyControl = new ProductFamilyControl(ProductDetailsActivity.this);
            apiResponse = productFamilyControl.getGroups(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductGroups().size() != 0){
                    createList(apiResponse.getResult().getProductGroups(), new RecyclerViewOnClickListenerHack() {
                        @Override
                        public void onClickListener(View view, int position) {
                            productSend.setIdProductGroup(apiResponse.getResult().getProductGroups().get(position).getId());
                            setText(CATEGORY_GROUP,apiResponse.getResult().getProductGroups().get(position).getName());
                            clear(CATEGORY_SUBGROUP,CATEGORY_SECTOR);
                            taskDialog.dismiss();
                        }
                    });
                }else{
                    toast(ProductDetailsActivity.this, "Não foi encontrado nenhum Grupo. Adicione um!");
                }
            }
        }
    }



    private class SubGroupTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductGroup> apiResponse;
        private int id;

        public SubGroupTask(int value) {
            this.id = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductGroupControl productGroupControl = new ProductGroupControl(ProductDetailsActivity.this);
            apiResponse = productGroupControl.getSubGroups(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductSubGroups().size() != 0){
                    createList(apiResponse.getResult().getProductSubGroups(), new RecyclerViewOnClickListenerHack() {
                        @Override
                        public void onClickListener(View view, int position) {
                            productSend.setIdProductSubGroup(apiResponse.getResult().getProductSubGroups().get(position).getId());
                            setText(CATEGORY_SUBGROUP,apiResponse.getResult().getProductSubGroups().get(position).getName());
                            clear(CATEGORY_SECTOR);
                            taskDialog.dismiss();
                        }
                    });
                }else{
                    toast(ProductDetailsActivity.this, "Não foi encontrado nenhum Subgrupo. Adicione um!");
                }
            }
        }
    }




    private class SectorTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductSubGroup> apiResponse;
        private int id;

        public SectorTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductSubGroupControl productSubGroupControl = new ProductSubGroupControl(ProductDetailsActivity.this);
            apiResponse = productSubGroupControl.getSectors(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                if(apiResponse.getResult().getProductSectores().size() != 0){
                    createList(apiResponse.getResult().getProductSectores(), new RecyclerViewOnClickListenerHack() {
                        @Override
                        public void onClickListener(View view, int position) {
                            productSend.setIdProductSector(apiResponse.getResult().getProductSectores().get(position).getId());
                            setText(CATEGORY_GROUP,apiResponse.getResult().getProductSectores().get(position).getName());
                            taskDialog.dismiss();
                        }
                    });
                }else{
                    toast(ProductDetailsActivity.this, "Não foi encontrado nenhum setor");
                }
            }
        }
    }

    private class ProductTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<Product> apiResponse;
        private ProductSend product;
        private int id;
        private DialogLoading dialogLoading;


        public ProductTask(ProductSend product, int id) {
            this.product = product;
            this.id = id;
            dialogLoading = new DialogLoading(ProductDetailsActivity.this);

        }

        @Override
        protected void onPreExecute() {
            dialogLoading.init();
            setEnable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductControl productControl = new ProductControl(ProductDetailsActivity.this);
            apiResponse = productControl.update(id,product.getName(),product.getDescription(),product.getUtility(),product.getTreeMap());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialogLoading.dismissD();
            final DialogConfirm dialogConfirm = new DialogConfirm(ProductDetailsActivity.this);
            if(apiResponse.getStatusBoolean()){
                dialogConfirm.init(EnumDialogOptions.CONFIRM,apiResponse.getMessage());
                dialogConfirm.onClickChanges(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirm.dismissD();
                    }
                });
            }else{
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }

}
