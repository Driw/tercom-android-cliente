package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;
import br.com.tercom.Interface.IProductCategory;

public class ProductFamily extends GenericEntity implements IProductCategory{
    private int id;
    private String name;
    private int type;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductGroup> productGroups;


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

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(ArrayList<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }
}
