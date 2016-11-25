package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Doctor.Utilities.TimeHelpers;
import com.spy.healthmatic.Doctor.adapters.PatientsAdapter;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.PatientRef;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Welcome.SplashScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;
import cz.msebera.android.httpclient.Header;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-26.
 */
public class MainDrActivity extends AppCompatActivity {

    private static Staff doctor;
    private ArrayList<Patient> patients;
    private PatientsAdapter patientsAdapter;
    private CircleProgressView circleProgressView;
    private long numOfPatientsChecked;
    private static final int CIRCLE_PROGRESS_VIEW_DELAY = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a reference to the staff object
        if (doctor == null) {
            Intent intent = getIntent();
            doctor = (Staff) intent.getSerializableExtra("STAFF");
        }
        numOfPatientsChecked = getPatientsCheckedToday(doctor.getPatientRefs());
        patients = doctor.getPatients();
        patientsAdapter = new PatientsAdapter(this, patients, doctor.getFirstName() + " " + doctor.getLastName());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rvPatients);
        recyclerView.setAdapter(patientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup the circle progress view
        circleProgressView = (CircleProgressView) findViewById(R.id.cpvPatients);
        initCircleProgressView();

        // Show the title if the toolbar is collapsed
        // Otherwise if the toolbar is expanded, hide the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Dr. " +
                            doctor.getFirstName() + " " +
                            doctor.getLastName()
                    );
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getPatients(doctor);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    public String loadJSONFromAsset() {
//        String json;
//        AssetManager assetManager = getAssets();
//        InputStream input;
//        try {
//            input = assetManager.open("patients.json");
//            int size = input.available();
//            byte[] buffer = new byte[size];
//            input.read(buffer);
//            input.close();
//
//            json = new String(buffer, "UTF-8");
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        return json;
//    }
//
//    private void getPatientJSONArray() {
//        JSONObject response;
//        JSONArray patientJsonResults;
//
//        try {
//            // TODO: Remove if check is complete
//            // response = new JSONObject(loadJSONFromAsset());
//            response = new JSONObject(JsonGlobalHelpers.loadJSONFromAsset(this, "patients.json"));
//            patientJsonResults = response.getJSONArray("patients");
//            patients.addAll(Patient.fromJSONArray(patientJsonResults));
//            patientsAdapter.notifyDataSetChanged();
//            Log.d("DEBUG", patients.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void initCircleProgressView() {

        int patientsSize = doctor.getPatients().size();

        circleProgressView.setBlockCount(patientsSize);
        circleProgressView.setUnitScale((float)1.20);
        circleProgressView.setMaxValue(patientsSize);
        circleProgressView.setText(Long.toString(numOfPatientsChecked));
        circleProgressView.setValueAnimated(numOfPatientsChecked, CIRCLE_PROGRESS_VIEW_DELAY);

        TextView textViewPatientNum = (TextView) findViewById(R.id.tvPatientNum);
        textViewPatientNum.setText(Integer.toString(patientsSize) + " ");
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

    private void getPatients(Staff staff) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/staffs/" + staff.getId() + "/patients";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                patients.clear();
                patients.addAll(Patient.fromJSONArray(jsonArray));
                doctor.setPatients(patients);

                numOfPatientsChecked = getPatientsCheckedToday(doctor.getPatientRefs());
                initCircleProgressView();
                patientsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private long getPatientsCheckedToday(ArrayList<PatientRef> patientRefs) {
        long numOfPatientsChecked = 0;

        // Get current date
        String dateNow = TimeHelpers.getCurrentDate();

        // Go through each patient
        for (PatientRef patientRef : patientRefs) {
            // Check if one of the checkup dates matches the current date
            for (String checkup: patientRef.getCheckupDates()) {
                String[] dateChecked = checkup.split(" ");
                if (dateChecked[0].equals(dateNow)) {
                    // Increment number of patients checked counter
                    numOfPatientsChecked++;
                    break;
                }
            }
        }

        return numOfPatientsChecked;
    }
}
