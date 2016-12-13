package com.spy.healthmatic.Doctor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spy.healthmatic.R;
import com.spy.healthmatic.Model.DrNotes;

import java.util.List;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-04.
 */

public class DrNotesAdapter extends RecyclerView.Adapter<DrNotesAdapter.ViewHolder>{
    private List<DrNotes> mDrNotes;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtvNoteDoctor;
        private TextView mtvNoteDate;
        private TextView mtvNote;

        public ViewHolder(View view) {
            super(view);
            mtvNoteDoctor = (TextView) view.findViewById(R.id.tvNoteNameStr);
            mtvNoteDate = (TextView) view.findViewById(R.id.tvNoteDateVal);
            mtvNote = (TextView) view.findViewById(R.id.tvNoteNote);
        }
    }

    public DrNotesAdapter(Context context, List<DrNotes> drNotes) {
        mDrNotes = drNotes;
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public DrNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_dr_note, parent, false);
        DrNotesAdapter.ViewHolder viewHolder = new DrNotesAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrNotesAdapter.ViewHolder holder, int position) {
        DrNotes drNote = mDrNotes.get(position);

        (holder.mtvNote).setText(drNote.getNotes());
        (holder.mtvNoteDate).setText(drNote.getDate());
        (holder.mtvNoteDoctor).setText(drNote.getDiagnosedByName());
    }

    @Override
    public int getItemCount() {
        return mDrNotes.size();
    }

}
