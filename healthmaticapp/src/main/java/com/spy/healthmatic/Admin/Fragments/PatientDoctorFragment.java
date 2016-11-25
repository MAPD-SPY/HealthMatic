package com.spy.healthmatic.Admin.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Adapters.AddPatientDoctorsAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
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

    ArrayList<Tab> tabs;
    ViewPager mViewPager;
    ArrayList<Staff> selectedDoctors;
    ArrayAdapter<String> doctorsAdapter;
    ArrayList<String> doctorNames;

//    ArrayList<Doctor> doctors;
    @Bind(R.id.doctor_list)
    RecyclerView mDoctorRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.fab_doctor_next)
    FloatingActionButton mFabDoctorNext;
    private Patient patient;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

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
        mViewPager = ((AdminAddPatient) getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_doctor, container, false);
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        setupAdapter();
        if (selectedDoctors != null && !selectedDoctors.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            setView();
        } else if (patient.getDoctors() != null && !patient.getDoctors().isEmpty()) {
            setupSelectedDoctors();
        }
        return rootView;
    }

    public void onResume() {
        super.onResume();
    }

    private void setupAdapter() {
        doctorNames = new ArrayList<>();
        for (Staff staff : AdminAddPatient.doctors) {
            doctorNames.add(staff.getFirstName() + " " + staff.getLastName());
        }
        doctorsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, doctorNames);
    }

    private void setupSelectedDoctors() {
        for (Doctor doctor : patient.getDoctors()) {
            for (Staff staff : AdminAddPatient.doctors) {
                if (staff.get_id().equals(doctor.getId())) {
                    selectedDoctors.add(staff);
                    doctorsAdapter.remove(staff.getFirstName() + " " + staff.getLastName());
                    break;
                }
            }
        }
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
        setView();
    }

    private void setView() {
        mAdapter = new AddPatientDoctorsAdapter(selectedDoctors);
        mDoctorRecyclerView.setLayoutManager(mLayoutManager);
        mDoctorRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_doctor_add)
    public void addDocotor() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_doctor);
        final AutoCompleteTextView doctorName = (AutoCompleteTextView) dialog.findViewById(R.id.doctor);
        doctorName.setAdapter(doctorsAdapter);
        final AppCompatButton saveToolButton = (AppCompatButton) dialog.findViewById(R.id.add_doctor);
        saveToolButton.setEnabled(false);
        doctorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveToolButton.setEnabled(true);
            }
        });
        saveToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String docotor = doctorName.getText().toString();
                if (docotor.equals("")) {
                    doctorName.setError("Please provide a value");
                    doctorName.requestFocus();
                    return;
                }
                Staff staff = AdminAddPatient.doctors.get(doctorNames.indexOf(docotor));
                doctorsAdapter.remove(docotor);
                saveDoctor(staff);
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    private void saveDoctor(Staff doctor) {
        if (selectedDoctors == null) {
            selectedDoctors = new ArrayList<>();
            setView();
            progressBar.setVisibility(View.GONE);
        }
        selectedDoctors.add(doctor);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_doctor_next)
    public void saveDoctorList() {
        if (selectedDoctors == null || selectedDoctors.isEmpty()) {
            return;
        }
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (Staff staff : selectedDoctors) {
            doctors.add(new Doctor(staff.get_id(), staff.getFirstName(), staff.getLastName(), ""));
        }
        patient.setDoctors(doctors);

        if (tabs.size() < 4) {
            Tab tab = new Tab("Nurse", 3);
            tabs.add(3, tab);
            mViewPager.getAdapter().notifyDataSetChanged();
        }
        mViewPager.setCurrentItem(3, true);
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
