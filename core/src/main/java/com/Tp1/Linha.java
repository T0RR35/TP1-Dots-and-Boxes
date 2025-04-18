package com.Tp1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.ScreenUtils;

public class Linha{
	
	private Sprite linha;
	private SpriteBatch batch;
	private Texture image;
    
    Linha(){
        image = new Texture("linhaApagada.png");
        batch = new SpriteBatch();
        linha = new Sprite(image);
        linha.setScale(0.2f, 0.5f);
        linha.setOrigin(0, 0);
    }
    
    Linha(int l){
    	
    	this(); //vai pro construtor padrao
    	
    	float x, y;
    	
    	if((l / 5) < 1) { //se p/6 <= 1 p esta na primeira linha
    		x = (l%5) * 116f;
    		y = -33f;
    		linha.setPosition(x, y);
    	}
    	else if((l / 5) < 2) { //se p/6 <= 2 p esta na segunda linha
    		x = (l%5) * 116f;
    		y = 320f;
    	    linha.setPosition(x, y);
    	}
    	else if((l / 5) < 3) { //se p/6 <= 3 p esta na terceira linha
    		x = (l%5) * 116f;
    		y = 240f;
    		linha.setPosition(x, y);
    	}
    	else if((l / 5) < 4) { //se p/6 <= 4 p esta na quarta linha
    		x = (l%5) * 116f;
    		y = 160f;
    		linha.setPosition(x, y);
    	}
    	else if((l / 5) < 5) { //se p/6 <= 5 p esta na quinta linha
    		x = (l%5) * 116f;
    		y = 80f;
    		linha.setPosition(x, y);
    	}
    	else{ //se p/6 > 5 p esta na sexta linha
    		x = (l%5) * 116f;
    		y = 0f;
    		linha.setPosition(x, y);
    	}
    }
    
    public void render() {
        batch.begin();
        linha.draw(batch);
        batch.end();
    }
    
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}