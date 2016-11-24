package com.spy.healthmatic.Global;

import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.API.StaffAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yatin on 21/11/16.
 */

public interface GlobalConst {
    String BASE_URL = "http://shelalainechan.com";

    String TABS = "tabs";
    String PATIENT = "patient";
    String STAFF = "staff";
    String ROLE = "role";
    String ACTION = "action";
    String CURRENTTABPOSITION = "CurrentTabPosition";

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PatientsListAPI PATIENTS_LIST_API = RETROFIT.create(PatientsListAPI.class);
    StaffAPI STAFF_API = RETROFIT.create(StaffAPI.class);
}
