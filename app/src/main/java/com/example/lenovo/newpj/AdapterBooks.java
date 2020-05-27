package com.example.lenovo.newpj;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;


public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.MyHolder> {

    Context context;
    List<Book> bookList;

    public AdapterBooks(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_books,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        final Book book = bookList.get(i);
        String uid = bookList.get(i).getUid();
        String uEmail = bookList.get(i).getuEmail();
        String uName = bookList.get(i).getuName();
        String bId = bookList.get(i).getbId();
        String bTitle = bookList.get(i).getbTitre();
        String bDescription = bookList.get(i).getbDescription();
        String bImage = bookList.get(i).getbImage();

        String bTime = bookList.get(i).getbId();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(bTime));
        String time = DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();



        myHolder.uNameTv.setText(uName);
        myHolder.bTimeTv.setText(time);
        myHolder.bTitleTv.setText(bTitle);
        myHolder.bDescriptionTv.setText(bDescription);

        if (bImage.equals("noImage")){
            myHolder.bImageTv.setVisibility(View.GONE);
        }else{
            try {
                Picasso.get().load(bImage).into(myHolder.bImageTv);
            }
            catch (Exception e ){

            }
        }

        myHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "More...", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.ajouterPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Ajouter au Panier...", Toast.LENGTH_SHORT).show();
                showAddCart(book);
            }
        });

        myHolder.reserverLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Reserver le Livre...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddCart(Book book) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_book,null);

        ImageView bookIv  = view.findViewById(R.id.bookIv);
        final TextView titleTv = view.findViewById(R.id.titleTv);
        final TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        final String bookId = book.getbId();
        String title = book.getbTitre();
        String description = book.getbDescription();
        String image = book.getbImage();

        if (book.getbImage().equals("noImage")){
            bookIv.setVisibility(View.GONE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_add_cart).into(bookIv);
        }catch (Exception e) {
            bookIv.setImageResource(R.drawable.ic_add_cart);
        }

        titleTv.setText("" + title);
        descriptionTv.setText("" + description);

        AlertDialog dialog = builder.create();
        dialog.show();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTv.getText().toString().trim();
                String description = descriptionTv.getText().toString().trim();

                addToCart(bookId,title,description);
            }
        });

    }

    private int itemId = 1;
    private void addToCart(String bookId, String title, String description) {
        itemId++;

        EasyDB easyDB = EasyDB.init(context,"ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text","unique"}))
                .addColumn(new Column("Item_BId", new String[]{"text","not null"}))
                .addColumn(new Column("Item_title", new String[]{"text","not null"}))
                .addColumn(new Column("Item_description", new String[]{"text","not null"}))
                .doneTableColumn();

        Boolean b =easyDB.addData("Item_Id",itemId)
                .addData("Item_BId",bookId)
                .addData("Item_title",title)
                .addData("Item_description",description)
                .doneDataAdding();

        Toast.makeText(context, "Added to cart...", Toast.LENGTH_SHORT).show();

    }



    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView bImageTv;
        TextView uNameTv,bTitleTv,bDescriptionTv,bTimeTv;
        ImageButton moreBtn;
        Button ajouterPanier,reserverLivre;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            bImageTv = itemView.findViewById(R.id.bImageTv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            bTitleTv = itemView.findViewById(R.id.bTitleTv);
            bTimeTv = itemView.findViewById(R.id.bTimeTv);
            bDescriptionTv = itemView.findViewById(R.id.bDescriptionTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            ajouterPanier = itemView.findViewById(R.id.ajouterPanier);
            reserverLivre = itemView.findViewById(R.id.reserverLivre);
        }
    }


}
