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

public class AddNotesActivity extends AppCompatActivity {

    private String doctorName;
    private String doctorId;
    private String patientID;
    private EditText drNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");
        doctorName = intent.getStringExtra("DOCTOR_NAME");
        doctorId = intent.getStringExtra("DOCTOR_ID");

        drNotes = (EditText) findViewById(R.id.etDrNotes);
        // Setup Cancel button handler
        Button bCancel = (Button) findViewById(R.id.bNotesCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup Add button listener
        Button bAdd = (Button) findViewById(R.id.bNotesAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (drNotes.getText().toString().equals("")) {
                        drNotes.setError("Please add notes for this patient.");
                        drNotes.requestFocus();
                        return;
                    }

                    setNotes();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setNotes() throws JSONException, UnsupportedEncodingException {

        // Setup the fields to be written to the dr notes
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("date", TimeHelpers.getCurrentDateAndTime(TimeHelpers.FORMAT_YYYMMDD_HMM_A));
        jsonParams.put("notes", drNotes.getText().toString());
        jsonParams.put("diagnosedByName", doctorName);
        jsonParams.put("drId", doctorId);
        addNotes(jsonParams);
    }

    private void addNotes(JSONObject jsonObject) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/patients/" + patientID + "/notes";
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
                        Toast.makeText(getApplicationContext(), "Unable to add notes", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
