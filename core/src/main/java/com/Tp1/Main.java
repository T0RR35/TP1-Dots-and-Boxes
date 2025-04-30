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
	Player player1 = new Player();
	Player player2 = new Player();

	@Override
	public void create() {
		batch = new SpriteBatch();
		imageBackground = new Texture("background.png");
		background = new Sprite(imageBackground);
		background.setOriginCenter();
		//escala baseada no tamanho da tela, entao o fundo sempre cobrira tudo
		background.setScale(Gdx.graphics.getWidth() / background.getWidth(), Gdx.graphics.getHeight() / background.getHeight());

		player1.setVezDeJogar(true);
		
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
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (j < 5) { //se for == 5 vai acessar memoria que nao existe 
					if(colunas[i][j].getTemQueVerificarSeDeuQuadrado() == true){
						verificaSeDeuQuadrado(i, j, true, false);
					}
				}
				if (i < 5) { //se for == 5 vai acessar memoria que nao existe 
					if(linhas[i][j].getTemQueVerificarSeDeuQuadrado() == true){
						verificaSeDeuQuadrado(i, j, false, true);
					}
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

		File getCSVFiles = new File("./assets/coordenadas.csv");
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

		boolean deuQuadradoEmCima=false, deuQuadradoEmbaixo=false, deuQuadradoNaDireita=false, deuQuadradoNaEsquerda=false;
		//se i == 5 so pode ter sido uma coluna que chamou a funcao
		if(i == 5){ //ve se a coluna eh a ultima da fileira
			deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}
		//se j == 5 so pode ter sido uma linha que chamou a funcao
		else if(j == 5){ // verifica se a linha eh a mais embaixo do mapa
			deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}
		//se foi uma coluna do canto esquerdo
		else if(quemChamouFoiUmaColuna == true && i == 0){
			deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}
		//se foi uma linha do topo
		else if(quemChamouFoiUmaLinha == true && j == 0){
			deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}
		//se foi uma linha que nao esta na beirada
		else if(quemChamouFoiUmaLinha == true){
			deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
			deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}
 		// se foi uma coluna que nao esta na beirada
		else if(quemChamouFoiUmaColuna == true){
			deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
			deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
		}

		//se fez quadrado aumenta ponto e repete a jogada
		if(deuQuadradoEmCima==true){repeteAjogadaEaumentaPonto();}
		if(deuQuadradoEmbaixo==true){repeteAjogadaEaumentaPonto();}
		if(deuQuadradoNaDireita==true){repeteAjogadaEaumentaPonto();}
		if(deuQuadradoNaEsquerda==true){repeteAjogadaEaumentaPonto();}

		if(deuQuadradoEmCima==false &&
		deuQuadradoEmbaixo==false &&
		deuQuadradoNaDireita==false &&
		deuQuadradoNaEsquerda==false){ //se nao fez quadrado
			passaAjogada();
		}
	}

	private boolean verificaSeDeuQuadradoNaEsquerda(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha){
		colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
		if(colunas[i-1][j].getEstaAcesa() == true){ //se a coluna do lado esquerdo estiver acesa
			if((linhas[i-1][j].getEstaAcesa() == true) && (linhas[i-1][j+1].getEstaAcesa() == true)){//se a linha da esquerda cima e esquerda baixo estiver acesas
				System.out.println("Deu quadrado!");
				return true;
			}
		}
		return false;
	}

	private boolean verificaSeDeuQuadradoEmCima(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha){
		linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
		if(linhas[i][j-1].getEstaAcesa() == true){ //se a linha de cima estiver acesa
			if((colunas[i][j-1].getEstaAcesa() == true) && (colunas[i+1][j-1].getEstaAcesa() == true)){//se as colunas de cima estao acesas
				System.out.println("Deu quadrado!");
				return true;
			}
		}
		return false;
	}

	private boolean verificaSeDeuQuadradoNaDireita(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha){
		colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
		if(colunas[i+1][j].getEstaAcesa() == true){ //se a coluna do lado direito estiver acesa
			if((linhas[i][j].getEstaAcesa() == true) && (linhas[i][j+1].getEstaAcesa() == true)){//se a linha da direita cima e direita baixo estiverem acesas
				System.out.println("Deu quadrado!");
				return true;
			}
		}
		return false;
	}

	private boolean verificaSeDeuQuadradoEmbaixo(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha){
		linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
		if(linhas[i][j+1].getEstaAcesa() == true){ //se a linha de baixo estiver acesa
			if((colunas[i][j].getEstaAcesa() == true) && (colunas[i+1][j].getEstaAcesa() == true)){//se as colunas de baixo estao acesas
				System.out.println("Deu quadrado!");
				return true;
			}
		}
		return false;
	}

	private void repeteAjogadaEaumentaPonto(){
		if(player1.getVezDeJogar() == true){//se for o player1 que fez o quadrado
			player1.aumentaScore();
			System.out.println("player 1: " + player1.getScore() + " pontos");
		}else if(player2.getVezDeJogar() == true){//se for o player2 que fez o quadrado
			player2.aumentaScore();
			System.out.println("player 2: " + player2.getScore() + " pontos");
		}
	}

	private void passaAjogada(){
		if(player1.getVezDeJogar() == true){//se for o player1 que fez o quadrado
			player2.setVezDeJogar(true);
			player1.setVezDeJogar(false);
		}else if(player2.getVezDeJogar() == true){//se for o player2 que fez o quadrado
			player1.setVezDeJogar(true);
			player2.setVezDeJogar(false);
		}
	}
}
