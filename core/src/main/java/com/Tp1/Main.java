package com.Tp1;

import java.util.Scanner;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.io.*;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */

class ModelObject {
	// atributos padrao
	protected SpriteBatch batch;
	protected Texture image;
	protected float x = 0, y = 0;

	// metodos padrao
	protected void render() {
	}

	protected void dispose() {
	}
}

public class Main extends Game {

	@Override
	public void create(){
		setScreen(new MenuJogo(this));
	}

	@Override
	public void render() {
    super.render(); //n sei pq mas só funciona c/ esse super render
	}

}
