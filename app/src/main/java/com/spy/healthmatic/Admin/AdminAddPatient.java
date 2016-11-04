package com.spy.healthmatic.Admin;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.POJO.Patient;
import com.spy.healthmatic.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddPatient extends AppCompatActivity {

    @Bind(R.id.pateint_progress)
    ProgressBar pateintProgress;

    @Bind(R.id.patient_weight)
    TextInputEditText patientWeight;
    @Bind(R.id.patient_height)
    TextInputEditText patient_height;
    @Bind(R.id.patient_blood)
    TextInputEditText patientBlood;
    @Bind(R.id.patient_condition)
    TextInputEditText patientCondition;

    @Bind(R.id.spinner_doctors)
    Spinner spinnerDoctors;
    @Bind(R.id.added_doctor_layout)
    LinearLayout addedDoctorLayout;

    @Bind(R.id.spinner_nurse)
    Spinner spinnerNurse;
    @Bind(R.id.added_nurse_layout)
    LinearLayout addedNurseLayout;

    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_patient);
        ButterKnife.bind(this);
        pateintProgress.setProgress(33);
    }

    @OnClick(R.id.vital_next)
    public void vitalNext(){
        pateintProgress.setProgress(66);
        viewFlipper.showNext();
    }

    @OnClick(R.id.doctor_previous)
    public void doctorPrevious(){
        pateintProgress.setProgress(33);
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.doctor_next)
    public void doctorNext(){
        pateintProgress.setProgress(100);
        viewFlipper.showNext();
    }

    @OnClick(R.id.nurse_previous)
    public void nursePrevious(){
        pateintProgress.setProgress(66);
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.nurse_done)
    public void nurseDone(){
        Toast.makeText(AdminAddPatient.this, "Patient added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, AdminMainActivity.class));

    }

}
