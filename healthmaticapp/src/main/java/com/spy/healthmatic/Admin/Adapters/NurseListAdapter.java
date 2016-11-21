package com.spy.healthmatic.Admin.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spy.healthmatic.Admin.Fragments.NurseList;
import com.spy.healthmatic.Model.Nurse;
import com.spy.healthmatic.R;

import java.util.ArrayList;

/**
 * Created by yatin on 28/10/16.
 */

public class NurseListAdapter extends RecyclerView.Adapter<NurseListAdapter.ViewHolder>  {

    private ArrayList<Nurse> nurses;
    private final NurseList.OnNurseListFragmentInteractionListener mListener;

    public NurseListAdapter(ArrayList<Nurse> nurses, NurseList.OnNurseListFragmentInteractionListener listener) {
        this.nurses = nurses;
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
    public NurseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_cardview, parent, false);
        return new NurseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NurseListAdapter.ViewHolder holder, final int position) {
        final Nurse nurse = nurses.get(position);
        holder.mNameView.setText(nurse.getName());
        holder.mPateintConditionView.setText(nurse.getFloor()+"");
        holder.mRoomNumberView.setText(nurse.getFloor()+"");
        if("male".equals(nurse.getGender())){
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
                    mListener.onListFragmentInteraction(nurse, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nurses.size();
    }
}
