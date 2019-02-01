package br.com.tercom.Entity;

import java.util.Date;

import br.com.tercom.Annotation.BindObject;

public class CustomerEmployee extends GenericEntity{
    private int id;
    @BindObject
    private CustomerProfile customerProfile;
    private String cpf;
    private String name;
    private String email;
    private String password;
    @BindObject
    private Phone phone;
    @BindObject
    private Phone cellphone;
    private boolean enabled;
    @BindObject
    private Register register;

    public CustomerEmployee(){
        id = 0;
        customerProfile = new CustomerProfile();
        cpf = "";
        name = "";
        email = "";
        password = "";
        phone = new Phone();
        cellphone = new Phone();
        enabled = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Phone getCellphone() {
        return cellphone;
    }

    public void setCellphone(Phone cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isEnable() {
        return enabled;
    }

    public void setEnable(boolean enable) {
        this.enabled = enable;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
