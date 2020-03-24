package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button etudiant;
    Button enseignant;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etudiant = (Button)findViewById(R.id.etudiant);
        enseignant = (Button)findViewById(R.id.enseignant) ;
        admin = (Button)findViewById(R.id.admin);

        etudiant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(MainActivity.this,Login.class);
                startActivity(registerIntent);
            }
        });
        enseignant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(MainActivity.this,Login.class);
                startActivity(registerIntent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(MainActivity.this,Login.class);
                startActivity(registerIntent);
            }
        });
    }

}
