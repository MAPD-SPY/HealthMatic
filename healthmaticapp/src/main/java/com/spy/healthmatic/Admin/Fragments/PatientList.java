package com.spy.healthmatic.Admin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.PatientApiObject;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientList extends Fragment implements GlobalConst, SwipeRefreshLayout.OnRefreshListener {

    public Retrofit retrofit;
    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    PatientsListAPI patientsListAPICall;

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
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.circlePVRim),

                getResources().getColor(R.color.circlePVBar),

                getResources().getColor(R.color.appBarScrim),

                getResources().getColor(R.color.yellow));

        patientsListAPICall = retrofit.create(PatientsListAPI.class);
//      Setting Recyclerview
        mRecyclerView.setHasFixedSize(false);
//      Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    public void onStart() {
        super.onStart();
//        patients = GlobalFunctions.getPatientJSONArray(getActivity());
        getPatientList(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadRecyclerViewElements();
//            }
//        }, 2000);
    }

    private void getPatientList(final boolean isRefresh) {
        Call<ArrayList<Patient>> call = patientsListAPICall.getPatientList();
        call.enqueue(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "RETROFIT FAILURE - RESPONSE FAIL >>>>> " + response.errorBody());
                    Toast.makeText(getActivity(), "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                patients = response.body();
                loadRecyclerViewElements();
                if (isRefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(getActivity(), "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadRecyclerViewElements() {
        mProgressDialog.setVisibility(View.GONE);
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
    public void addPatient() {
//        Toast.makeText(getActivity(), "Add Patient clicked", Toast.LENGTH_SHORT).show();
//        Patient patient = GlobalFunctions.getPatientJSONArray(getActivity()).get(0);
//        String patientString = new Gson().toJson(patient);
//        Call<Patient> call = patientsListAPICall.createPatient(patient);
//        call.enqueue(new Callback<Patient>() {
//
//            @Override
//            public void onResponse(Call<Patient> call, Response<Patient> response) {
//                if(!response.isSuccessful()){
//                    Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + response.errorBody());
//                    Toast.makeText(getActivity(), "Was not able to ADD Patient. Please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Patient patient = response.body();
//                Toast.makeText(getActivity(), " Patient Added", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<Patient> call, Throwable t) {
//                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
//                Toast.makeText(getActivity(), "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
//            }
//        });
        startActivity(new Intent(getActivity(), AdminAddPatient.class));
    }

    @Override
    public void onRefresh() {
        getPatientList(true);
    }

    public interface OnPatientListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Patient patient, int position);
    }

}
