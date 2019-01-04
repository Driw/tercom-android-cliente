package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ProductTypeList extends GenericEntity {
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductType> list;

    public ArrayList<ProductType> getList() {
        return list;
    }

    public void setList(ArrayList<ProductType> list) {
        this.list = list;
    }
}
