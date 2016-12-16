package com.spy.healthmatic.API;

import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.LoginModel;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;

//Team Name: Team SPY
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

    @GET("/staffs/{id}/patients")
    Call<ArrayList<Patient>> getAllStaffPatinet(@Path("id") String _id);

    @PUT("/staffs/{id}")
    @Headers("Content-Type: application/json")
    Call<Staff> updateStaff(@Path("id") String _id, @Body Staff staff);

    @POST("/login")
    @Headers("Content-Type: application/json")
    Call<Staff> login(@Body LoginModel loginModel);

    @POST("/loginsamsung")
    @Headers("Content-Type: application/json")
    Call<Staff> loginSamsung(@Body LoginModel loginModel);

    @POST("/staffs")
    @Headers("Content-Type: application/json")
    Call<Staff> createStaff(@Body Staff staff);
}
