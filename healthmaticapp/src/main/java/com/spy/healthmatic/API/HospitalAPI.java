package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.Hospital;
import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.Model.Laboratory;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Room;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by yatin on 15/12/16.
 */

public interface HospitalAPI {

    @GET("/hospitals")
    Call<ArrayList<Hospital>> getHospitalList();

    @GET("/hospitals/{id}/laboratories")
    Call<ArrayList<Laboratory>> getHospitalLabList(@Path("id") String _id);

    @PUT("/hospitals/{id}/rooms")
    @Headers("Content-Type: application/json")
    Call<Hospital> updateHospitalRoom(@Path("id") String _id, @Body Room room);
}
