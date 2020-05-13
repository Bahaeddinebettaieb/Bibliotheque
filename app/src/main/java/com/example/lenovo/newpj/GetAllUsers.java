package com.example.lenovo.newpj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetAllUsers extends AppCompatActivity {
    RecyclerView allUsers;
    AdapterUser adapterUser;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_users);

        allUsers = findViewById(R.id.allUsers);
        allUsers.setHasFixedSize(true);
        allUsers.setLayoutManager(new LinearLayoutManager(GetAllUsers.this));
        //setHasOptionsMenu(true);


        userList = new ArrayList<>();

        getAllUsers();
        //return view;
    }

    private void getAllUsers() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User modelUser = ds.getValue(User.class);

                    if (!modelUser.getUid().equals(user.getUid())){
                        userList.add(modelUser);
                    }
                    adapterUser = new AdapterUser(GetAllUsers.this,userList);
                    allUsers.setAdapter(adapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
