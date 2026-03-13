package com.example.twoplayersinfbnoroom;

import java.util.ArrayList;

public class GameTurn {
    private ArrayList<Card> player1;
    private ArrayList<Card> player2;

    public GameTurn(ArrayList<Card> player1, ArrayList<Card> player2) {
        this.player1 = player1;
        this.player2 = player2;
    }


    public GameTurn() {
        // Required empty constructor for Firebase
        this.player1 = new ArrayList<>();
        this.player2 = new ArrayList<>();
    }

    public ArrayList<Card> getPlayer1() {
        return player1;
    }

    public void setPlayer1(ArrayList<Card> player1) {
        this.player1 = player1;
    }

    public ArrayList<Card> getPlayer2() {
        return player2;
    }

    public void setPlayer2(ArrayList<Card> player2) {
        this.player2 = player2;
    }
}
