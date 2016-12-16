package com.spy.healthmatic.Admin.Adapters;

//Team Name: Team SPY

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yatin on 28/10/16.
 */

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder>  {

    private ArrayList<Patient> patients;
    private final PatientList.OnPatientListFragmentInteractionListener mListener;

    public PatientListAdapter(ArrayList<Patient> patients, PatientList.OnPatientListFragmentInteractionListener listener) {
        this.patients = patients;
        mListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPateintConditionView;
        public final TextView mRoomNumberView;
        public final CircleImageView mPatientGenderIdicator;
        public Patient patient;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.tvPatientName);
            mPateintConditionView = (TextView) itemView.findViewById(R.id.tvPatientCondition);
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoom);
            mPatientGenderIdicator = (CircleImageView) itemView.findViewById(R.id.ivPatient);
        }
    }

    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientListAdapter.ViewHolder holder, final int position) {
        final Patient patient = patients.get(position);
        holder.mNameView.setText(patient.getFirstName());
        holder.mPateintConditionView.setText(patient.getCondition());
        holder.mRoomNumberView.setText(patient.getBloodType()+"");
        if("male".equals(patient.getGender())){
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
        }else {
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(patient, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


}
