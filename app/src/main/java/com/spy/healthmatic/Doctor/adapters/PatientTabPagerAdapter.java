package com.spy.healthmatic.Doctor.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.spy.healthmatic.Doctor.patient_dr_fragments.BioFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.DrNotesFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.MedsFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.TestsFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.VitalsFragment;
import com.spy.healthmatic.Model.Patient;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */

public class PatientTabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private Patient patient;

    public PatientTabPagerAdapter(FragmentManager fm, int numberofTabs, Patient patient) {
        super(fm);
        this.tabCount = numberofTabs;
        this.patient = patient;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("PATIENT_OBJ", patient);


        switch (position) {
            case 0:
                MedsFragment meds = new MedsFragment();
                meds.setArguments(bundle);
                return meds;

            case 1:
                TestsFragment tests = new TestsFragment();
                tests.setArguments(bundle);
                return tests;

            case 2:
                VitalsFragment vitals = new VitalsFragment();
                return vitals;

            case 3:
                DrNotesFragment drNotes = new DrNotesFragment();
                return drNotes;

            case 4:
                BioFragment bioFragment = new BioFragment();
                return bioFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show the number total pages.
        return tabCount;
    }
}
