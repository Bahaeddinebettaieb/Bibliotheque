package com.example.lenovo.newpj;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    Livre livre;
    long maxId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_livre);

        titre = (EditText)findViewById(R.id.titre);
        auteur = (EditText)findViewById(R.id.auteur);
        editeur = (EditText)findViewById(R.id.editeur);
        nbPages = (EditText)findViewById(R.id.nbPage);
        isbn = (EditText)findViewById(R.id.isbn);
        ajouter = (Button)findViewById(R.id.ajouter);


        livre = new Livre();

        myRef = FirebaseDatabase.getInstance().getReference().child("Livre");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxId = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AjouterLivre.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livre.setAuteur(auteur.getText().toString().trim());
                livre.setEditeur(editeur.getText().toString().trim());
                livre.setIsbn(isbn.getText().toString().trim());
                livre.setNbPage(nbPages.getText().toString().trim());
                livre.setTitre(titre.getText().toString().trim());
                Toast.makeText(AjouterLivre.this,"Data inserted...",Toast.LENGTH_LONG).show();
                myRef.child(String.valueOf(maxId+1)).setValue(livre);
                Intent dashboardIntent = new Intent(AjouterLivre.this,Dashboard.class);
                startActivity(dashboardIntent);
            }
        });

    }

}
