package br.com.tercom.Entity;

import java.util.TreeMap;

public class ProductSend extends GenericEntity {
    private String name;
    private String description;
    private String utility;
    private Integer idProductUnit;
    private Integer idProductFamily;
    private Integer idProductGroup;
    private Integer idProductSubGroup;
    private Integer idProductSector;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public int getIdProductUnit() {
        return idProductUnit;
    }

    public void setIdProductUnit(int idProductUnit) {
        this.idProductUnit = idProductUnit;
    }

    public int getIdProductFamily() {
        return idProductFamily;
    }

    public void setIdProductFamily(int idProductFamily) {
        this.idProductFamily = idProductFamily;
    }

    public int getIdProductGroup() {
        return idProductGroup;
    }

    public void setIdProductGroup(int idProductGroup) {
        this.idProductGroup = idProductGroup;
    }

    public int getIdProductSubGroup() {
        return idProductSubGroup;
    }

    public void setIdProductSubGroup(int idProductSubGroup) {
        this.idProductSubGroup = idProductSubGroup;
    }

    public int getIdProductSector() {
        return idProductSector;
    }

    public void setIdProductSector(int idProductSector) {
        this.idProductSector = idProductSector;
    }

    public TreeMap<String,String> getTreeMap() {
        String[] arrayNames ={"idProductUnit","idProductFamily","idProductGroup","idProductSubGroup","idProductSector"};
        Integer[] arrayValues={idProductUnit,idProductFamily,idProductGroup,idProductSubGroup,idProductSector};
        TreeMap<String,String> treeMap = new TreeMap<>();
        for(int i = 0; i<arrayValues.length;i++){
            if(arrayValues[i] != null && arrayValues[i] != 0)
                treeMap.put(arrayNames[i],String.valueOf(arrayValues[i]));
        }
        return treeMap;
    }
}

