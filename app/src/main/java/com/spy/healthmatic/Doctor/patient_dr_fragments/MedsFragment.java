package com.spy.healthmatic.Doctor.patient_dr_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.Doctor.adapters.MedsAdapter;
import com.spy.healthmatic.R;
import com.spy.healthmatic.models.Patient;
import com.spy.healthmatic.models.Prescription;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */
public class MedsFragment extends Fragment {

    private MedsAdapter medsAdapter;
    private Patient patient;
    private List<Prescription> prescriptions;

    public MedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable("PATIENT_OBJ");
            prescriptions = patient.getPrescriptions();
            medsAdapter = new MedsAdapter(getActivity(), prescriptions);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meds, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rvMeds);
        recyclerView.setAdapter(medsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
