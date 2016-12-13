package com.spy.healthmatic.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class LabTest implements Serializable {
    private String _id;
    private String requestDate;
    private String requestedByName;
    private String testType;
    private String sampleTakenDate;
    private String sampleTakenByName;
    private String imageResult;
    private String status;

    public LabTest() {

    }

    public LabTest(JSONObject jsonObject) throws JSONException {
        this.requestDate = jsonObject.getString("requestDate");
        this.requestedByName = jsonObject.getString("requestedByName");
        this.testType = jsonObject.getString("testType");
        this.sampleTakenDate = jsonObject.getString("sampleTakenDate");
        this.sampleTakenByName = jsonObject.getString("sampleTakenByName");
        this.status = jsonObject.getString("status");
        this.imageResult = jsonObject.getString("imageResult");
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
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
