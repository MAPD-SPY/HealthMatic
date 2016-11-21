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

public class Vitals implements Serializable{
    private String date;
    private String takenByName;
    private int systolic;
    private int diastolic;
    private int heartRate;
    private int temperature;
    private int respirationRate;

    public Vitals() {

    }

    public Vitals(JSONObject jsonObject) throws JSONException {
        this.date = jsonObject.getString("date");
        this.takenByName = jsonObject.getString("takenByName");
        this.systolic = jsonObject.getInt("systolic");
        this.diastolic = jsonObject.getInt("diastolic");
        this.heartRate = jsonObject.getInt("heartRate");
        this.temperature = jsonObject.getInt("temperature");
        this.respirationRate = jsonObject.getInt("respirationRate");
    }

    public static ArrayList<Vitals> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Vitals> results = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            try {
                results.add(new Vitals(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public int getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(int respirationRate) {
        this.respirationRate = respirationRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTakenByName() {
        return takenByName;
    }

    public void setTakenByName(String takenByName) {
        this.takenByName = takenByName;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
