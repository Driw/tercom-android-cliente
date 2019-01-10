package br.com.tercom.Entity;

import java.util.ArrayList;

public class Order extends GenericEntity {

    public static final String orderStatus = "status teste";
    //STatus como constante???

    private int orderNumber;
    private ArrayList<ProdutoGenerico> produtos;

    public ArrayList<ProdutoGenerico> getProdutos() {
        return produtos;
    }
    public void setProdutos(ArrayList<ProdutoGenerico> produtos) {
        this.produtos = produtos;
    }
    public String getOrderStatus() { return orderStatus; }
    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
