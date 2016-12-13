package com.spy.healthmatic.LabAgent.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.LabAgent.PatientTestList;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * Created by yatin on 04/11/16.
 */

public class AgentPatientListAdapter extends RecyclerView.Adapter<AgentPatientListAdapter.ViewHolder> implements GlobalConst {

    private ArrayList<Patient> patients;
    private Context mContext;

    public AgentPatientListAdapter(ArrayList<Patient> patients, Context context) {
        this.patients = patients;
        this.mContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPateintConditionView;
        public final TextView mRoomNumberView;
        public final ImageView mPatientGenderIdicator;
        public Patient patient;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.tvPatientName);
            mPateintConditionView = (TextView) itemView.findViewById(R.id.tvPatientCondition);
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoom);
            mPatientGenderIdicator = (ImageView) itemView.findViewById(R.id.ivPatient);

            // Setup a listener to the current view
            mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PATIENT, patients.get(position));
                    Intent intent = new Intent(mContext, PatientTestList.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new AgentPatientListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Patient patient = patients.get(position);
        holder.mNameView.setText(patient.getFirstName());
        holder.mPateintConditionView.setText(patient.getCondition());
        holder.mRoomNumberView.setText(patient.getRoom()+"");
        if("male".equals(patient.getGender())){
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
        }else {
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
        }
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }
}
