package com.spy.healthmatic.Nurse;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.spy.healthmatic.Doctor.AddMedsActivity;
import com.spy.healthmatic.Doctor.AddTestActivity;
import com.spy.healthmatic.Doctor.PatientDrActivity;
import com.spy.healthmatic.Doctor.patient_dr_fragments.BioFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.DrNotesFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.MedsFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.TestsFragment;
import com.spy.healthmatic.Doctor.patient_dr_fragments.VitalsFragment;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Nurse.Fragments.PatientDetailsFragment;
import com.spy.healthmatic.Nurse.Fragments.TestResultFragment;
 import com.spy.healthmatic.R;

public class NursePatientDetailsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
      Patient patient;
    public static boolean isAgent = false;

    private FloatingActionButton fab;
    TextView txtName;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final String ARG_PAGE = "ARG_PAGE";

//
//    public static NursePatientDetailsActivity newInstance(int pageNumber) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, pageNumber);
//        NursePatientDetailsActivity myFragment = new NursePatientDetailsActivity();
//
//        return myFragment;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_patient_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbPatientNr);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        android.support.design.widget.AppBarLayout appbar = (android.support.design.widget.AppBarLayout) findViewById(R.id.appbarPatientDr);
//
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
//
//        lp.height = 250;

      //  appbar.setLayoutParams(lp);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
       // mPage = getArguments().getInt(ARG_PAGE);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerPatientNr);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT_OBJ");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Set the title to the name of the patient
        TextView title = new TextView(this);
        title.setText(patient.getFirstName() + " " + patient.getLastName());
        title.setTextAppearance(this, android.R.style.TextAppearance_Material_Widget_ActionBar_Title_Inverse);
        toolbar.addView(title);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsetoolbarPatientDr);
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));


        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setVisibility(View.GONE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()) {
                    case 0:
                        fab.setImageResource(R.drawable.ic_prescription_pill);
                        fab.setVisibility(View.GONE);
                        // fab.show();
                        break;
                    case 1:
                        fab.setVisibility(View.GONE);
                        fab.setImageResource(R.drawable.ic_test);
                        // fab.show();
                        break;
                    case 2:
                        fab.setVisibility(View.VISIBLE);
                        fab.setImageResource(R.drawable.ic_stethoscope);
                        // fab.hide();
                        break;
                    case 3:
                        fab.setVisibility(View.GONE);
                        fab.setImageResource(R.drawable.ic_dr_note);
                        // fab.show();
                        break;
                    case 4:
                        fab.setVisibility(View.GONE);
                        fab.setImageResource(R.drawable.ic_doctor);
                        // fab.show();
                        break;
                }
            }



            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        if(isAgent){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentAddTest;
                int i = mViewPager.getCurrentItem();

                switch (i) {

                    case 2:
                        intentAddTest = new Intent(NursePatientDetailsActivity.this, AddTestActivity.class);
                        startActivity(intentAddTest);
                        break;

                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nurse_patient_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           // mPage=position;
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
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MEDS";
                case 1:
                    return "TESTS";
                case 2:
                    return  "VITALS";
                case 3:
                    return  "NOTES";
                case 4:
                    return  "BIO";

            }
            return null;
        }
    }
}
