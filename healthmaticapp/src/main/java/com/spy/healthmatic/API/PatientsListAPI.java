package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.PatientApiObject;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yatin on 21/11/16.
 */

public interface PatientsListAPI {

    @GET("/patients")
    Call<ArrayList<Patient>> getPatientList();

    @POST("/patients")
    @Headers("Content-Type: application/json")
    Call<Patient> createPatient(@Body Patient patient);

    @DELETE("/patients/{id}")
    Call<ResponseBody> deletePatient(@Path("id") String _id);

    @POST("/patients/{id}/labTest")
    @Headers("Content-Type: application/json")
    Call<Patient> updatePatientLabTest(@Path("id") String _id, @Body LabTest labTest);
}
