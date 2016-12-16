package com.spy.healthmatic.Admin;

//Team Name: Team SPY

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spy.healthmatic.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddNurse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_nurse);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_nurse)
    public void addNurse(){
        Intent intent = new Intent(this, AdminMainActivity.class);
        intent.putExtra("CurrentFragment", 1);
        startActivity(intent);
    }
}
