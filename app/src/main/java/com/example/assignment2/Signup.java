package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText email, password, mobile, name;
    TextView login, signup;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup1);
        mobile = findViewById(R.id.mobile);
        name = findViewById(R.id.name);

        login = findViewById(R.id.login1);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        progressBar = findViewById(R.id.progressbar1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String names = name.getText().toString().trim();
                final String number = mobile.getText().toString().trim();
                final String emails = email.getText().toString().trim();
                final String passwords = password.getText().toString().trim();
                if (names.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    name.setError("Valid name is required");
                    name.requestFocus();
                }else  if (emails.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    email.setError("Valid email is required");
                    email.requestFocus();}
                else if (passwords.isEmpty() || passwords.length() < 6) {
                    progressBar.setVisibility(View.GONE);

                    password.setError("Enter minimum 6 number password");
                    password.requestFocus();
                } else if (number.isEmpty() || number.length() < 10) {
                    progressBar.setVisibility(View.GONE);
                    mobile.setError("Valid number is required");
                    mobile.requestFocus();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(emails, passwords)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Model_Data model_data=new Model_Data(names,emails,number);

                                        FirebaseDatabase.getInstance().getReference("User")
                                           .child(FirebaseAuth.getInstance().getUid())
                                           .setValue(model_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {

                                           Intent intent=new Intent(Signup.this,MapsActivity.class);
                                           startActivity(intent);
                                           finish();


                                           Toast.makeText(Signup.this, "Registration Successfully Complete", Toast.LENGTH_SHORT).show();
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressBar.setVisibility(View.GONE);
                                                if (e instanceof FirebaseAuthUserCollisionException){
                                                    Toast.makeText(Signup.this, "Your gmail are already Registered", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }

            }


        });

    }

   protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(Signup.this,MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity( intent);
        }


    }
}