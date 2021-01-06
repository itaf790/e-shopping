package com.app.e_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    private static final int VIBRATE_PERMISSION_REQUEST = 10;

    private CheckBox Checkboxrecived , Checkboxcard;
    private ImageView closeTextBtn;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = " + totalAmount, Toast.LENGTH_SHORT).show();

        Checkboxrecived = findViewById(R.id.recived);
        Checkboxcard = findViewById(R.id.credit_card);


    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.credit_card:
                startActivity(new Intent(this, CreditcardActivity.class));
                break;
            case R.id.recived:
                startActivity(new Intent(this, RecivedActivity.class));
            default:
                break;
        }
    }


    private void enableVibratePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.VIBRATE},
                VIBRATE_PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}


