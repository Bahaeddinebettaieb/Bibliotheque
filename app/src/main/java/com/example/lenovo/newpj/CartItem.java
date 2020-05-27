package com.example.lenovo.newpj;

public class CartItem {
    String id,bId,title,auteur,specialite;

    public CartItem() {
    }

    public CartItem(String id, String bId, String title, String auteur, String specialite) {
        this.id = id;
        this.bId = bId;
        this.title = title;
        this.auteur = auteur;
        this.specialite = specialite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
