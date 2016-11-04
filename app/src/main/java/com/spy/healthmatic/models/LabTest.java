package com.spy.healthmatic.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class LabTest implements Serializable {
    private String requestDate;
    private String requestedByName;
    private String type;
    private String sampleTakenDate;
    private String sampleTakenByName;
    private String imageResult;
    private String status;

    public LabTest() {

    }

    public LabTest(JSONObject jsonObject) throws JSONException {
        this.requestDate = jsonObject.getString("date");
        this.requestedByName = jsonObject.getString("requestedByName");
        this.type = jsonObject.getString("type");
        this.sampleTakenDate = jsonObject.getString("sampleTakenDate");
        this.sampleTakenByName = jsonObject.getString("sampleTakenByName");
        this.status = jsonObject.getString("status");
        this.imageResult = jsonObject.getString("imageResult");

    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestedByName() {
        return requestedByName;
    }

    public void setRequestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSampleTakenDate() {
        return sampleTakenDate;
    }

    public void setSampleTakenDate(String sampleTakenDate) {
        this.sampleTakenDate = sampleTakenDate;
    }

    public String getSampleTakenByName() {
        return sampleTakenByName;
    }

    public void setSampleTakenByName(String sampleTakenByName) {
        this.sampleTakenByName = sampleTakenByName;
    }

    public String getImageResult() {
        return imageResult;
    }

    public void setImageResult(String imageResult) {
        this.imageResult = imageResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
