package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class OrderAcceptance extends GenericEntity {
    private static final int OAS_APPROVING = 0;
    private static final int OAS_APPROVED = 1;
    private static final int OAS_REQUEST = 2;
    private static final int OAS_PAID = 3;
    private static final int OAS_ON_THE_WAY = 4;

    private int id;
    @BindObject()
    private OrderQuote orderQuote;
    @BindObject()
    private CustomerEmployee customerEmployee;
    @BindObject()
    private TercomEmployee tercomEmployee;
    @BindObject()
    private Address address;
    private int status;
    private String statusDescription;
    private String observations;
    private String register;

    public OrderAcceptance() {
        id = 0;
        orderQuote = new OrderQuote();
        customerEmployee = new CustomerEmployee();
        tercomEmployee = new TercomEmployee();
        address = new Address();
        status = OAS_APPROVING;
        statusDescription = "";
        observations = "";
        //TODO: Definir valor inicial para register igual à data atual em String.
    }
}

