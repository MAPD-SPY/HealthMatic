package com.spy.healthmatic.Nurse;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.spy.healthmatic.Global.GlobalFunctions;

import com.spy.healthmatic.R;
import com.spy.healthmatic.models.Patient;

public class NurseMainActivity extends AppCompatActivity {

    private ArrayList<Patient> patientList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NurseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_main);
       //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new NurseAdapter(patientList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        patientList = GlobalFunctions.getDummyPatients(10);
        //prepareData();

    }

    private void prepareData() {
//        Doctor dd=new Doctor("Doctor1","Male","Heart");
//        ArrayList<Doctor> doctors=new ArrayList<>();
//        doctors.add(dd);
//        Nurse nn=new Nurse();
//        ArrayList<Nurse> nurses=new ArrayList<>();
//        nurses.add(nn);
//
//        Patient patient;

//        for(int i=1;i<10;i++){
//            if(i%2==0) {
//                patient = new Patient("Patient" + i, "Male","2", "Normal", 33333333, "Heart", dd, doctors, nurses);
//
//                patientList.add(patient);
//                mAdapter.notifyDataSetChanged();
//
//
//            }
//            else
//            {
//                  patient = new Patient("Patient"+i, "Female", "2", "Normal", 33333333, "Neurology",dd, doctors, nurses);
//                  patientList.add(patient);
//                mAdapter.notifyDataSetChanged();
//
//
//
//            }
      //  }



    }
}
