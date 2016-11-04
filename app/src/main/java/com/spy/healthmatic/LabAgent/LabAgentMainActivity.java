package com.spy.healthmatic.LabAgent;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.LabAgent.Adapters.AgentPatientListAdapter;
import com.spy.healthmatic.POJO.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LabAgentMainActivity extends AppCompatActivity {

    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    ArrayList<Patient> patients;

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_agent_main);
        ButterKnife.bind(this);
        fab.setVisibility(View.GONE);
    }

    public void onStart(){
        super.onStart();
        patients = GlobalFunctions.getDummyPatients(10);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadRecyclerViewElements();
            }
        }, 2000);
    }

    private void loadRecyclerViewElements(){
        mProgressDialog.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(false);
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AgentPatientListAdapter(patients, this);
        mRecyclerView.setAdapter(mAdapter);
    }

}
