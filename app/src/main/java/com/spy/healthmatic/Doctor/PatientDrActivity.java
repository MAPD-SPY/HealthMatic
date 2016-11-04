package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spy.healthmatic.Doctor.adapters.PatientTabPagerAdapter;
import com.spy.healthmatic.R;
import com.spy.healthmatic.models.Patient;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-26.
 */

public class PatientDrActivity extends AppCompatActivity {

    private Patient patient;
    private int tabPos;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbPatientDr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get a reference of the patient object
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT_OBJ");

        // Set the title to the name of the patient
        TextView title = new TextView(this);
        title.setText(patient.getFirstName() + " " + patient.getLastName());
        title.setTextAppearance(this, android.R.style.TextAppearance_Material_Widget_ActionBar_Title_Inverse);
        toolbar.addView(title);

        // Setup the tabs to be shown
        String[] tabs = getResources().getStringArray(R.array.strArrayDetails);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tlPatientsForDoc);
        for (String tabName : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        final PagerAdapter pagerAdapter = new PatientTabPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(),
                patient);

        // Set up the ViewPager with the sections adapter.
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.containerPatientDr);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()) {
                    case 0:
                        fab.setImageResource(R.drawable.ic_prescription_pill);
                        // fab.show();
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.ic_test);
                        // fab.show();
                        break;
                    case 2:
                        fab.setImageResource(R.drawable.ic_stethoscope);
                        // fab.hide();
                        break;
                    case 3:
                        fab.setImageResource(R.drawable.ic_dr_note);
                        // fab.show();
                        break;
                    case 4:
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentAddTest;
                int i = mViewPager.getCurrentItem();

                switch (i) {
                    case 0:
                        intentAddTest = new Intent(PatientDrActivity.this, AddMedsActivity.class);
                        startActivity(intentAddTest);
                        break;
                    case 1:
                        intentAddTest = new Intent(PatientDrActivity.this, AddTestActivity.class);
                        startActivity(intentAddTest);
                        break;

                }
/*
                String[] testsArray = {"CBC", "Urinalysis", "Urine Culture"};
                List<String> testsList = Arrays.asList(testsArray);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientDrActivity.this,
                        android.R.layout.simple_dropdown_item_1line, testsList);

                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.atvLabTestTypes);
                autoCompleteTextView.setAdapter(adapter);


                AlertDialog.Builder builder = new AlertDialog.Builder(PatientDrActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_add_test,null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.setTitle("Laboratory Test");
                dialog.show();
                */
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_dr, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

}
