package com.spy.healthmatic.Nurse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Welcome.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NurseMainActivity extends AppCompatActivity {

    private Staff nurse;
    private ArrayList<Patient> patientList;
    private RecyclerView recyclerView;
    private NurseAdapter mAdapter;
    private ProgressBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_main);


//        if (patientList == null) {
//            patientList = new ArrayList<>();
//        }
//       patientList=(ArrayList<Patient>) getData();
        //getData();

        if (nurse == null) {
            Intent intent = getIntent();
            nurse = (Staff) intent.getSerializableExtra("STAFF");
        }

        mAdapter = new NurseAdapter(nurse.getPatients(),this);
//
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    private ArrayList<Patient> getData() {
    private void getData() {
        String url = "http://shelalainechan.com/staffs/5835d750bd8ed21ac83e2bc4";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
           public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    nurse = new Staff(response);
                    try {
                        getPatients();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                patientList.addAll(Patient.fromJSONArray(response));
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

    private void getPatients() throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/staffs/" + nurse.getId() + "/patients";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                ArrayList<Patient> patients = new ArrayList<>();
                patients.addAll(Patient.fromJSONArray(jsonArray));
                nurse.setPatients(patients);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
