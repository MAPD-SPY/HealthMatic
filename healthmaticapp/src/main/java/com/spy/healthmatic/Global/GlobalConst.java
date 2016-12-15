package com.spy.healthmatic.Global;

import com.spy.healthmatic.API.HospitalAPI;
import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.API.StaffAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Team Name: Team SPY
 * Created by yatin on 21/11/16.
 */

public interface GlobalConst {
    String BASE_URL = "http://shelalainechan.com";

    int DATABASE_VERSION = 2;
    String DATABASE_NAME = "HEALTHMATIC";

    String TABLE_STAFF = "STAFF_TABLE";

    String TABS = "tabs";
    String PATIENT = "patient";
    String STAFF = "staff";
    String ROLE = "role";
    String ACTION = "action";
    String CURRENTTABPOSITION = "CurrentTabPosition";

    int REQUEST_TAKE_PHOTO = 1991;
    int REQUEST_SELECT_PICTURE = 1992;
    int MY_PERMISSIONS_REQUEST_CAMERA = 1993;
    String FILE_STORAGE_PATH = "gs://healthmactic.appspot.com";

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PatientsListAPI PATIENTS_LIST_API = RETROFIT.create(PatientsListAPI.class);
    StaffAPI STAFF_API = RETROFIT.create(StaffAPI.class);
    HospitalAPI HOSPITAL_API = RETROFIT.create(HospitalAPI.class);
}
