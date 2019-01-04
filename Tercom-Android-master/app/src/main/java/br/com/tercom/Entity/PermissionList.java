package br.com.tercom.Entity;


import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class PermissionList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Permission> list;

    public ArrayList<Permission> getList() {
        return list;
    }

    public void setList(ArrayList<Permission> list) {
        this.list = list;
    }
}
