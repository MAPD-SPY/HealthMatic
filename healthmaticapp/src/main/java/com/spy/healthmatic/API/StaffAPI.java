package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yatin on 23/11/16.
 */

public interface StaffAPI {

    @GET("/staffs")
    Call<ArrayList<Staff>> getAllStaffs();

    @GET("/doctors")
    Call<ArrayList<Staff>> getAllDoctors();

    @GET("/nurses")
    Call<ArrayList<Staff>> getAllNurses();
}
