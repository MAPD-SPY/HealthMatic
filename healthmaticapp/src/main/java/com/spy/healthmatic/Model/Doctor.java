package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/10/16.
 */

public class Doctor implements Serializable {
    private String id;
    private String name;
    private String gender;
    private String speciality;

    public Doctor(){

    }

    public Doctor(String name, String gender, String speciality){
        this.name = name;
        this.gender = gender;
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
