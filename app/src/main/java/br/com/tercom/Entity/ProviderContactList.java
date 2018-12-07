package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ProviderContactList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProviderContact> list;

    public ArrayList<ProviderContact> getList() {
        return list;
    }

    public void setList(ArrayList<ProviderContact> list) {
        this.list = list;
    }
}
