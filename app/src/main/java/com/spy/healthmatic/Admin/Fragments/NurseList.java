package com.spy.healthmatic.Admin.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Adapters.DoctorListAdapter;
import com.spy.healthmatic.Admin.Adapters.NurseListAdapter;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.POJO.Doctor;
import com.spy.healthmatic.POJO.Nurse;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NurseList extends Fragment {

    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;

    ArrayList<Nurse> nurses;
    private OnNurseListFragmentInteractionListener mListener;

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public NurseList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nurse_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onStart(){
        super.onStart();
        nurses = GlobalFunctions.getDummyNurses(10);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadRecyclerViewElements();
            }
        }, 2000);
    }

    private void loadRecyclerViewElements(){
        mProgressDialog.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(false);
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NurseListAdapter(nurses, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NurseList.OnNurseListFragmentInteractionListener) {
            mListener = (NurseList.OnNurseListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @OnClick(R.id.fab)
    public void addNurse(){
        Toast.makeText(getActivity(), "Add Nurse clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnNurseListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Nurse nurse, int position);
    }

}
