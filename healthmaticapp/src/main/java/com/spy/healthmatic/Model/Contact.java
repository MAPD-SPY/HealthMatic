package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Contact implements Serializable{
    private String phone;
    private String email;
    private String emergencyContactName;
    private String emergencyContactNumber;

    public Contact() {

    }

    public Contact(String phone, String email, String emergencyContactName, String emergencyContactNumber) {
        this.phone = phone;
        this.email = email;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }
}
