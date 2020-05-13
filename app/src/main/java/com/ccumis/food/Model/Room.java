package com.ccumis.food.Model;

public class Room {
    private String  menber_1;
    private String  menber_2;
    private String menber_1_name;
    private String menber_2_name;
    private String good_name;

    public Room(String menber_1, String menber_2, String menber_1_name, String menber_2_name,String good_name) {
        this.menber_1 = menber_1;
        this.menber_2 = menber_2;
        this.menber_1_name = menber_1_name;
        this.menber_2_name = menber_2_name;
        this.good_name=good_name;
    }

    public String getGood_name(){
        return good_name;
    }

    public String getMenber_1() {
        return menber_1;
    }


    public String getMenber_2() {
        return menber_2;
    }

    public String getMenber_1_name() {
        return menber_1_name;
    }

    public String getMenber_2_name() {
        return menber_2_name;
    }
}
