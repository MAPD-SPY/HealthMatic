package com.spy.healthmatic.Admin.Fragments;

//Team Name: Team SPY

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Address;
import com.spy.healthmatic.Model.Contact;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientPersonalFragment extends Fragment implements GlobalConst {


    @Bind(R.id.p_fname)
    TextInputEditText mPFNameView;
    @Bind(R.id.p_lname)
    TextInputEditText mPLNameView;
    @Bind(R.id.p_dob)
    EditText mPDOBView;
//    @Bind(R.id.p_dob_layout)
//    TextInputLayout mPDOBViewLayout;
    @Bind(R.id.p_contact)
    TextInputEditText mPContactView;
    @Bind(R.id.p_email)
    TextInputEditText mPEmailView;
    @Bind(R.id.p_emergency_name)
    TextInputEditText mPEmergencyNameView;
    @Bind(R.id.p_emergency_contact)
    TextInputEditText mPEmergencyContactView;
    @Bind(R.id.p_street)
    TextInputEditText mPStreetView;
    @Bind(R.id.p_city)
    TextInputEditText mPCityView;
    @Bind(R.id.p_proviance)
    Spinner mPProvianceView;
    @Bind(R.id.p_zipcode)
    TextInputEditText mPZipcodeView;

    @Bind(R.id.save_patient_personal)
    FloatingActionButton mSavePatientPersonalButton;

    private Patient patient;
    ArrayList<Tab> tabs;

    ViewPager mViewPager;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    public PatientPersonalFragment() {
        // Required empty public constructor
    }

    public static PatientPersonalFragment newInstance(Patient patient, ArrayList<Tab> tabs) {
        PatientPersonalFragment fragment = new PatientPersonalFragment();
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
        mViewPager = ((AdminAddPatient)getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_personal, container, false);
        ButterKnife.bind(this, rootView);
        if(patient.getFirstName()!=null && !patient.getFirstName().equals("")) {
            setView();
        }
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        mPDOBView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(focus){
                    showDatePicker();
                }
            }
        });
        return rootView;
    }

    private void setView() {
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
    }


    @OnCheckedChanged(R.id.radio_male)
    public void radioGnder(boolean isChecked){
        if(isChecked) {
            patient.setGender(isChecked);
        }else{
            patient.setGender(false);
        }
    }

    @OnClick(R.id.p_dob)
    public void dateViewClicked(){
        showDatePicker();
    }

    @OnCheckedChanged(R.id.radio_married)
    public void radiomartial(boolean isChecked){
        if(isChecked) {
            patient.setMaritalStatus(isChecked);
        }else{
            patient.setMaritalStatus(false);
        }
    }

    public void showDatePicker(){
        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        mPDOBView.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.save_patient_personal)
    public void savePatientPersonalInfo() {
        String fname = mPFNameView.getText().toString();
        if (isInvalidName(fname)) {
            mPFNameView.setError("Required.");
            mPFNameView.requestFocus();
            return;
        } else {
            patient.setFirstName(fname);
            mPFNameView.setError(null);
        }
        String lName = mPLNameView.getText().toString();
        if (TextUtils.isEmpty(lName)) {
            mPLNameView.setError("Required.");
            mPLNameView.requestFocus();
            return;
        } else {
            patient.setLastName(lName);
            mPLNameView.setError(null);
        }
        String dob = mPDOBView.getText().toString();
        if (isInvalidDate(dob)) {
            mPDOBView.setError("Invalid.");
            mPDOBView.requestFocus();
            return;
        } else {
            patient.setBirthday(dob);
            mPDOBView.setError(null);
        }

        // Contact Object
        Contact contact = new Contact();
        String contact1 = mPContactView.getText().toString();
        if (isInvalidContact(contact1)) {
            mPContactView.setError("Required.");
            mPContactView.requestFocus();
            return;
        } else {
            contact.setPhone(contact1);
            mPContactView.setError(null);
        }
        String email = mPEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mPEmailView.setError("Required.");
            mPEmailView.requestFocus();
            return;
        } else {
            contact.setEmail(email);
            mPEmailView.setError(null);
        }
        String emergencyName = mPEmergencyNameView.getText().toString();
        if (TextUtils.isEmpty(emergencyName)) {
            mPEmergencyNameView.setError("Required.");
            mPEmergencyNameView.requestFocus();
            return;
        } else {
            contact.setEmergencyContactName(emergencyName);
            mPEmergencyNameView.setError(null);
        }
        String emergencyContact = mPEmergencyContactView.getText().toString();
        if (isInvalidContact(emergencyContact)) {
            mPEmergencyContactView.setError("Required.");
            mPEmergencyContactView.requestFocus();
            return;
        } else {
            contact.setEmergencyContactNumber(emergencyContact);
            mPEmergencyContactView.setError(null);
        }
        // Address Object
        Address address = new Address();
        String street = mPStreetView.getText().toString();
        if (TextUtils.isEmpty(street)) {
            mPStreetView.setError("Required.");
            mPStreetView.requestFocus();
            return;
        } else {
            address.setStreet(street);
            mPStreetView.setError(null);
        }
        String city = mPCityView.getText().toString();
        if (TextUtils.isEmpty(city)) {
            mPCityView.setError("Required.");
            mPCityView.requestFocus();
            return;
        } else {
            address.setCity(city);
            mPCityView.setError(null);
        }
        String proviance = mPProvianceView.getSelectedItem().toString();
        if (TextUtils.isEmpty(proviance)) {
            return;
        } else {
            address.setProvince(proviance);
        }
        String zipcode = mPZipcodeView.getText().toString();
        if (TextUtils.isEmpty(zipcode)) {
            mPZipcodeView.setError("Required.");
            mPZipcodeView.requestFocus();
            return;
        } else {
            address.setZipCode(zipcode);
            mPZipcodeView.setError(null);
        }

        patient.setContact(contact);
        patient.setAddress(address);
        patient.setAdmissionDate(GlobalFunctions.getTodaysDateFormatted());

        if(tabs.size()<2) {
            Tab tab = new Tab("Health", 1);
            tabs.add(1, tab);
            mViewPager.getAdapter().notifyDataSetChanged();
        }
        mViewPager.setCurrentItem(1, true);
    }

    public static boolean isInvalidName(String name){
        if(name==null || "".equals(name.trim())){
            return true;
        }
        return false;
    }

    public static boolean isInvalidContact(String contact){
        String regexStr = "^[0-9]*$";
        if(contact==null || "".equals(contact.trim()) || !contact.matches(regexStr)){
            return true;
        }
        return false;
    }

    public static boolean isInvalidDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date todaysDate = null, userDate = null;
        try {
            todaysDate = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
            userDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(todaysDate==null || userDate==null){
            return true;
        }
        if(date==null || "".equals(date.trim()) || userDate.after(todaysDate)){
            return true;
        }
        return false;
    }
}
