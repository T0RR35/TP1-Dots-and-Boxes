package com.Tp1;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TelaJogo implements Screen {

    private Main game;

    private SpriteBatch batch;
    private BitmapFont font;

    private float tempo = 0, deltaTime = 0;
    private Integer segundos, minutos;
    private VerificaQuadrado verifica;

    public TelaJogo(Main game, String dificuldade) {
        this.game = game;
        verifica = new VerificaQuadrado(dificuldade);
        show();
    }

    //funcao show substitui a create utilizando a interface screen
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
    //render recebe delta (fps)
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 186/255f, 206/255f, 1f); //fundo

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

        batch.begin();
        font.draw(batch, "Tempo: " + minutos + ":" + segundos.toString(), 20, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 50);
        batch.end();

        //renderizando pontos, linhas e colunas
        for (int i = 0; i < 6; i++) { //colunas
            for (int j = 0; j < 6; j++) { //linhas
                if (j < 5) { //se for == 5 vai acessar memoria que nao existe 
                    verifica.getColunas()[i][j].render();
                }
                if (i < 5) { //se for == 5 vai acessar memoria que nao existe 
                    verifica.getLinhas()[i][j].render();
                }
                verifica.getPontos()[i][j].render();
            }
        }

        if (passou1segundo) {
            verifica.setBotJogou(true);
            verifica.verificaTudo();
        } else {
            verifica.setBotJogou(false);
        }
    }

    @Override
    public void dispose() {
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
        batch.dispose();
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
