package com.Tp1;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Color;

public class TelaJogo implements Screen {

    private Main game;

    private SpriteBatch batch;
    private BitmapFont font;
    private SpriteBatch batchGameOver;
    private BitmapFont fontGameOver;
    private FitViewport viewport;
    private Sprite menuGameOver;
    private Texture imageGameOver;
    private FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator generator;

    private float tempo = 0, deltaTime = 0;
    private Integer segundos, minutos;
    private VerificaQuadrado verifica;

    public TelaJogo(Main game, String dificuldade) {
        this.game = game;
        verifica = new VerificaQuadrado(dificuldade);
        show();
    }

    // funcao show substitui a create utilizando a interface screen
    @Override
    public void show() {
        batch = new SpriteBatch();
        verifica.getPlayer1().setVezDeJogar(true);
        segundos = minutos = new Integer(0);
        font = new BitmapFont();
        try {
            verifica.olhaCoordenadas();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // render recebe delta (fps)
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 186 / 255f, 206 / 255f, 1f); // fundo

        boolean passou1segundo = false;
        if (tempo < 1f) {
            deltaTime = Gdx.graphics.getDeltaTime();
            tempo += deltaTime;
        } else {
            tempo = 0;
            passou1segundo = true;
            if (segundos % 59 == 0 && segundos > 0) {
                segundos = 0;
                minutos++;
            } else {
                segundos++;
            }
        }

        // renderizando pontos, linhas e colunas
        for (int i = 0; i < 6; i++) { // colunas
            for (int j = 0; j < 6; j++) { // linhas
                if (j < 5) { // se for == 5 vai acessar memoria que nao existe
                    verifica.getColunas()[i][j].render();
                }
                if (i < 5) { // se for == 5 vai acessar memoria que nao existe
                    verifica.getLinhas()[i][j].render();
                }
                verifica.getPontos()[i][j].render();
            }
        }

        batch.begin();
        int[] pontuacaoDosPlayers = new int[2];
        if (verifica.verificaSeOJogoAcabou(pontuacaoDosPlayers)) {
            desenhaGameOver(pontuacaoDosPlayers);
        }
        font.draw(batch, "Tempo: " + minutos + ":" + segundos.toString(), 20,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 50);
        batch.end();

        if (passou1segundo) {
            verifica.setBotJogou(true);
            verifica.verificaTudo();
        } else {
            verifica.setBotJogou(false);
        }
    }

    public void desenhaGameOver(int[] pontuacaoDosPlayers) {
        iniciaAsVariaveisGameOver();
        batchGameOver.begin();

        menuGameOver.draw(batchGameOver);
        fontGameOver.draw(batchGameOver,
                "GAME OVER!\nPlayer 1: " + pontuacaoDosPlayers[0] + "\nPlayer 2: " + pontuacaoDosPlayers[1],
                (Gdx.graphics.getWidth() / 2f) - 100, ((Gdx.graphics.getHeight()) / 2f) + 100);

        if (pontuacaoDosPlayers[0] > pontuacaoDosPlayers[1]) {
            fontGameOver.draw(batchGameOver, "PLAYER 1 WINS", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        } else if (pontuacaoDosPlayers[0] < pontuacaoDosPlayers[1]) {
            fontGameOver.draw(batchGameOver, "PLAYER 2 WINS", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        }
        if (pontuacaoDosPlayers[0] == pontuacaoDosPlayers[1]) {
            fontGameOver.draw(batchGameOver, "EMPATE", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        }

        menuGameOver.setScale(0.9f, 0.2f);

        menuGameOver.setPosition(
                (Gdx.graphics.getWidth() - 512) / 2,
                ((Gdx.graphics.getHeight() - 512) / 2f) - 170);

        menuGameOver.draw(batchGameOver); // desenha um retangulo quadrado embaixo
        parameter.size = 18;
        fontGameOver = generator.generateFont(parameter);
        fontGameOver.setColor(Color.BLACK);
        fontGameOver.draw(batchGameOver, "Clique aqui para reiniciar o jogo", (Gdx.graphics.getWidth() / 2f) - 170,
                ((Gdx.graphics.getHeight()) / 2f) - 155);

        reiniciaJogo();

        batchGameOver.end();
    }

    public void iniciaAsVariaveisGameOver() {
        batchGameOver = new SpriteBatch();
        fontGameOver = new BitmapFont();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/MinhaFonte.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        fontGameOver = generator.generateFont(parameter);
        fontGameOver.setUseIntegerPositions(false);
        fontGameOver.setColor(Color.BLACK);
        viewport = new FitViewport(8, 5);

        imageGameOver = new Texture("GameOver.png");
        menuGameOver = new Sprite(imageGameOver);
        menuGameOver.setScale(0.9f, 0.7f);
        menuGameOver.setOriginCenter();

        menuGameOver.setPosition(
                (Gdx.graphics.getWidth() - 512) / 2f,
                (Gdx.graphics.getHeight() - 512) / 2f);
    }

    public void reiniciaJogo() {
        Rectangle hitbox = new Rectangle(((Gdx.graphics.getWidth()) / 2) - 190, ((Gdx.graphics.getHeight()) / 2f) - 210,
                menuGameOver.getWidth() * 0.7f + 23, menuGameOver.getHeight() * 0.1f + 15);// (x, y, width, height)

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int mouseYreal = Gdx.graphics.getHeight() - mouseY;

        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            if (hitbox.contains(mouseX, mouseYreal)) {
                //new MenuJogo(game);
            }
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        fontGameOver.dispose();

        // Libera o FreeTypeFontGenerator, que é um recurso caro
        if (generator != null) {
            generator.dispose();
        }

        // Libera os recursos do batch
        batch.dispose();
        batchGameOver.dispose();

        // Libera os recursos de texturas
        if (imageGameOver != null) {
            imageGameOver.dispose();
        }

        // Libera recursos dos objetos do jogo
        for (Ponto[] ponto2 : verifica.getPontos()) {
            for (Ponto ponto : ponto2) {
                ponto.dispose();
            }
        }
        for (Linha[] linhas2 : verifica.getLinhas()) {
            for (Linha linha : linhas2) {
                linha.dispose();
            }
        }
        for (Coluna[] colunas2 : verifica.getColunas()) {
            for (Coluna coluna : colunas2) {
                coluna.dispose();
            }
        }
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
