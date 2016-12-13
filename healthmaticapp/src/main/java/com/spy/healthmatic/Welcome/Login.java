package com.spy.healthmatic.Welcome;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;
import com.samsung.android.sdk.pass.SpassInvalidStateException;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Doctor.MainDrActivity;
import com.spy.healthmatic.LabAgent.LabAgentMainActivity;
import com.spy.healthmatic.Model.Patient;
import com.google.gson.Gson;
import com.spy.healthmatic.API.StaffAPI;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.DB.StaffDB;
import com.spy.healthmatic.Doctor.MainDrActivity;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.LabAgent.LabAgentMainActivity;
import com.spy.healthmatic.Model.LoginModel;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Nurse.NurseMainActivity;
import com.spy.healthmatic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Team Name: Team SPY
 * Created by shelalainechan
 */

public class Login extends AppCompatActivity implements GlobalConst, Handler.Callback {

    private static final String TAG = "ActivityLogin";
    private static Staff staff;

    @Bind(R.id.buttonLogin)
    Button loginButton;
    @Bind(R.id.editTextEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTextPassword)
    EditText editTxtPassword;
    @Bind(R.id.signFingerLogin)
    AppCompatButton mSamsunButton;

    ProgressDialog progressDialog;

    Context mContext;

    private SpassFingerprint mSpassFingerprint;
    private Spass mSpass;

    private ArrayList<Integer> designatedFingers = null;
    private ArrayList<Integer> designatedFingersDialog = null;

    private boolean isFeatureEnabled_index = false;
    private boolean isFeatureEnabled_uniqueId = false;
    private boolean isFeatureEnabled_custom = false;
    private boolean isFeatureEnabled_backupPw = false;

    boolean onReadyEnroll = false, onReadyIdentify = false, isFeatureEnabled_fingerprint, needRetryIdentify;

    private Handler mHandler;
    private static final int MSG_AUTH = 1000;
    private static final int MSG_AUTH_UI_WITH_PW = 1001;
    private static final int MSG_AUTH_UI_WITHOUT_PW = 1002;
    private static final int MSG_CANCEL = 1003;
    private static final int MSG_REGISTER = 1004;
    private static final int MSG_GET_NAME = 1005;
    private static final int MSG_GET_UNIQUEID = 1006;
    private static final int MSG_AUTH_INDEX = 1007;
    private static final int MSG_AUTH_UI_INDEX = 1008;
    private static final int MSG_AUTH_UI_CUSTOM_LOGO = 1009;
    private static final int MSG_AUTH_UI_CUSTOM_TRANSPARENCY = 1010;
    private static final int MSG_AUTH_UI_CUSTOM_DISMISS = 1011;
    private static final int MSG_AUTH_UI_CUSTOM_BUTTON_STANDBY = 1012;


    private BroadcastReceiver mPassReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (SpassFingerprint.ACTION_FINGERPRINT_RESET.equals(action)) {
                Toast.makeText(mContext, "all fingerprints are removed", Toast.LENGTH_SHORT).show();
            } else if (SpassFingerprint.ACTION_FINGERPRINT_REMOVED.equals(action)) {
                int fingerIndex = intent.getIntExtra("fingerIndex", 0);
                Toast.makeText(mContext, fingerIndex + " fingerprints is removed", Toast.LENGTH_SHORT).show();
            } else if (SpassFingerprint.ACTION_FINGERPRINT_ADDED.equals(action)) {
                int fingerIndex = intent.getIntExtra("fingerIndex", 0);
                Toast.makeText(mContext, fingerIndex + " fingerprints is added", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SpassFingerprint.ACTION_FINGERPRINT_RESET);
        filter.addAction(SpassFingerprint.ACTION_FINGERPRINT_REMOVED);
        filter.addAction(SpassFingerprint.ACTION_FINGERPRINT_ADDED);
        mContext.registerReceiver(mPassReceiver, filter);
    };

    private void unregisterBroadcastReceiver() {
        try {
            if (mContext != null) {
                mContext.unregisterReceiver(mPassReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getEventStatusName(int eventStatus) {
        switch (eventStatus) {
            case SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS:
                return "STATUS_AUTHENTIFICATION_SUCCESS";
            case SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS:
                return "STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS";
            case SpassFingerprint.STATUS_TIMEOUT_FAILED:
                return "STATUS_TIMEOUT";
            case SpassFingerprint.STATUS_SENSOR_FAILED:
                return "STATUS_SENSOR_ERROR";
            case SpassFingerprint.STATUS_USER_CANCELLED:
                return "STATUS_USER_CANCELLED";
            case SpassFingerprint.STATUS_QUALITY_FAILED:
                return "STATUS_QUALITY_FAILED";
            case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE:
                return "STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE";
            case SpassFingerprint.STATUS_BUTTON_PRESSED:
                return "STATUS_BUTTON_PRESSED";
            case SpassFingerprint.STATUS_OPERATION_DENIED:
                return "STATUS_OPERATION_DENIED";
            case SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED:
            default:
                return "STATUS_AUTHENTIFICATION_FAILED";
        }
    }

    private SpassFingerprint.IdentifyListener mIdentifyListenerDialog = new SpassFingerprint.IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {
            printlog("identify finished : reason =" + getEventStatusName(eventStatus));
            int FingerprintIndex = 0;
            boolean isFailedIdentify = false;
            onReadyIdentify = false;
            try {
                FingerprintIndex = mSpassFingerprint.getIdentifiedFingerprintIndex();
            } catch (IllegalStateException ise) {
                printlog(ise.getMessage());
            }
            if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                hideProgressDialog();
                printlog("onFinished() : Identify authentification Success with FingerprintIndex : " + FingerprintIndex);
                printlog("onFinished() : Identify authentification mSpassFingerprint.getRegisteredFingerprintUniqueID().toString() : " + mSpassFingerprint.getRegisteredFingerprintUniqueID().get(FingerprintIndex));
                authenticateFingerWithServer(mSpassFingerprint.getRegisteredFingerprintUniqueID().get(FingerprintIndex).toString());
            } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
                printlog("onFinished() : Password authentification Success");
            } else if (eventStatus == SpassFingerprint.STATUS_USER_CANCELLED
                    || eventStatus == SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE) {
                printlog("onFinished() : User cancel this identify.");
            } else if (eventStatus == SpassFingerprint.STATUS_TIMEOUT_FAILED) {
                printlog("onFinished() : The time for identify is finished.");
            } else if (!mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_AVAILABLE_PASSWORD)) {
                if (eventStatus == SpassFingerprint.STATUS_BUTTON_PRESSED) {
                    printlog("onFinished() : User pressed the own button");
                    Toast.makeText(mContext, "Please connect own Backup Menu", Toast.LENGTH_SHORT).show();
                }
            } else {
                printlog("onFinished() : Authentification Fail for identify");
                isFailedIdentify = true;
            }
            if (!isFailedIdentify) {
                resetIdentifyIndexDialog();
            }
        }

        @Override
        public void onReady() {
            printlog("identify state is ready");
        }

        @Override
        public void onStarted() {
            printlog("User touched fingerprint sensor");
        }

        @Override
        public void onCompleted() {
            printlog("the identify is completed");
        }
    };

    private SpassFingerprint.RegisterListener mRegisterListener = new SpassFingerprint.RegisterListener() {
        @Override
        public void onFinished() {
            onReadyEnroll = false;
            log.d("SAMSUNG", "RegisterListener.onFinished()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        ButterKnife.bind(this);

        mHandler = new Handler(this);
        mSpass = new Spass();

        try {
            mSpass.initialize(this);
        } catch (SsdkUnsupportedException e) {
            mSpass = null;
            printlog("Exception: " + e);
        } catch (UnsupportedOperationException e) {
            mSpass = null;
            printlog("Fingerprint Service is not supported in the device");
        }
        isFeatureEnabled_fingerprint = false;
        if(mSpass!=null) {
            mSamsunButton.setVisibility(View.VISIBLE);
            isFeatureEnabled_fingerprint = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

            if (isFeatureEnabled_fingerprint) {
                mSpassFingerprint = new SpassFingerprint(this);
                printlog("Fingerprint Service is supported in the device.");
                printlog("SDK version : " + mSpass.getVersionName());
            } else {
                printlog("Fingerprint Service is not supported in the device.");
                return;
            }

            registerBroadcastReceiver();
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {

            case MSG_AUTH_UI_WITH_PW:
                startIdentifyDialog(true);
                break;
        }
        return true;
    }

    private void startIdentifyDialog(boolean backup) {
        if (onReadyIdentify == false) {
            onReadyIdentify = true;
            try {
                if (mSpassFingerprint != null) {
                    setIdentifyIndexDialog();
                    mSpassFingerprint.startIdentifyWithDialog(Login.this, mIdentifyListenerDialog, backup);
                }
                if (designatedFingersDialog != null) {
                    printlog("Please identify finger to verify you with " + designatedFingersDialog.toString() + " finger");
                } else {
                    printlog("Please identify finger to verify you");
                }
            } catch (IllegalStateException e) {
                onReadyIdentify = false;
                resetIdentifyIndexDialog();
                printlog("Exception: " + e);
            }
        } else {
            printlog("The previous request is remained. Please finished or cancel first");
        }
    }

    private void cancelIdentify() {
        if (onReadyIdentify == true) {
            try {
                if (mSpassFingerprint != null) {
                    mSpassFingerprint.cancelIdentify();
                }
                printlog("cancelIdentify is called");
            } catch (IllegalStateException ise) {
                printlog(ise.getMessage());
            }
            onReadyIdentify = false;
            needRetryIdentify = false;
        } else {
            printlog("Please request Identify first");
        }
    }

    private void registerFingerprint() {
        if (onReadyIdentify == false) {
            if (onReadyEnroll == false) {
                onReadyEnroll = true;
                if (mSpassFingerprint != null) {
                    mSpassFingerprint.registerFinger(Login.this, mRegisterListener);
                }
                printlog("Jump to the Enroll screen");
            } else {
                printlog("Please wait and try to register again");
            }
        } else {
            printlog("Please cancel Identify first");
        }
    }

    private void getFingerprintUniqueID() {
        SparseArray<String> mList = null;
        try {
            printlog("=Fingerprint Unique ID=");
            if (mSpassFingerprint != null) {
                mList = mSpassFingerprint.getRegisteredFingerprintUniqueID();
            }
            if (mList == null) {
                printlog("Registered fingerprint is not existed.");
            } else {
                for (int i = 0; i < mList.size(); i++) {
                    int index = mList.keyAt(i);
                    String ID = mList.get(index);
                    printlog("index " + index + ", Unique ID is " + ID);
                }
            }
        } catch (IllegalStateException ise) {
            printlog(ise.getMessage());
        }
    }

    private String getFirstFingerprintUniqueID() {
        SparseArray<String> mList = null;
        try {
            printlog("=Fingerprint Unique ID=");
            if (mSpassFingerprint != null) {
                mList = mSpassFingerprint.getRegisteredFingerprintUniqueID();
            }
            if (mList == null) {
                printlog("Registered fingerprint is not existed.");
            } else {
                for (int i = 0; i < mList.size(); i++) {
                    int index = mList.keyAt(i);
                    String ID = mList.get(index);
                    printlog("index " + index + ", Unique ID is " + ID);
                }
            }
        } catch (IllegalStateException ise) {
            printlog(ise.getMessage());
        }
        if(mList!=null){
            return mList.get(1);
        }
        return null;
    }

    private void setIdentifyIndexDialog() {
        if (isFeatureEnabled_index) {
            if (mSpassFingerprint != null && designatedFingersDialog != null) {
                mSpassFingerprint.setIntendedFingerprintIndex(designatedFingersDialog);
            }
        }
    }

    private void resetIdentifyIndexDialog() {
        designatedFingersDialog = null;
    }

    private void setDialogTitleAndDismiss() {
        if (isFeatureEnabled_custom) {
            try {
                if (mSpassFingerprint != null) {
                    mSpassFingerprint.setDialogTitle("HealthMatic App Authentication", 0x000000);
                    mSpassFingerprint.setCanceledOnTouchOutside(true);
                }
            } catch (IllegalStateException ise) {
                printlog(ise.getMessage());
            }
        }
    }

    @OnClick(R.id.buttonLogin)
    public void loginClick(){
        if (!validate()) {
            return;
        }
        showProgressDialog();
        String loginJson = new Gson().toJson(new LoginModel(editTxtEmail.getText().toString(), editTxtPassword.getText().toString(), ""));
        Call<Staff> call = STAFF_API.login(new LoginModel(editTxtEmail.getText().toString(), editTxtPassword.getText().toString(), ""));
        call.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "RETROFIT FAILURE - RESPONSE FAIL >>>>> " + response.errorBody());
                    Toast.makeText(Login.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                staff = response.body();
                if(staff == null){
                    Toast.makeText(Login.this, "Invalid Username/Password. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                //UPDATING USER LOGIN STATUS
                staff.setLoggedIn(true);
                if(isFeatureEnabled_fingerprint && (staff.getFingerKey()==null || "".equals(staff.getFingerKey()))){
                    askRegisterFingerPrint();
                }else{
                    new LoginUserInDB(staff).execute();
                }
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(Login.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick(R.id.signFingerLogin)
    public void samsungLoginClick(){
        setDialogTitleAndDismiss();
        startIdentifyDialog(false);
    }

    private void askRegisterFingerPrint(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Register your finger pass as authentication");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(getFirstFingerprintUniqueID()==null){
                    registerFingerprint();
                    return;
                }
                dialogInterface.dismiss();
                registerFingerprintOnServer(getFirstFingerprintUniqueID());
            }
        });
        builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new LoginUserInDB(staff).execute();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void registerFingerprintOnServer(String fingerUniqueId){
        staff.setFingerKey(fingerUniqueId);
        showProgressDialog();
        Call<Staff> call = STAFF_API.updateStaff(staff.get_id(), staff);
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                hideProgressDialog();
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                new LoginUserInDB(staff).execute();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                hideProgressDialog();
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void authenticateFingerWithServer(String fingerUniqueId){
        showProgressDialog();
        Call<Staff> call = STAFF_API.loginSamsung(new LoginModel("", "", fingerUniqueId));
        call.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                hideProgressDialog();
                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "RETROFIT FAILURE - RESPONSE FAIL >>>>> " + response.errorBody());
                    Toast.makeText(Login.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                staff = response.body();
                if(staff == null){
                    Toast.makeText(Login.this, "Invalid Username/Password. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                //UPDATING USER LOGIN STATUS
                staff.setLoggedIn(true);
                new LoginUserInDB(staff).execute();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                hideProgressDialog();
                Toast.makeText(Login.this, "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showToast(String msg){
        hideProgressDialog();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private boolean validate() {
        boolean valid = true;

        String email = editTxtEmail.getText().toString();
        String password = editTxtPassword.getText().toString();

        if (email.isEmpty()) {//|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            editTxtEmail.setError("Enter a valid email");
            valid = false;
        } else {
            editTxtEmail.setError(null);
        }
        if (password.isEmpty()) {
            editTxtPassword.setError("Enter password");
            editTxtEmail.setError(null);
        }

        return valid;
    }

    private class LoginUserInDB extends AsyncTask<Void, Void, Boolean> {

        private Staff staff;

        LoginUserInDB(Staff staff) {
            this.staff = staff;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = openOrCreateDatabase(GlobalConst.DATABASE_NAME, MODE_PRIVATE, null);
            GlobalFunctions.upgrade_tables(Login.this, db);
            return new StaffDB(Login.this).addStaff(staff);
        }

        protected void onPostExecute(Boolean result) {
            hideProgressDialog();
            String msg = "User - " + staff.getFirstName() + " Login successful.";
            if (result) {
                if ("doctor".equals(staff.getRole())) {
                    Intent intent = new Intent(Login.this, MainDrActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("nurse".equals(staff.getRole())) {
                    Intent intent = new Intent(Login.this, NurseMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("admin".equals(staff.getRole())) {
                    Intent intent = new Intent(Login.this, AdminMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("lab".equals(staff.getRole())) {
                    Intent intent = new Intent(Login.this, LabAgentMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Login.this.finish();
            } else {
                msg = "User - " + staff.getFirstName() + " Login un-successful.";
            }
            Toast.makeText(Login.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }

    private void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void printlog(String txt){
        log.d("SAMSUNG", txt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }
}
