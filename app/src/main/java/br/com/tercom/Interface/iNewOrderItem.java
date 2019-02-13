package br.com.tercom.Interface;

import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.Provider;

public interface iNewOrderItem {
    public int getId();
    public String getName();
    public Provider getProvider();
    public boolean isBetterPrice();
    public String getObservations();
    public Manufacture getManufacturer();

}
