package br.com.tercom.Entity;

import java.util.Arrays;

public class Address extends GenericEntity {
    private static final int MIN_CITY_LEN = 3;
    private static final int MAX_CITY_LEN = 48;
    private static final int CEP_LEN = 8;
    private static final int MIN_NEIGHBORHOOD_LEN = 3;
    private static final int MAX_NEIGHBORHOOD_LEN = 32;
    private static final int MIN_STREET_LEN = 3;
    private static final int MAX_STREET_LEN = 32;
    private static final int MIN_NUMBER = 00001;
    private static final int MAX_NUMBER = 99999;
    private static final int MAX_COMPLEMENT_LEN = 24;

    private int id;
    private String state;
    private String city;
    private String cep;
    private String neighborhood;
    private String street;
    private int number;
    private String complement;

    public Address() {
        id = 0;
        state = "";
        city = "";
        cep = "";
        neighborhood = "";
        street = "";
        number = 0;
    }

    public static int getCepLen() {
        return CEP_LEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(!Arrays.asList(UFs).contains(state))
            throw new IllegalArgumentException("UF inválido");
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city.length() < MIN_CITY_LEN || city.length() > MAX_CITY_LEN)
            throw new IllegalArgumentException(String.format("Cidade deve possuir de %d a %d caracteres", MIN_CITY_LEN, MAX_CITY_LEN));
        this.city = city;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if(cep.length() != CEP_LEN)
            throw new IllegalArgumentException(String.format("CEP deve possuir %d caracteres", CEP_LEN));
        this.cep = cep;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        if(neighborhood.length() < MIN_NEIGHBORHOOD_LEN || neighborhood.length() > MAX_NEIGHBORHOOD_LEN)
            throw new IllegalArgumentException(String.format("Bairro deve possuir de %d a %d caracteres", MIN_NEIGHBORHOOD_LEN, MAX_NEIGHBORHOOD_LEN));
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if(street.length() < MIN_STREET_LEN || street.length() > MAX_STREET_LEN)
            throw new IllegalArgumentException(String.format("Rua deve possuir de %d a %d caracteres", MIN_STREET_LEN, MAX_STREET_LEN));
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if(number < MIN_NUMBER || number > MAX_NUMBER)
            throw new IllegalArgumentException(String.format("Número do edifício deve ser de %d a %d", MIN_NUMBER, MAX_NUMBER));
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        if(complement.length() > MAX_COMPLEMENT_LEN)
            throw new IllegalArgumentException(String.format("Complemento deve possuir até %d caracteres", MAX_COMPLEMENT_LEN));
        this.complement = complement;
    }

    private static final String[] UFs = {
            "AC",
            "AL",
            "AP",
            "AM",
            "BA",
            "CE",
            "DF",
            "ES",
            "GO",
            "MA",
            "MT",
            "MS",
            "MG",
            "PA",
            "PB",
            "PR",
            "PE",
            "PI",
            "RJ",
            "RN",
            "RS",
            "RO",
            "RR",
            "SC",
            "SP",
            "SE",
            "TO"
    };
}
