package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.PatientApiObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by yatin on 21/11/16.
 */

public interface PatientsListAPI {

    @GET("/patients")
    Call<PatientApiObject> getPatientList();

    @POST("/patients")
    @Headers("Content-Type: application/json")
    Call<Patient> createPatient(@Body Patient patient);
}
