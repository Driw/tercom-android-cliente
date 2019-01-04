package br.com.tercom.Enum;

public enum PhoneType {
    CELLPHONE("cellphone"),
    COMMERCIAL("commercial"),
    RESIDENTIAL("residential");

    public String type;

    private PhoneType(String type) {
        this.type = type;
    }
}
