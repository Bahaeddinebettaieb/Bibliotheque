package com.example.lenovo.newpj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetLivre extends AppCompatActivity {
    ListView myListView;
    ArrayList<String> myArrayList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayAdapter<String> myArrayAdapter;
    Livre livre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_livre);

        livre = new Livre();
        myListView = (ListView)findViewById(R.id.listView);
        myArrayList = new ArrayList<>();
        myArrayAdapter = new ArrayAdapter<String>(this,R.layout.livre_info,R.id.livreInfo,myArrayList);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Livre");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    livre = ds.getValue(Livre.class);
                    myArrayList.add( "Titre:" +  " " + livre.getTitre().toString() + "," + " " + "Editeur:" + " " + livre.getEditeur().toString() + ","
                            + " " + "Auteur:" + " " + livre.getAuteur().toString());
                }
                myListView.setAdapter(myArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GetLivre.this,"Aucun livre...", Toast.LENGTH_LONG).show();

            }
        });
    }
}
