package com.spy.healthmatic.Welcome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Doctor.MainDrActivity;
import com.spy.healthmatic.LabAgent.LabAgentMainActivity;
import com.spy.healthmatic.Model.Patient;
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

/**
 * Team Name: Team SPY
 * Created by shelalainechan
 */

public class Login extends AppCompatActivity {

    private static final String TAG = "ActivityLogin";
    private static Staff staff;

    @Bind(R.id.buttonLogin) Button loginButton;
    @Bind(R.id.editTextEmail) EditText editTxtEmail;
    @Bind(R.id.editTextPassword) EditText editTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        // Get the login details
        String username = editTxtEmail.getText().toString();
        String password = editTxtPassword.getText().toString();

        // Get list of patients for staffs if applicable
        if (username.equals("d")) {
            // Create an Asynchronous HTTP instance
            String url = "http://shelalainechan.com/staffs/5834b3449ed610499fbedcee";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        staff = new Staff(response);
                        try {
                            getPatients();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                        if("a".equals(editTxtEmail.getText().toString())){
                            startActivity(new Intent(Login.this, AdminMainActivity.class));
                        }else if("d".equals(editTxtEmail.getText().toString())){
                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("PATIENTS_OBJ", patients);
                            bundle.putSerializable("STAFF", staff);

                            Intent intent = new Intent(Login.this, MainDrActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if("l".equals(editTxtEmail.getText().toString())){
                            startActivity(new Intent(Login.this, LabAgentMainActivity.class));
                        }
                        if("n".equals(editTxtEmail.getText().toString())){
                            startActivity(new Intent(Login.this, NurseMainActivity.class));
                        }
                    }
                }, 3000);
    }

    private boolean validate() {
        boolean valid = true;

        String email = editTxtEmail.getText().toString();
        String password = editTxtPassword.getText().toString();

        if (email.isEmpty() ) {//|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            editTxtEmail.setError("Enter a valid email");
            valid = false;
        }
        else {
            editTxtEmail.setError(null);
        }
        if (password.isEmpty()) {
            editTxtPassword.setError("Enter password");
            editTxtEmail.setError(null);
        }

        return valid;
    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private void getPatients() throws UnsupportedEncodingException {

        String url = "http://shelalainechan.com/staffs/" + staff.getId() + "/patients";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                ArrayList<Patient> patients = new ArrayList<>();
                patients.addAll(Patient.fromJSONArray(jsonArray));
                staff.setPatients(patients);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
