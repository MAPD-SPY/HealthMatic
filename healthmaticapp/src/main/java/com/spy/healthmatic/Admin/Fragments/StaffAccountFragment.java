package com.spy.healthmatic.Admin.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.spy.healthmatic.Admin.AdminAddStaff;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Model.Tab;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffAccountFragment extends Fragment implements GlobalConst {

    public ProgressDialog mProgressDialog;
    @Bind(R.id.s_username)
    TextInputEditText mUsernameView;
    @Bind(R.id.s_password)
    TextInputEditText mPasswordView;
    @Bind(R.id.s_floor)
    TextInputEditText mFloorView;
    @Bind(R.id.s_speciality)
    TextInputEditText mSpecialityView;
    @Bind(R.id.photo_badge)
    ImageView photoBadge;
    Staff staff;
    ArrayList<Tab> tabs;
    ViewPager mViewPager;
    FirebaseStorage storage;
    StorageReference storageRef, photoRef;
    String mCurrentPhotoPath;

    public StaffAccountFragment() {
        // Required empty public constructor
    }

    public static StaffAccountFragment newInstance(Staff staff, ArrayList<Tab> tabs) {
        StaffAccountFragment fragment = new StaffAccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(TABS, tabs);
        args.putSerializable(STAFF, staff);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabs = (ArrayList<Tab>) getArguments().getSerializable(TABS);
            staff = (Staff) getArguments().getSerializable(STAFF);
        }
        mViewPager = ((AdminAddStaff) getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_staff_account, container, false);
        ButterKnife.bind(this, rootView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl(FILE_STORAGE_PATH);
        if (staff.getUsername() != null && !staff.getPassword().equals("")) {
            setView();
        }
        return rootView;
    }

    private void setView() {
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
        mUsernameView.setText(staff.getUsername());
        mPasswordView.setText(staff.getPassword());
        mFloorView.setText(staff.getFloor());
        mSpecialityView.setText(staff.getSpecialty()[0]);
    }

    @OnClick(R.id.click_photo)
    public void clickPhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            takePhoto();
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.spy.healthmatic.fileprovider", photoFile);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoBadge.setVisibility(View.VISIBLE);
//            setPic();
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

    @OnClick(R.id.save_staff)
    public void saveStaff() {
        boolean isvalid = true;

        String username = mUsernameView.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("Required.");
            isvalid = false;
        } else {
            staff.setUsername(username);
            mUsernameView.setError(null);
        }

        String passoword = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(passoword)) {
            mPasswordView.setError("Required.");
            isvalid = false;
        } else {
            staff.setPassword(passoword);
            mPasswordView.setError(null);
        }

        String floor = mFloorView.getText().toString();
        if (TextUtils.isEmpty(floor)) {
            mFloorView.setError("Required.");
            isvalid = false;
        } else {
            staff.setFloor(floor);
            mFloorView.setError(null);
        }

        String speciality = mSpecialityView.getText().toString();
        if (TextUtils.isEmpty(speciality)) {
            mPasswordView.setError("Required.");
            isvalid = false;
        } else {
            staff.setSpecialty(new String[]{speciality});
            mPasswordView.setError(null);
        }
        if (!isvalid) {
            return;
        }
        String action = ((AdminAddStaff) getActivity()).getAction();
        staff.setImageName(GlobalFunctions.getCurrentDateInMilliseconds());
        if ("create".equals(action)) {
            saveStaffInServer();
        } else if ("update".equals(action)) {
            updateSatffInserver();
        }
    }

    private void saveStaffInServer() {
        showProgressDialog();
        Call<Staff> call = STAFF_API.createStaff(staff);
        String staffString = new Gson().toJson(staff);
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                uploadPhoto();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void updateSatffInserver() {
        showProgressDialog();
        Call<Staff> call = STAFF_API.updateStaff(staff.get_id(), staff);
        String staffString = new Gson().toJson(staff);
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                uploadPhoto();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD STAFF RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void uploadPhoto() {
        if (mCurrentPhotoPath == null || "".equals(mCurrentPhotoPath)) {
            showToast("Staff created/updated");
            successIntent();
            return;
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File(mCurrentPhotoPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        photoRef = storageRef.child("healthmatic/" + staff.getImageName() + ".jpg");
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
                showToast("Image Upload and Staff creation/update Succesfull");

            }
        });
    }

    private void successIntent() {
        Intent intent = new Intent(getActivity(), AdminMainActivity.class);
        intent.putExtra("CurrentFragment", 1);
        startActivity(intent);
    }

    private void showToast(String msg) {
        hideProgressDialog();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Creating Staff…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
