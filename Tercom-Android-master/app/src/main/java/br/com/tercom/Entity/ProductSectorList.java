package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class ProductSectorList extends GenericEntity{

    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductSector>  list;

    public ArrayList<ProductSector> getList() {
        return list;
    }

    public void setList(ArrayList<ProductSector> productSectores) {
        this.list = productSectores;
    }
}
