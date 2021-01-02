package com.app.e_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check= getIntent().getStringExtra("check");

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (check.equals("setting")){}
        else if(check.equals("login")){}

    }
}