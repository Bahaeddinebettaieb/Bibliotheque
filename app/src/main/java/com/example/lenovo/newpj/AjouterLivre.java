package com.example.lenovo.newpj;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AjouterLivre extends AppCompatActivity {
    EditText titre,auteur,editeur,nbPages,isbn;
    Button ajouter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ProgressDialog progressDialog;
    DataItem dataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_livre);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();

        titre = (EditText)findViewById(R.id.titre);
        auteur = (EditText)findViewById(R.id.auteur);
        editeur = (EditText)findViewById(R.id.editeur);
        nbPages = (EditText)findViewById(R.id.nbPage);
        isbn = (EditText)findViewById(R.id.isbn);
        ajouter = (Button)findViewById(R.id.ajouter);
        progressDialog = new ProgressDialog(this);

        insertData();
    }

    private void insertData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Livre");

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titreTest = titre.getText().toString();
                final String auteurTest = auteur.getText().toString();
                final String editeurTest = editeur.getText().toString();
                final String nbPagesTest = nbPages.getText().toString();
                final String isbntest = isbn.getText().toString();
                final DataItem dataItem = new DataItem(titreTest,auteurTest,editeurTest,nbPagesTest,isbntest);

                myRef.child("Livre").setValue(dataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.setMessage("En train d'enregistrer");
                        progressDialog.show();
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        myRef.child("Livre01").setValue(dataItem);
//                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });

    }


}
