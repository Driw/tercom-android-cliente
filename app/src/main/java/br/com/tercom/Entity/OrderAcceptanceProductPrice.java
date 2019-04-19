package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class OrderAcceptanceProductPrice extends GenericEntity {
    private int id;
    @BindObject
    private OrderItemProduct orderItemProduct;
    @BindObject
    private ProductValue quotedProductPrice;
    private String observations;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderItemProduct getOrderItemProduct() {
        return orderItemProduct;
    }

    public void setOrderItemProduct(OrderItemProduct orderItemProduct) {
        this.orderItemProduct = orderItemProduct;
    }

    public ProductValue getQuotedProductPrice() {
        return quotedProductPrice;
    }

    public void setQuotedProductPrice(ProductValue quotedProductPrice) {
        this.quotedProductPrice = quotedProductPrice;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
