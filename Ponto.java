package com.Tp1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.ScreenUtils;

public class Ponto extends ModelObject {

	private Sprite ponto;

	Ponto() {
		image = new Texture("botaoApagado.png");
		batch = new SpriteBatch();
		ponto = new Sprite(image);
		ponto.setScale(0.03f, 0.03f);
		ponto.setOrigin(0, 0);
	}

	Ponto(int p) {

		this(); // vai pro construtor padrao

		float x, y;
		x = (p % 6) * 116f + 15; // 15 eh metade do tamanho do ponto

		if ((p / 6) < 1) { // se p/6 <= 1 p esta na primeira linha
			y = 410f;
		} else if ((p / 6) < 2) { // se p/6 <= 2 p esta na segunda linha
			y = 336f;
		} else if ((p / 6) < 3) { // se p/6 <= 3 p esta na terceira linha
			y = 262f;
		} else if ((p / 6) < 4) { // se p/6 <= 4 p esta na quarta linha
			y = 188f;
		} else if ((p / 6) < 5) { // se p/6 <= 5 p esta na quinta linha
			y = 114f;
		} else { // se p/6 > 5 p esta na sexta linha
			y = 40f;
		}
		ponto.setPosition(x, y);
	}

	public void render() {
		batch.begin();
		ponto.draw(batch);
		batch.end();
	}

	public void dispose() {
		batch.dispose();
		image.dispose();
	}
}