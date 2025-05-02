package com.Tp1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Linha extends ModelObject {

	private Sprite linha;
	private boolean estaAcesa;
	private boolean temQueVerificarSeDeuQuadrado;
	private boolean jaVerificou;
	private Rectangle hitbox;

	Linha() {
		image = new Texture("linhaApagada.png");
		batch = new SpriteBatch();
		linha = new Sprite(image);
		linha.setScale(0.2f, 0.2f); // 102x256
		linha.setOrigin(0, 0);

		hitbox = new Rectangle();
		
		estaAcesa = false;
		temQueVerificarSeDeuQuadrado = false;
		jaVerificou = false;
	}

	Linha(int l) {

		this(); // vai pro construtor padrao

		float x, y;
		x = (l % 5) * 116f + 38;

		if ((l / 5) < 1) { // se l/6 < 1 l esta na primeira linha
			y = 370f;
		} else if ((l / 5) < 2) { // se l/6 < 2 l esta na segunda linha
			y = 296f;
		} else if ((l / 5) < 3) { // se l/6 < 3 l esta na terceira linha
			y = 222f;
		} else if ((l / 5) < 4) { // se l/6 < 4 l esta na quarta linha
			y = 148f;
		} else if ((l / 5) < 5) { // se l/6 < 5 l esta na quinta linha
			y = 74f;
		} else { // se l/6 > 5 l esta na sexta linha
			y = 0f;
		}
		linha.setPosition(x, y);
	}

	public void render() {
		batch.begin();
		linha.draw(batch);
		batch.end();
		testaColisao();
	}

	public void dispose() {
		batch.dispose();
		image.dispose();
	}

	public void testaColisao() {
		hitbox = new Rectangle(linha.getX(), linha.getY() + 44, linha.getWidth()*0.2f, linha.getHeight()*0.2f - 88);//(x, y, width, height)
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();

		int mouseYreal = Gdx.graphics.getHeight() - mouseY;

		if(jaVerificou == false){
			if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
				if (hitbox.contains(mouseX, mouseYreal)) {
					image = new Texture("linhaAcesa.png");
					linha.setRegion(image);
					estaAcesa = true;
					temQueVerificarSeDeuQuadrado = true;
					jaVerificou = true;
				}
			}
		}
	}

	public boolean getEstaAcesa(){
		return estaAcesa;
	}

	public boolean getTemQueVerificarSeDeuQuadrado(){
		return temQueVerificarSeDeuQuadrado;
	}

	public void setTemQueVerificarSeDeuQuadrado(boolean value){
		temQueVerificarSeDeuQuadrado = value;
	}
}