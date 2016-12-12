package com.spy.healthmatic.Doctor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BioFragment extends Fragment {

    private String doctorName;
    private Patient patient;
    private ArrayAdapter<String> doctorsAdapter;
    private ArrayList<String> doctorNames;
    private ArrayList<Staff> selectedDoctors;

    public BioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable("PATIENT_OBJ");
            doctorName = getArguments().getString("DOCTOR_NAME");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bio, container, false);

        // Show Patient's info
        TextView personDetail = (TextView) view.findViewById(R.id.txtViewPersonalDetail);
        String gender = (patient.getGender()) ?
                getResources().getString(R.string.strMale) :
                getResources().getString(R.string.strFemale);
        String birthday = patient.getBirthday();
        String weight = Integer.toString(patient.getWeight());
        String height = Integer.toString(patient.getHeight());
        String bloodType = patient.getBloodType();
        String maritalStat = (patient.getMaritalStatus()) ?
                getResources().getString(R.string.strSingle) :
                getResources().getString(R.string.strMarried);
        personDetail.setText(getResources().getString(R.string.strBirthday) + " " + birthday + "\n" +
                            getResources().getString(R.string.strGender) + " "  + gender + "\n" +
                            getResources().getString(R.string.strWeight) + " "  + weight + " " +
                                getResources().getString(R.string.strUnitWeight) + "\n" +
                            getResources().getString(R.string.strHeight) + " " + height + " " +
                                getResources().getString(R.string.strUnitHeight) + "\n" +
                            getResources().getString(R.string.strBloodType) + " " + bloodType + "\n" +
                            getResources().getString(R.string.strMaritalStatus) + " " + maritalStat);

        // Show patient's address
        TextView address = (TextView) view.findViewById(R.id.tvAddressHeader);
        address.setText(patient.getAddress().getStreet() + "\n" +
            patient.getAddress().getCity() + ", " +
            patient.getAddress().getProvince() + "\n" +
            patient.getAddress().getZipCode());

        // Show patient's contact info
        TextView contact = (TextView) view.findViewById(R.id.tvContactHeader);
        contact.setText(getResources().getString(R.string.strPhone) + " " +
                    patient.getContact().getPhone() + "\n" +
                getResources().getString(R.string.strEmail) + " " +
                    patient.getContact().getEmail() + "\n" +
                getResources().getString(R.string.strEmergencyName) + " " +
                    patient.getContact().getEmergencyContactName() + "\n" +
                getResources().getString(R.string.strEmergencyPhone) + " " +
                    patient.getContact().getEmergencyContactNumber());
        return view;
    }

}
