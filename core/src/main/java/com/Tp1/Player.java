package com.Tp1;

public class Player {
    private int score;
    private boolean vezDeJogar;

    Player(){
        score = 0;
        vezDeJogar = false;
    }

    public void aumentaScore(){
        this.score++;
        System.out.println(score);
    }

    public void setVezDeJogar(boolean value){
        this.vezDeJogar = value;
    }
}
