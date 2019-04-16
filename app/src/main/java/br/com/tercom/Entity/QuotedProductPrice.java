package br.com.tercom.Entity;

import java.util.Date;

public class QuotedProductPrice extends GenericEntity {
    /**
     * @var int código de identificação único do preço de produto cotado.
     */
    private int id;
    /**
     * @var Product objeto do tipo produto do qual o preço pertence.
     */
    private Product product;
    /**
     * @var Provider objeto do tipo fornecedor que oferece o preço.
     */
    private Provider provider;
    /**
     * @var Manufacturer objeto do tipo fabricante que oferece o preço.
     */
    private Manufacture manufacturer;
    /**
     * @var ProductPackage objeto do tipo embalagem de produto que oferece o preço.
     */
    private ProductPackage productPackage;
    /**
     * @var ProductType objeto do tipo tipo de produto que oferece o preço.
     */
    private ProductType productType;
    /**
     * @var string nome de exibição no preço do produto.
     */
    private String name;
    /**
     * @var int quantidade do produto oferecido pelo preço.
     */
    private int amount;
    /**
     * @var float preço total para aquisição do produto.
     */
    private float price;
    /**
     * @var DateTime horário da última atualização do preço de produto cotado.
     */
    private Date lastUpdate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
