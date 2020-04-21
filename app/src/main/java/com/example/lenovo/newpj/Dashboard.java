package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    CardView ajouterUtilisateur,ajouterLivre,gestionLivre ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ajouterUtilisateur = (CardView)findViewById(R.id.ajouterUtilisateur);
        ajouterLivre = (CardView)findViewById(R.id.ajouterLivre);
        gestionLivre = (CardView)findViewById(R.id.gestionLivre);

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
    }
}
