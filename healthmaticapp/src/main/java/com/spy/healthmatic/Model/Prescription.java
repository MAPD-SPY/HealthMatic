package com.spy.healthmatic.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Prescription implements Serializable{
    private String date;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String prescribedByName;

    public Prescription(JSONObject jsonObject) throws JSONException {
        this.date = jsonObject.getString("date");
        this.medicineName = jsonObject.getString("medicineName");
        this.dosage = jsonObject.getString("dosage");
        this.frequency = jsonObject.getString("frequency");
        this.duration = jsonObject.getString("duration");
        this.prescribedByName = jsonObject.getString("prescribedByName");
    }

    public Prescription() {

    }

    public static ArrayList<Prescription> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Prescription> results = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            try {
                results.add(new Prescription(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrescribedByName() {
        return prescribedByName;
    }

    public void setPrescribedByName(String prescribedByName) {
        this.prescribedByName = prescribedByName;
    }
}
