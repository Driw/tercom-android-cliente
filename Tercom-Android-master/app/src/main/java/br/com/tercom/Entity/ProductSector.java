package br.com.tercom.Entity;

import br.com.tercom.Interface.IProductCategory;

public class ProductSector extends GenericEntity implements IProductCategory{

    private int id;
    private String name;
    private int type;
    private int idProductSubGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if(name == null)
            return "Não selecionado";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdProductSubGroup() {
        return idProductSubGroup;
    }

    public void setIdProductSubGroup(int idProductSubGroup) {
        this.idProductSubGroup = idProductSubGroup;
    }
}
