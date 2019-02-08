package br.com.tercom.Boundary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.Provider;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewrOrderItemActivity extends AbstractAppCompatActivity {

    private OrderItemProduct orderItemProduct;
    private Product produto;
    private Manufacture fabricante;
    private Provider fornecedor;

    @BindView(R.id.txtOrderProductName)
    EditText txtOrderProductName;
    @BindView(R.id.txtOrderManufacturerName)
    EditText txtOrderManufacturerName;
    @BindView(R.id.txtOrderProviderName)
    EditText txtOrderProviderName;
    @BindView(R.id.txtOrderInformation)
    EditText txtOrderInformation;

    @OnClick(R.id.btnOrderItemAdd) void addOrderItem() {
        produto.setName(txtOrderProductName.getText().toString());
        fabricante.setFantasyName(txtOrderManufacturerName.getText().toString());
        fornecedor.setFantasyName(txtOrderProviderName.getText().toString());
        orderItemProduct.setObservations(txtOrderInformation.getText().toString());
        orderItemProduct.setProduct(produto);
        orderItemProduct.setManufacturer(fabricante);
        orderItemProduct.setProvider(fornecedor);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_item);
        createToolbar();
        ButterKnife.bind(this);

    }
}
