package com.example.lenovo.newpj;

public class Livre {
    String  nbCopie,origine;
    String titre,auteur,specialite;
    String nomDonneur,situation,cin,telDonneur;

    public Livre(){

    }

    public Livre(String nbCopie, String origine, String titre, String auteur, String specialite, String nomDonneur, String situation, String cin, String telDonneur) {
        this.nbCopie = nbCopie;
        this.origine = origine;
        this.titre = titre;
        this.auteur = auteur;
        this.specialite = specialite;
        this.nomDonneur = nomDonneur;
        this.situation = situation;
        this.cin = cin;
        this.telDonneur = telDonneur;
    }

    public String getNbCopie() {
        return nbCopie;
    }

    public void setNbCopie(String nbCopie) {
        this.nbCopie = nbCopie;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNomDonneur() {
        return nomDonneur;
    }

    public void setNomDonneur(String nomDonneur) {
        this.nomDonneur = nomDonneur;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTelDonneur() {
        return telDonneur;
    }

    public void setTelDonneur(String telDonneur) {
        this.telDonneur = telDonneur;
    }
}
