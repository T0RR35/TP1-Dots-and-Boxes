package com.Tp1;

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
    private Sprite playerXplayer;
    private Sprite playerXeasy;
    private Sprite playerXhard;
    private Texture imagePlayerXplayer;
    private Texture imagePlayerXeasy;
    private Texture imagePlayerXhard;
    public BitmapFont font;
    public FitViewport viewport;

    public MenuJogo(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        imagePlayerXplayer = new Texture("playerXplayer.jpg");
        playerXplayer = new Sprite(imagePlayerXplayer);
        playerXplayer.setScale(0.3f, 0.3f);
        imagePlayerXeasy = new Texture("playerXeasy.jpg");
        playerXeasy = new Sprite(imagePlayerXeasy);
        playerXeasy.setScale(0.3f, 0.3f);
        imagePlayerXhard = new Texture("playerXhard.jpg");
        playerXhard = new Sprite(imagePlayerXhard);
        playerXhard.setScale(0.3f, 0.3f);

        font.setUseIntegerPositions(false);
        // font.getData().setScale();
    }

    // render recebe delta (fps)
    @Override
    public void render(float delta) {

        ScreenUtils.clear(1f, 186/255f, 206/255f, 1f); //desenha fundo

        batch.begin();

        playerXplayer.setPosition(-150, 50);
        playerXhard.setPosition(15, 50);
        playerXeasy.setPosition(250, 50);
        playerXplayer.draw(batch);
        playerXhard.draw(batch);
        playerXeasy.draw(batch);

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
