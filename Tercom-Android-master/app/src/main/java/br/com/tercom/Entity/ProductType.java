package br.com.tercom.Entity;

import br.com.tercom.Interface.IProductValueItem;

public class ProductType extends GenericEntity implements IProductValueItem {
    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
