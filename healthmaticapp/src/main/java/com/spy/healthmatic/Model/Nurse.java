package com.spy.healthmatic.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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

    public Nurse(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.gender = jsonObject.getString("gender");
        this.floor = jsonObject.getString("floor");
    }

    public static ArrayList<Nurse> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Nurse> results = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            try {
                results.add(new Nurse(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
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
