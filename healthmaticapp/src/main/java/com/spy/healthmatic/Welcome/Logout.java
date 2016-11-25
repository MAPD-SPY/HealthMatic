package com.spy.healthmatic.Welcome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.DB.StaffDB;
import com.spy.healthmatic.Doctor.MainDrActivity;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.LabAgent.LabAgentMainActivity;
import com.spy.healthmatic.Model.Staff;
import com.spy.healthmatic.Nurse.NurseMainActivity;
import com.spy.healthmatic.R;

public class Logout extends AppCompatActivity {

    Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        new SignOutUserInDB().execute();
    }

    private class SignOutUserInDB extends AsyncTask<Void, Void ,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            staff = GlobalFunctions.getStaff(Logout.this);
            staff.setLoggedIn(false);
            return new StaffDB(Logout.this).updateStaff(staff);
        }

        protected void onPostExecute(Boolean result){
            String msg = "User - "+staff.getFirstName() + " Logout successful.";
            if(result){
                Intent intent = new Intent(Logout.this, SplashScreen.class);
                intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                startActivity(intent);
            }else{
                msg = "User - "+staff.getFirstName() + " Logout un-successful.";
                //TODO update server also with unsuccessfull logout i.e reverse signOutUserFormServer() function
                if ("doctor".equals(staff.getRole())) {
                    Intent intent = new Intent(Logout.this, MainDrActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("nurse".equals(staff.getRole())) {
                    Intent intent = new Intent(Logout.this, NurseMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("admin".equals(staff.getRole())) {
                    Intent intent = new Intent(Logout.this, AdminMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("lab".equals(staff.getRole())) {
                    Intent intent = new Intent(Logout.this, LabAgentMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
            Toast.makeText(Logout.this, msg, Toast.LENGTH_LONG).show();
        }
    }

}
