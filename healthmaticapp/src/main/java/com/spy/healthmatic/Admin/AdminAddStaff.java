package com.spy.healthmatic.Admin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.spy.healthmatic.Admin.Fragments.StaffAccountFragment;
import com.spy.healthmatic.Admin.Fragments.StaffPersonalFragment;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdminAddStaff extends AppCompatActivity implements GlobalConst {

    ArrayList<Tab> tabs;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.container)
    ViewPager mViewPager;
    String role, action;
    private Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_staff);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            role = bundle.getString(ROLE);
            action = bundle.getString(ACTION);
            staff = (Staff) bundle.getSerializable(STAFF);
        }
        if (staff == null) {
            staff = new Staff();
            staff.setRole(role);
            tabs = new ArrayList<>(2);
            tabs.add(0, new Tab("Personal", 0));
        }else{
            tabs = new ArrayList<>(2);
            tabs.add(0, new Tab("Personal", 0));
            tabs.add(1, new Tab("Account Setup", 1));
        }

        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0, true);

        tabLayout.setupWithViewPager(mViewPager);
    }

    public ViewPager getViewPagerObject() {
        return mViewPager;
    }

    public String getAction() {
        return action;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return StaffPersonalFragment.newInstance(staff, tabs);
            }
            return StaffAccountFragment.newInstance(staff, tabs);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).getName();
        }
    }

}
