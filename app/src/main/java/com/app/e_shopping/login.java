package com.app.e_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.e_shopping.Model.Users;
import com.app.e_shopping.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    private EditText inputemail, inputpassword;
    private Button loginbutton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;


    private String parentDbName="Users";


    //private CheckBox checkBoxrememberme;
     private CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        loginbutton=(Button)findViewById(R.id.login);
        inputemail=(EditText) findViewById(R.id.email);
        inputpassword=(EditText) findViewById(R.id.pass);
        AdminLink= (TextView)findViewById(R.id.admin);
        NotAdminLink= (TextView)findViewById(R.id.not_admin);
        loadingBar= new ProgressDialog(this);

      //  remember= findViewById(R.id.remember);

     //  checkBoxrememberme = (CheckBox)findViewById(R.id.remember);
       // Paper.init(this);

//        SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
  //     String checkbox= preferences.getString("remember","");
    //   if (checkbox.equals("true")){
      //     Intent intent= new Intent(login.this,HomeActivity.class);}
      // else if (checkbox.equals("false")){
       //    Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();}
        
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        /////
      ///  hay ll admin panel
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




       ////////////////////////////////////
// remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
   // @Override
    //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      //  if (buttonView.isChecked()){
        //    SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
// SharedPreferences.Editor editor= preferences.edit();
// editor.putString("remember","true");
// editor.apply();
// Toast.makeText(login.this,"Checked",Toast.LENGTH_SHORT).show();}

       // else if (!buttonView.isChecked()){

         //   SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
           // SharedPreferences.Editor editor= preferences.edit();
            //editor.putString("remember","false");
            // editor.apply();
            //Toast.makeText(login.this,"UnChecked",Toast.LENGTH_SHORT).show();
        //}
    //}
// });

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
      //if (checkBoxrememberme.isChecked()) {

         //   Paper.book().write(Prevalent.UserEmailKey,email);
       //     Paper.book().write(Prevalent.UserPasswordKey,password);
     //  }


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

                               Intent intent = new Intent(login.this, AdminActivity.class);
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