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



    public User(int type, String realN, String nickN, String mail, String account, String pwd, String phone, String address) {
        this.type = type;
        this.realN = realN;
        this.nickN = nickN;
        this.mail = mail;
        this.account = account;
        this.pwd = pwd;
        this.phone = phone;
        this.address = address;
    }
}
