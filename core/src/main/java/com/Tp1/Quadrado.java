package com.Tp1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Quadrado {
    
    private ShapeRenderer quadrado = new ShapeRenderer();
    private Rectangle retangulo;
    private Color cor;
    private ControlaSons controleSons;

    Quadrado(String ondeEstaOquadrado, int i, int j, String qualPlayerFezOquadrado){
        
        controleSons = ControlaSons.getInstance();
        // define a cor de acordo com o player
        if(qualPlayerFezOquadrado.equals("player1")){
            controleSons.playQuadradoSound();
            cor = Color.RED;
        }else if(qualPlayerFezOquadrado.equals("player2")){
            controleSons.playQuadradoSound();
            cor = Color.BLUE;
        }
        
        if(ondeEstaOquadrado.equals("Direita")){
            retangulo = new Rectangle((i * 116f) + 30, ((5-j) * 74) - 30, 116, 74);
        }
        if(ondeEstaOquadrado.equals("Esquerda")){
            retangulo = new Rectangle((i * 116f) + 30 - 116, ((5-j) * 74) - 30, 116, 74);
        }
        if(ondeEstaOquadrado.equals("EmCima")){
            retangulo = new Rectangle((i * 116f) + 30, ((5-j) * 74) - 30 + 74, 116, 74);
        }
        if(ondeEstaOquadrado.equals("Embaixo")){
            retangulo = new Rectangle((i * 116f) + 30, ((5-j) * 74) - 30, 116, 74);
        }

    }

    public void render(){
        quadrado.begin(ShapeRenderer.ShapeType.Filled);
        quadrado.setColor(cor);
        quadrado.rect(retangulo.x, retangulo.y, retangulo.width, retangulo.height);
        quadrado.end();
    }

    public void dispose() {
        quadrado.dispose();
    }
}
