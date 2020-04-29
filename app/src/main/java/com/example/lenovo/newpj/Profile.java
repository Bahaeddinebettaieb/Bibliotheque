package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView nomPrenom,email,phone,occupation;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER = "user";
    String emailTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        emailTest = intent.getStringExtra("emailTest");

        nomPrenom = (TextView)findViewById(R.id.nomPrenom);
        email = (TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phone);
        occupation = (TextView)findViewById(R.id.occupation);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("emailTest").getValue().equals(emailTest)){
                        nomPrenom.setText(ds.child("nomPrenom").getValue(String.class));
                        email.setText(ds.child("email").getValue(String.class));
                        phone.setText(ds.child("phone").getValue(String.class));
                        occupation.setText(ds.child("occupation").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
