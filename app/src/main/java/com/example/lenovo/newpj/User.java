package com.example.lenovo.newpj;

public class User {
    private String nomPrenom,email,password,phone,occupation,uid;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String nomPrenom, String email, String password, String phone, String occupation){
        this.nomPrenom = nomPrenom;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.occupation = occupation;
        //this.uid = uid;

    }

    public User(String nomPrenom, String email, String phone, String occupation) {
        this.nomPrenom = nomPrenom;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;

    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
