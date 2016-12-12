package com.spy.healthmatic.Doctor.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.spy.healthmatic.Doctor.Fragments.BioFragment;
import com.spy.healthmatic.Doctor.Fragments.DrNotesFragment;
import com.spy.healthmatic.Doctor.Fragments.MedsFragment;
import com.spy.healthmatic.Doctor.Fragments.TestsFragment;
import com.spy.healthmatic.Doctor.Fragments.VitalsFragment;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */

public class PatientTabPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_MEDS = 0;
    private static final int TAB_TESTS = 1;
    private static final int TAB_VITALS = 2;
    private static final int TAB_NOTES = 3;
    private static final int TAB_BIO = 4;

    private int tabCount;
    private Patient patient;
    private Staff doctor;

    public PatientTabPagerAdapter(FragmentManager fm, int numberofTabs, Patient patient, Staff doctor) {
        super(fm);
        this.tabCount = numberofTabs;
        this.patient = patient;
        this.doctor = doctor;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("STAFF_OBJ", doctor);

        switch (position) {
            case TAB_MEDS:
                MedsFragment meds = new MedsFragment();
                bundle.putSerializable("PATIENT_MEDS_OBJ", patient.getPrescriptions());
                meds.setArguments(bundle);
                return meds;

            case TAB_TESTS:
                TestsFragment tests = new TestsFragment();
                bundle.putSerializable("PATIENT_TESTS_OBJ", patient.getLabTests());
                tests.setArguments(bundle);
                return tests;

            case TAB_VITALS:
                VitalsFragment vitals = new VitalsFragment();
                bundle.putSerializable("PATIENT_VITALS_OBJ", patient.getVitals());
                vitals.setArguments(bundle);
                return vitals;

            case TAB_NOTES:
                DrNotesFragment drNotes = new DrNotesFragment();
                bundle.putSerializable("PATIENT_NOTES_OBJ", patient.getDrNotes());
                drNotes.setArguments(bundle);
                return drNotes;

            case TAB_BIO:
                BioFragment bioFragment = new BioFragment();
                bundle.putSerializable("PATIENT_OBJ", patient);
                bioFragment.setArguments(bundle);
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
