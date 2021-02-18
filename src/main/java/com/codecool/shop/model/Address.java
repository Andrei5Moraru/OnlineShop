package com.codecool.shop.model;

public class Address {

    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;


    public Address (String name, String streetAddr, String city,
                    String state, String zip)
    {
        this.name = name;
        streetAddress = streetAddr;
        this.city = city;
        this.state = state;
        zipCode = zip;
    }


    public String getName() {
        return name;
    }

    public void setName(String theName) {
        this.name = theName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}