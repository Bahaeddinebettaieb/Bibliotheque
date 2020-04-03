package com.example.lenovo.newpj;

public class Livre {
    String  nbPage,isbn;
    String titre,auteur,editeur;

    public Livre(){

    }

    public Livre(String nbPage, String isbn, String titre, String auteur, String editeur) {
        this.nbPage = nbPage;
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
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
}
