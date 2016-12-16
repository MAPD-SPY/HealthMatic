package com.spy.healthmatic.Nurse;

//Team Name: Team SPY

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Doctor.PatientActivity;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * Created by prashantn.pol on 2016-10-31.
 */

public class NurseAdapter extends RecyclerView.Adapter<NurseAdapter.MyViewHolder> {

    private Staff nurse;
    ArrayList<Patient> patientList;
    Context context;
    private PatientList.OnPatientListFragmentInteractionListener mListener;

    public  NurseAdapter(ArrayList<Patient> patientList,Context context, Staff nurse)
    {
            this.context=context;
            this.patientList=patientList;
            this.nurse = nurse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);

        return new MyViewHolder(itemView, context, patientList);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Patient patient = patientList.get(position);
        holder.mNameView.setText(patient.getFirstName());
        holder.mPateintConditionView.setText(patient.getCondition());
        holder.mRoomNumberView.setText(patient.getRoom() + "");
        if ("male".equals(patient.getGender())) {
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
        } else {
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(holder.context, PatientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PATIENT_OBJ", patientList.get(position));
                bundle.putSerializable("STAFF_OBJ", nurse);

                 intent.putExtras(bundle);
                 holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public View mView;
        public TextView mNameView;
        public TextView mPateintConditionView;
        public TextView mRoomNumberView;
        public ImageView mPatientGenderIdicator;
        public Patient patient;
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Context context;

        public MyViewHolder(View view, Context context, ArrayList<Patient> patients) {
            super(view);
            this.patients = patients;
            this.context = context;
            //  view.setOnClickListener(this);

            mView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.tvPatientName);
            mPateintConditionView = (TextView) itemView.findViewById(R.id.tvPatientCondition);
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoom);
            mPatientGenderIdicator = (ImageView) itemView.findViewById(R.id.ivPatient);


        }

    }

}
