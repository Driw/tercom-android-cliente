package br.com.tercom.Entity;

import java.util.ArrayList;

import br.com.tercom.Annotation.BindObject;

public class Customer extends GenericEntity {

    private int id;
    private String stateRegistry;
    private String cnpj;
    private String companyName;
    private String fantasyName;
    private String email;
    @BindObject(type = BindObject.TYPE.LIST)
    private ArrayList<Phone> phones;
    //@BindObject(type = BindObject.TYPE.LIST)
    //TODO Addresses
    private boolean inactive;
    @BindObject
    private Register register;

    public Customer(){
        id = 0;
        phones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStateRegistry() {
        return stateRegistry;
    }

    public void setStateRegistry(String stateRegistry) {
        this.stateRegistry = stateRegistry;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Phone> phones) {
        this.phones = phones;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
