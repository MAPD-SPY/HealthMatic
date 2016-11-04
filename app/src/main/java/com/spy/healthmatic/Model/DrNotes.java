package com.spy.healthmatic.Model;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class DrNotes {
    private long date;
    private String notes;
    private String diagnosedByName;

    public DrNotes() {}

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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
}
