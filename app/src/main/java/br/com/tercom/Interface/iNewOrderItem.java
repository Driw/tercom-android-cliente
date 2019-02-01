package br.com.tercom.Interface;

import br.com.tercom.Entity.Provider;

public interface iNewOrderItem {
    public int getId();
    public Provider getProvider();
    public boolean isBetterPrice();
    public String getObservations();
    public String getName();

}
