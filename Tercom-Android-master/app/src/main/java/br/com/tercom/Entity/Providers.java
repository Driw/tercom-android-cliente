package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class Providers extends GenericEntity{
     private int pageCount;
     @BindObject
     private ProviderList providers;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ProviderList getProviders() {
        return providers;
    }

    public void setProviders(ProviderList providers) {
        this.providers = providers;
    }
}
