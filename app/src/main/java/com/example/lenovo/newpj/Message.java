package com.example.lenovo.newpj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Message extends AppCompatActivity {

     Toolbar toolbar;
     RecyclerView recyclerView;
     TextView nameTv,userStatusTv;
     EditText messageEt;
     ImageButton sendBtn;


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
    }
}
