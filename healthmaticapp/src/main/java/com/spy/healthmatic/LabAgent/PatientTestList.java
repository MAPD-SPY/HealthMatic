package com.spy.healthmatic.LabAgent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spy.healthmatic.Doctor.adapters.TestsAdapter;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Global.RecyclerItemClickListener;
import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class PatientTestList extends AppCompatActivity implements GlobalConst {

    @Bind(R.id.test_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_dialog)
    ProgressBar mRecylerProgressDialog;

    private RecyclerView.Adapter mAdapter;

    private Patient patient;
    private List<LabTest> labTests;

    private String mCurrentPhotoPath;
    FirebaseStorage storage;
    StorageReference storageRef, photoRef;

    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_test_list);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            patient = (Patient) bundle.getSerializable(PATIENT);
        }
//      Setting Recyclerview
        mRecyclerView.setHasFixedSize(false);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl(FILE_STORAGE_PATH);

        prepareLabTestList();
    }

    private void prepareLabTestList() {
        ArrayList<LabTest> allLabTest = patient.getLabTests();
        if (allLabTest == null || allLabTest.isEmpty()) {
            Toast.makeText(this, "No Lab Test for this patient.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        labTests = new ArrayList<>();
        for (LabTest labTest : allLabTest) {
            if ("On-going".equals(labTest.getStatus())) {
                labTests.add(labTest);
            }
        }
        if (labTests.isEmpty()) {
            Toast.makeText(this, "No Pending Lab Test for this patient.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        prepareTestList();
    }

    private void prepareTestList() {
        mRecylerProgressDialog.setVisibility(View.GONE);
        mAdapter = new TestsAdapter(this, labTests);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            //
            @Override
            public void onItemClick(View view, int position) {
                updateTestDialog(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    public void updateTestDialog(final int position) {
        mCurrentPhotoPath = null;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_test_layout);
        AppCompatImageButton clickPhoto = (AppCompatImageButton) dialog.findViewById(R.id.click_photo);
        ImageView photoBadge = (ImageView) dialog.findViewById(R.id.photo_badge);
        if (mCurrentPhotoPath != null) {
            photoBadge.setVisibility(View.VISIBLE);
        }
        clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askStoragePermission();
                    return;
                }
                takePhoto();
            }
        });
        final AppCompatButton updateTestButton = (AppCompatButton) dialog.findViewById(R.id.update_test);
        updateTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPhotoPath == null) {
                    return;
                }
                uploadPhoto(position);
                mCurrentPhotoPath = null;
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
            takePhoto();
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.spy.healthmatic.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
//            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("IMAGEPATH", "path of image mCurrentPhotoPath >>> " + mCurrentPhotoPath);
        return image;
    }

    private void uploadPhoto(final int position) {
        showProgressDialog();
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File(mCurrentPhotoPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        photoRef = storageRef.child("healthmatic/" + GlobalFunctions.getCurrentDateInMilliseconds() + ".jpg");
        UploadTask uploadTask = photoRef.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                showToast("Unable to upload image. Please try again.");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                labTests.get(position).setStatus("Complete");
                labTests.get(position).setSampleTakenDate(GlobalFunctions.getTodaysDateFormatted());
                labTests.get(position).setImageResult(taskSnapshot.getDownloadUrl().toString());
                updateTestOnServer(position);
            }
        });
    }

    private void updateTestOnServer(int position){
        Call<Patient> patientCall = PATIENTS_LIST_API.updatePatientLabTest(patient.get_id(), labTests.get(position));
        patientCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        showToast( e.getMessage());
                    }
                    return;
                }
                showToast("Test update Succesfull");
                patient = response.body();
                labTests = patient.getLabTests();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                showToast("Test update Un-Succesfull");
            }
        });
    }

    private void showToast(String msg) {
        hideProgressDialog();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Updating Test…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            setPic();
        }
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
