package br.com.tercom.Entity;

import android.app.Service;

import java.util.Date;

public class QuotedServicePrice extends GenericEntity {

    /**
     * @var int código de identificação único do preço de serviço cotado.
     */
    private int id;
    /**
     * @var Service objeto do tipo serviço do qual o preço pertence.
     */
    private Service service;
    /**
     * @var Provider objeto do tipo fornecedor que oferece o preço.
     */
    private Provider provider;
    /**
     * @var string nome de exibição no preço do serviço.
     */
    private String name;
    /**
     * @var string dascrição adicional.
     */
    private String additionalDescription;
    /**
     * @var float preço total para aquisição do serviço.
     */
    private float price;
    /**
     * @var DateTime horário da última atualização do preço de serviço cotado.
     */
    private Date lastUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
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
