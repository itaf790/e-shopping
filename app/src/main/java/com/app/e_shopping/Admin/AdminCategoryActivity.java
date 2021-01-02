package com.app.e_shopping.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.e_shopping.HomeActivity;
import com.app.e_shopping.MainActivity;
import com.app.e_shopping.R;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView airpods,jbl,logitech,magickeyboared,watch,temp;
    private Button logOutBtn, checkOrdersBtn, mantainProductsBtn ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        mantainProductsBtn=(Button) findViewById(R.id.maintain_btn);
        logOutBtn = (Button) findViewById(R.id.admin_logout_btn);
        checkOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        mantainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });


        airpods = (ImageView) findViewById(R.id.airpods);
        jbl = (ImageView) findViewById(R.id.jbl);
        logitech = (ImageView) findViewById(R.id.logitech);
        watch = (ImageView) findViewById(R.id.watch);

        temp = (ImageView) findViewById(R.id.temp);
        magickeyboared= (ImageView) findViewById(R.id.magickey);


        airpods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Airpods");
                startActivity(intent);

            }
        });


        jbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","JBL");
                startActivity(intent);

            }
        });

        logitech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Lgitech");
                startActivity(intent);
            }
        });

        magickeyboared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Madickeyboared");
                startActivity(intent);
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Watch");
                startActivity(intent);
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Tempreture");
                startActivity(intent);
            }
        });





    }
}
