package com.spy.healthmatic.Welcome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Team Name: Team SPY
 * Created by shelalainechan
 */

public class Login extends AppCompatActivity implements GlobalConst {

    private static final String TAG = "ActivityLogin";
    private static Staff staff;

    @Bind(R.id.buttonLogin) Button loginButton;
    @Bind(R.id.editTextEmail) EditText editTxtEmail;
    @Bind(R.id.editTextPassword) EditText editTxtPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
                    Toast.makeText(Login.this, getResources().getString(R.string.strRetroFitFailureMsg), Toast.LENGTH_LONG).show();
                    return;
                }
                Staff staff = response.body();
                if(staff == null){
                    Toast.makeText(Login.this, getResources().getString(R.string.strInvalidLogin), Toast.LENGTH_LONG).show();
                    return;
                }
                //UPDATING USER LOGIN STATUS
                staff.setLoggedIn(true);
                new LoginUserInDB(staff).execute();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("RETROFIT", "RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(Login.this, getResources().getString(R.string.strRetroFitFailureMsg), Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean validate() {
        boolean valid = true;

        String email = editTxtEmail.getText().toString();
        String password = editTxtPassword.getText().toString();

        if (email.isEmpty()) {//|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            editTxtEmail.setError(getResources().getString(R.string.strEnterValidEmail));
            valid = false;
        } else {
            editTxtEmail.setError(null);
        }
        if (password.isEmpty()) {
            editTxtPassword.setError(getResources().getString(R.string.strEnterPassword));
            editTxtEmail.setError(null);
        }

        return valid;
    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "LoginModel failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
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
            String msg = staff.getFirstName() + " " + getResources().getString(R.string.strUserLoginSuccessful);
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
                } else if ("tech".equals(staff.getRole())) {
                    Intent intent = new Intent(Login.this, LabAgentMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Login.this.finish();
            } else {
                msg = staff.getFirstName() + " " + getResources().getString(R.string.strUserLoginUnsuccessful);
            }
            Toast.makeText(Login.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show progress dialog while fetching data from the web server
     */
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.strAuthenticating));
        progressDialog.show();
    }

    /**
     * Hide progress dialog when no longer needed
     */
    private void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
