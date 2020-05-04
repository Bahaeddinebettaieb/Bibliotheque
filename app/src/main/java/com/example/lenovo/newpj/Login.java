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

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;

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
                  if(!task.isSuccessful()){
                      Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                  }else{
                      startActivity(new Intent(Login.this,Dashboard.class));
                  }
              }
          });
      System.out.println(emailTest);
      System.out.println(passwordTest);
//      System.out.println("here" + mAuth.getCurrentUser().getUid());

    }

//    private Boolean validEmail(){
//        String val = email.getText().toString();
//
//        if (val.isEmpty()){
//            email.setError("Field can not be empty");
//            return false;
//        }else{
//            email.setError(null);
//            return true;
//        }
//    }
//
//    private Boolean validPassword(){
//        String val = password.getText().toString();
//
//        if(val.isEmpty()){
//            password.setError("Field cannot be empty");
//            return false;
//        }else{
//            password.setError(null);
//            return true;
//        }
//    }
//
//    private void loginUser(){
//        if (!validEmail() | !validPassword()){
//            return;
//        }else{
//            isUser();
//        }
//    }
//
//    private void isUser(){
//         String userEmail = email.getText().toString().trim();
//        final String userPassword = password.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USER");
//
//        Query checkUser = reference.orderByChild("email").equalTo(userEmail);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               if (dataSnapshot.exists()){
//
//                   email.setError(null);
//
//                   String passwordFormDB = dataSnapshot.child(userPassword).child("password").getValue(String.class);
//
//                   if (passwordFormDB.equals(userPassword)){
//
//                       email.setError(null);
//
//                       String emailFormDB = dataSnapshot.child(userPassword).child("email").getValue(String.class);
//                       String nomPrenomFormDB = dataSnapshot.child(userPassword).child("nomPrenom").getValue(String.class);
//                       String occupationFormDB = dataSnapshot.child(userPassword).child("occupation").getValue(String.class);
//                       String phoneFormDB = dataSnapshot.child(userPassword).child("phone").getValue(String.class);
//
//
//                       Intent intent = new Intent(Login.this,Dashboard.class);
//
//                       intent.putExtra("email",emailFormDB);
//                       intent.putExtra("nomPrenom",nomPrenomFormDB);
//                       intent.putExtra("occupation",occupationFormDB);
//                       intent.putExtra("password",passwordFormDB);
//                       intent.putExtra("phone",phoneFormDB);
//
//                       startActivity(intent);
//
//                   }else{
//                       password.setError("Wrong password");
//                       password.requestFocus();
//                   }
//               }else{
//                   email.setError("No such User exist");
//                   email.requestFocus();
//               }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
