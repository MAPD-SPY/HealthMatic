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
import com.spy.healthmatic.Doctor.Utilities.TestHelpers;
import com.spy.healthmatic.Doctor.adapters.LabTestTypeAdapter;
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

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("requestDate", TestHelpers.getCurrentDate());
        jsonParams.put("requestedByName", "Dr John Smith");
        jsonParams.put("testType", mTestsSelected.get(0));
        jsonParams.put("sampleTakenDate", "");
        jsonParams.put("sampleTakenByName", "");
        jsonParams.put("imageResult", "");
        jsonParams.put("status", "On-going");
        addLabTest(jsonParams);
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
