package com.Tp1;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuJogo implements Screen {

    private Main game;

    private SpriteBatch batch;
    private Sprite background;
    private Sprite playerXplayer;
    private Sprite playerXeasy;
    private Sprite playerXhard;
    private Texture imageBackground;
    private Texture imagePlayerXplayer;
    private Texture imagePlayerXeasy;
    private Texture imagePlayerXhard;
    public BitmapFont font;
    public FitViewport viewport;
    public float tempo = 0;
    public Integer relogio;
    public float deltaTime = 0;

    public MenuJogo(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        imageBackground = new Texture("fundoMenuTemporario.jpg");
        background = new Sprite(imageBackground);
        imagePlayerXplayer = new Texture("playerXplayer.jpg");
        playerXplayer = new Sprite(imagePlayerXplayer);
        playerXplayer.setScale(0.3f, 0.3f);
        imagePlayerXeasy = new Texture("playerXeasy.jpg");
        playerXeasy = new Sprite(imagePlayerXeasy);
        playerXeasy.setScale(0.3f, 0.3f);
        imagePlayerXhard = new Texture("playerXhard.jpg");
        playerXhard = new Sprite(imagePlayerXhard);
        playerXhard.setScale(0.3f, 0.3f);

        // escala baseada no tamanho da tela, entao o fundo sempre cobrira tudo
        background.setScale(Gdx.graphics.getWidth() / background.getWidth(),
                Gdx.graphics.getHeight() / background.getHeight());
        deltaTime = 0;
        relogio = new Integer(0);

        font.setUseIntegerPositions(false);
        // font.getData().setScale();
    }

    // render recebe delta (fps)
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (tempo < 1f) {
            deltaTime = Gdx.graphics.getDeltaTime();
            tempo += deltaTime;
        } else {
            tempo = 0;
            relogio++;
        }
        batch.begin();
        batch.draw(background, 0, 0); // desenha fundo

        playerXplayer.setPosition(-150, 50);
        playerXhard.setPosition(15, 50);
        playerXeasy.setPosition(250, 50);
        playerXplayer.draw(batch);
        playerXhard.draw(batch);
        playerXeasy.draw(batch);

        font.draw(batch, relogio.toString(), 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, " Bem-vindo ao Dots and Boxes", 210, 300);
        font.draw(batch, "Clique em qualquer lugar para começar!", 190, 100);
        batch.end();

        selecionaDificuldade();
    }

    public void selecionaDificuldade() {
        Rectangle hitboxPlayerXplayer = new Rectangle(30, 145, playerXplayer.getWidth() * 0.3f,
                playerXplayer.getHeight() * 0.3f);// (x, y, width, height)
        Rectangle hitboxPlayerXhard = new Rectangle(230, 145, playerXhard.getWidth() * 0.3f,
                playerXhard.getHeight() * 0.3f);// (x, y, width, height)
        Rectangle hitboxPlayerXeasy = new Rectangle(445, 145, playerXeasy.getWidth() * 0.3f,
                playerXeasy.getHeight() * 0.3f);// (x, y, width, height)

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int mouseYreal = Gdx.graphics.getHeight() - mouseY;

        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            if (hitboxPlayerXplayer.contains(mouseX, mouseYreal)) {
                game.setScreen(new TelaJogo(game, "player"));
                System.out.println("Player X player");
                dispose();
            } else if (hitboxPlayerXhard.contains(mouseX, mouseYreal)) {
                game.setScreen(new TelaJogo(game, "hard"));
                System.out.println("Player X hard");
                dispose();
            } else if (hitboxPlayerXeasy.contains(mouseX, mouseYreal)) {
                game.setScreen(new TelaJogo(game, "easy"));
                System.out.println("Player X easy");
                dispose();
            }
        }
    }

    @Override
    public void dispose() {

        batch.dispose();
        imageBackground.dispose();
    }

    // implementaçao obrigatoria da class screen
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
