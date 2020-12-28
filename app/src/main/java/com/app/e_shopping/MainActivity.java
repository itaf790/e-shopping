package com.app.e_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.e_shopping.Model.Users;
import com.app.e_shopping.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    private Button btnjoin, btnlogin;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnjoin = (Button)findViewById(R.id.btnjoin);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        loadingBar= new ProgressDialog(this);


        Paper.init(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });
        String UserPasswordKey= Paper.book().read(Prevalent.UserPasswordKey);

        String UserEmailKey= Paper.book().read(Prevalent.UserEmailKey);

        if (UserEmailKey!="" && UserPasswordKey!= ""){
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserEmailKey,UserPasswordKey);

                loadingBar.setTitle("Already Loggen in");
                loadingBar.setMessage("please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }

        }



    }

    private void AllowAccess( final String email,  final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(email).exists()){
                    Users usersData= snapshot.child("Users").child(email).getValue(Users.class);
                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Please wait, you are already looged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentonlineusers=usersData;
                            startActivity(intent);
                        }

                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
                else {

                    Toast.makeText(MainActivity.this, "Account with this"+ email+"email do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
