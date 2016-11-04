package com.spy.healthmatic.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shelalainechan on 2016-11-03.
 */

public class LabTestType {
    private String name;
    private String description;

    public LabTestType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public LabTestType(JSONObject jsonObject) throws JSONException {

        this.name = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
    }

    public static ArrayList<LabTestType> fromJSONArray(JSONArray jsonArray) {
        ArrayList<LabTestType> labTestTypes = new ArrayList<LabTestType>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                labTestTypes.add(new LabTestType(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return labTestTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
