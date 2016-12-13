package com.spy.healthmatic.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Fragments.DoctorList;
import com.spy.healthmatic.Admin.Fragments.NurseList;
import com.spy.healthmatic.Admin.Fragments.PatientList;
import com.spy.healthmatic.Admin.Fragments.StaffList;
import com.spy.healthmatic.Doctor.PatientActivity;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;
import com.spy.healthmatic.Welcome.Logout;

import butterknife.ButterKnife;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PatientList.OnPatientListFragmentInteractionListener,
        DoctorList.OnDoctorListFragmentInteractionListener, NurseList.OnNurseListFragmentInteractionListener, GlobalConst {


    FragmentTransaction fragmentTransaction;
    int currentFragment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            currentFragment = bundle.getInt("CurrentFragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = null;
        if(currentFragment == 0){
            fragment = new PatientList();
            navigationView.getMenu().getItem(0).setChecked(true);
        }else if(currentFragment == 1){
            fragment = new StaffList();
            navigationView.getMenu().getItem(1).setChecked(true);
        }
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main2,fragment);
        fragmentTransaction.commit();

        Staff staff = GlobalFunctions.getStaff(this);
        View headerLayout = navigationView.getHeaderView(0);
        TextView mNameView = (TextView) headerLayout.findViewById(R.id.user_name);
        TextView mEmailView = (TextView) headerLayout.findViewById(R.id.user_email);
        mNameView.setText(staff.getFirstName());
        mEmailView.setText(staff.getContact().getEmail());
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
            Intent intent = new Intent(this, Logout.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Patient patient, int position) {
        Toast.makeText(AdminMainActivity.this, "Patient "+position + " clicked", Toast.LENGTH_SHORT).show();
//        TODO Form a same POJO class for Patient
        Bundle bundle = new Bundle();
        bundle.putSerializable("PATIENT_OBJ", patient);

        Intent intent = new Intent(this, PatientActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Staff doctor, int position) {
        Intent intent = new Intent(AdminMainActivity.this, AdminAddStaff.class);
        intent.putExtra(ACTION, "update");
        intent.putExtra(STAFF, doctor);
        startActivity(intent);
        Toast.makeText(AdminMainActivity.this, "Doctor "+position + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNurseListFragmentInteraction(Staff nurse, int position) {
        Intent intent = new Intent(AdminMainActivity.this, AdminAddStaff.class);
        intent.putExtra(ACTION, "update");
        intent.putExtra(STAFF, nurse);
        startActivity(intent);
        Toast.makeText(AdminMainActivity.this, "Nurse "+position + " clicked", Toast.LENGTH_SHORT).show();
    }
}
