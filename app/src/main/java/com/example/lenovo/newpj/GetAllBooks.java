package com.example.lenovo.newpj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetAllBooks extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Book> bookList;
    AdapterBooks adapterBooks;

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

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (!TextUtils.isEmpty(query)){
//                    searchBooks(query);
//                }else{
//                    loadBooks();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!TextUtils.isEmpty(newText)){
//                    searchBooks(newText);
//                }else{
//                    loadBooks();
//                }
//                return false;
//            }
//        });

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
        return super.onOptionsItemSelected(item);
    }

}
