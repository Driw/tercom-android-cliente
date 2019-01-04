package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class ProductValue extends GenericEntity {

    private int id;
    @BindObject
    private Product product;
    @BindObject
    private Provider provider;
    @BindObject
    private Manufacture manufacture;
    @BindObject
    private ProductPackage productPackage;
    @BindObject
    private ProductType productType;
    @BindObject
    private LastUpdate lastUpdate;
    private String name;
    private int amount;
    private double price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        if(product == null)
            product = new Product();
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public ProductPackage getPackage() {
        return productPackage;
    }

    public void setPackage(ProductPackage _package) {
        this.productPackage = _package;
    }

    public ProductType getType() {
        return productType;
    }

    public void setType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LastUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
