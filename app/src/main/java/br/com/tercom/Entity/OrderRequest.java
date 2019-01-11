package br.com.tercom.Entity;

import java.util.Calendar;

public class OrderRequest extends GenericEntity {
    public final Float MIN_BUDGET = 0f;
    public final int ORS_NONE = 0;
    public final int ORS_CANCEL_BY_CUSTOMER = 1;
    public final int ORS_CANCEL_BY_TERCOM = 2;
    public final int ORS_QUEUED = 3;
    public final int ORS_QUOTING = 4;
    public final int ORS_QUOTED = 5;
    public final int ORS_DONE = 6;

    private int id;
    private Float budget;
    private int status;
    private String statusMessage;
    private String register;
    private String expiration;
    private CustomerEmployee customerEmployee;
    private TercomEmployee tercomEmployee;

    public int getId() {
        return id;
    }

    public OrderRequest setId(int id) {
        this.id = id;
        return this;
    }

    public Float getBudget() {
        return budget;
    }

    public OrderRequest setBudget(Float budget) {
        if(budget < MIN_BUDGET)
            throw new NumberFormatException(String.format("Orçamento não pode ser inferior a R$ %.2f", MIN_BUDGET));
        this.budget = budget;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public OrderRequest setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public OrderRequest setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public String getRegister() {
        return register;
    }

    public OrderRequest setRegister(String register) {
        this.register = register;
        return this;
    }

    public void setRegisterCurrent(){
        register = String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public String getExpiration() {
        return expiration;
    }

    public OrderRequest setExpiration(String expiration) {
        //TODO: Rever - Não entendi como funciona
        this.expiration = expiration;
        return this;
    }

    public CustomerEmployee getCustomerEmployee() {
        if(customerEmployee == null) {
            customerEmployee = new CustomerEmployee();
            customerEmployee.setId(0);
        }
        return customerEmployee;
    }

    public OrderRequest setCustomerEmployee(CustomerEmployee customerEmployee) {
        this.customerEmployee = customerEmployee;
        return this;
    }

    public TercomEmployee getTercomEmployee() {
        if(tercomEmployee == null) {
            tercomEmployee = new TercomEmployee();
            tercomEmployee.setId(0);
        }
        return tercomEmployee;
    }

    public OrderRequest setTercomEmployee(TercomEmployee tercomEmployee) {
        this.tercomEmployee = tercomEmployee;
        return this;
    }
}
