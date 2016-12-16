package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.Model.Patient;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//Team Name: Team SPY
/**
 * Created by yatin on 21/11/16.
 */

public interface PatientsListAPI {

    @GET("/patients")
    Call<ArrayList<Patient>> getPatientList();

    @POST("/patients")
    @Headers("Content-Type: application/json")
    Call<Patient> createPatient(@Body Patient patient);

    @PUT("/patients/{id}")
    @Headers("Content-Type: application/json")
    Call<Patient> updatePatient(@Path("id") String _id, @Body Patient patient);

    @DELETE("/patients/{id}")
    Call<ResponseBody> deletePatient(@Path("id") String _id);

    @PUT("/patients/{id}/labTest")
    @Headers("Content-Type: application/json")
    Call<Patient> updatePatientLabTest(@Path("id") String _id, @Body LabTest labTest);

}
