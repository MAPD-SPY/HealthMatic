package com.spy.healthmatic.Admin.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spy.healthmatic.Admin.AdminAddStaff;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffAccountFragment extends Fragment implements GlobalConst {

    @Bind(R.id.s_username)
    TextInputEditText mUsernameView;
    @Bind(R.id.s_password)
    TextInputEditText mPasswordView;
    @Bind(R.id.s_floor)
    TextInputEditText mFloorView;
    @Bind(R.id.s_speciality)
    TextInputEditText mSpecialityView;

    Staff staff;
    ArrayList<Tab> tabs;

    ViewPager mViewPager;
    public ProgressDialog mProgressDialog;

    public StaffAccountFragment() {
        // Required empty public constructor
    }

    public static StaffAccountFragment newInstance(Staff staff, ArrayList<Tab> tabs) {
        StaffAccountFragment fragment = new StaffAccountFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_staff_account, container, false);
        ButterKnife.bind(this, rootView);
        if(staff.getFirstName()!=null && !staff.getFirstName().equals("")) {
            setView();
        }
        return rootView;
    }

    private void setView(){
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
        mUsernameView.setText(staff.getUsername());
        mPasswordView.setText(staff.getPassword());
        mFloorView.setText(staff.getFloor());
        mSpecialityView.setText(staff.getSpecialty()[0]);
    }

    @OnClick(R.id.save_staff)
    public void saveStaff(){
        boolean isvalid = true;

        String username = mUsernameView.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("Required.");
            isvalid = false;
        } else {
            staff.setUsername(username);
            mUsernameView.setError(null);
        }

        String passoword = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(passoword)) {
            mPasswordView.setError("Required.");
            isvalid = false;
        } else {
            staff.setPassword(passoword);
            mPasswordView.setError(null);
        }

        String floor = mFloorView.getText().toString();
        if (TextUtils.isEmpty(floor)) {
            mFloorView.setError("Required.");
            isvalid = false;
        } else {
            staff.setFloor(floor);
            mFloorView.setError(null);
        }

        String speciality = mSpecialityView.getText().toString();
        if (TextUtils.isEmpty(speciality)) {
            mPasswordView.setError("Required.");
            isvalid = false;
        } else {
            staff.setSpecialty(new String[]{speciality});
            mPasswordView.setError(null);
        }
        if(!isvalid){
            return;
        }
        String action = ((AdminAddStaff)getActivity()).getAction();
        if("create".equals(action)) {
            saveStaffInServer();
        }else if("update".equals(action)){
            updateSatffInserver();
        }
    }

    private void saveStaffInServer(){
        showProgressDialog();
        Call<Staff> call = STAFF_API.createStaff(staff);
        String staffString = new Gson().toJson(staff);
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                showToast("Staff Created");
                Intent intent = new Intent(getActivity(), AdminMainActivity.class);
                intent.putExtra("CurrentFragment", 1);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void updateSatffInserver(){
        showProgressDialog();
        Call<Staff> call = STAFF_API.updateStaff(staff.get_id(), staff);
        String staffString = new Gson().toJson(staff);
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                showToast("Staff Updated");
                Intent intent = new Intent(getActivity(), AdminMainActivity.class);
                intent.putExtra("CurrentFragment", 1);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void showToast(String msg){
        hideProgressDialog();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Creating Staff…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
