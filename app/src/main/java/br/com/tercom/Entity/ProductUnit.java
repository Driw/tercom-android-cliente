package br.com.tercom.Entity;

import br.com.tercom.Interface.IProductCategory;

public class ProductUnit extends  GenericEntity implements IProductCategory {
    private int id;
    private int type;
    private String name;
    private String shortName;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
