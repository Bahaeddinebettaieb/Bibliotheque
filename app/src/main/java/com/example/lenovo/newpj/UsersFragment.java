package com.example.lenovo.newpj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
    RecyclerView allUsers;
    AdapterUser adapterUser;
    List<User>userList;

    public UsersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_users, container, false);

        allUsers = view.findViewById(R.id.allUsers);
        allUsers.setHasFixedSize(true);
        allUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList = new ArrayList<>();

        getAllUsers();
        return view;
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
                    adapterUser = new AdapterUser(getActivity(),userList);
                    allUsers.setAdapter(adapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
