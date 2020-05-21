package com.example.lenovo.newpj;

public class Book {

    String  bId,bNBCopie,bOrigine;
    String bTitre,bAuteur,bSpecialite;
    String dNomDonneur,bSituation,dCIN,dTelephoneDonneur;
    String bDescription,bImage,uid,uEmail,uName,bTimeStamp;

    public Book() {

    }

    public Book(String bId, String bNBCopie, String bOrigine, String bTitre, String bAuteur, String bSpecialite, String dNomDonneur,
                String bSituation, String dCIN, String dTelephoneDonneur, String bDescription, String bImage, String uid,
                String uEmail, String uName, String bTimeStamp) {
        this.bId = bId;
        this.bNBCopie = bNBCopie;
        this.bOrigine = bOrigine;
        this.bTitre = bTitre;
        this.bAuteur = bAuteur;
        this.bSpecialite = bSpecialite;
        this.dNomDonneur = dNomDonneur;
        this.bSituation = bSituation;
        this.dCIN = dCIN;
        this.dTelephoneDonneur = dTelephoneDonneur;
        this.bDescription = bDescription;
        this.bImage = bImage;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uName = uName;
        this.bTimeStamp = bTimeStamp;
    }

    public String getbTimeStamp() {
        return bTimeStamp;
    }

    public void setbTimeStamp(String bTimeStamp) {
        this.bTimeStamp = bTimeStamp;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbNBCopie() {
        return bNBCopie;
    }

    public void setbNBCopie(String bNBCopie) {
        this.bNBCopie = bNBCopie;
    }

    public String getbOrigine() {
        return bOrigine;
    }

    public void setbOrigine(String bOrigine) {
        this.bOrigine = bOrigine;
    }

    public String getbTitre() {
        return bTitre;
    }

    public void setbTitre(String bTitre) {
        this.bTitre = bTitre;
    }

    public String getbAuteur() {
        return bAuteur;
    }

    public void setbAuteur(String bAuteur) {
        this.bAuteur = bAuteur;
    }

    public String getbSpecialite() {
        return bSpecialite;
    }

    public void setbSpecialite(String bSpecialite) {
        this.bSpecialite = bSpecialite;
    }

    public String getdNomDonneur() {
        return dNomDonneur;
    }

    public void setdNomDonneur(String dNomDonneur) {
        this.dNomDonneur = dNomDonneur;
    }

    public String getbSituation() {
        return bSituation;
    }

    public void setbSituation(String bSituation) {
        this.bSituation = bSituation;
    }

    public String getdCIN() {
        return dCIN;
    }

    public void setdCIN(String dCIN) {
        this.dCIN = dCIN;
    }

    public String getdTelephoneDonneur() {
        return dTelephoneDonneur;
    }

    public void setdTelephoneDonneur(String dTelephoneDonneur) {
        this.dTelephoneDonneur = dTelephoneDonneur;
    }

    public String getbDescription() {
        return bDescription;
    }

    public void setbDescription(String bDescription) {
        this.bDescription = bDescription;
    }

    public String getbImage() {
        return bImage;
    }

    public void setbImage(String bImage) {
        this.bImage = bImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
