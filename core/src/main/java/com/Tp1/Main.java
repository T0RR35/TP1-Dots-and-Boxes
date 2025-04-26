package com.Tp1;

import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Scanner;
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

public class Main extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture image;

	Coluna[] colunas = new Coluna[30];
	Ponto[] pontos = new Ponto[36]; // era pra ser 35
	Linha[] linhas = new Linha[30];

	@Override
	public void create() {

		try {
			olhaCoordenadas();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// dahjiwbduyoaw
	}

	void olhaCoordenadas() throws FileNotFoundException {

		File getCSVFiles = new File("D:\\2025\\cefet\\3 ano\\zProjetos\\TP1\\assets\\coordenadas.csv");
		Scanner sc = new Scanner(getCSVFiles);
		sc.useDelimiter(";|\\n");
		int p = 0, l = 0, c = 0;
		String token = "";

		while (sc.hasNext()) {

			token = sc.next();

			if ("Ponto".equals(token)) {
				pontos[p] = new Ponto(p);
				p++;
			} else if ("Linha".equals(token)) {
				linhas[l] = new Linha(l);
				l++;
			} else if ("Coluna".equals(token)) {
				colunas[c] = new Coluna(c);
				c++;
			}
		}
		sc.close();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

		for (int i = 0; i < pontos.length; i++) {

			if (i < colunas.length) {
				colunas[i].render();
			}
			if (i < linhas.length) {
				linhas[i].render();
			}
			pontos[i].render();
		}
	}

	@Override
	public void dispose() {
		/*
		 * for (int i = 0; i < 36; i++) {
		 * pontos[i].dispose();
		 * }
		 */
		// batch.dispose();
		// image.dispose();
	}
}
