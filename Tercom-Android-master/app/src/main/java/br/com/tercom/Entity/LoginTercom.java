package br.com.tercom.Entity;

import br.com.tercom.Annotation.BindObject;

public class LoginTercom extends GenericEntity {

    @BindObject
    private TercomEmployee tercomEmployee;

    private int id;
    private String token;
    private boolean logout;
    private String ipAddress;
    private String browser;
    @BindObject
    private Register register;
    @BindObject
    private Expiration expiration;

    public TercomEmployee getTercomEmployee(){
        return this.tercomEmployee;
    }

    public void setTercomEmployee(TercomEmployee tercomEmployee) {
        this.tercomEmployee = tercomEmployee;
    }

    public int getTercomEmployeeId(){
        return this.tercomEmployee.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Expiration getExpiration() {
        return expiration;
    }

    public void setExpiration(Expiration expiration) {
        this.expiration = expiration;
    }
}
