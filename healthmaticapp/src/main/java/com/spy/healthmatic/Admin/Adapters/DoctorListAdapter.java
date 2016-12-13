package com.spy.healthmatic.Admin.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spy.healthmatic.Admin.Fragments.DoctorList;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * Created by yatin on 28/10/16.
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> implements GlobalConst {

    private final DoctorList.OnDoctorListFragmentInteractionListener mListener;
    private ArrayList<Staff> doctors;
    Context context;


    public DoctorListAdapter(ArrayList<Staff> doctors, DoctorList.OnDoctorListFragmentInteractionListener listener, Context context) {
        this.doctors = doctors;
        mListener = listener;
        this.context = context;
    }

    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new DoctorListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorListAdapter.ViewHolder holder, final int position) {
        final Staff doctor = doctors.get(position);
        holder.mNameView.setText(doctor.getFirstName());
        holder.mPateintConditionView.setText(doctor.getLastName());
        holder.mRoomNumberView.setText(doctor.getFloor() + "");
        if (doctor.getImageName() != null && !"".equals(doctor.getImageName())) {
            Glide.with(context).load(doctor.getImageName()).error(R.drawable.ic_menu_camera).into(holder.mPatientGenderIdicator);
        } else {
            if ("male".equals(doctor.getGender() + "")) {
                holder.mPatientGenderIdicator.setImageResource(R.drawable.user_male);
            } else {
                holder.mPatientGenderIdicator.setImageResource(R.drawable.user_female);
            }
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
