package br.com.tercom.Entity;

import java.util.Calendar;

public class OrderAcceptanceProduct extends GenericEntity {
    private static final int MIN_NAME_LEN = 2;
    private static final int MAX_NAME_LEN = 64;
    private static final int MIN_AMOUNT = 1;
    private static final int MAX_AMOUNT = 999;
    private static final double MIN_PRICE = 0.01;
    private static final double MAX_PRICE = 99999.99;

    private int id;
    private int idQuotedProductPrice;
    private Product product;
    private Provider provider;
    private Manufacture manufacturer;
    private ProductPackage productPackage;
    private ProductType productType;
    private String name;
    private int amount;
    private double price;
    private String lastUpdate;

    public OrderAcceptanceProduct() {
        id = 0;
        amount = 0;
        price = 0;
        lastUpdate = Calendar.getInstance().getTime().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuotedProductPrice() {
        return idQuotedProductPrice;
    }

    public void setIdQuotedProductPrice(int idQuotedProductPrice) {
        this.idQuotedProductPrice = idQuotedProductPrice;
    }

    public Product getProduct() {
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

    public Manufacture getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacture manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
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

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getProductId() {
        return this.product == null ? 0 : this.product.getId();
    }

    public int getProviderId() {
        return this.provider == null ? 0 : this.provider.getId();
    }

    public int getManufacturerId() {
        return this.manufacturer == null ? 0 : this.manufacturer.getId();
    }

    public int getProductPackageId() {
        return this.productPackage == null ? 0 : this.productPackage.getId();
    }

    public int getProductTypeId() {
        return this.productType == null ? 0 : this.productType.getId();
    }
}
