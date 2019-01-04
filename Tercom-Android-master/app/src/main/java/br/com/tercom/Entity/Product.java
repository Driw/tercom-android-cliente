package br.com.tercom.Entity;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import br.com.tercom.Annotation.BindObject;

public class Product extends GenericEntity {

    private int id;
    private String name;
    private String description;
    private String utility;
    private boolean inactive;
    @BindObject
    private ProductUnit unit;
    @BindObject
    private Category category;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Price> prices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }
}

