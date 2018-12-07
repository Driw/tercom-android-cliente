package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ProviderList extends GenericEntity {

    private int pageCount;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Provider> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<Provider> getProviders() {
        return list;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.list = providers;
    }
}
