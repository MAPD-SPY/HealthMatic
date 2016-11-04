package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Person implements Serializable{

    public static final boolean FEMALE = false;
    public static final boolean MALE = true;

    private String firstName;
    private String lastName;
    private String birthday;
    private boolean gender;
    private Address address;
    private Contact contact;
    private String maritalStatus;

    public Person(String firstName, String lastName, boolean gender, String birthday,
                  Address address, Contact contact, String maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
