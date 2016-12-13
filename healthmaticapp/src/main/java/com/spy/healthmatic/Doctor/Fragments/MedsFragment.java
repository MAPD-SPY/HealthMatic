package com.spy.healthmatic.Doctor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.Doctor.Adapters.MedsAdapter;
import com.spy.healthmatic.Model.Prescription;
import com.spy.healthmatic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */
public class MedsFragment extends Fragment {

    private String doctorName;
    private MedsAdapter medsAdapter;
    private List<Prescription> prescriptions;

    public MedsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prescriptions = (ArrayList<Prescription>) getArguments().getSerializable("PATIENT_MEDS_OBJ");
            doctorName = getArguments().getString("DOCTOR_NAME");
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
