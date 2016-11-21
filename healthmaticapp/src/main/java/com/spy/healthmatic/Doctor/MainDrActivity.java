package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spy.healthmatic.Doctor.Utilities.JsonGlobalHelpers;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Doctor.adapters.PatientsAdapter;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Welcome.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-26.
 */
public class MainDrActivity extends AppCompatActivity {

    private ArrayList<Patient> patients;
    private PatientsAdapter patientsAdapter;
    private CircleProgressView circleProgressView;
    private long numOfPatientsChecked = 2;
    private static final int CIRCLE_PROGRESS_VIEW_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (patients == null) {
            patients = new ArrayList<>();
            patientsAdapter = new PatientsAdapter(this, patients);
            getPatientJSONArray();
        }

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
                    collapsingToolbarLayout.setTitle("My Patients");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public String loadJSONFromAsset() {
        String json;
        AssetManager assetManager = getAssets();
        InputStream input;
        try {
            input = assetManager.open("patients.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    private void getPatientJSONArray() {
        JSONObject response;
        JSONArray patientJsonResults;

        try {
            // TODO: Remove if check is complete
            // response = new JSONObject(loadJSONFromAsset());
            response = new JSONObject(JsonGlobalHelpers.loadJSONFromAsset(this, "patients.json"));
            patientJsonResults = response.getJSONArray("patients");
            patients.addAll(Patient.fromJSONArray(patientJsonResults));
            patientsAdapter.notifyDataSetChanged();
            Log.d("DEBUG", patients.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initCircleProgressView() {

        int patientsSize = patients.size();

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
}
