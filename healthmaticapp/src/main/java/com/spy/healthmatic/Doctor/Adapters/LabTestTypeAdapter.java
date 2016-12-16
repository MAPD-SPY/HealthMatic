package com.spy.healthmatic.Doctor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spy.healthmatic.Model.Laboratory;
import com.spy.healthmatic.R;
import com.spy.healthmatic.models.LabTestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-04.
 */

public class LabTestTypeAdapter extends RecyclerView.Adapter<LabTestTypeAdapter.ViewHolder> {

    private List<Laboratory> mLabTests;
    private ArrayList<String> mLabTestsSelected;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbLabTestType;
        private TextView tvLabTestDescription;

        public ViewHolder(View view) {
            super(view);
            cbLabTestType = (CheckBox) view.findViewById(R.id.cbTestType);
            tvLabTestDescription = (TextView) view.findViewById(R.id.tvLabTestDescription);
        }
    }

    public LabTestTypeAdapter(Context context, List<Laboratory> labTests) {
        mLabTests = labTests;
        mContext = context;

        if (mLabTestsSelected == null) {
            mLabTestsSelected = new ArrayList<String>();
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public LabTestTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_test_type, parent, false);
        LabTestTypeAdapter.ViewHolder viewHolder = new LabTestTypeAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LabTestTypeAdapter.ViewHolder holder, final int position) {
        final Laboratory labTestType = mLabTests.get(position);

        final CheckBox checkBox = holder.cbLabTestType;
        TextView textView = holder.tvLabTestDescription;
        checkBox.setText(labTestType.getName());
        checkBox.setChecked(false);
        textView.setText(labTestType.getDescription());

        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {
                    mLabTestsSelected.add(labTestType.getName());
                } else {
                    int i = mLabTestsSelected.indexOf(labTestType.getName());
                    mLabTestsSelected.remove(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mLabTests.size();
    }

    public ArrayList<String> getItemsSelected() {
        return mLabTestsSelected;
    }

}
