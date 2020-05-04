package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    CardView ajouterUtilisateur,ajouterLivre,gestionLivre,profileUtilisteur,logout ;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ajouterUtilisateur = (CardView)findViewById(R.id.ajouterUtilisateur);
        ajouterLivre = (CardView)findViewById(R.id.ajouterLivre);
        gestionLivre = (CardView)findViewById(R.id.gestionLivre);
        profileUtilisteur = (CardView)findViewById(R.id.profileUtilisateur);
        logout = (CardView)findViewById(R.id.logout) ;

        ajouterUtilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouterIntenet = new Intent(Dashboard.this, Register.class);
                startActivity(ajouterIntenet);
            }
        });

        ajouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouterLivreIntent = new Intent(Dashboard.this,AjouterLivre.class);
                startActivity(ajouterLivreIntent);
            }
        });

        gestionLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestionLivreIntent = new Intent (Dashboard.this,GetLivre.class);
                startActivity(gestionLivreIntent);
            }
        });

        profileUtilisteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(Dashboard.this,Profile.class);
                startActivity(profileIntent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(Dashboard.this,Login.class);
                finish();
                startActivity(loginIntent);
            }
        });


    }
}
