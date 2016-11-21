package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/10/16.
 */

public class Nurse implements Serializable{
    private String name;
    private String gender;
    private int floor;

    public Nurse(){}

    public Nurse(String name, String gender, int floor){
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
