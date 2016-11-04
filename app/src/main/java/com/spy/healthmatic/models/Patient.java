package com.spy.healthmatic.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-25.
 */

public class Patient extends Person implements Serializable {

    private int weight;
    private int height;
    private String bloodType;
    private String occupation;
    private long admissionDate;
    private long dischargedDate;
    private String condition;
    private int room;
    private String[] allergies;
    private ArrayList<LabTest> labTests;
    private ArrayList<Prescription> prescriptions;
    private ArrayList<Vitals> vitals;
    private ArrayList<DrNotes> drNotes;
    private ArrayList<String> nurses;
    private ArrayList<String> doctors;

    public Patient(JSONObject jsonObject) throws JSONException {
        super(jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getBoolean("gender"),
                " ", null, null, " ");
        this.condition = jsonObject.getString("condition");
        this.room = jsonObject.getInt("room");

        // Add all prescriptions
        JSONArray patientJsonResults;
        patientJsonResults = jsonObject.getJSONArray("prescriptions");
        prescriptions = new ArrayList<>();
        this.prescriptions.addAll(fromPrescriptionJSONArray(patientJsonResults));

        // Add all tests
        patientJsonResults = jsonObject.getJSONArray("tests");
        labTests = new ArrayList<>();
        this.labTests.addAll(fromLabTestJSONArray(patientJsonResults));
    }

    public static ArrayList<Patient> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Patient> patients = new ArrayList<Patient>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                patients.add(new Patient(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return patients;
    }

    public static ArrayList<Prescription> fromPrescriptionJSONArray(JSONArray jsonArray) {
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                prescriptions.add(new Prescription(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return prescriptions;
    }

    public static ArrayList<LabTest> fromLabTestJSONArray(JSONArray jsonArray) {
        ArrayList<LabTest> labTests = new ArrayList<LabTest>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                labTests.add(new LabTest(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return labTests;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public long getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(long admissionDate) {
        this.admissionDate = admissionDate;
    }

    public long getDischargedDate() {
        return dischargedDate;
    }

    public void setDischargedDate(long dischargedDate) {
        this.dischargedDate = dischargedDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public ArrayList<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(ArrayList<LabTest> labTests) {
        this.labTests = labTests;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public ArrayList<Vitals> getVitals() {
        return vitals;
    }

    public void setVitals(ArrayList<Vitals> vitals) {
        this.vitals = vitals;
    }

    public ArrayList<DrNotes> getDrNotes() {
        return drNotes;
    }

    public void setDrNotes(ArrayList<DrNotes> drNotes) {
        this.drNotes = drNotes;
    }

    public ArrayList<String> getNurses() {
        return nurses;
    }

    public void setNurses(ArrayList<String> nurses) {
        this.nurses = nurses;
    }

    public ArrayList<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<String> doctors) {
        this.doctors = doctors;
    }
}
