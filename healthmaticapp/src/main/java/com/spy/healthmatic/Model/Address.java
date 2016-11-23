package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Address implements Serializable{
    private String street;
    private String city;
    private String province;
    private String zipCode;

    public Address() {

    }

    public Address(String street, String city, String province, String zipCode) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.zipCode = zipCode;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
