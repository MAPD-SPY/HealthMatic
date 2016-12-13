package com.spy.healthmatic.Global;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.google.gson.Gson;
import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.API.StaffAPI;
import com.spy.healthmatic.DB.StaffDB;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Nurse;
import com.spy.healthmatic.Model.Staff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Team Name: Team SPY
 * Created by yatin on 28/10/16.
 */

public class GlobalFunctions implements GlobalConst {

    public static ArrayList<Doctor> getDummyDoctors(int maxCount) {
        ArrayList<Doctor> doctors = new ArrayList<>();
//        for (int i=0; i<maxCount; i++){
//            if(i%2==0) {
//                doctors.add(new Doctor("Doctor "+i, "male", "Heart"));
//            }else{
//                doctors.add(new Doctor("Doctor "+i, "female", "Brain"));
//            }
//        }
        return doctors;
    }

    public static ArrayList<Nurse> getDummyNurses(int maxCount) {
        ArrayList<Nurse> nurses = new ArrayList<>();
//        for (int i=0; i<maxCount; i++){
//            if(i%2==0) {
//                nurses.add(new Nurse("Nurse "+i, "male", i));
//            }else{
//                nurses.add(new Nurse("Nurse "+i, "female", i));
//            }
//        }
        return nurses;
    }

    public static ArrayList<com.spy.healthmatic.Model.Patient> getPatientJSONArray(Context context) {
        JSONObject response;
        JSONArray patientJsonResults;
        ArrayList<com.spy.healthmatic.Model.Patient> patients = new ArrayList<>();
        try {
            response = new JSONObject(loadJSONFromAsset(context));
            patientJsonResults = response.getJSONArray("patients");
            patients.addAll(com.spy.healthmatic.Model.Patient.fromJSONArray(patientJsonResults));
            Log.d("DEBUG", patients.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static String loadJSONFromAsset(Context context) {
        String json;
        AssetManager assetManager = context.getAssets();
        InputStream input;
        try {
            input = assetManager.open("patients.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static String getTodaysDateFormatted() {
        java.text.SimpleDateFormat formattedDt = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formattedDt.format(new Date());
    }

    public static String getStaffJson(Staff staff) {
        return new Gson().toJson(staff);
    }

    public static Staff getStaff(String json) {
        if (json == null)
            return null;
        else {
            return new Gson().fromJson(json, Staff.class);
        }
    }

    public static boolean checkDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {

            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(GlobalConst.DATABASE_NAME).toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    public static void upgrade_tables(Context con, SQLiteDatabase db) {
        new StaffDB(con).onUpgrade(db, 0, 0);
        db.close();
    }

    public static Staff getStaff(Context context) {
        return new StaffDB(context).getStaff();
    }

    public static String getCurrentDateInMilliseconds() {
        return new Date().getTime() + "";
    }

}
