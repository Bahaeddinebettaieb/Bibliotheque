package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    CardView ajouterUtilisateur ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ajouterUtilisateur = (CardView)findViewById(R.id.ajouterUtilisateur);

        ajouterUtilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouterIntenet = new Intent(Dashboard.this, Register.class);
                startActivity(ajouterIntenet);
            }
        });
    }
}
