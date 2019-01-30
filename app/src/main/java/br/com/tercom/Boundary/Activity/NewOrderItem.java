package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import br.com.tercom.Adapter.AddProductAdapter;
import br.com.tercom.Adapter.AddServiceAdapter;
import br.com.tercom.Adapter.CategoryAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.OrderItemControl;
import br.com.tercom.Control.ProductControl;
import br.com.tercom.Control.ServiceControl;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductList;
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

public class NewOrderItem extends AbstractAppCompatActivity {

    public static final int ADD_PRODUCT = 1;
    public static final int ADD_SERVICE = 2;

    private int selectedType;
    private OrderItemControl orderItemControl;
    private int orderRequestId;
    private RecyclerView rvSearch;
    private EditText editSearch;
    private AddProductTask addProductTask;
    private AddServiceTask addServiceTask;
    private ProductTask productTask;
    private SearchServiceTask searchServiceTask;
    private  Dialog dialog;
    private Product selectedProduct;
    private Services selectedServices;


    @BindView(R.id.txtOrderProductName)
    TextView txtOrderProductName;
    @BindView(R.id.txtOrderManufacturerName)
    TextView txtOrderManufacturerName;
    @BindView(R.id.txtOrderProviderName)
    TextView txtOrderProviderName;
    @BindView(R.id.txtOrderProductLabel)
    TextView txtOrderProductLabel;
    @BindView(R.id.txtOrderManufacturerLabel)
    TextView txtOrderManufacturerLabel;
    @BindView(R.id.txtOrderproviderLabel)
    TextView txtOrderproviderLabel;

    @OnClick(R.id.btnOrderItemAdd) void addOrderItem() {

    }

    @OnClick(R.id.txtOrderProductName) void selectAdd() {
        initDialog(selectedType);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_item);
        selectedType = getIntent().getExtras().getInt("typeAdd");
        orderRequestId = getIntent().getExtras().getInt("orderRequestId");
        orderItemControl = new OrderItemControl(this);
        ButterKnife.bind(this);
        if(selectedType == ADD_SERVICE){
            txtOrderProductLabel.setText("Serviço:");
            txtOrderProductName.setHint("Nome do Serviço");
            txtOrderManufacturerLabel.setVisibility(View.GONE);
            txtOrderManufacturerName.setVisibility(View.GONE);
        }
    }



    private void initDialog(final int typeReference) {
        dialog = new Dialog(this);
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
                        search(typeReference, editSearch.getText().toString());

                    return true;
                }
                return false;
            }
        });
        dialog.show();

    }

    private void search(int reference,String value){

        switch (reference){
            case ADD_PRODUCT:
                searchProduct(value);
                break;
            case ADD_SERVICE:
                searchService(value);
        }

    }

    private void searchService(String name) {
        if(searchServiceTask == null || searchServiceTask.getStatus() != AsyncTask.Status.RUNNING){
            searchServiceTask = new SearchServiceTask(name);
            searchServiceTask.execute();
        }

    }

    private void searchProduct(String name) {
        if(productTask == null || productTask.getStatus() != AsyncTask.Status.RUNNING){
            productTask = new ProductTask(name);
            productTask.execute();
        }
    }

    private void initService() {
        if(addServiceTask == null || addServiceTask.getStatus() != AsyncTask.Status.RUNNING){

        }
    }

    private void initProduct() {

    }

    private void createListProducts(final ProductList result) {
        AddProductAdapter categoryAdapter = new AddProductAdapter(this,result.getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        categoryAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                selectedProduct = result.getList().get(position);
                txtOrderProductName.setText(result.getList().get(position).getName());
                dialog.dismiss();
            }
        });
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(categoryAdapter);
    }



    private void createListServices(final ServicesList result) {
        AddServiceAdapter categoryAdapter = new AddServiceAdapter(this,result.getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        categoryAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                selectedServices = result.getList().get(position);
                txtOrderProductName.setText(result.getList().get(position).getName());
                dialog.dismiss();
            }
        });
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setAdapter(categoryAdapter);
    }




    private class ProductTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ProductList> apiResponse;
        private String value;


        public ProductTask(String value) {
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ProductControl productControl = new ProductControl(NewOrderItem.this);
            apiResponse = productControl.search(value, EnumREST.NAME);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                createListProducts(apiResponse.getResult());
            }
        }
    }


    private class SearchServiceTask extends AsyncTask<Void,Void,Void>{

        private ApiResponse<ServicesList> apiResponse;
        private String name;


        public SearchServiceTask(String name) {
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null)
                Looper.prepare();
            ServiceControl serviceControl = new ServiceControl(NewOrderItem.this);
            apiResponse = serviceControl.search(name, EnumREST.NAME);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (apiResponse.getStatusBoolean()) {
                createListServices(apiResponse.getResult());
            }else{
                DialogConfirm dialogConfirm = new DialogConfirm(NewOrderItem.this);
                dialogConfirm.init(EnumDialogOptions.FAIL,apiResponse.getMessage());
            }
        }
    }




    private class AddProductTask extends AsyncTask<Void,Void,Void>{
        private int idOrderRequest;
        private int idProduct;
        private int idProvider;
        private int idManufacturer;
        private String observation;
        private boolean betterPrice;

        public AddProductTask(int idOrderRequest, int idProduct, int idProvider, int idManufacturer, String observation, boolean betterPrice) {
            this.idOrderRequest = idOrderRequest;
            this.idProduct = idProduct;
            this.idProvider = idProvider;
            this.idManufacturer = idManufacturer;
            this.observation = observation;
            this.betterPrice = betterPrice;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() != null)
                Looper.prepare();
            orderItemControl.addProduct(idOrderRequest,idProduct,idProvider,idManufacturer,observation,betterPrice);
            return null;
        }
    }


    private class AddServiceTask extends AsyncTask<Void,Void,Void>{
        private int idOrderRequest;
        private int idService;
        private int idProvider;
        private String observations;
        private boolean betterPrice;

        public AddServiceTask(int idOrderRequest, int idService, int idProvider, String observations, boolean betterPrice) {
            this.idOrderRequest = idOrderRequest;
            this.idService = idService;
            this.idProvider = idProvider;
            this.observations = observations;
            this.betterPrice = betterPrice;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper()== null)
                Looper.prepare();
            orderItemControl.addService(idOrderRequest,idService,idProvider,observations,betterPrice);

            return null;
        }
    }


}
