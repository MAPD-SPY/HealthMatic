package com.spy.healthmatic.Doctor.patient_dr_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.Doctor.adapters.DrNotesAdapter;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.DrNotes;
import com.spy.healthmatic.Model.Patient;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrNotesFragment extends Fragment {

    private String doctorName;
    private DrNotesAdapter drNotesAdapter;
    private Patient patient;
    private List<DrNotes> drNotes;

    public DrNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable("PATIENT_OBJ");
            drNotes = patient.getDrNotes();
            doctorName = getArguments().getString("DOCTOR_NAME");
            drNotesAdapter = new DrNotesAdapter(getActivity(), drNotes);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dr_notes, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rvNotes);
        recyclerView.setAdapter(drNotesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
