package com.spy.healthmatic.Model;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Contact {
    private String phone;
    private String email;
    private String emergencyContactName;
    private String getEmergencyContactNumber;

    public Contact() {

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

    public String getGetEmergencyContactNumber() {
        return getEmergencyContactNumber;
    }

    public void setGetEmergencyContactNumber(String getEmergencyContactNumber) {
        this.getEmergencyContactNumber = getEmergencyContactNumber;
    }
}
