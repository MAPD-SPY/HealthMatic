package com.spy.healthmatic.Model;

//Team Name: Team SPY

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yatin on 21/11/16.
 */

public class Insurance implements Serializable{
    private String name;
    private String expiryDate;

    public Insurance(){}
    public Insurance(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.expiryDate = jsonObject.getString("expiryDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
