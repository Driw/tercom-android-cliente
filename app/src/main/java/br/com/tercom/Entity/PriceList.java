package br.com.tercom.Entity;

import java.util.ArrayList;

public class PriceList extends GenericEntity {

    private ArrayList<Price> list;

    public ArrayList<Price> getList() {
        return list;
    }

    public void setList(ArrayList<Price> list) {
        this.list = list;
    }
}
