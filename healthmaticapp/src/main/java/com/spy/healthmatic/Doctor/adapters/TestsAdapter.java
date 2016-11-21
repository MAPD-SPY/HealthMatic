package com.spy.healthmatic.Doctor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.LabTest;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-02.
 */

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {

    private List<LabTest> mLabTests;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvTestName;
        private TextView mtvTestRequestedByName;
        private TextView mtvTestRequestedDateVal;
        private TextView mtvTestStatusVal;

        public ViewHolder(View view) {
            super(view);
            mtvTestName = (TextView) view.findViewById(R.id.tvTestName);
            mtvTestRequestedByName = (TextView) view.findViewById(R.id.tvTestRequestedByName);
            mtvTestRequestedDateVal = (TextView) view.findViewById(R.id.tvTestRequestedDateVal);
            mtvTestStatusVal = (TextView) view.findViewById(R.id.tvTestStatusVal);
        }
    }

    public TestsAdapter(Context context, List<LabTest> labTests) {
        mLabTests = labTests;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_test, parent, false);
        TestsAdapter.ViewHolder viewHolder = new TestsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TestsAdapter.ViewHolder holder, int position) {
        LabTest labTest = mLabTests.get(position);

        (holder.mtvTestName).setText(labTest.getTestType());
        (holder.mtvTestRequestedByName).setText(labTest.getRequestedByName());
        (holder.mtvTestRequestedDateVal).setText(labTest.getRequestDate());
        (holder.mtvTestStatusVal).setText(labTest.getStatus());
    }

    @Override
    public int getItemCount() {
        return mLabTests.size();
    }

}
