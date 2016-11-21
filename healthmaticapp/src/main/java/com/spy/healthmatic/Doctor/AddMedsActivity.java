package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Doctor.Utilities.TestHelpers;
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
public class AddMedsActivity extends AppCompatActivity {

    private String patientID;
    private SeekBar sbDuration;
    private EditText medName, medFreq, medDosage, medDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");

        medName = (EditText) findViewById(R.id.etMedsInName);
        medFreq = (EditText) findViewById(R.id.etMedsFrequency);
        medDosage = (EditText) findViewById(R.id.etMedsInDosage);
        medDuration = (EditText) findViewById(R.id.etDurationVal);

        Button bAdd = (Button) findViewById(R.id.bTestAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setPrescription();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        Button bCancel = (Button) findViewById(R.id.bTestCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sbDuration = (SeekBar) findViewById(R.id.sbMedsInDuration);
        sbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                medDuration.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        medDuration.setText(Integer.toString(sbDuration.getProgress()));
        medDuration.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                int newProgress;

                if (medDuration.getText().toString().equals("")) {
                    newProgress = 0;
                } else {
                    newProgress = Integer.parseInt(medDuration.getText().toString());
                }
                newProgress = (newProgress > 100) ? 100 : newProgress;
                sbDuration.setProgress(newProgress);
                return false;
            }
        });
    }

    private void setPrescription() throws JSONException, UnsupportedEncodingException {

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("date", TestHelpers.getCurrentDate());
        jsonParams.put("medicineName", medName.getText().toString());
        jsonParams.put("dosage", medDosage.getText().toString());
        jsonParams.put("frequency", medFreq.getText().toString());
        jsonParams.put("duration", medDuration.getText().toString());
        jsonParams.put("prescribedByName", "Dr John Smith");
        addPrescription(jsonParams);
    }

    private void addPrescription(JSONObject jsonObject) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/patients/" + patientID + "/prescriptions";
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
                        Toast.makeText(getApplicationContext(), "Unable to add prescription", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
