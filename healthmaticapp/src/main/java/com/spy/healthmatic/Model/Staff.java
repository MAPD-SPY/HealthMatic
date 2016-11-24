package com.spy.healthmatic.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-22.
 */

public class Staff extends Person implements Serializable{
    private String id;
    private String role;
    private String floor;
    private String[] specialty;
    private ArrayList<PatientRef> patientRefs;
    private ArrayList<Patient> patients;

    public Staff() {
    }

    public Staff(JSONObject jsonObject) throws JSONException {
        super(jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getBoolean("gender"),
                jsonObject.getString("birthday"),
                jsonObject.getJSONObject("address"),
                jsonObject.getJSONObject("contact"),
                jsonObject.getBoolean("maritalStatus"));

        this.id = jsonObject.getString("_id");
        this.role = jsonObject.getString("role");

        try {
            this.floor = jsonObject.getString("floor");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all patient references
            JSONArray patientJsonResults = jsonObject.getJSONArray("patients");
            patientRefs = new ArrayList<>();
            this.patientRefs.addAll(fromPatientsRefJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<PatientRef> fromPatientsRefJSONArray(JSONArray jsonArray) {
        ArrayList<PatientRef> patientRefs = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                patientRefs.add(new PatientRef(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return patientRefs;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String[] getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String[] specialty) {
        this.specialty = specialty;
    }

    public ArrayList<PatientRef> getPatientRefs() {
        return patientRefs;
    }

    public void setPatientRefs(ArrayList<PatientRef> patientRefs) {
        this.patientRefs = patientRefs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

}
