package com.example.lenovo.newpj;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class GetAllBooks extends AppCompatActivity {

    RecyclerView recyclerView,cartItemsTv;
    List<Book> bookList;
    AdapterBooks adapterBooks;

    TextView itemTitleTv,itemSpecialiteTv,itemAuteurTv;

    String title,specialite,auteur;
    String id,bid,btitle,bauteur,bspecialite;

    AdapterCartItem adapterCartItem;
    ArrayList<CartItem>cartItemsList;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_books);
        firebaseAuth = FirebaseAuth.getInstance();


        recyclerView = (RecyclerView)findViewById(R.id.booksRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetAllBooks.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        bookList =new ArrayList<>();
        
        loadBooks();
    }

    private void loadBooks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Book bookModel = ds.getValue(Book.class);
                    bookList.add(bookModel);
                    adapterBooks = new AdapterBooks(GetAllBooks.this,bookList);
                    recyclerView.setAdapter(adapterBooks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GetAllBooks.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchBooks(final String searchQuery){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Book bookModel = ds.getValue(Book.class);
                    if (bookModel.getbTitre().toLowerCase().contains(searchQuery) || bookModel.getbDescription().toLowerCase().contains(searchQuery)){
                        bookList.add(bookModel);
                    }
                    adapterBooks = new AdapterBooks(GetAllBooks.this,bookList);
                    recyclerView.setAdapter(adapterBooks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GetAllBooks.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id== R.id.action_logout){
            firebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(GetAllBooks.this,Login.class);
            finish();
            startActivity(loginIntent);
        }

        if (id == R.id.showCartItems){
            showCartItem();
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadBookss() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
        reference.child("bId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    bid = ""+ds.child("bId").getValue();
                    btitle = ""+ds.child("bTitre").getValue();
                    bauteur = ""+ds.child("bAuteur").getValue();
                    bspecialite = ""+ds.child("bSpecialite").getValue();

                    itemTitleTv.setText(btitle);
                    itemAuteurTv.setText(bauteur);
                    itemSpecialiteTv.setText(bspecialite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GetAllBooks.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCartItem() {

        cartItemsList = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart,null);
        TextView titleTv = view.findViewById(R.id.itemTitleTv);
        TextView specialiteTv = view.findViewById(R.id.itemSpecialiteTv);
        TextView auteurTv = view.findViewById(R.id.itemAuteurTv);
        RecyclerView cartItemsRecycle = view.findViewById(R.id.cartItemsTv);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        //itemTitleTv.setText(title);

        //titleTv.setText(btitle);

        EasyDB easyDB = EasyDB.init(this,"ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text","unique"}))
                .addColumn(new Column("Item_BId", new String[]{"text","not null"}))
                .addColumn(new Column("Item_title", new String[]{"text","not null"}))
                .addColumn(new Column("Item_bspecialite", new String[]{"text","not null"}))
                .addColumn(new Column("Item_auteur", new String[]{"text","not null"}))
                .doneTableColumn();

        Cursor res = easyDB.getAllData();
        while (res.moveToNext()){
            id = res.getString(0);
            bid = res.getString(1);
            btitle = res.getString(2);
            bspecialite = res.getString(3);
            bauteur = res.getString(4);

            CartItem cartItem = new CartItem(
                    ""+id,
                    ""+bid,
                    ""+btitle,
                    ""+bauteur,
                    ""+bspecialite
            );
            cartItemsList.add(cartItem);
        }

        adapterCartItem = new AdapterCartItem(this,cartItemsList);
        cartItemsRecycle.setAdapter(adapterCartItem);

        loadBookss();
        //itemAuteurTv.setText(bauteur);
        //itemSpecialiteTv.setText(bspecialite);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

    }

}
