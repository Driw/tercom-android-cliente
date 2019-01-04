package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Interface.IProductValueItem;

public class Manufacture extends GenericEntity implements IProductValueItem {

    private int id;
    private String fantasyName;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    @Override
    public String getName() {
        return fantasyName;
    }
}
