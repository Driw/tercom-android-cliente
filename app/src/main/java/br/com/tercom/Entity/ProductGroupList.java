package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public  class ProductGroupList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductGroup> list;

    public ArrayList<ProductGroup> getList() {
        return list;
    }

    public void setList(ArrayList<ProductGroup> list) {
        this.list = list;
    }
}
