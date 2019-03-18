package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class OrderQuote extends GenericEntity {
    private static final int OQS_DOING = 0;
    private static final int OQS_DONE = 1;

    private int id;
    @BindObject(type = BindObject.TYPE.OBJECT)
    private OrderRequest orderRequest;
    private int status;
    private String register;

    public OrderQuote() {
        setId(0);
        status = OQS_DOING;
        //TODO: Definir valor inicla para register.
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
