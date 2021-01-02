package com.app.e_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.e_shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check= "";
    private TextView pageTitle , titleQuestions;
    private EditText Email, question1, question2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check= getIntent().getStringExtra("check");
        pageTitle= findViewById(R.id.page_title);
        titleQuestions= findViewById(R.id.page_title);
        Email= findViewById(R.id.find_email);
        question1= findViewById(R.id.ques_1);
        question2= findViewById(R.id.ques_2);
        verifyButton= findViewById(R.id.verify_btn);



    }


    @Override
    protected void onStart() {
        super.onStart();
        Email.setVisibility(View.GONE);

        if (check.equals("setting"))
        {

            titleQuestions.setText("Please set Answer for  the Follwing Security Questions");
            pageTitle.setText("Set Questions");
            verifyButton.setText("Set");
            displayPreviousAnswers();

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setAnswers();

                }
            });



        }
        else if(check.equals("login"))
        {

            Email.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    veriyfyUser();
                }
            });
        }

    }

    private void setAnswers()
    {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (question1.equals("") && question2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();
        }
        else  {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Prevalent.currentonlineusers.getEmail());

            HashMap<String, Object> userdataMap= new HashMap<>();
            userdataMap.put("answer1",answer1);
            userdataMap.put("answer2",answer2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "you have set answer security questions successfully ", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }


    private void displayPreviousAnswers(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Prevalent.currentonlineusers.getEmail());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    String ans1 = snapshot.child("answer1").getValue().toString();
                    String ans2 = snapshot.child("answer2").getValue().toString();
                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
private void veriyfyUser(){


        final String email = Email.getText().toString();
    final String answer1 = question1.getText().toString().toLowerCase();
    final String answer2 = question2.getText().toString().toLowerCase();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
            .child(Prevalent.currentonlineusers.getEmail());

    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){

                String mEmail = snapshot.child("email").getValue().toString();
                if (email.equals(mEmail) || email.equals(Prevalent.currentonlineusers.getEmail())){

                    if (snapshot.hasChild("Security Questions")){



                        String ans1 = snapshot.child("answer1").getValue().toString();
                        String ans2 = snapshot.child("answer2").getValue().toString();
                        if (!ans1.equals(answer1)){
                            Toast.makeText(ResetPasswordActivity.this, "your 1st answer is wrong", Toast.LENGTH_SHORT).show();

                        }
                        else  if (!ans2.equals(answer2)){
                            Toast.makeText(ResetPasswordActivity.this, "your 2nd answer is wrong", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                            builder.setTitle("New Password");

                            
                        }
                    }


                    
                }
                else {

                    Toast.makeText(ResetPasswordActivity.this, "This user Email not exist ", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}


}