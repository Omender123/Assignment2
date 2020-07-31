package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
  ImageView imageView;
  TextView name ,gmail,number;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.profile);
         imageView=findViewById(R.id.cancel);
         name=findViewById(R.id.txt_name);
        gmail=findViewById(R.id.txt_email);
        number=findViewById(R.id.txt_no);
        String name2=getIntent().getStringExtra("names");
         FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,MapsActivity.class));

            }


        });

    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Model_Data model_data=dataSnapshot.getValue(Model_Data.class);
            String name1=model_data.getName();
            String email1=model_data.getGmail();
            String no=model_data.getMobile();

            name.setText(name1);
            gmail.setText(email1);
            number.setText(no);



            /* for (DataSnapshot snapshot: dataSnapshot.getChildren()){
           Model_Data model_data=snapshot.getValue(Model_Data.class);
           String name1=model_data.getName();
           String email1=model_data.getGmail();
           String no=model_data.getMobile();

           name.setText(name1);
           gmail.setText(email1);
           number.setText(no);

       }*/
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
  }
}
