package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ServicesList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Services> list;

    public ArrayList<Services> getList() {
        return list;
    }

    public void setList(ArrayList<Services> list) {
        this.list = list;
    }
}
