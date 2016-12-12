package com.spy.healthmatic.LabAgent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spy.healthmatic.Doctor.adapters.TestsAdapter;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PatientTestList extends AppCompatActivity implements GlobalConst {

    @Bind(R.id.test_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mProgressDialog;

    private RecyclerView.Adapter mAdapter;

    private Patient patient;
    private List<LabTest> labTests;

    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_test_list);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            patient = (Patient) bundle.getSerializable(PATIENT);
        }
//      Setting Recyclerview
        mRecyclerView.setHasFixedSize(false);

        prepareLabTestList();
    }

    private void prepareLabTestList(){
        ArrayList<LabTest> allLabTest = patient.getLabTests();
        if(allLabTest==null || allLabTest.isEmpty()){
            Toast.makeText(this, "No Lab Test for this patient.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        labTests = new ArrayList<>();
        for(LabTest labTest : allLabTest){
            if("On-going".equals(labTest.getStatus())){
                labTests.add(labTest);
            }
        }
        if(labTests.isEmpty()){
            Toast.makeText(this, "No Pending Lab Test for this patient.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        prepareTestList();
    }

    private void prepareTestList(){
        mProgressDialog.setVisibility(View.GONE);
        mAdapter = new TestsAdapter(this, labTests);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addTool(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_tool_layout);
        AppCompatImageButton clickPhoto = (AppCompatImageButton) dialog.findViewById(R.id.click_photo);
        clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askStoragePermission();
            }
        });
        final AppCompatButton saveToolButton = (AppCompatButton) dialog.findViewById(R.id.save_tool);
        saveToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toolName = mToolView.getText().toString();
                if (toolName.equals("")){
                    mToolView.setError("Please provide a value");
                    mToolView.requestFocus();
                    return;
                }
                Tool tool = new Tool(toolName, mCurrentPhotoPath);
                saveTool(tool);
                mCurrentPhotoPath="";
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void askStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            selectPhotos();
        }
    }

    public void selectPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPhotos();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
//                  setPic();
            }
            if (requestCode == REQUEST_SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                mCurrentPhotoPath = getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {

        // just some safety built in
        if (uri == null) {
            // perform some logging or show user feedback
            Log.d(PatientTestList.class.getSimpleName(), "Failed to parse image path from image URI " + uri);
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here, thanks to the answer from @mad indicating this is needed for
        // working code based on images selected using other file managers
        return uri.getPath();
    }

    public void strechDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp = null;
        window = null;
    }
}
