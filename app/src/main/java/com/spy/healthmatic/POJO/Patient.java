package com.spy.healthmatic.POJO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yatin on 28/10/16.
 */

public class Patient implements Serializable{
    private String name;
    private String gender;
    private String roomNumber;
    private String condition;
    private int emergencyContactNumber;
    private String department;
    private Doctor mainDoctor;
    private ArrayList<Doctor> doctors;
    private ArrayList<Nurse> nurses;

    public Patient(){}

    public Patient(String name, String gender, String roomNumber, String condition, int emergencyContactNumber, String department,
                   Doctor doctor, ArrayList<Doctor> doctors, ArrayList<Nurse> nurses){
        this.name = name;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.condition = condition;
        this.emergencyContactNumber = emergencyContactNumber;
        this.department = department;
        this.mainDoctor = doctor;
        this.doctors = doctors;
        this.nurses = nurses;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(int emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Doctor getMainDoctor() {
        return mainDoctor;
    }

    public void setMainDoctor(Doctor mainDoctor) {
        this.mainDoctor = mainDoctor;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(ArrayList<Nurse> nurses) {
        this.nurses = nurses;
    }
}
