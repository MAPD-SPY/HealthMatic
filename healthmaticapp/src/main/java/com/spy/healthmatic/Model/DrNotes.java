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

public class DrNotes implements Serializable{
    private String date;
    private String notes;
    private String diagnosedByName;
    private String drId;

    public DrNotes() {}

    public DrNotes(JSONObject jsonObject) throws JSONException {
        this.date = jsonObject.getString("date");
        this.notes = jsonObject.getString("notes");
        this.diagnosedByName = jsonObject.getString("diagnosedByName");
    }

    public static ArrayList<DrNotes> fromJSONArray(JSONArray jsonArray) {
        ArrayList<DrNotes> results = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            try {
                results.add(new DrNotes(jsonArray.getJSONObject(x)));
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnosedByName() {
        return diagnosedByName;
    }

    public void setDiagnosedByName(String diagnosedByName) {
        this.diagnosedByName = diagnosedByName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
