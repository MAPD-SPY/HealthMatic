package com.spy.healthmatic.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spy.healthmatic.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_doctor);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_doctor)
    public void addDoctor(){
        Intent intent = new Intent(this, AdminMainActivity.class);
        intent.putExtra("CurrentFragment", 1);
        startActivity(intent);
    }
}
