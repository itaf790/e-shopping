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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register extends AppCompatActivity {

    private Button createaccount;
    private EditText inputname, inputemail, inputpass;
    //// firebase
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createaccount=(Button)findViewById(R.id.create);
        inputname=(EditText) findViewById(R.id.name);
        inputemail=(EditText) findViewById(R.id.emailsign);
        inputpass=(EditText) findViewById(R.id.passsign);
        loadingBar= new ProgressDialog(this);

        ////// had firebase ll login and signin 
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateAccount();
            }
        });

    }

    private void CreateAccount() {
        String name= inputname.getText().toString() ;
        String email= inputemail.getText().toString() ;
        String password= inputpass.getText().toString() ;

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
       else{

           loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please wait, while we are checking the credential");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            Validaemail(name,email,password);
        }

    }

    private void Validaemail(final String name, final String email, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(email).exists())) {
                    HashMap<String, Object> userdataMap= new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(email).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(register.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(register.this,login.class);
                                startActivity(intent);
                            }

                            else {
                                loadingBar.dismiss();
                                Toast.makeText(register.this, "Network Error: Please try again after some time ", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });




                } else {
                    Toast.makeText(register.this, "This" + email + "already exists", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                    Toast.makeText(register.this, "Please etry again using another email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}