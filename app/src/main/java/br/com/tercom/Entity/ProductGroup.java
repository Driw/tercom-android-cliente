package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;
import br.com.tercom.Interface.IProductCategory;

public class ProductGroup extends GenericEntity implements IProductCategory {

    private int id;
    private String name;
    private int type;
    private int idProductFamily;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductSubGroup>  productSubGroups;

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

    public int getIdProductFamily() {
        return idProductFamily;
    }

    public void setIdProductFamily(int idProductFamily) {
        this.idProductFamily = idProductFamily;
    }

    public ArrayList<ProductSubGroup> getProductSubGroups() {
        return productSubGroups;
    }

    public void setProductSubGroups(ArrayList<ProductSubGroup> productSubGroups) {
        this.productSubGroups = productSubGroups;
    }
}
