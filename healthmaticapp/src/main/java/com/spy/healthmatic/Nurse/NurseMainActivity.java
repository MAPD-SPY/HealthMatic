package com.spy.healthmatic.Nurse;

import android.app.ProgressDialog;
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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Global.GlobalFunctions;

import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

public class NurseMainActivity extends AppCompatActivity {

    private ArrayList<Patient> patientList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NurseAdapter mAdapter;
    private ProgressBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_main);
       //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressDialog = (ProgressBar) findViewById(R.id.progress_dialog);
    }

    public void onStart(){
        super.onStart();
        patientList = GlobalFunctions.getPatientJSONArray(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadRecyclerViewElements();
            }
        }, 2000);
    }

    private void loadRecyclerViewElements(){
        progressDialog.setVisibility(View.GONE);
        mAdapter = new NurseAdapter(patientList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


}
