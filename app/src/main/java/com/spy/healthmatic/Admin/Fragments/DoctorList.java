package com.spy.healthmatic.Admin.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Adapters.DoctorListAdapter;
import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.POJO.Doctor;
import com.spy.healthmatic.POJO.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorList extends Fragment {

    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;

    ArrayList<Doctor> doctors;
    private OnDoctorListFragmentInteractionListener mListener;

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public DoctorList() {
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
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onStart(){
        super.onStart();
        doctors = GlobalFunctions.getDummyDoctors(10);
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
        mAdapter = new DoctorListAdapter(doctors, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoctorListFragmentInteractionListener) {
            mListener = (OnDoctorListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @OnClick(R.id.fab)
    public void addDoctor(){
        Toast.makeText(getActivity(), "Add Doctor clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnDoctorListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Doctor doctor, int position);
    }

}
