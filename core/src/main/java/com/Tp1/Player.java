package com.Tp1;

public class Player {
    private int score;
    private boolean vezDeJogar;
    private String dificuldade;

    Player(String dificuldade){
        score = 0;
        vezDeJogar = false;
        this.dificuldade = dificuldade;
        
    }

    public void aumentaScore(){
        this.score++;
    }

    public int getScore(){
        return score;
    }

    public void setVezDeJogar(boolean value){
        this.vezDeJogar = value;
    }

    public boolean getVezDeJogar(){
        return vezDeJogar;
    }

    public String getDificuldade(){
        return dificuldade;
    }

    public boolean verificaSeEhBot(){
        if(dificuldade.equals("easy") || dificuldade.equals("hard")){
            return true;
        }else{
            return false;
        }
    }
}
