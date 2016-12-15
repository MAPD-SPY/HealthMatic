package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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
public class AddMedsActivity extends AppCompatActivity {

    private String patientID;
    private String doctorName;
    private SeekBar sbDuration;
    @Bind(R.id.etMedsInName) EditText medName;
    @Bind(R.id.etMedsFrequency) EditText medFreq;
    @Bind(R.id.etMedsInDosage) EditText medDosage;
    @Bind(R.id.etDurationVal) EditText medDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");
        doctorName = intent.getStringExtra("DOCTOR_NAME");

        Button bAdd = (Button) findViewById(R.id.bTestAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Save the prescription if all fields have been populated
                    if (validate()) {
                        setPrescription();
                    }
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
        medDuration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int newProgress;

                    if (medDuration.getText().toString().equals("")) {
                        newProgress = 0;
                    } else {
                        newProgress = Integer.parseInt(medDuration.getText().toString());
                    }
                    newProgress = (newProgress > 100) ? 100 : newProgress;
                    sbDuration.setProgress(newProgress);
                }
            }
        });
    }

    private void setPrescription() throws JSONException, UnsupportedEncodingException {

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("date", TimeHelpers.getCurrentDateAndTime(TimeHelpers.FORMAT_YYYMMDD_HMM_A));
        jsonParams.put("medicineName", medName.getText().toString());
        jsonParams.put("dosage", medDosage.getText().toString());
        jsonParams.put("frequency", medFreq.getText().toString());
        jsonParams.put("duration", medDuration.getText().toString());
        jsonParams.put("prescribedByName", doctorName);
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



    /**
     * Input format validation to check whether all input fields have been populated
     * @return  true - all fields have been populated
     *          false - not all fields have been populated
     */
    private boolean validate() {
        boolean isValid = true;

        // Show the corresponding error message if the medicine field is empty
        if (isInvalidString(medName.getText().toString())) {
            medName.setError(getResources().getString(R.string.strThisIsRequired));
            isValid = false;
        }

        // Show the corresponding error message if the dosage field is empty
        if (isInvalidString(medDosage.getText().toString())) {
            medDosage.setError(getResources().getString(R.string.strThisIsRequired));
            isValid = false;
        }

        // Show the corresponding error message if the frequency field is empty
        if (isInvalidString(medFreq.getText().toString())) {
            medFreq.setError(getResources().getString(R.string.strThisIsRequired));
            isValid = false;
        }

        return isValid;
    }

    public static boolean isInvalidString(String string){
        if(string==null || string.isEmpty()){
            return true;
        }
        return false;
    }
}
