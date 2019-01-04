package br.com.tercom.Entity;

import java.util.Date;

import br.com.tercom.Annotation.BindObject;

public class TercomEmployee extends GenericEntity{
    private int id;
    @BindObject
    private TercomProfile tercomProfile;
    private String cpf;
    private String name;
    private String email;
    private String password;
    @BindObject
    private Phone phone;
    @BindObject
    private Phone cellphone;
    private boolean enable;
    private Date register;

    public TercomEmployee(){
        id = 0;
        tercomProfile = new TercomProfile();
        cpf = "";
        name = "";
        email = "";
        password = "";
        phone = new Phone();
        cellphone = new Phone();
        enable = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TercomProfile getTercomProfile() {
        return tercomProfile;
    }

    public void setTercomProfile(TercomProfile tercomProfile) {
        this.tercomProfile = tercomProfile;
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
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
        this.register = register;
    }
}
