package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;
import br.com.tercom.Interface.iNewOrderItem;

public class OrderItemService extends GenericEntity implements iNewOrderItem {

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

    @Override
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

    @Override
    public Provider getProvider() {
        if(provider == null)
            provider = new Provider();
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean isBetterPrice() {
        return betterPrice;
    }

    public void setBetterPrice(boolean betterPrice) {
        this.betterPrice = betterPrice;
    }

    @Override
    public String getObservations() {
        return observations;
    }

    @Override
    public Manufacture getManufacturer() {
        return null;
    }

    @Override
    public String getName() {
        if (service != null) {
            return service.getName();
        } else {
            return "";
        }
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
