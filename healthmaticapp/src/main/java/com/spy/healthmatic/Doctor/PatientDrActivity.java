package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Doctor.Adapters.PatientTabPagerAdapter;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Model.Vitals;
import com.spy.healthmatic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-10-26.
 */

public class PatientDrActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int TAB_MEDS = 0;
    private static final int TAB_TESTS = 1;
    private static final int TAB_VITALS = 2;
    private static final int TAB_NOTES = 3;
    private static final int TAB_BIO = 4;

    private ViewPager mViewPager;
    private String doctorName;
    private Patient patient;
    private Staff doctor;

    private int tabPos;
    private FloatingActionButton fab;
    private TextView admissionDate, lastCheckup;
    private static boolean isAgent = false;
    @Bind(R.id.tvRRVal) TextView textViewRRate;
    @Bind(R.id.tvBPVal) TextView textViewBP;
    @Bind(R.id.tvHRVal) TextView textViewHR;
    @Bind(R.id.tvTempVal) TextView textViewTemp;
    @Bind(R.id.tvAdmissionDateVal) TextView textViewAdmission;
    @Bind(R.id.tvLastCheckVal) TextView textViewCheckup;
    @Bind(R.id.swipe_refresh_patient) SwipeRefreshLayout swipeRefreshPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dr);

        ButterKnife.bind(this);

        // Setup listener to swipe to refresh
        swipeRefreshPatient.setOnRefreshListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbPatientDr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get a reference of the patient object
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT_OBJ");
        doctor = (Staff) intent.getSerializableExtra("STAFF_OBJ");
        doctorName = "Dr " + doctor.getFirstName() + " " + doctor.getLastName();
        isAgent = intent.getBooleanExtra("isAgent", false);

        // Initialize fields in the Summary/Latest view
        initLatestView();

        // Set the title to the name of the patient
        TextView title = new TextView(this);
        title.setText(patient.getFirstName() + " " + patient.getLastName());
        title.setTextAppearance(this, android.R.style.TextAppearance_Material_Widget_ActionBar_Title_Inverse);
        toolbar.addView(title);


        // Setup the tabs to be shown
        String[] tabs = getResources().getStringArray(R.array.strArrayDetails);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlPatientsForDoc);
        for (String tabName : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        final PagerAdapter pagerAdapter = new PatientTabPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(),
                patient, doctor);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerPatientDr);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()) {
                    case TAB_MEDS:
                        fab.setImageResource(R.drawable.ic_prescription_pill);
                        fab.show();
                        break;
                    case TAB_TESTS:
                        fab.setImageResource(R.drawable.ic_test);
                        fab.show();
                        break;
                    case TAB_VITALS:
                        fab.setImageResource(R.drawable.ic_stethoscope);
                        fab.show();
                        break;
                    case TAB_NOTES:
                        fab.setImageResource(R.drawable.ic_dr_note);
                        fab.show();
                        break;
                    case TAB_BIO:
                        fab.setImageResource(R.drawable.ic_doctor);
                        fab.show();
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
                    case TAB_MEDS:
                        intentAddTest = new Intent(PatientDrActivity.this, AddMedsActivity.class);
                        intentAddTest.putExtra("PATIENT_ID", patient.get_id());
                        intentAddTest.putExtra("DOCTOR_NAME", doctorName);
                        startActivity(intentAddTest);
                        break;
                    case TAB_TESTS:
                        intentAddTest = new Intent(PatientDrActivity.this, AddTestActivity.class);
                        intentAddTest.putExtra("PATIENT_ID", patient.get_id());
                        intentAddTest.putExtra("DOCTOR_NAME", doctorName);
                        startActivity(intentAddTest);
                        break;
                    case TAB_VITALS:
                        intentAddTest = new Intent(PatientDrActivity.this, AddVitalsActivity.class);
                        intentAddTest.putExtra("PATIENT_ID", patient.get_id());
                        intentAddTest.putExtra("DOCTOR_NAME", doctorName);
                        startActivity(intentAddTest);
                        break;
                    case TAB_NOTES:
                        intentAddTest = new Intent(PatientDrActivity.this, AddNotesActivity.class);
                        intentAddTest.putExtra("PATIENT_ID", patient.get_id());
                        intentAddTest.putExtra("DOCTOR_NAME", doctorName);
                        intentAddTest.putExtra("DOCTOR_ID", doctor.get_id());
                        startActivity(intentAddTest);
                        break;
                    case TAB_BIO:
                        intentAddTest = new Intent(PatientDrActivity.this, AddDrActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PATIENT_OBJ", patient);
                        intentAddTest.putExtras(bundle);
                        startActivity(intentAddTest);
                        break;
                }
            }
        });
     }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch patient data from server
        getPatientFromServer();

    }

    @Override
    public void onRefresh() {
        // Fetch patient data from server
        getPatientFromServer();
    }

    private void getPatientFromServer() {
        // Create an Asynchronous HTTP instance
        String url = "http://shelalainechan.com/patients/" + patient.get_id();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray patientJsonResults = null;

                try {
                    // Update the patient's records
                    final Patient patientNew = new Patient(response);
                    patient.setLabTests(patientNew.getLabTests());
                    patient.setDrNotes(patientNew.getDrNotes());
                    patient.setPrescriptions(patientNew.getPrescriptions());
                    patient.setVitals(patientNew.getVitals());
                    patient.setDoctors(patientNew.getDoctors());
                    patient.setNurses(patientNew.getNurses());

                    // Notify adapter of this change
                    mViewPager.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshPatient.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                swipeRefreshPatient.setRefreshing(false);
            }
        });
    }

    /**
     * Initialize the vitals information shown in Summary / Latest view
     */
    private void initLatestView() {
        // Display admission date
        textViewAdmission.setText(patient.getAdmissionDate());

        // Display latest vitals information
        if (patient.getVitals().size() > 0) {
            Vitals vitals = patient.getVitals().get(patient.getVitals().size() - 1);
            textViewRRate.setText(Integer.toString(vitals.getRespirationRate()) + " " + getResources().getString(R.string.strUnitRR));
            textViewBP.setText(Integer.toString(vitals.getSystolic()) + " / " +
                    Integer.toString(vitals.getDiastolic()) + " " + getResources().getString(R.string.strUnitmmHg));
            textViewHR.setText(Integer.toString(vitals.getHeartRate()) + " " + getResources().getString(R.string.strUnitBpm));
            textViewTemp.setText(Integer.toString(vitals.getTemperature()) + " " + getResources().getString(R.string.strUnitC));
            textViewCheckup.setText(vitals.getDate());
        }
    }
}
