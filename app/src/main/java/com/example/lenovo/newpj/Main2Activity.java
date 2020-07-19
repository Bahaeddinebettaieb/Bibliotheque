package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    Button etudiant;
    Button enseignant;
    Button admin,donneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etudiant = (Button)findViewById(R.id.etudiant);
        enseignant = (Button)findViewById(R.id.enseignant) ;
        admin = (Button)findViewById(R.id.admin);
        donneur = (Button)findViewById(R.id.donneur);


        etudiant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(Main2Activity.this,Login.class);
                startActivity(registerIntent);
            }
        });
        enseignant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(Main2Activity.this,Login.class);
                startActivity(registerIntent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent registerIntent = new Intent(Main2Activity.this,Login.class);
                startActivity(registerIntent);
            }
        });

        donneur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent addBookIntent = new Intent(Main2Activity.this,AddBook.class);
                startActivity(addBookIntent
                );
            }
        });



    }

}
