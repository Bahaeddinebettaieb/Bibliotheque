package com.example.lenovo.newpj;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    private EditText nomPrenom,email,phone,occupation;
    private Button update;
    private FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    BottomNavigationView navigation;



    String emailTest,nomPrenomTest,occupationTest,phoneTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nomPrenom = (EditText) findViewById(R.id.nomPrenom);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        occupation = (EditText) findViewById(R.id.occupation);
        update = (Button) findViewById(R.id.update);
        navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(selectedListener);

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });

   }

    public void Update(){
        if (isNameChanged() || isMailChanged() || isOccupationChanged() || isPhoneChanged()){
           Toast.makeText(Profile.this, "Data has been updated...",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Profile.this, "Data is same and cannot be updated",Toast.LENGTH_SHORT).show();
        }
   }

    private boolean isMailChanged() {
        if (!emailTest.equals(email.getText().toString())){
            mDatabase.child(user.getUid()).child("email").setValue(email.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!nomPrenomTest.equals(nomPrenom.getText().toString())){
           mDatabase.child(user.getUid()).child("nomPrenom").setValue(nomPrenom.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isOccupationChanged(){
        if (occupationTest.equals(occupation.getText().toString())) {
            mDatabase.child(user.getUid()).child("occupation").setValue(occupation.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isPhoneChanged(){
        if (phoneTest.equals(phone.getText().toString())){
            mDatabase.child(user.getUid()).child("phone").setValue(phone.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            Intent intentDash = new Intent(Profile.this,Dashboard.class);
                            startActivity(intentDash);
                            return true;
                        case R.id.nav_profile:
                            Intent intentProfile = new Intent(Profile.this,Profile.class);
                            startActivity(intentProfile);
                            return true;

                        case R.id.nav_users:
                            Intent intentGet = new Intent(Profile.this,GetAllUsers.class);
                            startActivity(intentGet);
                    }
                    return false;
                }
            };

}
