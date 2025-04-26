package com.Tp1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.ScreenUtils;

public class Linha extends ModelObject {
	
	private Sprite linha;
    
    Linha(){
        image = new Texture("linhaApagada.png");
        batch = new SpriteBatch();
        linha = new Sprite(image);
        linha.setScale(0.2f, 0.2f); //102x256
        linha.setOrigin(0, 0);
    }
    
    Linha(int l){
    	
    	this(); //vai pro construtor padrao
    	
    	x = (l%5) * 116f + 38;
    	
    	if((l / 5) < 1) { //se l/6 < 1 l esta na primeira linha
    		y = 370f;
    	}
    	else if((l / 5) < 2) { //se l/6 < 2 l esta na segunda linha
    		y = 296f;
    	}
    	else if((l / 5) < 3) { //se l/6 < 3 l esta na terceira linha
    		y = 222f;
    	}
    	else if((l / 5) < 4) { //se l/6 < 4 l esta na quarta linha
    		y = 148f;
    	}
    	else if((l / 5) < 5) { //se l/6 < 5 l esta na quinta linha
    		y = 74f;
    	}
    	else{ //se l/6 > 5 l esta na sexta linha
    		y = 0f;
    	}
    	linha.setPosition(x, y);
    }
    
	@Override
    public void render() {
        batch.begin();
        linha.draw(batch);
        batch.end();
    }
    
	@Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
    
    public void testaColisao() {
    	
    	Rectangle hitbox = linha.getBoundingRectangle();
    	if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
    		if(hitbox.contains(Gdx.input.getX(), Gdx.input.getY()));
    	}    	
    }
    	
}