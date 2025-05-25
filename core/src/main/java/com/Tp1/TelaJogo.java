package com.Tp1;

import java.io.FileNotFoundException;
import java.util.Iterator;

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
import com.badlogic.gdx.graphics.Color;

public class TelaJogo implements Screen {

    private Main game;

    private SpriteBatch batch;
    private BitmapFont font;
    private SpriteBatch batchGameOver;
    private BitmapFont fontGameOver;
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
        ScreenUtils.clear(183 / 255f, 223 / 255f, 223 / 255f, 1f); // fundo

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

        //renderizando os quadrados
        Iterator<Quadrado> iter = verifica.getQuadrados().iterator();
        for(Quadrado quadrado1 : verifica.getQuadrados()){
            Quadrado quadrado = iter.next();
            quadrado.render();
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
        if (verifica.verificaSeOJogoAcabou()) {
            desenhaGameOver();
        }else{
            mostraPontuacaoNaTela();
            
            font = generator.generateFont(parameter);
            font.setColor(Color.BLACK);
            font.draw(batch, "Tempo: " + minutos + ":" + segundos.toString(), (Gdx.graphics.getWidth() / 2) - 40,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 50);
        }

        batch.end();

        if (passou1segundo) {
            verifica.setBotJogou(true);
            verifica.verificaTudo();
        } else {
            verifica.setBotJogou(false);
        }
    }

    public void mostraPontuacaoNaTela(){
        BitmapFont fontPontuacao = new BitmapFont();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/MinhaFonte.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        fontPontuacao = generator.generateFont(parameter);
        fontPontuacao.setUseIntegerPositions(false);
        fontPontuacao.setColor(Color.BLACK);

        fontPontuacao.draw(batch,
                "Player 1: " + verifica.getPlayer1().getScore(),
                15, (Gdx.graphics.getHeight()) - 10);
        fontPontuacao.draw(batch,
                "Player 2: " + verifica.getPlayer2().getScore(),
                Gdx.graphics.getWidth()  - 150, (Gdx.graphics.getHeight()) - 10);
    }

    public void desenhaGameOver() {
        iniciaAsVariaveisGameOver();
        batchGameOver.begin();

        menuGameOver.draw(batchGameOver);
        fontGameOver.draw(batchGameOver,
                "Player 1: " + verifica.getPlayer1().getScore() + "\nPlayer 2: " + verifica.getPlayer2().getScore(),
                (Gdx.graphics.getWidth() / 2f) - 100, ((Gdx.graphics.getHeight()) / 2f) + 100);

        if (verifica.getPlayer1().getScore() > verifica.getPlayer2().getScore()) {
            fontGameOver.draw(batchGameOver, "PLAYER 1 WINS!", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        } else if (verifica.getPlayer1().getScore() < verifica.getPlayer2().getScore()) {
            fontGameOver.draw(batchGameOver, "PLAYER 2 WINS", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        }
        if (verifica.getPlayer1().getScore() == verifica.getPlayer2().getScore()) {
            fontGameOver.draw(batchGameOver, "EMPATE", (Gdx.graphics.getWidth() / 2f) - 100,
                    ((Gdx.graphics.getHeight()) / 2f) - 40);
        }

        parameter.size = 18;
        fontGameOver = generator.generateFont(parameter);
        fontGameOver.setColor(Color.BLACK);
        fontGameOver.draw(batchGameOver, "CLIQUE AQUI PARA REINICIAR", (Gdx.graphics.getWidth() / 2f) - 140,
                ((Gdx.graphics.getHeight()) / 2f) - 145);

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

        imageGameOver = new Texture("GameOver.png");
        menuGameOver = new Sprite(imageGameOver);
        menuGameOver.setOriginCenter();
    }

    public void reiniciaJogo() {
        Rectangle hitbox = new Rectangle(((Gdx.graphics.getWidth()) / 2) - 190, ((Gdx.graphics.getHeight()) / 2f) - 210,
                menuGameOver.getWidth() * 0.7f + 23, menuGameOver.getHeight() * 0.1f + 15);// (x, y, width, height)

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int mouseYreal = Gdx.graphics.getHeight() - mouseY;

        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            if (hitbox.contains(mouseX, mouseYreal)) {
                limpaJogo();
                game.setScreen(new MenuJogo(game));
            }
        }
    }

    public void limpaJogo() {
        for (Linha[] linhas2 : verifica.getLinhas()) {
            for (Linha linha : linhas2) {
                linha.reseta();
            }
        }
        for (Coluna[] colunas2 : verifica.getColunas()) {
            for (Coluna coluna : colunas2) {
                coluna.reseta();
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
