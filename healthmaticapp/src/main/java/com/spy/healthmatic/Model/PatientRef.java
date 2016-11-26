package com.spy.healthmatic.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-22.
 */

public class PatientRef implements Serializable{
    private String id;
    private String patientId;
    private String[] checkupDates;

    public PatientRef() {
    }

    public PatientRef(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("_id");
        this.patientId = jsonObject.getString("patientId");
        this.checkupDates = setCheckupDates(jsonObject.getJSONArray("checkupDates"));
    }

    private String[] setCheckupDates(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] checkups = new String[length];
        for (int i = 0; i < length; i++) {
            checkups[i] = jsonArray.get(i).toString();
        }
        return checkups;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientID) {
        this.patientId = patientID;
    }

    public String[] getCheckupDates() {
        return checkupDates;
    }

    public void setCheckupDates(String[] checkupDates) {
        this.checkupDates = checkupDates;
    }
}
