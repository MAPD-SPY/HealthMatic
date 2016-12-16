package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.spy.healthmatic.R.id.doctor;

public class AddDrActivity extends AppCompatActivity implements GlobalConst {

    private Patient patient;
    private ArrayList<Staff> doctors;
    private ArrayList<String> doctorNames;
    private ArrayAdapter<String> doctorsAdapter;
    private int selection;
    @Bind(doctor) AutoCompleteTextView acvDoctorName;
    @Bind(R.id.llcard) LinearLayout llCardView;
    @Bind(R.id.btnAddDoctor) Button saveButton;
    @Bind(R.id.btnCancel) Button cancelButton;
    @Bind(R.id.ivStaff) ImageView ivDoctorImage;
    @Bind(R.id.tvStaffName) TextView tvDoctorName;
    @Bind(R.id.tvSpecialization) TextView tvSpecialties;
    @Bind(R.id.tvContactNum) TextView tvContactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dr);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT_OBJ");

        // Bind this view
        ButterKnife.bind(this);

        // Initially hide the card view
        llCardView.setVisibility(View.INVISIBLE);

        // Setup the save button
        saveButton.setEnabled(false);

        // Setup the listener for the doctor name textview
        acvDoctorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveButton.setEnabled(true);

                String doctor = acvDoctorName.getText().toString();
                if (!doctor.equals("")) {
                    tvDoctorName.setText(doctor);
                    // Show the details of the doctor selected in the card
                    selection = doctorNames.indexOf(doctor);
                    if(doctors.get(selection).getImageName()!=null && !"".equals(doctors.get(selection).getImageName().trim())){
                        Glide.with(AddDrActivity.this).load(doctors.get(selection).getImageName()).error(R.drawable.ic_menu_camera).into(ivDoctorImage);
                    }
                    tvSpecialties.setText(Arrays.toString(doctors.get(selection).getSpecialty()));
                    tvContactNum.setText(String.valueOf(doctors.get(selection).getContact().getPhone()));
                    llCardView.setVisibility(View.VISIBLE);
                }
            }
        });

        // Setup the save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctor = acvDoctorName.getText().toString();
                if (doctor.equals("")) {
                    acvDoctorName.setError(getResources().getString(R.string.strErrorDrInput));
                    acvDoctorName.requestFocus();
                    return;
                }

                try {
                    setDoctor();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        // Setup the cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDoctorList();
    }

    /**
     * Setup the doctor adapter for the AutoCompleteView
     */
    private void setupAdapter() {

        // Get all doctors assigned to the patient
        ArrayList<Doctor> patientDoctors = new ArrayList<>();
        patientDoctors = (patient.getDoctors());

        // Remove the doctor(s) which has already been assigned to this patient from the selection
        if (patientDoctors != null) {
            for (Doctor patientDoctor : patientDoctors) {
                for (Staff doctor : doctors) {
                    if (patientDoctor.getId().equals(doctor.get_id())) {
                        doctors.remove(doctor);
                        break;
                    }
                }
            }
        }

        // Build the doctor names based on the names of doctors object
        doctorNames = new ArrayList<>();
        for (Staff staff : doctors) {
            doctorNames.add(staff.getFirstName() + " " + staff.getLastName());
        }
        doctorsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorNames);

        // Assign the doctorNames to the AutoCompleteTextView
        acvDoctorName.setAdapter(doctorsAdapter);
    }

    /**
     * Get the list of doctors from the server
     */
    private void getDoctorList(){
        Call<ArrayList<Staff>> call = STAFF_API.getAllDoctors();
        call.enqueue(new Callback<ArrayList<Staff>>() {
            @Override
            public void onResponse(Call<ArrayList<Staff>> call, Response<ArrayList<Staff>> response) {
                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "RETROFIT FAILURE - RESPONSE FAIL >>>>> " + response.errorBody());
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.strRetroFitFailureMsg),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // Save the list of doctors
                doctors = response.body();
                // Setup the adapter
                setupAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<Staff>> call, Throwable t) {
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.strRetroFitFailureMsg),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDoctor() throws JSONException, UnsupportedEncodingException {

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("id", doctors.get(selection).get_id());
        jsonParams.put("name", doctors.get(selection).getFirstName() + " " + doctors.get(selection).getLastName());
        jsonParams.put("specialty", Arrays.toString(doctors.get(selection).getSpecialty()));
        jsonParams.put("gender", doctors.get(selection).getGender() ? "Female" : "Male" );
        addDoctor(jsonParams);
    }

    private void addDoctor(JSONObject jsonObject) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/patients/" + patient.get_id() + "/doctors";
        ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        AsyncHttpClient client = new AsyncHttpClient();

        client.put(getApplicationContext(), url,
                entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getApplicationContext(), "Unable to add doctor", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
