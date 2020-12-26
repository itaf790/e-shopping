package com.app.e_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView airpods,jbl,logitech,magickeyboared,watch,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        airpods= (ImageView)findViewById(R.id.airpods);
        jbl= (ImageView)findViewById(R.id.jbl);
        logitech= (ImageView)findViewById(R.id.logitech);
        magickeyboared= (ImageView)findViewById(R.id.magickey);
        watch= (ImageView)findViewById(R.id.watch);
        temp= (ImageView)findViewById(R.id.temp);


    }
}