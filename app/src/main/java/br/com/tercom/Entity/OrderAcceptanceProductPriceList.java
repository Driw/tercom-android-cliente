package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class OrderAcceptanceProductPriceList extends GenericEntity {

    @BindObject(type = BindObject.TYPE.LIST)
    ArrayList<OrderAcceptanceProductPrice> list;

    public ArrayList<OrderAcceptanceProductPrice> getList() {
        return list;
    }

    public void setList(ArrayList<OrderAcceptanceProductPrice> list) {
        this.list = list;
    }
}
