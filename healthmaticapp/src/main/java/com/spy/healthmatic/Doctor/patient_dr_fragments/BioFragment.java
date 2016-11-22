package com.spy.healthmatic.Doctor.patient_dr_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BioFragment extends Fragment {

    private Patient patient;

    public BioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable("PATIENT_OBJ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bio, container, false);

        // Show Patient's info
        TextView personDetail = (TextView) view.findViewById(R.id.txtViewPersonalDetail);
        String gender = (patient.getGender()) ? "Male" : "Female";
        String birthday = patient.getBirthday();
        String weight = Integer.toString(patient.getWeight());
        String height = Integer.toString(patient.getHeight());
        String bloodType = patient.getBloodType();
        String maritalStat = (patient.getMaritalStatus()) ? "Single" : "Married";
        personDetail.setText("Birthday: " + birthday + "\n" +
                            "Gender: " + gender + "\n" +
                            "Weight: " + weight + "\n" +
                            "Height: " + height + "\n" +
                            "Blood Type: " + bloodType + "\n" +
                            "Marital Status: " + maritalStat);

        // Show patient's address
        TextView address = (TextView) view.findViewById(R.id.tvAddressHeader);
        address.setText(patient.getAddress().getStreet() + "\n" +
            patient.getAddress().getCity() + ", " +
            patient.getAddress().getProvince() + "\n" +
            patient.getAddress().getZipCode());

        // Show patient's contact info
        TextView contact = (TextView) view.findViewById(R.id.tvContactHeader);
        contact.setText("Phone: " + patient.getContact().getPhone() + "\n" +
                "Email: " + patient.getContact().getEmail() + "\n" +
                "Emergency name: " + patient.getContact().getEmergencyContactName() + "\n" +
                "Emergency phone: " + patient.getContact().getEmergencyContactNumber());
        return view;
    }
}
