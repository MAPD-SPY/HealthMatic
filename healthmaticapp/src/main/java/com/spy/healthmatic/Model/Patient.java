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

public class Patient extends Person implements Serializable {

    private String _id;
    private int weight;
    private int height;
    private String bloodType;
    private String occupation;
    private String admissionDate;
    private String dischargedDate;
    private String condition;
    private int room;
    private String[] allergies;
    private ArrayList<LabTest> labTests;
    private ArrayList<Prescription> prescriptions;
    private ArrayList<Vitals> vitals;
    private ArrayList<DrNotes> drNotes;
    private ArrayList<Nurse> nurses;
    private ArrayList<Doctor> doctors;
    private Insurance insurance;

    public Patient() {
    }

    public Patient(JSONObject jsonObject) throws JSONException {

        super(jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getBoolean("gender"),
                jsonObject.getString("birthday"),
                jsonObject.getJSONObject("address"),
                jsonObject.getJSONObject("contact"),
                jsonObject.getBoolean("maritalStatus"));

//        // Set the address
//        this.setAddress(newAddress(jsonObject.getJSONObject("address")));
//        // Set the contact info
//        this.setContact(newContact(jsonObject.getJSONObject("contact")));

        this._id = jsonObject.getString("_id");

        this.weight = jsonObject.getInt("weight");
        this.height = jsonObject.getInt("height");
        this.bloodType = jsonObject.getString("bloodType");
        this.occupation = jsonObject.getString("occupation");
        this.condition = jsonObject.getString("condition");
        this.room = jsonObject.getInt("room");
        this.admissionDate = jsonObject.getString("admissionDate");
        this.dischargedDate = jsonObject.getString("dischargedDate");

        JSONArray patientJsonResults;
        try {
            // Add all prescriptions
            patientJsonResults = jsonObject.getJSONArray("prescriptions");
            prescriptions = new ArrayList<>();
            this.prescriptions.addAll(fromPrescriptionJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all vitals
            patientJsonResults = jsonObject.getJSONArray("vitals");
            vitals = new ArrayList<>();
            this.vitals.addAll(fromVitalsJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all laboratory tests
            patientJsonResults = jsonObject.getJSONArray("labTests");
            labTests = new ArrayList<>();
            this.labTests.addAll(fromLabTestJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all doctors
            patientJsonResults = jsonObject.getJSONArray("doctors");
            doctors = new ArrayList<>();
            this.doctors.addAll(fromDoctorJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all nurses
            patientJsonResults = jsonObject.getJSONArray("nurses");
            nurses = new ArrayList<>();
            this.nurses.addAll(fromNurseJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all notes
            patientJsonResults = jsonObject.getJSONArray("drNotes");
            drNotes = new ArrayList<>();
            this.drNotes.addAll(fromDrNotesJSONArray(patientJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Add all notes
            patientJsonResults = jsonObject.getJSONArray("drNotes");
            drNotes = new ArrayList<>();
            this.drNotes.addAll(fromDrNotesJSONArray(patientJsonResults));
            // Add insurance
            insurance = new Insurance(jsonObject.getJSONObject("insurance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Patient> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Patient> patients = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                patients.add(new Patient(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return patients;
    }

    private Contact newContact(JSONObject jsonObject) throws JSONException {

        Contact contact= new Contact(jsonObject.getString("phone"),
                jsonObject.getString("email"),
                jsonObject.getString("emergencyContactName"),
                jsonObject.getString("emergencyContactNumber"));
        return contact;
    }

    private Address newAddress(JSONObject jsonObject) throws JSONException {

        Address address = new Address(jsonObject.getString("street"),
                jsonObject.getString("city"),
                jsonObject.getString("province"),
                jsonObject.getString("zipCode"));
        return address;
    }

    private static ArrayList<Prescription> fromPrescriptionJSONArray(JSONArray jsonArray) {
        ArrayList<Prescription> prescriptions = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                prescriptions.add(new Prescription(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return prescriptions;
    }

    private static ArrayList<Vitals> fromVitalsJSONArray(JSONArray jsonArray) {
        ArrayList<Vitals> vitals = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                vitals.add(new Vitals(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return vitals;
    }

    private static ArrayList<LabTest> fromLabTestJSONArray(JSONArray jsonArray) {
        ArrayList<LabTest> labTests = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                labTests.add(new LabTest(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return labTests;
    }

    private static ArrayList<DrNotes> fromDrNotesJSONArray(JSONArray jsonArray) {
        ArrayList<DrNotes> drNotes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                drNotes.add(new DrNotes(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return drNotes;
    }

    private static ArrayList<Doctor> fromDoctorJSONArray(JSONArray jsonArray) {
        ArrayList<Doctor> doctors = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                doctors.add(new Doctor(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return doctors;
    }

    private static ArrayList<Nurse> fromNurseJSONArray(JSONArray jsonArray) {
        ArrayList<Nurse> nurses = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                nurses.add(new Nurse(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return nurses;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargedDate() {
        return dischargedDate;
    }

    public void setDischargedDate(String dischargedDate) {
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

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(ArrayList<Nurse> nurses) {
        this.nurses = nurses;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
