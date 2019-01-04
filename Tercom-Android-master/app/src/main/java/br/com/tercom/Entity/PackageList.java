package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class PackageList extends GenericEntity {
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductPackage> list;

    public ArrayList<ProductPackage> getList() {
        return list;
    }

    public void setList(ArrayList<ProductPackage> list) {
        this.list = list;
    }
}
