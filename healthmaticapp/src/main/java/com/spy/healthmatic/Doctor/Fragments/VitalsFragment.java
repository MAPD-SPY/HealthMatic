package com.spy.healthmatic.Doctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.Doctor.Adapters.VitalsAdapter;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Vitals;
import com.spy.healthmatic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */
public class VitalsFragment extends Fragment {

    private String doctorName;
    private VitalsAdapter vitalsAdapter;
    private Patient patient;
    private List<Vitals> vitals;

    public VitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vitals = (ArrayList<Vitals>) getArguments().getSerializable("PATIENT_VITALS_OBJ");
            vitalsAdapter = new VitalsAdapter(getActivity(), vitals);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vitals, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rvVitals);
        recyclerView.setAdapter(vitalsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
