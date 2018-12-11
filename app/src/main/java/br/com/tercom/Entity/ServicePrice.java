package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class ServicePrice extends GenericEntity {
    private int id;
    @BindObject()
    private Services service;
    @BindObject()
    private Provider provider;
    private String name;
    private String additionalDescription;
    private Float price;

    public ServicePrice()
    {
        id = 0;
        service = new Services();
        provider = new Provider();
        name = "";
        additionalDescription = "";
        price = 0f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
