package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;
import br.com.tercom.Interface.IProductCategory;

public class  ProductSubGroup extends GenericEntity implements IProductCategory{
    
    private int id;
    private String name;
    private int type;
    private int idProductGroup;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<ProductSector>  productSectores;

    
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

    public int getIdProductGroup() {
        return idProductGroup;
    }

    public void setIdProductGroup(int idProductGroup) {
        this.idProductGroup = idProductGroup;
    }

    public ArrayList<ProductSector> getProductSectores() {
        return productSectores;
    }

    public void setProductSectores(ArrayList<ProductSector> productSectores) {
        this.productSectores = productSectores;
    }
}
