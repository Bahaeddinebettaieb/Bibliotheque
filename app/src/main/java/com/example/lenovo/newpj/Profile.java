package com.example.lenovo.newpj;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


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
        fab = (FloatingActionButton)findViewById(R.id.fab);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("USER");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditPassword();
            }
        });

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

    private void showEditPassword() {
        String options[] = {"Edit password"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Choose Action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showPasswordUpdate();
            }
        });
        builder.create().show();
    }

//    private void showPasswordUpdate() {
//        View view = LayoutInflater.from(Profile.this).inflate(R.layout.update_password,null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Update password");
//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(10,10,10,10);
//        final EditText editText = new EditText(this);
//        editText.setHint("Update password");
//        linearLayout.addView(editText);
//        builder.setView(linearLayout);
//        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String value = editText.getText().toString().trim();
//                if (!TextUtils.isEmpty(value)){
//                    HashMap<String,Object> hashMap = new HashMap<>();
//                    hashMap.put("password",value);
//                    mDatabase.child(user.getUid()).updateChildren(hashMap)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }else{
//                    Toast.makeText(Profile.this, "Please enter Password", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//    }

    private void showPasswordUpdate() {

        View view = LayoutInflater.from(Profile.this).inflate(R.layout.update_password,null);

        final EditText passwordEt = view.findViewById(R.id.passwordEt);
        final EditText newPasswordEt = view.findViewById(R.id.newPasswordEt);
        Button updatePasswordBtn = view.findViewById(R.id.updatePassword);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = passwordEt.getText().toString().trim();
                String newPassword = newPasswordEt.getText().toString().trim();
                if (TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(Profile.this, "Enter your current password...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.length()<6){
                    Toast.makeText(Profile.this, "Password length must atleast 6 characters...", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                updatePassword(oldPassword,newPassword);
            }
        });

    }

    private void updatePassword(String oldPassword, final String newPassword) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),oldPassword);

        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Profile.this, "Password updated...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
