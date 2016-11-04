package com.spy.healthmatic.Doctor.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Doctor.PatientDrActivity;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.Patient;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-26.
 */

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {

    private List<Patient> mPatients;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mivPatient;
        private TextView mtvPatientName;
        private TextView mtvPatientCondition;
        private TextView mtvRoomNum;

        public ViewHolder(View view) {
            super(view);
            mivPatient = (ImageView) view.findViewById(R.id.ivPatient);
            mtvPatientName = (TextView) view.findViewById(R.id.tvPatientName);
            mtvRoomNum = (TextView) view.findViewById(R.id.tvRoomNum);
            mtvPatientCondition = (TextView) view.findViewById(R.id.tvPatientCondition);

            // Setup a listener to the current view
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PATIENT_OBJ", mPatients.get(position));

                    Intent intent = new Intent(mContext, PatientDrActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public PatientsAdapter(Context context, List<Patient> patients) {
        mPatients = patients;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public PatientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_patient, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientsAdapter.ViewHolder holder, int position) {
        Patient patient = mPatients.get(position);

        ImageView imageView = holder.mivPatient;
        if (patient.getGender() == Patient.FEMALE) {
            imageView.setBackgroundResource(R.mipmap.user_female);
        } else {
            imageView.setBackgroundResource(R.mipmap.user_male);
        }
        TextView textViewName = holder.mtvPatientName;
        textViewName.setText(patient.getFirstName() + " " + patient.getLastName());
        TextView textViewRoom = holder.mtvRoomNum;
        textViewRoom.setText(Integer.toString(patient.getRoom()));
        TextView textViewCondition = holder.mtvPatientCondition;
        textViewCondition.setText(patient.getCondition());
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }


}
