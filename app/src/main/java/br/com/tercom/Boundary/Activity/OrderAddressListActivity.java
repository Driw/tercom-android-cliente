package br.com.tercom.Boundary.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.tercom.Adapter.OrderAddressListAdapter;
import br.com.tercom.Application.AppTercom;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Control.AddressControl;
import br.com.tercom.Entity.Address;
import br.com.tercom.Entity.AddressList;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.com.tercom.Application.AppTercom.CUSTOMER_STATIC;
import static br.com.tercom.Util.Util.toast;

public class OrderAddressListActivity extends AbstractAppCompatActivity {

    private Address address;
    private String gsonQuote;
    private AddressTask addressTask;

    @BindView(R.id.rv_OrderAddressList)
    RecyclerView rv_OrderAddressList;
    @OnClick(R.id.btnOrderNewAddress) void selectNewAddress() {
        initDialog();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address_list);
        gsonQuote = getIntent().getExtras().getString("orderquote");
        ButterKnife.bind(this);
            initAddressTask();
        }

    private void initAddressTask() {
        if(addressTask == null || addressTask.getStatus() != AsyncTask.Status.RUNNING){
            addressTask = new AddressTask(CUSTOMER_STATIC.getId());
            addressTask.execute();
    }
}

    private boolean isAddressEmpty(){
        if (CUSTOMER_STATIC.getAddresses() != null) {
            if (CUSTOMER_STATIC.getAddresses().size() > 0) { return false; }
            else { return true; }
        } else {
            return true;
        }
    }

    private void initDialog() {
        final Dialog dialog = new Dialog(OrderAddressListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_new_order_address);
        address = new Address();
        final EditText txtNewAddressStreet = dialog.findViewById(R.id.txtNewAddressStreet);
        final EditText txtNewAddressNumber = dialog.findViewById(R.id.txtNewAddressNumber);
        final EditText txtNewAddressComplement = dialog.findViewById(R.id.txtNewAddressComplement);
        final EditText txtNewAddressCEP = dialog.findViewById(R.id.txtNewAddressCEP);
        final EditText txtNewAddressNeighborhood = dialog.findViewById(R.id.txtNewAddressNeighborhood);
        final EditText txtNewAddressCity = dialog.findViewById(R.id.txtNewAddressCity);
        final EditText txtNewAddressState = dialog.findViewById(R.id.txtNewAddressState);
        Button btnAddNewAddressDialog = dialog.findViewById(R.id.btnAddNewAddressDialog);
        btnAddNewAddressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNewAddressStreet.getText().toString().equals("") &&
                        !txtNewAddressNumber.getText().toString().equals("") &&
                        !txtNewAddressCEP.getText().toString().equals("") &&
                        !txtNewAddressNeighborhood.getText().toString().equals("") &&
                        !txtNewAddressCity.getText().toString().equals("") &&
                        !txtNewAddressState.getText().toString().equals("")){
                    address.setStreet(txtNewAddressStreet.getText().toString());
                    address.setNumber(Integer.parseInt(txtNewAddressNumber.getText().toString()));
                    address.setComplement(txtNewAddressComplement.getText().toString());
                    address.setCep(txtNewAddressCEP.getText().toString());
                    address.setNeighborhood(txtNewAddressNeighborhood.getText().toString());
                    address.setCity(txtNewAddressCity.getText().toString());
                    address.setState(txtNewAddressState.getText().toString());
                    CUSTOMER_STATIC.getAddresses().add(address);
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(OrderAddressListActivity.this, OrderAcceptanceMainActivity.class);
                    intent.putExtra("idOrderRequest", getIntent().getExtras().getInt("idOrderRequest"));
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Campo(s) obrigatórios em branco.", Toast.LENGTH_SHORT);
                }
            }
        });
        dialog.show();
    }


    private void setAdapter(){
        OrderAddressListAdapter orderAddressListAdapter = new OrderAddressListAdapter(this, CUSTOMER_STATIC.getAddresses());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_OrderAddressList.setLayoutManager(layoutManager);
        rv_OrderAddressList.setAdapter(orderAddressListAdapter);
        orderAddressListAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(OrderAddressListActivity.this, OrderAcceptanceMainActivity.class);
                intent.putExtra("idAddress", CUSTOMER_STATIC.getAddresses().get(position).getId());
                intent.putExtra("orderquote", gsonQuote);
                startActivity(intent);
            }
        });
    }

    private class AddressTask extends AsyncTask<Void,Void,Void>{
        ApiResponse<AddressList> apiResponse;
        private int id;

        public AddressTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Looper.myLooper() == null){
                Looper.prepare();
            }
            apiResponse = new AddressControl(OrderAddressListActivity.this).getAll(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(apiResponse.getStatusBoolean()){
                CUSTOMER_STATIC.setAddresses(apiResponse.getResult().getList());
                setAdapter();
            }
        }
    }

}
