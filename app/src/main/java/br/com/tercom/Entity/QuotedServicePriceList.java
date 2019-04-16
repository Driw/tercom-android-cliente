package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class QuotedServicePriceList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    ArrayList<QuotedServicePrice> list;

    public ArrayList<QuotedServicePrice> getList() {
        return list;
    }

    public void setList(ArrayList<QuotedServicePrice> list) {
        this.list = list;
    }
}


