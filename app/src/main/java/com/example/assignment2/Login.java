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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email,password;
    TextView login,signup;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email1);
        password=findViewById(R.id.password1);
        login=findViewById(R.id.login2);
        signup=findViewById(R.id.signup2);
        progressBar=findViewById(R.id.progressbar2);
        mAuth= FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent=new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emails = email.getText().toString().trim();
                final String passwords = password.getText().toString().trim();

                if (emails.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    email.setError("Valid email is required");
                    email.requestFocus();
                }
                else if (passwords.isEmpty() || passwords.length() < 6) {
                    progressBar.setVisibility(View.GONE);

                    password.setError("Enter minimum 6 number password");
                    password.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(emails, passwords)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(Login.this,MapsActivity.class));
                                        finish();
                                        Toast.makeText(Login.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                                    } else {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                        password.requestFocus();
                                        Toast.makeText(Login.this, "Please check password ", Toast.LENGTH_SHORT).show();

                                    }else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                        email.requestFocus();
                                        Toast.makeText(Login.this, "Please check Gmail ", Toast.LENGTH_SHORT).show();
                                    }
                                    }

                                    // ...
                                }
                            });


                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(Login.this,MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity( intent);
        }


    }
}
