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
        showPatientBio(view);

        // Show patient's address
        showPatientAddress(view);

        // Show patient's contact info
        showPatientContactInfo(view);

        // Show the staffs assigned to the patient
        showPatientStaffs(view);

        return view;
    }

    /**
     * Show patient's bio details such as birthday, gender, weight, height blood type, and marital status
     */
    private void showPatientBio(View view) {
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
    }

    /**
     * Show patient's address
     */
    private void showPatientAddress(View view) {
        TextView address = (TextView) view.findViewById(R.id.tvAddressHeader);
        address.setText(patient.getAddress().getStreet() + "\n" +
                patient.getAddress().getCity() + ", " +
                patient.getAddress().getProvince() + "\n" +
                patient.getAddress().getZipCode());

    }

    /**
     * Show patient's contact information
     */
    private void showPatientContactInfo(View view) {
        TextView contact = (TextView) view.findViewById(R.id.tvContactHeader);
        contact.setText(getResources().getString(R.string.strPhone) + " " +
                patient.getContact().getPhone() + "\n" +
                getResources().getString(R.string.strEmail) + " " +
                patient.getContact().getEmail() + "\n" +
                getResources().getString(R.string.strEmergencyName) + " " +
                patient.getContact().getEmergencyContactName() + "\n" +
                getResources().getString(R.string.strEmergencyPhone) + " " +
                patient.getContact().getEmergencyContactNumber());
    }

    private void showPatientStaffs(View view) {

        // Build the name of the doctors assigned to this patient
        StringBuilder doctorNames = new StringBuilder();
        int size = patient.getDoctors().size();
        // Add all doctor names to doctorNames
        for (int x = 0; x < size; x++) {
            doctorNames.append(patient.getDoctors().get(x).getName());
            if (x < (size - 1)) {
                doctorNames.append(", ");
            }
        }

        // Build the name of the nurses assigned to this patient
        StringBuilder nurseNames = new StringBuilder();
        size = patient.getNurses().size();
        // Add all nurse names to nurseNames
        for (int x = 0; x < size; x++) {
            nurseNames.append(patient.getNurses().get(x).getName());
            if (x < (size - 1)) {
                nurseNames.append(", ");
            }
        }
        if (nurseNames.length() == 0) {
            nurseNames.append("NONE");
        }


        TextView staffs = (TextView) view.findViewById(R.id.tvStaffHeader);
        staffs.setText((getResources().getString(R.string.strDoctors) + " " +
                doctorNames.toString() + "\n\n" +
                getResources().getString(R.string.strNurses) + " " +
                nurseNames).toString());
    }
}
