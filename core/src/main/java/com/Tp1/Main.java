package com.Tp1;

import java.util.Scanner;
import com.badlogic.gdx.ApplicationAdapter;
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

public class Main extends ApplicationAdapter {

	private SpriteBatch batch;
	private Sprite background;
	private Texture imageBackground;

	Coluna[][] colunas = new Coluna[6][5];
	Ponto[][] pontos = new Ponto[6][6];
	Linha[][] linhas = new Linha[5][6];

	@Override
	public void create() {
		batch = new SpriteBatch();
		imageBackground = new Texture("assets\\background.png");
		background = new Sprite(imageBackground);
		background.setOriginCenter();
		//escala baseada no tamanho da tela, entao o fundo sempre cobrira tudo
		background.setScale(Gdx.graphics.getWidth() / background.getWidth(), Gdx.graphics.getHeight() / background.getHeight());
		
		try {
			olhaCoordenadas();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

		batch.begin();
		batch.draw(background, 0, 0); //desenha fundo
		batch.end();

		//renderizando pontos, linhas e colunas
		for (int i = 0; i < 6; i++) { //colunas
			for(int j = 0; j < 6; j++){ //linhas
				if (j < 5) { //se for == 5 vai acessar memoria que nao existe 
					colunas[i][j].render();
				}
				if (i < 5) { //se for == 5 vai acessar memoria que nao existe 
					linhas[i][j].render();
				}
				pontos[i][j].render();
			}
		}

		//verificando se deu quadrado
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(colunas[i][j].getTemQueVerificarSeDeuQuadrado() == true){
					verificaSeDeuQuadrado(i, j, true, false);
				}
				if(linhas[i][j].getTemQueVerificarSeDeuQuadrado() == true){
					verificaSeDeuQuadrado(i, j, false, true);
				}
			}
		}
		
	}

	@Override
	public void dispose() {
		for (Ponto[] ponto2 : pontos) {
			for (Ponto ponto : ponto2) {
				ponto.dispose();
			}
		}
		for (Linha[] linhas2 : linhas) {
			for (Linha linha : linhas2) {
				linha.dispose();
			}
		}
		for (Coluna[] colunas2 : colunas) {
			for (Coluna coluna : colunas2) {
				coluna.dispose();
			}
		}
		batch.dispose();
		imageBackground.dispose();
	}

	void olhaCoordenadas() throws FileNotFoundException {

		File getCSVFiles = new File("assets\\coordenadas.csv");
		Scanner sc = new Scanner(getCSVFiles);
		sc.useDelimiter(";|\\n");
		int p = 0, c = 0, l = 0;
		int ll = 0, cl = 0; // linhas e colunas da matriz LINHA[][]
		int lc = 0, cc = 0; // linhas e colunas da matriz COLUNA[][]
		int lp = 0, cp = 0;// linhas e colunas da matriz PONTO[][]
		String token = "";

		while (sc.hasNext()) {
			token = sc.next();

			if ("Ponto".equals(token)) {
				pontos[lp][cp] = new Ponto(p);
				p++;
				if (lp == 5) {
					lp = 0;
					cp++;
				} else {
					lp++;
				}
			} else if ("Linha".equals(token)) {
				linhas[ll][cl] = new Linha(l);
				l++;
				if (ll == 4) {
					ll = 0;
					cl++;
				} else {
					ll++;
				}
			} else if ("Coluna".equals(token)) {
				colunas[lc][cc] = new Coluna(c);
				c++;
				if (lc == 5) {
					lc = 0;
					cc++;
				} else {
					lc++;
				}
			}
		}
		sc.close();
	}

	private void verificaSeDeuQuadrado(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {

		//se i == 5 so pode ter sido uma coluna que chamou a funcao
		if(i == 5){ //ve se a coluna eh a ultima da fileira
			colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(colunas[i-1][j].getEstaAcesa() == true){ //se a coluna do lado esquerdo estiver acesa
				if((linhas[i-1][j].getEstaAcesa() == true) && (linhas[i-1][j+1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
		//se j == 5 so pode ter sido uma linha que chamou a funcao
		else if(j == 5){ // verifica se a linha eh a mais embaixo do mapa
			linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(linhas[i][j-1].getEstaAcesa() == true){ //se a linha de cima estiver acesa
				if((colunas[i][j-1].getEstaAcesa() == true) && (colunas[i+1][j-1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
		else if(quemChamouFoiUmaColuna == true && i == 0){
			colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(colunas[i+1][j].getEstaAcesa() == true){ //se a coluna do lado direito estiver acesa
				if((linhas[i][j].getEstaAcesa() == true) && (linhas[i][j+1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
		else if(quemChamouFoiUmaLinha == true && j == 0){
			linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(linhas[i][j+1].getEstaAcesa() == true){ //se a linha de baixo estiver acesa
				if((colunas[i][j].getEstaAcesa() == true) && (colunas[i+1][j].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
		//se foi uma linha que nao esta na beirada
		else if(quemChamouFoiUmaLinha == true){
			linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(linhas[i][j+1].getEstaAcesa() == true){ //se a linha de baixo estiver acesa
				if((colunas[i][j].getEstaAcesa() == true) && (colunas[i+1][j].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
			if(linhas[i][j-1].getEstaAcesa() == true){ //se a linha de cima estiver acesa
				if((colunas[i][j-1].getEstaAcesa() == true) && (colunas[i+1][j-1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
 		// se foi uma coluna que nao esta na beirada
		else if(quemChamouFoiUmaColuna == true){
			colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
			if(colunas[i-1][j].getEstaAcesa() == true){ //se a coluna do lado esquerdo estiver acesa
				if((linhas[i-1][j].getEstaAcesa() == true) && (linhas[i-1][j+1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
			if(colunas[i+1][j].getEstaAcesa() == true){ //se a coluna do lado direito estiver acesa
				if((linhas[i][j].getEstaAcesa() == true) && (linhas[i][j+1].getEstaAcesa() == true)){
					System.out.println("Deu quadrado!");
				}
			}
		}
	}
}
