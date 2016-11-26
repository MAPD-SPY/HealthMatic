package com.spy.healthmatic.Model;

import java.util.ArrayList;

import com.google.gson.Gson;

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
    private String _id;
    private String username;
    private String password;
    private String fingerKey;
    private boolean isLoggedIn;
    private String role;
    private String floor;
    private String[] specialty;
    private ArrayList<PatientRef> patientRefs;

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

        this._id = jsonObject.getString("_id");
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFingerKey() {
        return fingerKey;
    }

    public void setFingerKey(String fingerKey) {
        this.fingerKey = fingerKey;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
