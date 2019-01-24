package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class OrderItemService extends GenericEntity {

    public static final int MAX_OBSERVATIONS_LEN = 128;
    private int id;
    @BindObject
    private Services service;
    private Provider provider;
    private boolean betterPrice;
    private String observations;

    public OrderItemService(){
        id = 0;
        betterPrice = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Services getService() {
        if(service == null)
            service = new Services();
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Provider getProvider() {
        if(provider == null)
            provider = new Provider();
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean isBetterPrice() {
        return betterPrice;
    }

    public void setBetterPrice(boolean betterPrice) {
        this.betterPrice = betterPrice;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
