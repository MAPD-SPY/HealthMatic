package com.spy.healthmatic.Admin.Fragments;

//Team Name: Team SPY

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spy.healthmatic.AndroidSlidingTab.SlidingTabLayout;
import com.spy.healthmatic.R;

import java.util.Locale;

public class StaffList extends Fragment {

    ViewPager mViewPager;
    private SlidingTabLayout slidingTabLayout;
    SectionsPagerAdapter mSectionsPagerAdapter;

    public StaffList() {
        // Required empty public constructor
    }

    public static StaffList newInstance(String param1, String param2) {
        StaffList fragment = new StaffList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_staff_list,container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(R.color.colorPrimaryDark);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        slidingTabLayout.setViewPager(mViewPager);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DoctorList();
                case 1:
                default:
                    return new NurseList();

            }
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Doctors".toUpperCase(l);
                case 1:
                    return "Nurses".toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
