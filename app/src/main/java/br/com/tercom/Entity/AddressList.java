package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class AddressList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Address> list;

    public ArrayList<Address> getList() {
        return list;
    }

    public void setList(ArrayList<Address> list) {
        this.list = list;
    }
}
