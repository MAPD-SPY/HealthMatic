package com.spy.healthmatic.models;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class LabTest {
    private long requestDate;
    private String requestedByName;
    private String type;
    private long sampleTakenDate;
    private String sampleTakenByName;
    private String imageResult;
    private String status;

    public long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(long requestDate) {
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

    public long getSampleTakenDate() {
        return sampleTakenDate;
    }

    public void setSampleTakenDate(long sampleTakenDate) {
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
