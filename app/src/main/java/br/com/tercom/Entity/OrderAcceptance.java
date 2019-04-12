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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderQuote getOrderQuote() {
        return orderQuote;
    }

    public void setOrderQuote(OrderQuote orderQuote) {
        this.orderQuote = orderQuote;
    }

    public CustomerEmployee getCustomerEmployee() {
        return customerEmployee;
    }

    public void setCustomerEmployee(CustomerEmployee customerEmployee) {
        this.customerEmployee = customerEmployee;
    }

    public TercomEmployee getTercomEmployee() {
        return tercomEmployee;
    }

    public void setTercomEmployee(TercomEmployee tercomEmployee) {
        this.tercomEmployee = tercomEmployee;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}

