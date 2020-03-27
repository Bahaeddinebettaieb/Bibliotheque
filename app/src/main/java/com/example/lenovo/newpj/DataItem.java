package com.example.lenovo.newpj;

import android.widget.EditText;

public class DataItem {
    String titre,auteur,editeur;
    String  nbPage,isbn;



        public DataItem(String titre, String auteur, String editeur, String nbPage, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPage = nbPage;
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getNbPage() {
        return nbPage;
    }

    public void setNbPage(String nbPage) {
        this.nbPage = nbPage;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
