package com.ccumis.food.Model;

public class Room {
    private String  menber_1;
    private String  menber_2;

    public Room(String menber_1, String menber_2) {
        this.menber_1 = menber_1;
        this.menber_2 = menber_2;
    }

    public String getMenber_1() {
        return menber_1;
    }

    public void setMenber_1(String menber_1) {
        this.menber_1 = menber_1;
    }

    public String getMenber_2() {
        return menber_2;
    }

    public void setMenber_2(String menber_2) {
        this.menber_2 = menber_2;
    }
}
