package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class QuotedProductPriceList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    ArrayList<QuotedProductPrice> list;

    public ArrayList<QuotedProductPrice> getList() {
        return list;
    }

    public void setList(ArrayList<QuotedProductPrice> list) {
        this.list = list;
    }
}


