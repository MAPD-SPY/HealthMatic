package com.spy.healthmatic.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Person implements Serializable {

    public static final boolean FEMALE = false;
    public static final boolean MALE = true;
    private Address address;
    private Contact contact;
    private String firstName;
    private String lastName;
    private String birthday;
    private boolean gender;
    private boolean maritalStatus;

    public Person() {

    }

    public Person(String firstName, String lastName, boolean gender, String birthday,
                  Address address, Contact contact, Boolean maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.maritalStatus = maritalStatus;
    }

    public Person(String firstName, String lastName, boolean gender, String birthday,
                  JSONObject addressJson, JSONObject contactJson, Boolean maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = newAddress(addressJson);
        this.contact = newContact(contactJson);
        this.maritalStatus = maritalStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Boolean getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    private Contact newContact(JSONObject jsonObject) {
        try {
            Contact contact = new Contact(jsonObject.getString("phone"),
                    jsonObject.getString("email"),
                    jsonObject.getString("emergencyContactName"),
                    jsonObject.getString("emergencyContactNumber"));
            return contact;
        } catch (Exception e) {
            return null;
        }
    }

    private Address newAddress(JSONObject jsonObject) {
        try {
            Address address = new Address(jsonObject.getString("street"),
                    jsonObject.getString("city"),
                    jsonObject.getString("province"),
                    jsonObject.getString("zipCode"));
            return address;
        } catch (Exception e) {
            return null;
        }
    }
}
