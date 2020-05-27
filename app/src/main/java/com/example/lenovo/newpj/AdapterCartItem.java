package com.example.lenovo.newpj;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.HolderCartItem> {
    private Context context;
    private ArrayList<CartItem> cartItemsList;

    public AdapterCartItem(Context context, ArrayList<CartItem> cartItemsList) {
        this.context = context;
        this.cartItemsList = cartItemsList;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cartitems,viewGroup,false);
        return new HolderCartItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, final int i) {

        final CartItem cartItem = cartItemsList.get(i);

        final String id = cartItem.getId();
        String bId = cartItem.getbId();
        String title = cartItem.getTitle();
        String auteur = cartItem.getAuteur();
        String specialite = cartItem.getSpecialite();

        holder.itemTitleTv.setText(""+title);
        holder.itemSpeacialiteTv.setText(""+specialite);
        holder.itemAuteurTv.setText(""+auteur);


        holder.itemRemoveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyDB easyDB = EasyDB.init(context,"ITEMS_DB")
                        .setTableName("ITEMS_TABLE")
                        .addColumn(new Column("Item_Id", new String[]{"text","unique"}))
                        .addColumn(new Column("Item_BId", new String[]{"text","not null"}))
                        .addColumn(new Column("Item_title", new String[]{"text","not null"}))
                        .addColumn(new Column("Item_specialite", new String[]{"text","not null"}))
                        .addColumn(new Column("Item_auteur", new String[]{"text","not null"}))
                        .doneTableColumn();

                easyDB.deleteRow(1,id);
                Toast.makeText(context, "Removed from cart...", Toast.LENGTH_SHORT).show();

                cartItemsList.remove(i);
                notifyItemChanged(i);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    class HolderCartItem extends RecyclerView.ViewHolder{

        TextView itemTitleTv,itemAuteurTv,itemSpeacialiteTv,itemRemoveTv;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);

            itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
            itemAuteurTv = itemView.findViewById(R.id.itemAuteurTv);
            itemSpeacialiteTv = itemView.findViewById(R.id.itemSpecialiteTv);
            itemRemoveTv = itemView.findViewById(R.id.itemRemoveTv);
        }


    }
}
