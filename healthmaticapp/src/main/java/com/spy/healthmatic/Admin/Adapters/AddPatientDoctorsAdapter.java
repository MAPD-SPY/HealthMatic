package com.spy.healthmatic.Admin.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Admin.Fragments.DoctorList;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * Created by yatin on 22/11/16.
 */

public class AddPatientDoctorsAdapter extends RecyclerView.Adapter<AddPatientDoctorsAdapter.ViewHolder> {

    private ArrayList<Staff> doctors;

    public AddPatientDoctorsAdapter(ArrayList<Staff> doctors) {
        this.doctors = doctors;
    }
    @Override
    public AddPatientDoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new AddPatientDoctorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddPatientDoctorsAdapter.ViewHolder holder, int position) {
        final Staff doctor = doctors.get(position);
        holder.mNameView.setText(doctor.getFirstName());
        holder.mPateintConditionView.setText(doctor.getLastName());
        holder.mRoomNumberView.setText(doctor.getUsername());
        if("male".equals(doctor.getGender())){
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
        }else {
            holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
        }
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPateintConditionView;
        public final TextView mRoomNumberView;
        public final ImageView mPatientGenderIdicator;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.tvPatientName);
            mPateintConditionView = (TextView) itemView.findViewById(R.id.tvPatientCondition);
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoom);
            mPatientGenderIdicator = (ImageView) itemView.findViewById(R.id.ivPatient);
        }
    }
}
