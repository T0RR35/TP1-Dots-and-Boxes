package com.Tp1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Coluna extends ModelObject {

	private Sprite coluna;
	private boolean estaAcesa;
	private boolean temQueVerificarSeDeuQuadrado;
	private boolean jaVerificou;
	private Rectangle hitbox;
	private ControlaSons controleSons;

	Coluna() {
		image = new Texture("colunaApagada.png");
		batch = new SpriteBatch();
		coluna = new Sprite(image);
		coluna.setScale(0.2f, 0.15f); // 512x512
		coluna.setOrigin(0, 0);

		hitbox = new Rectangle();

		estaAcesa = false;
		temQueVerificarSeDeuQuadrado = false;
		jaVerificou = false;
	}

	Coluna(int c) {

		this(); // vai pro construtor padrao

		x = (c % 6) * 116f - 20;

		if ((c / 6) < 1) { // se c/6 < 1 c esta na primeira linha
			y = 360f;
		} else if ((c / 6) < 2) { // se c/6 < 2 c esta na segunda linha
			y = 262f;
		} else if ((c / 6) < 3) { // se p/6 < 3 c esta na terceira linha
			y = 188f;
		} else if ((c / 6) < 4) { // se c/6 < 4 c esta na quarta linha
			y = 114f;
		} else { // se c/6 < 5 c esta na quinta linha
			y = 48f;
		}
		coluna.setPosition(x, y);
	}

	public void render() {
		batch.begin();
		coluna.draw(batch);
		batch.end();
		testaColisao();
		controleSons = ControlaSons.getInstance();
	}

	public void dispose() {
		batch.dispose();
		image.dispose();
	}

	public void testaColisao() {

		hitbox = new Rectangle(coluna.getX() + 44, coluna.getY(), coluna.getWidth()*0.2f - 88, coluna.getHeight()*0.15f);//(x, y, width, height)
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();

		int mouseYreal = Gdx.graphics.getHeight() - mouseY;

		if(jaVerificou == false){
			if (hitbox.contains(mouseX, mouseYreal)) {
                coluna.setAlpha(1);
            } else {
				coluna.setAlpha(0.7f);
			}
			if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
				if (hitbox.contains(mouseX, mouseYreal)) {
					controleSons.playClickSound();
					temQueVerificarSeDeuQuadrado = true;
					acendeColuna();
				}
			}
		}
	}

	public void acendeColuna(){
		image = new Texture("colunaAcesa.png");
		coluna.setRegion(image);
		estaAcesa = true;
		jaVerificou = true;
	}

	public void reseta() {
		this.estaAcesa = false;
		this.jaVerificou = false;
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