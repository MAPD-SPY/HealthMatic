package com.spy.healthmatic.Doctor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.Vitals;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-04.
 */

public class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.ViewHolder> {

    private List<Vitals> mVitals;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvBloodPressure;
        private TextView mtvTemperature;
        private TextView mtvRespirationRate;
        private TextView mtvHeartRate;
        private TextView mtvDateTaken;

        public ViewHolder(View view) {
            super(view);
            mtvRespirationRate = (TextView) view.findViewById(R.id.tvRRVal);
            mtvBloodPressure = (TextView) view.findViewById(R.id.tvBPVal);
            mtvHeartRate = (TextView) view.findViewById(R.id.tvHRVal);
            mtvTemperature = (TextView) view.findViewById(R.id.tvTempVal);
            mtvDateTaken = (TextView) view.findViewById(R.id.tvVitalsTakenDateVal);
        }
    }


    public VitalsAdapter(Context context, List<Vitals> vitals) {
        mVitals = vitals;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public VitalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_vitals, parent, false);
        VitalsAdapter.ViewHolder viewHolder = new VitalsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VitalsAdapter.ViewHolder holder, int position) {
        Vitals vitals = mVitals.get(position);

        (holder.mtvRespirationRate).setText(Integer.toString(vitals.getRespirationRate()) + " " +
                mContext.getResources().getString(R.string.strUnitRR));
        (holder.mtvBloodPressure).setText(Integer.toString(vitals.getSystolic()) + " / " +
                Integer.toString(vitals.getDiastolic()) + " " +
                mContext.getResources().getString(R.string.strUnitmmHg));
        (holder.mtvHeartRate).setText(Integer.toString(vitals.getHeartRate()) + " " +
                mContext.getResources().getString(R.string.strUnitBpm));
        (holder.mtvTemperature).setText(Integer.toString(vitals.getTemperature()) + " " +
                mContext.getResources().getString(R.string.strUnitC));

        (holder.mtvDateTaken).setText(vitals.getDate());
    }

    @Override
    public int getItemCount() {
        return mVitals.size();
    }

}
