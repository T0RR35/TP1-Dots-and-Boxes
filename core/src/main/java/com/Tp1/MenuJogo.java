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
    private Sprite fundoMenu;
    private Texture imagefundoMenu;
    private Sprite playerXplayer;
    private Sprite playerXeasy;
    private Sprite playerXhard;
    private Texture imagePlayerXplayer;
    private Texture imagePlayerXeasy;
    private Texture imagePlayerXhard;
    public BitmapFont font;
    public FitViewport viewport;
    private ControlaSons controleSons;

    public MenuJogo(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        imagefundoMenu = new Texture("Menu.jpg");
        fundoMenu = new Sprite(imagefundoMenu);

        imagePlayerXplayer = new Texture("playerXplayer.jpg");
        playerXplayer = new Sprite(imagePlayerXplayer);
        playerXplayer.setScale(1f, 1f);

        imagePlayerXeasy = new Texture("playerXeasy.jpg");
        playerXeasy = new Sprite(imagePlayerXeasy);
        playerXeasy.setScale(1f, 1f);

        imagePlayerXhard = new Texture("playerXhard.jpg");
        playerXhard = new Sprite(imagePlayerXhard);
        playerXhard.setScale(1f, 1f);

        font.setUseIntegerPositions(false);

        controleSons = ControlaSons.getInstance(); //inicializando
        controleSons.playMusic(); //toca msc de fundo
       
        // font.getData().setScale();
    }

    // render recebe delta (fps)
    @Override
    public void render(float delta) {

        ScreenUtils.clear(1f, 186/255f, 206/255f, 1f); //desenha fundo

        batch.begin();
        playerXplayer.setPosition(139, 270);
        playerXhard.setPosition(139, 150);
        playerXeasy.setPosition(139, 30);
        fundoMenu.draw(batch);
        playerXplayer.draw(batch);
        playerXhard.draw(batch);
        playerXeasy.draw(batch);

        //font.draw(batch, " Bem-vindo ao Dots and Boxes", 210, 300);
        //font.draw(batch, "Clique em qualquer lugar para começar!", 190, 100);

        batch.end();

        selecionaDificuldade();
    }

    public void selecionaDificuldade() {
        Rectangle hitboxPlayerXplayer = new Rectangle(139, 270, playerXplayer.getWidth() * 1f,
                playerXplayer.getHeight() * 1f);// (x, y, width, height)
        Rectangle hitboxPlayerXhard = new Rectangle(139, 150, playerXhard.getWidth() * 1f,
                playerXhard.getHeight() * 1f);// (x, y, width, height)
        Rectangle hitboxPlayerXeasy = new Rectangle(139, 30, playerXeasy.getWidth() * 1f,
                playerXeasy.getHeight() * 1f);// (x, y, width, height)

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int mouseYreal = Gdx.graphics.getHeight() - mouseY;

        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            
            if (hitboxPlayerXplayer.contains(mouseX, mouseYreal)) {
                controleSons.playClickSound();
                game.setScreen(new TelaJogo(game, "player"));
                System.out.println("Player X player");
                dispose();
            } else if (hitboxPlayerXhard.contains(mouseX, mouseYreal)) {
                controleSons.playClickSound();
                game.setScreen(new TelaJogo(game, "hard"));
                System.out.println("Player X hard");
                dispose();
            } else if (hitboxPlayerXeasy.contains(mouseX, mouseYreal)) {
                controleSons.playClickSound();
                game.setScreen(new TelaJogo(game, "easy"));
                System.out.println("Player X easy");
                dispose();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose(); 
        imagePlayerXplayer.dispose();
        imagePlayerXeasy.dispose();
        imagePlayerXhard.dispose();
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
