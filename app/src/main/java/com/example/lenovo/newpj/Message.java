package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Message extends AppCompatActivity {

     Toolbar toolbar;
     RecyclerView recyclerView;
     TextView nameTv,userStatusTv;
     EditText messageEt;
     ImageButton sendBtn;

     FirebaseAuth firebaseAuth;
     FirebaseDatabase database;
     DatabaseReference ref;

     ValueEventListener seenListener;
     DatabaseReference userRef;

     List<Chat> chatList;
     AdapterChat adapterChat;


     String hisUid;
     String myUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

//        toolbar = (Toolbar)findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
        recyclerView = (RecyclerView)findViewById(R.id.chat_recyclerView);
        nameTv = (TextView)findViewById(R.id.nameTv);
        userStatusTv = (TextView)findViewById(R.id.userStatut);
        messageEt = (EditText)findViewById(R.id.messageEt);
        sendBtn = (ImageButton)findViewById(R.id.sendBtn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        Intent intent =getIntent();
        hisUid = intent.getStringExtra("hisUid");

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("USER");

        Query userQuery = ref.orderByChild("uid").equalTo(hisUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = "" + ds.child("nomPrenom").getValue();
                    String image = "" + ds.child("email").getValue();
                    String onlineStatus = "" + ds.child("onlineStatus").getValue();

                    if (onlineStatus.equals("online")){
                        userStatusTv.setText(onlineStatus);
                    }else{
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(onlineStatus));
                        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();
                        userStatusTv.setText("Last seen at : " + dateTime);
                    }

                    nameTv.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString().trim();

                if (TextUtils.isEmpty(message)){
                    Toast.makeText(Message.this,"Can not send the emty message...",Toast.LENGTH_SHORT).show();
                }else{
                    sendMessage(message);
                }
            }
        });

        readMessage();
        seenMessage();
    }


    private void seenMessage() {
        userRef = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chatModel = ds.getValue(Chat.class);
                    if (chatModel.getReceiver().equals(myUid) && chatModel.getSender().equals(hisUid)){
                        HashMap<String,Object> hasSeenHashMap = new HashMap<>();

                        hasSeenHashMap.put("isSeen",true);
                        ds.getRef().updateChildren(hasSeenHashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chatModel = ds.getValue(Chat.class);
                    if (chatModel.getReceiver().equals(myUid) && chatModel.getSender().equals(hisUid) ||
                            chatModel.getReceiver().equals(hisUid) && chatModel.getSender().equals(myUid)){
                        chatList.add(chatModel);
                    }
                    adapterChat = new AdapterChat(Message.this,chatList);
                    adapterChat.notifyDataSetChanged();

                    recyclerView.setAdapter(adapterChat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String message){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put("message",message);
        hashMap.put("timestamp",timeStamp);
        hashMap.put("isSeen",false);
        databaseReference.child("Chats").push().setValue(hashMap);

        messageEt.setText(" ");
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            myUid =user.getUid();
        }else{
            startActivity(new Intent(Message.this,Login.class));
            finish();
        }
    }

    private void checkOnlineStatus(String status){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("USER").child(myUid);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("onlineStatus",status);

        dbRef.updateChildren(hashMap);
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        checkOnlineStatus("online");
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timeStamp);
        userRef.removeEventListener(seenListener);
    }

    @Override
    protected void onResume() {
        checkOnlineStatus("online");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id== R.id.action_logout){
            firebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(Message.this,Login.class);
            checkUserStatus();
            finish();
            startActivity(loginIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
