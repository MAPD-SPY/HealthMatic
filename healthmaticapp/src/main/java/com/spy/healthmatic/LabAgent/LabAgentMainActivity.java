package com.spy.healthmatic.LabAgent;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Admin.Adapters.PatientListAdapter;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.LabAgent.Adapters.AgentPatientListAdapter;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Nurse.NurseAdapter;
import com.spy.healthmatic.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.spy.healthmatic.R;
import com.spy.healthmatic.Welcome.Logout;
import com.spy.healthmatic.Welcome.SplashScreen;

public class LabAgentMainActivity extends AppCompatActivity implements GlobalConst, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<Patient> patients;
    Staff labAgent;

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_agent_main);
        ButterKnife.bind(this);
        fab.setVisibility(View.GONE);
        labAgent = GlobalFunctions.getStaff(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.circlePVRim),

                getResources().getColor(R.color.circlePVBar),

                getResources().getColor(R.color.appBarScrim),

                getResources().getColor(R.color.yellow));
//      Setting Recyclerview
        mRecyclerView.setHasFixedSize(false);
//      Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getPatientList(false);
    }

    public void onStart(){
        super.onStart();
//        patients = GlobalFunctions.getPatientJSONArray(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadRecyclerViewElements();
//            }
//        }, 2000);
    }

    private void getPatientList(final boolean isRefresh) {
        Call<ArrayList<Patient>> call = STAFF_API.getAllStaffPatinet(labAgent.get_id());
        call.enqueue(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "RETROFIT FAILURE - RESPONSE FAIL >>>>> " + response.errorBody());
                    Toast.makeText(LabAgentMainActivity.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                patients = response.body();
                loadRecyclerViewElements();
                if (isRefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(LabAgentMainActivity.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadRecyclerViewElements(){
        mProgressDialog.setVisibility(View.GONE);
        mAdapter = new AgentPatientListAdapter(patients,this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            Intent intent = new Intent(this, Logout.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getPatientList(true);
    }
}
