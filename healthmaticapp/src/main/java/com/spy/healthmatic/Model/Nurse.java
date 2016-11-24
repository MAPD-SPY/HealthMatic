package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/10/16.
 */

public class Nurse implements Serializable{
    private String id;
    private String name;
    private String gender;
    private String floor;

    public Nurse(){}

    public Nurse(String id, String name, String gender, String floor){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.floor = floor;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
