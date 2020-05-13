package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    User c;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser!=null){
                    startActivity(new Intent(Login.this,Dashboard.class));
                    Toast.makeText(Login.this,"You are logged in ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Login.this,"Please login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
      String emailTest = email.getText().toString();
      String passwordTest = password.getText().toString();
      if (TextUtils.isEmpty(emailTest)){
          Toast.makeText(Login.this,"Fields are Empty",Toast.LENGTH_LONG).show();
      }
      if (TextUtils.isEmpty(passwordTest)) {
          Toast.makeText(Login.this,"Fields are Empty",Toast.LENGTH_LONG).show();
      }
      if (passwordTest.length() < 5){
          Toast.makeText(Login.this,"Soit 6 ou + ",Toast.LENGTH_LONG).show();
      }
          mAuth.signInWithEmailAndPassword(emailTest,passwordTest).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      FirebaseUser user = mAuth.getCurrentUser();

                      if (task.getResult().getAdditionalUserInfo().isNewUser()){
                          String email = user.getEmail();
                          String uid = user.getUid();



                          HashMap<Object,String>hashMap = new HashMap<>();
                          hashMap.put("email",email);
                          hashMap.put("uid",uid);
                          hashMap.put("nomPrenom",c.getNomPrenom());
                          hashMap.put("occupation",c.getOccupation());
                          hashMap.put("onlineStatus","online");
                          hashMap.put("password",c.getPassword());
                          hashMap.put("phone",c.getPhone());

                          FirebaseDatabase database = FirebaseDatabase.getInstance();
                          DatabaseReference mDatabase = database.getReference("USER");
                          mDatabase.child(uid).setValue(hashMap);
                      }
                      startActivity(new Intent(Login.this,Dashboard.class));
                  }else{
                      Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();

              }
          });
      }

}
