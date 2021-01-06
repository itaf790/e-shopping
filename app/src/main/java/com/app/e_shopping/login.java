package com.app.e_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.e_shopping.Admin.AdminCategoryActivity;
import com.app.e_shopping.Model.Users;
import com.app.e_shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    private EditText inputemail, inputpassword;
    private Button loginbutton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink , ForgetPasswordLink;
    private String parentDbName="Users";
    private ImageView closeTextBtn;

    private com.rey.material.widget.CheckBox chkBoxRememberMe;
    private MainActivity mainActivity=new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        loginbutton=(Button)findViewById(R.id.login);
        inputemail=(EditText) findViewById(R.id.email);
        inputpassword=(EditText) findViewById(R.id.pass);
        AdminLink= (TextView)findViewById(R.id.admin);
        NotAdminLink= (TextView)findViewById(R.id.not_admin);
        ForgetPasswordLink = findViewById(R.id.forget);
        loadingBar= new ProgressDialog(this);


        chkBoxRememberMe = findViewById(R.id.remember);
        Paper.init(this);;

        
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login.this,ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setText("Login Admin");
                NotAdminLink.setVisibility(View.VISIBLE);
                AdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setText("Login");
                NotAdminLink.setVisibility(View.INVISIBLE);
                AdminLink.setVisibility(View.VISIBLE);
                parentDbName="Users";
            }
        });



    }








    private void LoginUser() {
        String email= inputemail.getText().toString() ;
        String password= inputpassword.getText().toString() ;

         if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else {

             loadingBar.setTitle("Login Account");
             loadingBar.setMessage("please wait, while we are checking the credential");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();

             AllowAccessToAccount(email,password);
         }

         }

    private void AllowAccessToAccount(final String email, final String password) {
///// hay ll remember me
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserEmailKey, email);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(parentDbName).child(email).exists()){

                    Users usersData= snapshot.child(parentDbName).child(email).getValue(Users.class);
                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {

                           if (parentDbName.equals("Admins"))
                           {

                               Intent intent = new Intent(login.this, AdminCategoryActivity.class);
                               startActivity(intent);

                               Toast.makeText(login.this, "Welcome Admin, you are logged in Succeessfully", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();



                           }
                           else if (parentDbName.equals("Users"))
                           {
                               Toast.makeText(login.this, "Logged in Succeessfully", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(login.this, HomeActivity.class);
                               Prevalent.currentonlineusers= usersData;
                               startActivity(intent);

                           }
                        }

                        else {
                            loadingBar.dismiss();
                            Toast.makeText(login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
                else {

                    Toast.makeText(login.this, "Account with this "+ email+" email do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



}
}