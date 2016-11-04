package com.spy.healthmatic.Doctor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.Prescription;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-02.
 */

public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.ViewHolder> {

    private List<Prescription> mPrescription;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvMedsName;
        // TODO Uncomment once available
        // private TextView mtvMedsNameDetails;
        private TextView mtvMedsDosage;
        private TextView mtvMedsFrequency;
        private TextView mtvMedsDuration;
        private TextView mtvPrescribedBy;
        private TextView mtvPrescribedDate;

        public ViewHolder(View view) {
            super(view);
            mtvMedsName = (TextView) view.findViewById(R.id.tvMedsName);
            // TODO Uncomment once available
            // mtvMedsNameDetails = (TextView) view.findViewById(R.id.tvMedsDetail);
            mtvMedsDosage = (TextView) view.findViewById(R.id.tvMedsDosageVal);
            mtvMedsFrequency = (TextView) view.findViewById(R.id.tvsMedsFrequencyVal);
            mtvMedsDuration = (TextView) view.findViewById(R.id.tvMedsDurationVal);
            mtvPrescribedBy = (TextView) view.findViewById(R.id.tvMedsDrName);
            mtvPrescribedDate = (TextView) view.findViewById(R.id.tvMedsRxDateVal);
        }
    }

    public MedsAdapter(Context context, List<Prescription> prescriptions) {
        mPrescription = prescriptions;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public MedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_meds, parent, false);
        MedsAdapter.ViewHolder viewHolder = new MedsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MedsAdapter.ViewHolder holder, int position) {
        Prescription prescription = mPrescription.get(position);

        (holder.mtvMedsName).setText(prescription.getMedicineName());
        (holder.mtvMedsDosage).setText(prescription.getDosage());
        (holder.mtvMedsFrequency).setText(prescription.getFrequency());
        (holder.mtvMedsDuration).setText(prescription.getDuration());
        (holder.mtvPrescribedDate).setText(prescription.getDate());
        (holder.mtvPrescribedBy).setText(prescription.getPrescribedByName());
    }

    @Override
    public int getItemCount() {
        return mPrescription.size();
    }

}
