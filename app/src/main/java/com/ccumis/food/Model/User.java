package com.ccumis.food.Model;

public class User {

    private int type;//0 is supplier ;1 is customer;2 is both
    private String realN;
    private String nickN;
    private String mail;
    private String account ;
    private String pwd;
    private String phone;
    private String address;
    public String userId;
    private String UId;



    public User( String nickN, String mail, String phone, String address, String uId) {
        this.nickN = nickN;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
        UId = uId;
    }

    public String getUId() {
        return UId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getNickN() {
        return nickN;
    }

}
