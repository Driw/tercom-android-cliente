package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class OrderRequestList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<OrderRequest> list;

    public ArrayList<OrderRequest> getList() {
        return list;
    }

    public void setList(ArrayList<OrderRequest> list) {
        this.list = list;
    }
}
