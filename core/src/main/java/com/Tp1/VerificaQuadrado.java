package com.Tp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VerificaQuadrado {

    private Coluna[][] colunas = new Coluna[6][5];
    private Ponto[][] pontos = new Ponto[6][6];
    private Linha[][] linhas = new Linha[5][6];
    private Player player1;
    private Player player2;
    int qntColunasAcesas = 0, qntLinhasAcesas = 0;
    boolean botJogou;

    public VerificaQuadrado(String dificuldade) {
        player1 = new Player(dificuldade);
        player2 = new Player(dificuldade);
        if (dificuldade.equals("easy") || dificuldade.equals("hard")) {
            botJogou = false;
        } else {
            botJogou = true;
        }
    }

    public void verificaTudo() {
        //verificando se deu quadrado
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (j < 5) { //se for == 5 vai acessar memoria que nao existe 
                    if (colunas[i][j].getTemQueVerificarSeDeuQuadrado() == true) { //pra nao contar quadrado quando clicar em uma coluna/linha que ja esta acesa
                        verificaSeDeuQuadrado(i, j, true, false);
                    }
                }
                if (i < 5) { //se for == 5 vai acessar memoria que nao existe 
                    if (linhas[i][j].getTemQueVerificarSeDeuQuadrado() == true) {
                        verificaSeDeuQuadrado(i, j, false, true);
                    }
                }
            }
        }
    }

    private void verificaSeDeuQuadrado(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {

        if (quemChamouFoiUmaColuna) {
            qntColunasAcesas++;
        }
        if (quemChamouFoiUmaLinha) {
            qntLinhasAcesas++;
        }

        boolean deuQuadradoEmCima = false, deuQuadradoEmbaixo = false, deuQuadradoNaDireita = false, deuQuadradoNaEsquerda = false;
        //se i == 5 so pode ter sido uma coluna que chamou a funcao
        if (i == 5) { //ve se a coluna eh a ultima da fileira
            deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } //se j == 5 so pode ter sido uma linha que chamou a funcao
        else if (j == 5) { // verifica se a linha eh a mais embaixo do mapa
            deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } //se foi uma coluna do canto esquerdo
        else if (quemChamouFoiUmaColuna == true && i == 0) {
            deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } //se foi uma linha do topo
        else if (quemChamouFoiUmaLinha == true && j == 0) {
            deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } //se foi uma linha que nao esta na beirada
        else if (quemChamouFoiUmaLinha == true) {
            deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
            deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } // se foi uma coluna que nao esta na beirada
        else if (quemChamouFoiUmaColuna == true) {
            deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
            deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        }

        //se fez quadrado aumenta ponto e repete a jogada
        if (deuQuadradoEmCima)repeteAjogadaEaumentaPonto();
        if (deuQuadradoEmbaixo)repeteAjogadaEaumentaPonto();
        if (deuQuadradoNaDireita)repeteAjogadaEaumentaPonto();
        if (deuQuadradoNaEsquerda)repeteAjogadaEaumentaPonto();
        //nao tem como fazer apenas um if com || ja que se vc fizer 2 quadrados de uma vez contaria apenas um ponto

        //se nao fez quadrado
        if (!deuQuadradoEmCima && !deuQuadradoEmbaixo && !deuQuadradoNaDireita && !deuQuadradoNaEsquerda) {
            passaAjogada();
        }
    }

    private boolean verificaSeDeuQuadradoNaEsquerda(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {
        colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (colunas[i - 1][j].getEstaAcesa() == true) { //se a coluna do lado esquerdo estiver acesa
            if ((linhas[i - 1][j].getEstaAcesa() == true) && (linhas[i - 1][j + 1].getEstaAcesa() == true)) {//se a linha da esquerda cima e esquerda baixo estiver acesas
                System.out.println("Deu quadrado!");
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoEmCima(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {
        linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (linhas[i][j - 1].getEstaAcesa() == true) { //se a linha de cima estiver acesa
            if ((colunas[i][j - 1].getEstaAcesa() == true) && (colunas[i + 1][j - 1].getEstaAcesa() == true)) {//se as colunas de cima estao acesas
                System.out.println("Deu quadrado!");
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoNaDireita(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {
        colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (colunas[i + 1][j].getEstaAcesa() == true) { //se a coluna do lado direito estiver acesa
            if ((linhas[i][j].getEstaAcesa() == true) && (linhas[i][j + 1].getEstaAcesa() == true)) {//se a linha da direita cima e direita baixo estiverem acesas
                System.out.println("Deu quadrado!");
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoEmbaixo(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {
        linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (linhas[i][j + 1].getEstaAcesa() == true) { //se a linha de baixo estiver acesa
            if ((colunas[i][j].getEstaAcesa() == true) && (colunas[i + 1][j].getEstaAcesa() == true)) {//se as colunas de baixo estao acesas
                System.out.println("Deu quadrado!");
                return true;
            }
        }
        return false;
    }

    public boolean verificaSeOJogoAcabou(){
        if(qntColunasAcesas == 30 && qntLinhasAcesas == 30){
            return true;
        }else{return false;}
    }

    private void repeteAjogadaEaumentaPonto() {
        if (player1.getVezDeJogar() == true) {//se for o player1 que fez o quadrado
            player1.aumentaScore();
            System.out.println("player 1: " + player1.getScore() + " pontos");
        } else if (player2.getVezDeJogar() == true) {//se for o player2 que fez o quadrado
            player2.aumentaScore();
            System.out.println("player 2: " + player2.getScore() + " pontos");
            if (player2.verificaSeEhBot() == true) { //se o player 2 for um bot
                if(player2.getDificuldade().equals("easy")){
                    fazAjogadaDeBotEasy();
                }else{
                    fazAjogadaDeBotHard();
                }
            }
        }
    }

    private void passaAjogada() {
        if (player1.getVezDeJogar() == true) {//se for o player1 que jogou por ultimo
            player2.setVezDeJogar(true);
            player1.setVezDeJogar(false);
            if (player2.verificaSeEhBot() == true) { //se o player 2 for um bot
                if(player2.getDificuldade().equals("easy")){
                    fazAjogadaDeBotEasy();
                }else{
                    fazAjogadaDeBotHard();
                }
            }
        } else if (player2.getVezDeJogar() == true) {//se for o player2 que jogou por ultimo
            player1.setVezDeJogar(true);
            player2.setVezDeJogar(false);
        }
    }

    private void fazAjogadaDeBotEasy() { //DIFICULDADE FACIL PQ VAI COLOCAR UM ALEATORIO
        int random = (int) (Math.random() * 2);
        int i, j;
        if (botJogou == true){ 
            if (random == 0 && qntLinhasAcesas < 30) {//vai clicar em uma linha aleatoria
                for (;;) {
                    i = (int) (Math.random() * 5); //valores entre 0 e 4
                    j = (int) (Math.random() * 6); //valores entre 0 e 5
                    if (linhas[i][j].getEstaAcesa() == false) {
                        linhas[i][j].acendeLinha();
                        verificaSeDeuQuadrado(i, j, false, true);
                        break;
                    }
                }
            } else if (qntColunasAcesas < 30) {//vai clicar em uma coluna aleatoria
                for (;;) {
                    i = (int) (Math.random() * 6); //valores entre 0 e 4
                    j = (int) (Math.random() * 5); //valores entre 0 e 5
                    if (colunas[i][j].getEstaAcesa() == false) {
                        colunas[i][j].acendeColuna();
                        verificaSeDeuQuadrado(i, j, true, false);
                        break;
                    }
                }
            }
        } else {
        }
    }

    public void fazAjogadaDeBotHard(){

    }
    //logica jogo
    public void olhaCoordenadas() throws FileNotFoundException {

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

    public Coluna[][] getColunas() {
        return colunas;
    }

    public Ponto[][] getPontos() {
        return pontos;
    }

    public Linha[][] getLinhas() {
        return linhas;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setBotJogou(boolean botJogou) {
        this.botJogou = botJogou;
    }
}