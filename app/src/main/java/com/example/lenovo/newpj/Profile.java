package com.example.lenovo.newpj;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.HashMap;

public class Profile extends AppCompatActivity {

    private EditText nomPrenom,email,phone,occupation;
    private Button update;
    private FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    ProgressDialog pd;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);

        pd = new ProgressDialog(Profile.this);

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
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showEditProfileDialog();
//            }
//        });
//    }
//
//    private void showEditProfileDialog(){
//        String options [] = {"Edit nom et prenom","Edit email","Edit occupation","Edit phone"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
//
//        builder.setTitle("Choose Action");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(which == 0){
//                    //Edit nom et prenom
//                    pd.setMessage("Updating nom et prenom");
//                    updateProfile("nomPrenom");
//                }else if (which == 1){
//                    //Edit email
//                    pd.setMessage("Updating email");
//                    updateProfile("email");
//                }else if (which == 2){
//                    //Edit occupation
//                    pd.setMessage("Updating occuaption");
//                    updateProfile("occupation");
//                }else if (which == 3){
//                    //Edit phone
//                    pd.setMessage("Updating phone");
//                    updateProfile("phone");
//                }
//            }
//        });
//
//        builder.create().show();
//    }
//
//    private void updateProfile(final String key){
//        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
//        builder.setTitle("Update" + key);
//
//        LinearLayout linearLayout = new LinearLayout(Profile.this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(10,10,10,10);
//
//        final EditText editText = new EditText((Profile.this));
//        editText.setHint("Enter" + key);
//        linearLayout.addView(editText);
//
//        builder.setView(linearLayout);
//
//        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String value = editText.getText().toString().trim();
//
//                if (!TextUtils.isEmpty(value)){
//                    pd.show();
//                    HashMap<String,Object> result =new HashMap<>();
//                    result.put(key,value);
//
//                    mDatabase.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            pd.dismiss();
//                            Toast.makeText(Profile.this,"Updated...",Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
//                            Toast.makeText(Profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }else {
//                    Toast.makeText(Profile.this,"Enter"+key,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.create().show();
   }

   public void Update(){
        if (isNameChanged() || isMailChanged()){
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

}
