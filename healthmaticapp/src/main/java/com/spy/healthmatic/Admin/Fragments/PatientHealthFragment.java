package com.spy.healthmatic.Admin.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Insurance;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHealthFragment extends Fragment implements GlobalConst {

    @Bind(R.id.p_wieght)
    TextInputEditText mWeightView;
    @Bind(R.id.p_height)
    TextInputEditText mHeightView;
    @Bind(R.id.p_blood)
    TextInputEditText mBloodView;
    @Bind(R.id.p_occupation)
    TextInputEditText mOccupationView;
    @Bind(R.id.p_condition)
    TextInputEditText mConditionView;
    @Bind(R.id.p_room)
    TextInputEditText mRoomView;
    @Bind(R.id.p_insurance_name)
    TextInputEditText mInsuranceNameView;
    @Bind(R.id.p_insurance_expiry_date)
    TextInputEditText mInsuranceExpiryView;
    @Bind(R.id.save_patient_health)
    FloatingActionButton mSavePatientHelathButton;

    private Patient patient;
    ArrayList<Tab> tabs;
    ViewPager mViewPager;

    public PatientHealthFragment() {
        // Required empty public constructor
    }

    public static PatientHealthFragment newInstance(Patient patient, ArrayList<Tab> tabs) {
        PatientHealthFragment fragment = new PatientHealthFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_patient_health, container, false);
        ButterKnife.bind(this, rootView);
        if(patient.getBloodType()!=null && !patient.getBloodType().equals("")) {
            setView();
        }
        return rootView;
    }

    private void setView(){
        mSavePatientHelathButton.setVisibility(View.GONE);
    }

    @OnClick(R.id.save_patient_health)
    public void savePatientHealthObject(){
        boolean isvalid = true;
        String weight = mWeightView.getText().toString();
        if (TextUtils.isEmpty(weight)) {
            mWeightView.setError("Required.");
            isvalid = false;
        } else {
            patient.setWeight(Integer.parseInt(weight));
            mWeightView.setError(null);
        }
        String height = mHeightView.getText().toString();
        if (TextUtils.isEmpty(height)) {
            mHeightView.setError("Required.");
            isvalid = false;
        } else {
            patient.setHeight(Integer.parseInt(height));
            mHeightView.setError(null);
        }
        String blood = mBloodView.getText().toString();
        if (TextUtils.isEmpty(blood)) {
            mBloodView.setError("Required.");
            isvalid = false;
        } else {
            patient.setBloodType(blood);
            mBloodView.setError(null);
        }
        String occupation = mOccupationView.getText().toString();
        if (TextUtils.isEmpty(occupation)) {
            mOccupationView.setError("Required.");
            isvalid = false;
        } else {
            patient.setOccupation(occupation);
            mOccupationView.setError(null);
        }

        String condition = mConditionView.getText().toString();
        if (TextUtils.isEmpty(condition)) {
            mConditionView.setError("Required.");
            isvalid = false;
        } else {
            patient.setCondition(condition);
            mConditionView.setError(null);
        }
        String room = mRoomView.getText().toString();
        if (TextUtils.isEmpty(room)) {
            mRoomView.setError("Required.");
            isvalid = false;
        } else {
            patient.setRoom(Integer.parseInt(room));
            mRoomView.setError(null);
        }
        Insurance insurance = new Insurance();
        String insuranceName = mInsuranceNameView.getText().toString();
        if (TextUtils.isEmpty(insuranceName)) {
            mInsuranceNameView.setError("Required.");
            isvalid = false;
        } else {
            insurance.setName(insuranceName);
            mInsuranceNameView.setError(null);
        }
        String insuranceExpiryDate = mInsuranceExpiryView.getText().toString();
        if (TextUtils.isEmpty(insuranceExpiryDate)) {
            mInsuranceExpiryView.setError("Required.");
            isvalid = false;
        } else {
            insurance.setExpiryDate(insuranceExpiryDate);
            mInsuranceExpiryView.setError(null);
        }
        if(!isvalid){
            return;
        }
        patient.setInsurance(insurance);
        Tab tab = new Tab("Doctos", 2);
        tabs.add(2, tab);
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.setCurrentItem(2, true);
        mSavePatientHelathButton.setVisibility(View.GONE);
    }

}
