package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class OrderQuoteList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<OrderQuote> list;

    public ArrayList<OrderQuote> getList() {
        return list;
    }

    public void setList(ArrayList<OrderQuote> list) {
        this.list = list;
    }
}
