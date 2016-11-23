package com.spy.healthmatic.Nurse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Doctor.adapters.PatientsAdapter;
import com.spy.healthmatic.Global.GlobalFunctions;

import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Welcome.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NurseMainActivity extends AppCompatActivity {

    private ArrayList<Patient> patientList;
    private RecyclerView recyclerView;
    private NurseAdapter mAdapter;
    private ProgressBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_main);

        String url = "http://shelalainechan.com/patients";

        if (patientList == null) {
            patientList = new ArrayList<>();
        }
//       patientList=(ArrayList<Patient>) getData();
        getData();

        mAdapter = new NurseAdapter(patientList,this);
//
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    private ArrayList<Patient> getData() {
    private void getData() {
        String url = "http://shelalainechan.com/staffs";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
           public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                patientList.addAll(Patient.fromJSONArray(response));
//                JSONArray patientJsonResults = null;
//
//                try {
////                    patientJsonResults = response.getJSONArray("patients");
////                    patientList.addAll(Patient.fromJSONArray(patientJsonResults));
//                    patientList.addAll(Patient.fromJSONArray(response));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
  //  return  patientList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            Intent intent = new Intent(this, SplashScreen.class);
            intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
