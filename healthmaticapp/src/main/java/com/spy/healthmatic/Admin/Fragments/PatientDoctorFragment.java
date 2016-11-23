package com.spy.healthmatic.Admin.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.spy.healthmatic.Admin.Adapters.AddPatientDoctorsAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDoctorFragment extends Fragment implements GlobalConst {

    private Patient patient;
    ArrayList<Tab> tabs;
    ViewPager mViewPager;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    ArrayList<Doctor> doctors;

    @Bind(R.id.doctor_list)
    RecyclerView mDoctorRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.fab_doctor_next)
    FloatingActionButton mFabDoctorNext;

    public PatientDoctorFragment() {
        // Required empty public constructor
    }

    public static PatientDoctorFragment newInstance(Patient patient, ArrayList<Tab> tabs) {
        PatientDoctorFragment fragment = new PatientDoctorFragment();
        Bundle args = new Bundle();
        args.putSerializable(TABS, tabs);
        args.putSerializable(PATIENT, patient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabs = (ArrayList<Tab>) getArguments().getSerializable(TABS);
            patient = (Patient) getArguments().getSerializable(PATIENT);
        }
        mViewPager = ((AdminAddPatient)getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_doctor, container, false);
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        if(patient.getDoctors()!=null && !patient.getDoctors().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            mFabDoctorNext.setVisibility(View.GONE);
            doctors = patient.getDoctors();
            setView();
        }
        return rootView;
    }

    private void setView(){
        mAdapter = new AddPatientDoctorsAdapter(doctors);
        mDoctorRecyclerView.setLayoutManager(mLayoutManager);
        mDoctorRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_doctor_add)
    public void addDocotor(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_doctor);
        final TextInputEditText doctorName = (TextInputEditText) dialog.findViewById(R.id.doctor);
        final AppCompatButton saveToolButton = (AppCompatButton) dialog.findViewById(R.id.add_doctor);
        saveToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String docotor = doctorName.getText().toString();
                if (docotor.equals("")){
                    doctorName.setError("Please provide a value");
                    doctorName.requestFocus();
                    return;
                }
                Doctor doctor = new Doctor(docotor, "Male", "Special");
                saveDoctor(doctor);
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    private void saveDoctor(Doctor doctor){
        if(doctors == null){
            doctors = new ArrayList<>();
            setView();
            progressBar.setVisibility(View.GONE);
        }
        doctors.add(doctor);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_doctor_next)
    public void saveDoctorList() {
        if(doctors==null || doctors.isEmpty()){
            return;
        }
        patient.setDoctors(doctors);

        Tab tab = new Tab("Nurse", 3);
        tabs.add(3, tab);
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.setCurrentItem(3, true);
        mFabDoctorNext.setVisibility(View.GONE);
    }

    public void strechDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp = null;
        window = null;
    }


}
