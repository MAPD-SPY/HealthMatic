package com.spy.healthmatic.Nurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Doctor.PatientDrActivity;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Nurse.Fragments.PatientDetailsFragment;
 import com.spy.healthmatic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashantn.pol on 2016-10-31.
 */

public class NurseAdapter extends RecyclerView.Adapter<NurseAdapter.MyViewHolder>  {

    private   PatientList.OnPatientListFragmentInteractionListener mListener;

    ArrayList<Patient> patientList;
    Context context;

    public  NurseAdapter(ArrayList<Patient> patientList,Context context)
    {
            this.context=context;
            this.patientList=patientList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new MyViewHolder(itemView,context,patientList);
     }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Patient patient = patientList.get(position);
        if(patient!=null) {
            holder.mNameView.setText(patient.getFirstName() + " " + patient.getLastName());
            holder.mPateintConditionView.setText(patient.getCondition());
            // holder.mRoomNumberView.setText(patient.getRoomNumber());
            if ("male".equals(patient.getGender())) {
                holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
            } else {
                holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
            }
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.context, NursePatientDetailsActivity.class);
                    //   intent.putExtra("PatientName",patient.getName());
                    //   intent.putExtra("Department",patient.getDepartment());


                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PATIENT_OBJ", patientList.get(position));


                    intent.putExtras(bundle);
                    holder.context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public   View mView;
        public   TextView mNameView;
        public   TextView mPateintConditionView;
        public   TextView mRoomNumberView;
        public   ImageView mPatientGenderIdicator;
        public Patient patient;
        ArrayList<Patient>  patients=new ArrayList<Patient>();
        Context context;

        public MyViewHolder(View view, Context context, ArrayList<Patient> patients) {
            super(view);
            this.patients=patients;
            this.context=context;
          //  view.setOnClickListener(this);

            mView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.tvPatientName);
            mPateintConditionView = (TextView) itemView.findViewById(R.id.tvPatientCondition);
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoomNum);
            mPatientGenderIdicator = (ImageView) itemView.findViewById(R.id.ivPatient);

        }


    }


}
