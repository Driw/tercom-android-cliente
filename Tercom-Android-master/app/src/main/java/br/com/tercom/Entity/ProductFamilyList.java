package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public  class ProductFamilyList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductFamily> list;

    public ArrayList<ProductFamily> getList() {
        return list;
    }

    public void setList(ArrayList<ProductFamily> list) {
        this.list = list;
    }
}
