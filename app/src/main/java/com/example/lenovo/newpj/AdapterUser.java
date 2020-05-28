package com.example.lenovo.newpj;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    List<User>userList;

    FirebaseAuth firebaseAuth;
    String myUid;

    public AdapterUser(Context context, List<User> userList) {
        this.context = context;
        this.userList = new ArrayList<>(userList);

        firebaseAuth = FirebaseAuth.getInstance();
        myUid = firebaseAuth.getUid();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        final String hisUID = userList.get(i).getUid();
        String userName = userList.get(i).getNomPrenom();
        final String userEmail = userList.get(i).getEmail();

        myHolder.nomPrenom.setText(userName);
        myHolder.email.setText(userEmail);

        myHolder.blockTv.setImageResource(R.drawable.ic_unblock);
        checkIsBlocked(hisUID,myHolder,i);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,Message.class);
                intent.putExtra("hisUid",hisUID);
                context.startActivity(intent);

                isBlockedOrNot(hisUID);
            }
        });

        myHolder.blockTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userList.get(i).isBlocked()){
                    unBlockUser(hisUID);
                }else{
                    blockUser(hisUID);
                }
            }
        });

    }

    private void isBlockedOrNot(final String hisUID){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");
        ref.child(myUid).child("BlockedUsers").orderByChild("uid").equalTo(myUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if (ds.exists()){
                                Toast.makeText(context, "You're blocked by that user, can't send message...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Intent intent = new Intent(context,Message.class);
                        intent.putExtra("hisUid",hisUID);
                        context.startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void checkIsBlocked(String hisUID, final MyHolder myHolder, final int i) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");
        ref.child(myUid).child("BlockedUsers").orderByChild("uid").equalTo(hisUID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if (ds.exists()){
                                myHolder.blockTv.setImageResource(R.drawable.ic_block);
                                userList.get(i).setBlocked(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void blockUser(String hisUID) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",hisUID);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");
        ref.child(myUid).child("BlockedUsers").child(hisUID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Blocked Successfully...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void unBlockUser(String hisUID) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");
        ref.child(myUid).child("BlockedUsers").orderByChild("uid").equalTo(hisUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if (ds.exists()){
                                ds.getRef().removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Unbloked Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    } 

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView imgProfile,blockTv;
        TextView nomPrenom,email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            blockTv = itemView.findViewById(R.id.blockTv);
            imgProfile =itemView.findViewById(R.id.imgProfile);
            nomPrenom = itemView.findViewById(R.id.nomPrenom);
            email = itemView.findViewById(R.id.email);
        }
    }

}
