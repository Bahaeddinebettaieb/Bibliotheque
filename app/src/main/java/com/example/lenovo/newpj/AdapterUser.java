package com.example.lenovo.newpj;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    List<User>userList;

    public AdapterUser(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String userName = userList.get(i).getNomPrenom();
        final String userEmail = userList.get(i).getEmail();

        myHolder.nomPrenom.setText(userName);
        myHolder.email.setText(userEmail);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView imgProfile;
        TextView nomPrenom,email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile =itemView.findViewById(R.id.imgProfile);
            nomPrenom = itemView.findViewById(R.id.nomPrenom);
            email = itemView.findViewById(R.id.email);
        }
    }

}
