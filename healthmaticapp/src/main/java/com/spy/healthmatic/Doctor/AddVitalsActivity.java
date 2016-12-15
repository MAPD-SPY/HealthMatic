package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Doctor.Utilities.TimeHelpers;
import com.spy.healthmatic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-20.
 */
public class AddVitalsActivity extends AppCompatActivity {

    private String doctorName;
    private String patientID;
    @Bind(R.id.etSystolic) EditText bpSystolic;
    @Bind(R.id.etDiastolic) EditText bpDiastolic;
    @Bind(R.id.etHeartRate) EditText heartRate;
    @Bind(R.id.etRespirationRate) EditText respirationRate;
    @Bind(R.id.etTemperature) EditText temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vitals);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");
        doctorName = intent.getStringExtra("DOCTOR_NAME");

        // Setup Cancel button handler
        Button bCancel = (Button) findViewById(R.id.bVitalsTestCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup Add button listener
        Button bAdd = (Button) findViewById(R.id.bVitalsTestAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Save the vitals if it passes validation
                    if (validate()) {
                        setVitals();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setVitals() throws JSONException, UnsupportedEncodingException {

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("date", TimeHelpers.getCurrentDateAndTime(TimeHelpers.FORMAT_YYYMMDD_HMM_A));
        jsonParams.put("respirationRate", respirationRate.getText().toString());
        jsonParams.put("heartRate", heartRate.getText().toString());
        jsonParams.put("temperature", temperature.getText().toString());
        jsonParams.put("systolic", bpSystolic.getText().toString());
        jsonParams.put("diastolic", bpDiastolic.getText().toString());
        jsonParams.put("takenByName", doctorName);
        addVitals(jsonParams);
    }

    private void addVitals(JSONObject jsonObject) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/patients/" + patientID + "/vitals";
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
                        Toast.makeText(getApplicationContext(), "Unable to add vitals", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

    /**
     * Input format validation to check whether all input fields have been populated
     * @return  true - all fields have been populated
     *          false - not all fields have been populated
     */
    private boolean validate() {
        boolean isValid = true;

        // Show the corresponding Toast message if all fields are empty
        if (bpSystolic.getText().toString().isEmpty() &&
                bpDiastolic.getText().toString().isEmpty() &&
                heartRate.getText().toString().isEmpty() &&
                respirationRate.getText().toString().isEmpty() &&
                temperature.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.strErrorMsgVitals), Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else {
            // Check if only the Systolic field is populated
            if (!bpSystolic.getText().toString().isEmpty()) {
                if (bpDiastolic.getText().toString().isEmpty()) {
                    bpDiastolic.setError(getResources().getString(R.string.strThisIsRequired));
                    isValid = false;
                }
            }

            // Check if only the Diastolic field is populated
            if (!bpDiastolic.getText().toString().isEmpty()) {
                if (bpSystolic.getText().toString().isEmpty()) {
                    bpSystolic.setError(getResources().getString(R.string.strThisIsRequired));
                    isValid = false;
                }
            }
        }

        return isValid;
    }

}
