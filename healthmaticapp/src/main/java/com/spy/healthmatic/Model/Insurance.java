package com.spy.healthmatic.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yatin on 21/11/16.
 */

public class Insurance {
    private String name;
    private String expiryDate;

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
