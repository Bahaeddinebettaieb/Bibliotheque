package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    CardView ajouterUtilisateur,ajouterLivre,gestionLivre,profileUtilisteur,logout,message ;
    BottomNavigationView navigation;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ajouterUtilisateur = (CardView)findViewById(R.id.ajouterUtilisateur);
        ajouterLivre = (CardView)findViewById(R.id.ajouterLivre);
        gestionLivre = (CardView)findViewById(R.id.gestionLivre);
        profileUtilisteur = (CardView)findViewById(R.id.profileUtilisateur);
        logout = (CardView)findViewById(R.id.logout) ;
        message = (CardView)findViewById(R.id.message);
        navigation = (BottomNavigationView)findViewById(R.id.navigation);




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

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(Dashboard.this,Message.class);
                startActivity(messageIntent);
            }
        });

//        navigation.setOnNavigationItemSelectedListener(selectedListener);
//
//        actionBar = getSupportActionBar();
//        actionBar.setTitle("Users");
//        UsersFragment fragmentUser = new UsersFragment();
//        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//        ft1.replace(R.id.content,fragmentUser,"");
//        ft1.commit();

    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    switch (menuItem.getItemId()){
//                        case R.id.nav_home:
//                            Intent intentDash = new Intent(Dashboard.this,Dashboard.class);
//                            startActivity(intentDash);
//                            return true;
//
//                        case R.id.nav_users:
//                            actionBar =getSupportActionBar();
//                            actionBar.setTitle("Users");
//                            UsersFragment fragmentUser = new UsersFragment();
//                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                            ft1.replace(R.id.content,fragmentUser,"");
//                            ft1.commit();
//                            return true;
//                    }
//                    return false;
//                }
//            };
}
