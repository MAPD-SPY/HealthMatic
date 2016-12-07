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
    private EditText bpSystolic, bpDiastolic, heartRate, temperature, respirationRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vitals);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");
        doctorName = intent.getStringExtra("DOCTOR_NAME");

        bpSystolic = (EditText) findViewById(R.id.etSystolic);
        bpDiastolic = (EditText) findViewById(R.id.etDiastolic);
        heartRate = (EditText) findViewById(R.id.etHeartRate);
        respirationRate = (EditText) findViewById(R.id.etRespirationRate);
        temperature = (EditText) findViewById(R.id.etTemperature);

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
                    setVitals();
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
        jsonParams.put("date", TimeHelpers.getCurrentDateAndTime());
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
}
