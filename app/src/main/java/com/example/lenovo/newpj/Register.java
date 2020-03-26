package com.example.lenovo.newpj;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    //EditText nomPrenom;
    EditText email;
    EditText password;
    Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       // nomPrenom = (EditText) findViewById(R.id.nomPrenom);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String emailTest = email.getText().toString().trim();
        String passwordTest = password.getText().toString().trim();
       // String nomPrenomtest = nomPrenom.getText().toString().trim();

//        if (TextUtils.isEmpty(nomPrenomtest)) {
//            Toast.makeText(this, "S'il vous plait entrer votre Nom et Prénom", Toast.LENGTH_LONG).show();
//            return;
//        }

        if (TextUtils.isEmpty(emailTest)) {
            Toast.makeText(this, "S'il vous plait entrer votre Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordTest)) {
            Toast.makeText(this, "S'il vous plait entrer votre mot de passe", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("En train d'enregistrer");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailTest, passwordTest).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Inscription avec Succès!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
