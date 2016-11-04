package com.spy.healthmatic.Admin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientList extends Fragment {

    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;

    ArrayList<Patient> patients;
    private OnPatientListFragmentInteractionListener mListener;

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public PatientList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onStart(){
        super.onStart();
        patients = GlobalFunctions.getPatientJSONArray(getActivity());
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PatientListAdapter(patients, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPatientListFragmentInteractionListener) {
            mListener = (OnPatientListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.fab)
    public void addPatient(){
        Toast.makeText(getActivity(), "Add Patient clicked", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), AdminAddPatient.class));
    }

    public interface OnPatientListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Patient patient, int position);
    }

}
