package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView nomPrenom,email,phone,occupation;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private static final String USER = "USER";

    //User user;
    String emailTest,nomPrenomTest,occupationTest,phoneTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nomPrenom = (TextView) findViewById(R.id.nomPrenom);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        occupation = (TextView) findViewById(R.id.occupation);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("USER");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Query query = mDatabase.orderByChild("email").equalTo(user.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    emailTest = "" + ds.child("email").getValue();
                    nomPrenomTest = "" + ds.child("nomPrenom").getValue();
                    occupationTest = "" + ds.child("occupation").getValue();
                    phoneTest = "" + ds.child("phone").getValue();

                    email.setText(emailTest);
                    nomPrenom.setText(nomPrenomTest);
                    occupation.setText(occupationTest);
                    phone.setText(phoneTest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
