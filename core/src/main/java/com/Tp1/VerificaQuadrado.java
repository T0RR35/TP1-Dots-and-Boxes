package com.Tp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VerificaQuadrado {

    private Coluna[][] colunas = new Coluna[6][5];
    private Ponto[][] pontos = new Ponto[6][6];
    private Linha[][] linhas = new Linha[5][6];
    private ArrayList<Quadrado> quadrados = new ArrayList<>();
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
        // verificando se deu quadrado
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (j < 5) { // se for == 5 vai acessar memoria que nao existe
                    if (colunas[i][j].getTemQueVerificarSeDeuQuadrado() == true) { // pra nao contar quadrado quando
                                                                                   // clicar em uma coluna/linha que ja
                                                                                   // esta acesa
                        qntColunasAcesas++;
                        if(verificaSeDeuQuadrado(i, j, true, false) == false){
                            passaAjogada();
                        }
                    }
                }
                if (i < 5) { // se for == 5 vai acessar memoria que nao existe
                    if (linhas[i][j].getTemQueVerificarSeDeuQuadrado() == true) {
                        qntLinhasAcesas++;
                        if(verificaSeDeuQuadrado(i, j, false, true) == false){
                            passaAjogada();
                        }
                    }
                }
            }
        }
    }

    private boolean verificaSeDeuQuadrado(int i, int j, boolean quemChamouFoiUmaColuna, boolean quemChamouFoiUmaLinha) {

        boolean deuQuadradoEmCima = false, deuQuadradoEmbaixo = false, deuQuadradoNaDireita = false,
                deuQuadradoNaEsquerda = false;
        // se i == 5 so pode ter sido uma coluna que chamou a funcao
        if (i == 5) { // ve se a coluna eh a ultima da fileira
            deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna,
                    quemChamouFoiUmaLinha);
        } // se j == 5 so pode ter sido uma linha que chamou a funcao
        else if (j == 5) { // verifica se a linha eh a mais embaixo do mapa
            deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } // se foi uma coluna do canto esquerdo
        else if (quemChamouFoiUmaColuna == true && i == 0) {
            deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } // se foi uma linha do topo
        else if (quemChamouFoiUmaLinha == true && j == 0) {
            deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } // se foi uma linha que nao esta na beirada
        else if (quemChamouFoiUmaLinha == true) {
            deuQuadradoEmCima = verificaSeDeuQuadradoEmCima(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
            deuQuadradoEmbaixo = verificaSeDeuQuadradoEmbaixo(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
        } // se foi uma coluna que nao esta na beirada
        else if (quemChamouFoiUmaColuna == true) {
            deuQuadradoNaDireita = verificaSeDeuQuadradoNaDireita(i, j, quemChamouFoiUmaColuna, quemChamouFoiUmaLinha);
            deuQuadradoNaEsquerda = verificaSeDeuQuadradoNaEsquerda(i, j, quemChamouFoiUmaColuna,
                    quemChamouFoiUmaLinha);
        }

        // se fez quadrado aumenta ponto e repete a jogada
        if (deuQuadradoEmCima)
            repeteAjogadaEaumentaPonto();
        if (deuQuadradoEmbaixo)
            repeteAjogadaEaumentaPonto();
        if (deuQuadradoNaDireita)
            repeteAjogadaEaumentaPonto();
        if (deuQuadradoNaEsquerda)
            repeteAjogadaEaumentaPonto();
        // nao tem como fazer apenas um if com || ja que se vc fizer 2 quadrados de uma
        // vez contaria apenas um ponto

        // se teve algum quadrado
        if (deuQuadradoEmCima || deuQuadradoEmbaixo || deuQuadradoNaDireita || deuQuadradoNaEsquerda) {
            return true;
        } else {// se nao fez quadrado
            return false;
        }
    }

    private boolean verificaSeDeuQuadradoNaEsquerda(int i, int j, boolean quemChamouFoiUmaColuna,
            boolean quemChamouFoiUmaLinha) {
        colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (colunas[i - 1][j].getEstaAcesa() == true) { // se a coluna do lado esquerdo estiver acesa
            if ((linhas[i - 1][j].getEstaAcesa() == true) && (linhas[i - 1][j + 1].getEstaAcesa() == true)) {// se a
                                                                                                             // linha da
                                                                                                             // esquerda
                                                                                                             // cima e
                                                                                                             // esquerda
                                                                                                             // baixo
                                                                                                             // estiver
                                                                                                             // acesas
                System.out.println("Deu quadrado!");
                if (player1.getVezDeJogar()) {
                    quadrados.add(new Quadrado("Esquerda", i, j, "player1"));
                } else {
                    quadrados.add(new Quadrado("Esquerda", i, j, "player2"));
                }
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoEmCima(int i, int j, boolean quemChamouFoiUmaColuna,
            boolean quemChamouFoiUmaLinha) {
        linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (linhas[i][j - 1].getEstaAcesa() == true) { // se a linha de cima estiver acesa
            if ((colunas[i][j - 1].getEstaAcesa() == true) && (colunas[i + 1][j - 1].getEstaAcesa() == true)) {// se as
                                                                                                               // colunas
                                                                                                               // de
                                                                                                               // cima
                                                                                                               // estao
                                                                                                               // acesas
                System.out.println("Deu quadrado!");
                if (player1.getVezDeJogar()) {
                    quadrados.add(new Quadrado("EmCima", i, j, "player1"));
                } else {
                    quadrados.add(new Quadrado("EmCima", i, j, "player2"));
                }
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoNaDireita(int i, int j, boolean quemChamouFoiUmaColuna,
            boolean quemChamouFoiUmaLinha) {
        colunas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (colunas[i + 1][j].getEstaAcesa() == true) { // se a coluna do lado direito estiver acesa
            if ((linhas[i][j].getEstaAcesa() == true) && (linhas[i][j + 1].getEstaAcesa() == true)) {// se a linha da
                                                                                                     // direita cima e
                                                                                                     // direita baixo
                                                                                                     // estiverem acesas
                System.out.println("Deu quadrado!");
                if (player1.getVezDeJogar()) {
                    quadrados.add(new Quadrado("Direita", i, j, "player1"));
                } else {
                    quadrados.add(new Quadrado("Direita", i, j, "player2"));
                }
                return true;
            }
        }
        return false;
    }

    private boolean verificaSeDeuQuadradoEmbaixo(int i, int j, boolean quemChamouFoiUmaColuna,
            boolean quemChamouFoiUmaLinha) {
        linhas[i][j].setTemQueVerificarSeDeuQuadrado(false);
        if (linhas[i][j + 1].getEstaAcesa() == true) { // se a linha de baixo estiver acesa
            if ((colunas[i][j].getEstaAcesa() == true) && (colunas[i + 1][j].getEstaAcesa() == true)) {// se as colunas
                                                                                                       // de baixo estao
                                                                                                       // acesas
                System.out.println("Deu quadrado!");
                if (player1.getVezDeJogar()) {
                    quadrados.add(new Quadrado("Embaixo", i, j, "player1"));
                } else {
                    quadrados.add(new Quadrado("Embaixo", i, j, "player2"));
                }
                return true;
            }
        }
        return false;
    }

    public boolean verificaSeOJogoAcabou() {
        if (qntColunasAcesas == 30 && qntLinhasAcesas == 30) {
            return true;
        } else {
            return false;
        }
    }

    private void repeteAjogadaEaumentaPonto() {
        if (player1.getVezDeJogar() == true) {// se for o player1 que fez o quadrado
            player1.aumentaScore();
            System.out.println("player 1: " + player1.getScore() + " pontos");
        } else if (player2.getVezDeJogar() == true) {// se for o player2 que fez o quadrado
            player2.aumentaScore();
            System.out.println("player 2: " + player2.getScore() + " pontos");
            if (player2.verificaSeEhBot() == true) { // se o player 2 for um bot
                if (player2.getDificuldade().equals("easy")) {
                    fazAjogadaDeBotEasy();
                } else {
                    //fazAjogadaDeBotHard(); essa funcao eh chamada na propria funcao fazAjogadaDeBotHard()
                }
            }
        }
    }

    private void passaAjogada() {
        if (player1.getVezDeJogar() == true) {// se for o player1 que jogou por ultimo
            player2.setVezDeJogar(true);
            player1.setVezDeJogar(false);
            if (player2.verificaSeEhBot() == true) { // se o player 2 for um bot
                if (player2.getDificuldade().equals("easy")) {
                    fazAjogadaDeBotEasy();
                } else {
                    fazAjogadaDeBotHard();
                }
            }
        } else if (player2.getVezDeJogar() == true) {// se for o player2 que jogou por ultimo
            player1.setVezDeJogar(true);
            player2.setVezDeJogar(false);
        }
    }

    private void fazAjogadaDeBotEasy() { // DIFICULDADE FACIL PQ VAI COLOCAR UM ALEATORIO
        int random = (int) (Math.random() * 2);
        int i, j;
        if (botJogou == true) {
            if (random == 0 && qntLinhasAcesas < 30) {// vai clicar em uma linha aleatoria
                for (;;) {
                    i = (int) (Math.random() * 5); // valores entre 0 e 4
                    j = (int) (Math.random() * 6); // valores entre 0 e 5
                    if (linhas[i][j].getEstaAcesa() == false) {
                        qntLinhasAcesas++;
                        linhas[i][j].acendeLinha();
                        if(verificaSeDeuQuadrado(i, j, false, true) == false){
                            passaAjogada();
                        }
                        break;
                    }
                }
            } else if (qntColunasAcesas < 30) {// vai clicar em uma coluna aleatoria
                for (;;) {
                    i = (int) (Math.random() * 6); // valores entre 0 e 4
                    j = (int) (Math.random() * 5); // valores entre 0 e 5
                    if (colunas[i][j].getEstaAcesa() == false) {
                        qntColunasAcesas++;
                        colunas[i][j].acendeColuna();
                        if(verificaSeDeuQuadrado(i, j, true, false) == false){
                            passaAjogada();         
                        }
                        break;
                    }
                }
            }
        } else {
        }
    }

    public void fazAjogadaDeBotHard() {
        // analisa o mapa

        boolean fezUmQuadrado = false;
        for (int i = 0; i < 6; i++) { // colunas
            for (int j = 0; j < 6; j++) { // linhas
                if (j < 5 && qntColunasAcesas < 30) { // se for == 5 vai acessar memoria que nao existe
                    if(colunas[i][j].getEstaAcesa() == false){
                        if (verificaSeDeuQuadrado(i, j, true, false)) {
                            qntColunasAcesas++;
                            colunas[i][j].acendeColuna();
                            fezUmQuadrado = true;
                            break;
                        }
                    }
                }
                if (i < 5 && qntLinhasAcesas < 30) { // se for == 5 vai acessar memoria que nao existe
                    if(linhas[i][j].getEstaAcesa() == false){
                        if (verificaSeDeuQuadrado(i, j, false, true)) {
                            qntLinhasAcesas++;
                            linhas[i][j].acendeLinha();
                            fezUmQuadrado = true;
                            break;
                        }
                    }
                }
            }
            if(fezUmQuadrado)break;
        }

        if(fezUmQuadrado)fazAjogadaDeBotHard();
        else fazAjogadaDeBotEasy();
    }

    // logica jogo
    public void olhaCoordenadas() throws FileNotFoundException {
        File getCSVFiles = new File("./assets/coordenadas.csv");
        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter(";|\\n");
        int ponto = 0, coluna = 0, linha = 0;
        int linhaLinha = 0, colunaLinha = 0; // linhas e colunas da matriz LINHA[][]
        int linhaColuna = 0, colunaColuna = 0; // linhas e colunas da matriz COLUNA[][]
        int linhaPonto = 0, colunaPonto = 0;// linhas e colunas da matriz PONTO[][]
        String token = "";
        while (sc.hasNext()) {
            token = sc.next();
            if ("Ponto".equals(token)) {
                pontos[linhaPonto][colunaPonto] = new Ponto(ponto);
                ponto++;
                if (linhaPonto == 5) {
                    linhaPonto = 0;
                    colunaPonto++;
                } else {
                    linhaPonto++;
                }
            } else if ("Linha".equals(token)) {
                linhas[linhaLinha][colunaLinha] = new Linha(linha);
                linha++;
                if (linhaLinha == 4) {
                    linhaLinha = 0;
                    colunaLinha++;
                } else {
                    linhaLinha++;
                }
            } else if ("Coluna".equals(token)) {
                colunas[linhaColuna][colunaColuna] = new Coluna(coluna);
                coluna++;
                if (linhaColuna == 5) {
                    linhaColuna = 0;
                    colunaColuna++;
                } else {
                    linhaColuna++;
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

    public ArrayList<Quadrado> getQuadrados() {
        return quadrados;
    }

    public void setBotJogou(boolean botJogou) {
        this.botJogou = botJogou;
    }
}