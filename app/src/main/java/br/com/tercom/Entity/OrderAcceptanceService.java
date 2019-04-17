package br.com.tercom.Entity;

public class OrderAcceptanceService extends GenericEntity {
    private static final int MIN_NAME_LEN = 2;
    private static final int MAX_NAME_LEN = 64;
    private static final int MIN_AMOUNT = 1;
    private static final int MAX_AMOUNT = 999;
    private static final double MIN_PRICE = 0.01;
    private static final double MAX_PRICE = 99999.99;

    private int id;
    private int idQuotedServicePrice;
    private Services service;
    private Provider provider;
    private String name;
    private String additionalDescription;
    private double price;
    private int amountRequest;
    private double subprice;
    private String observations;
    private String lastUpdate;

    public OrderAcceptanceService() {
        id = 0;
        name = "";
        additionalDescription = "";
        price = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuotedServicePrice() {
        return idQuotedServicePrice;
    }

    public void setIdQuotedServicePrice(int idQuotedServicePrice) {
        this.idQuotedServicePrice = idQuotedServicePrice;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(int amountRequest) {
        this.amountRequest = amountRequest;
    }

    public double getSubprice() {
        return subprice;
    }

    public void setSubprice(double subprice) {
        this.subprice = subprice;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getServiceId() {
        return service == null ? 0 : service.getId();
    }

    public int getProviderId() {
        return provider == null ? 0 : provider.getId();
    }

}
