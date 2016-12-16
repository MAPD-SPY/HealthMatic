package com.spy.healthmatic.Model;

//Team Name: Team SPY

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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

    public Doctor(String id, String name, String gender, String speciality){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.speciality = speciality;
    }

    public Doctor(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.gender = jsonObject.getString("gender");
        this.speciality = jsonObject.getString("specialty");
    }

    public static ArrayList<Doctor> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Doctor> results = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            try {
                results.add(new Doctor(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
