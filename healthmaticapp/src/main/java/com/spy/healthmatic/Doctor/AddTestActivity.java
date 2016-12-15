package com.spy.healthmatic.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Doctor.Utilities.JsonGlobalHelpers;
import com.spy.healthmatic.Doctor.Utilities.TimeHelpers;
import com.spy.healthmatic.Doctor.Adapters.LabTestTypeAdapter;
import com.spy.healthmatic.R;
import com.spy.healthmatic.models.LabTestType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-20.
 */

public class AddTestActivity extends AppCompatActivity {

    private String doctorName;
    private static ArrayList<LabTestType> labTestTypes;
    private ArrayList<String> mTestsSelected;
    private LabTestTypeAdapter labTestTypeAdapter;
    private String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        // Initialize the types of laboratory tests
        if (labTestTypes == null) {
            labTestTypes = new ArrayList<>();
            getLabTestTypesJSONArray();
        }
        labTestTypeAdapter = new LabTestTypeAdapter(this, labTestTypes);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("PATIENT_ID");
        doctorName = intent.getStringExtra("DOCTOR_NAME");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rvLabTestType);
        recyclerView.setAdapter(labTestTypeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Button bCancel = (Button) findViewById(R.id.bTestCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button bAdd = (Button) findViewById(R.id.bTestAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setLabTest();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getLabTestTypesJSONArray() {
        JSONObject response;
        JSONArray labTestTypesJsonResults;

        try {
            response = new JSONObject(JsonGlobalHelpers.loadJSONFromAsset(this, "labTests.json"));
            labTestTypesJsonResults = response.getJSONArray("labTests");
            labTestTypes.addAll(LabTestType.fromJSONArray(labTestTypesJsonResults));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void setLabTest() throws JSONException, UnsupportedEncodingException {

        mTestsSelected = labTestTypeAdapter.getItemsSelected();

        // Check if none is selected
        if (mTestsSelected.size() == 0) {
            // Show a Toast message that there is nothing to save
            Toast.makeText(this, getResources().getString(R.string.strErrorMsgTests), Toast.LENGTH_LONG).show();
        }
        else {
            // Create an array JSON Object
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();

            // Get time and date
            String timeDateStamp = TimeHelpers.getCurrentDateAndTime(TimeHelpers.FORMAT_YYYMMDD_HMM_A);

            // Setup the list of tests selected
            for (String mTestSelected : mTestsSelected) {
                // Create a JSON Object
                JSONObject jsonObject = new JSONObject();

                // Setup the elements of this object
                jsonObject.put("requestDate", timeDateStamp);
                jsonObject.put("requestedByName", doctorName);
                jsonObject.put("testType", mTestSelected);
                jsonObject.put("sampleTakenDate", "");
                jsonObject.put("sampleTakenByName", "");
                jsonObject.put("imageResult", "");
                jsonObject.put("status", "On-going");

                // Add this into the jsonObject array
                jsonObjects.add(jsonObject);
            }

            // Convert the Array of jsonObjects into a jsonArray format
            JSONArray jsonArray = new JSONArray(jsonObjects);

            // Format this into the proper labTest jsonObject
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("labTests", jsonArray);

            // Send these tests to the server
            addLabTest(jsonParams);
        }
    }

    private void addLabTest(JSONObject jsonObject) throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/patients/" + patientID + "/tests";
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
                Toast.makeText(getApplicationContext(), "Unable to add selected test", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
