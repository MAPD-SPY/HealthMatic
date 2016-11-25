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
 * Created by yatin on 28/10/16.
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private ArrayList<Staff> doctors;
    private final DoctorList.OnDoctorListFragmentInteractionListener mListener;

    public DoctorListAdapter(ArrayList<Staff> doctors, DoctorList.OnDoctorListFragmentInteractionListener listener) {
        this.doctors = doctors;
        mListener = listener;
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
            mRoomNumberView = (TextView) itemView.findViewById(R.id.tvRoomNum);
            mPatientGenderIdicator = (ImageView) itemView.findViewById(R.id.ivPatient);
        }
    }

    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new DoctorListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorListAdapter.ViewHolder holder, final int position) {
        final Staff doctor = doctors.get(position);
        holder.mNameView.setText(doctor.getFirstName());
        holder.mPateintConditionView.setText(doctor.getLastName());
        holder.mRoomNumberView.setText(doctor.getFloor()+"");
        if("male".equals(doctor.getGender()+"")){
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
                    mListener.onListFragmentInteraction(doctor, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }
}
