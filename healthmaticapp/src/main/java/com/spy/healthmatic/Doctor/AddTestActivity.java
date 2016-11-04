package com.spy.healthmatic.Doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.spy.healthmatic.Doctor.Utilities.JsonGlobalHelpers;
import com.spy.healthmatic.Doctor.adapters.LabTestTypeAdapter;
import com.spy.healthmatic.R;
import com.spy.healthmatic.models.LabTestType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddTestActivity extends AppCompatActivity {

    private ArrayList<LabTestType> labTestTypes;
    private ArrayList<String> mTestsSelected;
    LabTestTypeAdapter labTestTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        // Initialize the types of laboratory tests
        if (labTestTypes == null) {
            labTestTypes = new ArrayList<>();
            labTestTypeAdapter = new LabTestTypeAdapter(this, labTestTypes);
            getLabTestTypesJSONArray();
        }

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
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disabled Back press
        // super.onBackPressed();
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
}
