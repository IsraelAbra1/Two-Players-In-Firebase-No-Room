package com.example.twoplayersinfbnoroom;

public class Card {
    private int num;
    private String color;

    public Card(int num, String color)
    {
        this.num = num;
        this.color = color;
    }

    public Card() {
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
