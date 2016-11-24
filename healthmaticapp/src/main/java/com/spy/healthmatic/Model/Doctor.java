package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/10/16.
 */

public class Doctor implements Serializable {
    private int id;
    private String name;
    private String gender;
    private String speciality;

    public Doctor(){

    }

    public Doctor(String name, String gender, String speciality){
        this.id = 1;
        this.name = name;
        this.gender = gender;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
