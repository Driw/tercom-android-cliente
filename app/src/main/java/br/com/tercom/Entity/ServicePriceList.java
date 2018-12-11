package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ServicePriceList extends GenericEntity {
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ServicePrice> list;

    public ArrayList<ServicePrice> getList() {
        return list;
    }

    public void setList(ArrayList<ServicePrice> list) {
        this.list = list;
    }
}
