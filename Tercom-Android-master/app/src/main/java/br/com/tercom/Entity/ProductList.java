package br.com.tercom.Entity;

import java.util.ArrayList;

public class ProductList  extends GenericEntity{

    private ArrayList<Product> list;

    public ArrayList<Product> getList() {
        return list;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }
}
