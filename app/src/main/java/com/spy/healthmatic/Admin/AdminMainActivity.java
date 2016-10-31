package com.spy.healthmatic.Admin;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Fragments.DoctorList;
import com.spy.healthmatic.Admin.Fragments.NurseList;
import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Admin.Fragments.StaffList;
import com.spy.healthmatic.POJO.Doctor;
import com.spy.healthmatic.POJO.Nurse;
import com.spy.healthmatic.POJO.Patient;
import com.spy.healthmatic.R;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PatientList.OnPatientListFragmentInteractionListener,
        DoctorList.OnDoctorListFragmentInteractionListener, NurseList.OnNurseListFragmentInteractionListener {


    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PatientList fragment = new PatientList();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main2,fragment);
        fragmentTransaction.commit();

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_patient_list) {
            Toast.makeText(getApplicationContext(),"PatientList Selected",Toast.LENGTH_SHORT).show();
            PatientList fragment = new PatientList();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main2,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_staff_list) {
            Toast.makeText(getApplicationContext(),"StaffList Selected",Toast.LENGTH_SHORT).show();
            StaffList fragment = new StaffList();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main2,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_edit_profile) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Patient patient, int position) {
        Toast.makeText(AdminMainActivity.this, "Patient "+position + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Doctor doctor, int position) {
        Toast.makeText(AdminMainActivity.this, "Doctor "+position + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Nurse nurse, int position) {
        Toast.makeText(AdminMainActivity.this, "Nurse "+position + " clicked", Toast.LENGTH_SHORT).show();
    }
}
