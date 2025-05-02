package com.Tp1;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuJogo implements Screen{
    private Main game;

    private SpriteBatch batch;
	private Sprite background;
	private Texture imageBackground;
    public BitmapFont font;
    public FitViewport viewport;

    public MenuJogo(Main game){
        this.game = game;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);
		imageBackground = new Texture("fundoMenuTemporario.jpg");
		background = new Sprite(imageBackground);
		background.setOriginCenter();
		//escala baseada no tamanho da tela, entao o fundo sempre cobrira tudo
		background.setScale(Gdx.graphics.getWidth() / background.getWidth(), Gdx.graphics.getHeight() / background.getHeight());

        font.setUseIntegerPositions(false);
		//font.getData().setScale();
    }

    //render recebe delta (fps)
    @Override
	public void render(float delta) {

        
		ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

		batch.begin();
		batch.draw(background, 0, 0); //desenha fundo
        font.draw(batch, " Bem-vindo ao Dots and Boxes", 200, 300);
		font.draw(batch, "Clique em qualquer lugar para começar!", 200, 100);
		batch.end();

        if (Gdx.input.isTouched()) {
			game.setScreen(new TelaJogo(game));
			dispose();
		}
    }

    @Override
    public void dispose() {
            
            batch.dispose();
            imageBackground.dispose();
        }
    
    //implementaçao obrigatoria da class screen
    @Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}


    }
