package com.example.lenovo.newpj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class Register extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    EditText nomPrenom;
    EditText email;
    EditText password;
    EditText phone;
    Button register;
    CheckBox admin,enseignant,etudiant;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER = "user";
    private User user;
    public ImageView imageToUpload;
    public Button choiseImage;
    private Uri filePath;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nomPrenom = (EditText) findViewById(R.id.nomPrenom);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        register = (Button) findViewById(R.id.register);
        admin = (CheckBox)findViewById(R.id.admin);
        enseignant = (CheckBox)findViewById(R.id.enseignant);
        etudiant = (CheckBox)findViewById(R.id.etudiant);
        imageToUpload = (ImageView)findViewById(R.id.imageToUpload);
        choiseImage = (Button)findViewById(R.id.choiseImage);
        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("USER");

        mAuth = FirebaseAuth.getInstance();

        choiseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTest = email.getText().toString().trim();
                String passwordTest = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailTest) || (TextUtils.isEmpty(passwordTest) || (passwordTest.length() < 6) ) ){
                    Toast.makeText(Register.this, "S'il vous plait vérifier votre Email ou mot de passe", Toast.LENGTH_LONG).show();
                    return;
                }

                String nomPrenomTest = nomPrenom.getText().toString().trim();
                String phoneTest = phone.getText().toString().trim();
                String occupation = null;

                if(TextUtils.isEmpty(nomPrenomTest) || (TextUtils.isEmpty(phoneTest))){
                    Toast.makeText(Register.this, "S'il vous plait vérifier votre nom ou téléphone sont remplis ou non", Toast.LENGTH_LONG).show();
                    return;
                }

                if(phoneTest.length() != 8){
                    phone.setError("Il doit avoir 8 nombres!");
                }

                if(etudiant.isChecked()){
                    occupation = etudiant.getText().toString().trim();
                }
                if (enseignant.isChecked()){
                    occupation = enseignant.getText().toString().trim();
                }
                if (admin.isChecked()){
                    occupation = admin.getText().toString().trim();
                }

                user = new User(nomPrenomTest,emailTest,passwordTest,phoneTest,occupation);
                registerUser(emailTest,passwordTest);
            }
        });

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() !=null){
            filePath =data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageToUpload.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void registerUser(String email, String password){
        progressDialog.setMessage("En train d'enregistrer");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();



                            Toast.makeText(Register.this, "Inscription avec Succès!", Toast.LENGTH_LONG).show();

                            updateUI(user);
                        } else {
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent dashboardIntent = new Intent(this,Dashboard.class);
        startActivity(dashboardIntent);
    }
}
