package com.spy.healthmatic.Admin.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.spy.healthmatic.Admin.AdminAddStaff;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Address;
import com.spy.healthmatic.Model.Contact;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffPersonalFragment extends Fragment implements GlobalConst {

    @Bind(R.id.p_fname)
    TextInputEditText mPFNameView;
    @Bind(R.id.p_lname)
    TextInputEditText mPLNameView;
    @Bind(R.id.p_dob)
    EditText mPDOBView;
    @Bind(R.id.p_contact)
    TextInputEditText mPContactView;
    @Bind(R.id.p_email)
    TextInputEditText mPEmailView;
    @Bind(R.id.p_street)
    TextInputEditText mPStreetView;
    @Bind(R.id.p_city)
    TextInputEditText mPCityView;
    @Bind(R.id.p_proviance)
    Spinner mPProvianceView;
    @Bind(R.id.p_zipcode)
    TextInputEditText mPZipcodeView;

    Staff staff;
    ArrayList<Tab> tabs;

    ViewPager mViewPager;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    public StaffPersonalFragment() {
        // Required empty public constructor
    }

    public static StaffPersonalFragment newInstance(Staff staff, ArrayList<Tab> tabs) {
        StaffPersonalFragment fragment = new StaffPersonalFragment();
        Bundle args = new Bundle();
        args.putSerializable(TABS, tabs);
        args.putSerializable(STAFF, staff);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabs = (ArrayList<Tab>) getArguments().getSerializable(TABS);
            staff = (Staff) getArguments().getSerializable(STAFF);
        }
        mViewPager = ((AdminAddStaff)getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_staff_personal, container, false);
        ButterKnife.bind(this, rootView);
        if(staff.getFirstName()!=null && !staff.getFirstName().equals("")) {
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

    private void setView(){
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
        mPFNameView.setText(staff.getFirstName());
        mPLNameView.setText(staff.getLastName());
        mPDOBView.setText(staff.getBirthday());
        mPContactView.setText(staff.getContact().getPhone());
        mPEmailView.setText(staff.getContact().getEmail());
        mPStreetView.setText(staff.getAddress().getStreet());
        mPCityView.setText(staff.getAddress().getCity());
        mPZipcodeView.setText(staff.getAddress().getZipCode());
    }

    @OnCheckedChanged(R.id.radio_male)
    public void radioGnder(boolean isChecked){
        if(isChecked) {
            staff.setGender(isChecked);
        }else{
            staff.setGender(false);
        }
    }

    @OnCheckedChanged(R.id.radio_married)
    public void radiomartial(boolean isChecked){
        if(isChecked) {
            staff.setMaritalStatus(isChecked);
        }else{
            staff.setMaritalStatus(false);
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


    @OnClick(R.id.save_staff_personal)
    public void saveStaffPersonalInfo() {
        boolean isvalid = true;
        String fname = mPFNameView.getText().toString();
        if (TextUtils.isEmpty(fname)) {
            mPFNameView.setError("Required.");
            isvalid = false;
        } else {
            staff.setFirstName(fname);
            mPFNameView.setError(null);
        }
        String lName = mPLNameView.getText().toString();
        if (TextUtils.isEmpty(lName)) {
            mPLNameView.setError("Required.");
            isvalid = false;
        } else {
            staff.setLastName(lName);
            mPLNameView.setError(null);
        }

        String dob = mPDOBView.getText().toString();
        if (TextUtils.isEmpty(dob)) {
            mPDOBView.setError("Required.");
            isvalid = false;
        } else {
            staff.setBirthday(dob);
            mPDOBView.setError(null);
        }

        // Contact Object
        Contact contact = new Contact();
        String contact1 = mPContactView.getText().toString();
        if (TextUtils.isEmpty(contact1)) {
            mPContactView.setError("Required.");
            isvalid = false;
        } else {
            contact.setPhone(contact1);
            mPContactView.setError(null);
        }
        String email = mPEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mPEmailView.setError("Required.");
            isvalid = false;
        } else {
            contact.setEmail(email);
            mPEmailView.setError(null);
        }

        // Address Object
        Address address = new Address();
        String street = mPStreetView.getText().toString();
        if (TextUtils.isEmpty(street)) {
            mPStreetView.setError("Required.");
            isvalid = false;
        } else {
            address.setStreet(street);
            mPStreetView.setError(null);
        }
        String city = mPCityView.getText().toString();
        if (TextUtils.isEmpty(city)) {
            mPCityView.setError("Required.");
            isvalid = false;
        } else {
            address.setCity(city);
            mPCityView.setError(null);
        }
        String proviance = mPProvianceView.getSelectedItem().toString();
        if (TextUtils.isEmpty(proviance)) {
            isvalid = false;
        } else {
            address.setProvince(proviance);
        }
        String zipcode = mPZipcodeView.getText().toString();
        if (TextUtils.isEmpty(zipcode)) {
            mPZipcodeView.setError("Required.");
            isvalid = false;
        } else {
            address.setZipCode(zipcode);
            mPZipcodeView.setError(null);
        }

        if(!isvalid){
            return;
        }
        staff.setContact(contact);
        staff.setAddress(address);

        if(tabs.size()<2) {
            Tab tab = new Tab("Account", 1);
            tabs.add(1, tab);
            mViewPager.getAdapter().notifyDataSetChanged();
        }
        mViewPager.setCurrentItem(1, true);
    }

}
