package Controler;

import Modelo.Personagem;
import Modelo.Narwhals;
import Modelo.Spiral;
import Modelo.IceCream;
import Modelo.Cereja;
import Modelo.Banana;
import Modelo.Uva;
import Modelo.Ice;
import Modelo.Morango;
import Modelo.Kiwi;
import Modelo.Save;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.GreenTroll;
import Modelo.BlueTroll;
import Modelo.WhiteTroll;
import Modelo.Narwhals;
import Modelo.Fase;
import auxiliar.Posicao;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private IceCream hero;
    private Fase faseAtual;
    private int subfaseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int fase;
    private boolean proxFase;
    

    public Tela() {
        this.proxFase = false;
        Desenho.setCenario(this);
        initComponents();
        this.addKeyListener(this);   /*teclado*/
        this.addMouseListener(this); /*mouse*/
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        faseAtual = new Fase();
        faseAtual.array = new ArrayList<Personagem>();
        faseAtual.numSubfases = 3;
        this.fase = 0;
        
        /*Cria faseAtual adiciona personagens*/
        hero = new IceCream("icecream.png");
        
        setFase();

        System.out.format("\nSejam bem vindos ao jogo BAD ICE CREAM!! - Desenvolvido por Agnes Bressan e Carolina Elias\n\n");
        System.out.format("Informacoes sobre o jogo:\n");
        System.out.format("- O objetivo do jogo eh capturar todas as frutas distribuidas no mapa sem que seja atingido pelos monstros\n");
        System.out.format("- Caso seja atingido por um deles, a fase reiniciara, caso consiga voce sera direcionado a proxima fase\n");
        System.out.format("- Para movimentar o seu heroi use as teclas das setas\n");
        System.out.format("- Caso queira reiniciar a fase eh so apertar a tecla R a qualque momento\n");
        System.out.format("- Caso queira pausar a fase eh so apertar a tecla P a qualque momento\n");
        System.out.format("- Para salvar seu progresso no jogo clique na tecla S, ao abrir o jogo voce podera retomar os seu progresso\n\n");
    }
    
    public boolean saveGame(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            //salva a fase e os personagens atuais que o usuário está no arquivo de jogo salvo
            Save salvarProgresso = new Save(faseAtual.array, fase, subfaseAtual);
            out.writeObject(salvarProgresso);
            System.out.format("\nJogo salvo!!");
            System.out.format("\nProgresso atual\nFASE %d\nSUBFASE %d\n", (fase+1), (subfaseAtual+1));
            
        } catch (FileNotFoundException ex) {
            logAndPrintError("Erro ao salvar o jogo: arquivo não encontrado", ex);
            return false;
        } catch (IOException ex) {
            logAndPrintError("Erro ao salvar o jogo", ex);
            return false;
        }
        return true;
    }
    
    public boolean load(String filename) {
        File saveGameFile = new File(filename);

        if (saveGameFile.exists()) {
            try (FileInputStream fileIn = new FileInputStream(saveGameFile);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

                //lê o arquivo que o jogo foi salvo e atualiza o jogo qual fase o usuario parou e quais elementos do jogo existiam
                Save progressoSalvo = (Save) in.readObject();
                this.faseAtual.array = progressoSalvo.getPersonagens();
                this.fase = progressoSalvo.getFase();
                this.subfaseAtual = progressoSalvo.getsubFase();
                System.out.format("\nProgresso de jogo restaurado");
                System.out.format("\nProgresso atual\nFASE %d\nSUBFASE %d\n", (fase+1), (subfaseAtual+1));

            } catch (IOException | ClassNotFoundException ex) {
                logAndPrintError("Erro ao carregar o jogo", ex);
                return false;
            }
            return true;
            
        } else {     
            System.out.format("\nSem jogos salvos!!");
            System.out.format("\nNovo jogo\n");
            return false;
        }
    }

    private void logAndPrintError(String message, Exception ex) {
        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, message, ex);
        System.out.println("Erro: " + message);
    }

    public void setFase(){
        if(this.fase == 0){
            setFase0();
        }
        if(this.fase == 1){
            setFase1();
        }
        if(this.fase== 2){
            setFase2();
        }
        if(this.fase == 3){
            setFase3();
        }
    }
    
    
    public void setFase0(){
        faseAtual.matriz = new int[][]{
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1},
            {-1,    0,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      0,     0,     5,     -1},
            {-1,    2,      0,     11,      0,     11,      0,     11,      0,      11,      0,     11,      0,    10,     2,     -1},
            {-1,    0,      1,      1,      0,      0,      0,      0,      0,       0,      0,      0,      1,     1,     0,     -1},
            {-1,    2,      1,     -1,     -1,     -1,      0,      0,      0,       0,     -1,     -1,     -1,     1,     2,     -1},
            {-1,    0,      0,     -1,      0,      0,      1,      0,      0,       1,      0,      0,     -1,     0,     0,     -1},
            {-1,    2,      0,     -1,      0,      0,     -1,     -1,     -1,      -1,      0,      0,     -1,     0,     2,     -1},
            {-1,    0,      0,     -1,      0,      1,     -1,     -1,     -1,      -1,      1,      0,     -1,     0,     0,     -1},
            {-1,    2,      0,     -1,      0,      1,     -1,     -1,     -1,      -1,      1,      0,     -1,     0,     2,     -1},
            {-1,    0,      0,     -1,      0,      0,     -1,     -1,     -1,      -1,      0,      0,     -1,     0,     0,     -1},
            {-1,    2,      0,     -1,      5,      0,      1,      0,      0,       1,      0,      0,     -1,     0,     2,     -1},
            {-1,    0,      1,     -1,     -1,     -1,      0,      0,      0,       0,     -1,     -1,     -1,     1,     0,     -1},
            {-1,    2,      1,      1,      0,      0,      0,      0,      0,       0,      0,      0,      1,     1,     2,     -1},
            {-1,    0,      7,      0,     11,      0,     11,      0,     11,       0,     11,      0,     11,     0,     0,     -1},
            {-1,    2,      0,      0,      0,      0,      0,      0,      0,       0,      0,      0,      0,     0,     2,     -1},
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,     -1}
        };

        Ice bloco[] = new Ice[256]; //index -1  
        Banana banana[] = new Banana[20]; //index 1

        GreenTroll greentroll[] = new GreenTroll[20]; //index 4
        Narwhals narwhals[] = new Narwhals[20];
 
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                if(this.subfaseAtual == 0){
                    this.addPersonagem(hero);
                    hero.setPosicao(1, 3);
                    
                    if(faseAtual.matriz[i][j] == -1){
                        bloco[i] = new Ice("ice.png");
                        bloco[i].setPosicao(i,j);
                        this.addPersonagem(bloco[i]);
                    }


                    if(faseAtual.matriz[i][j] == 1){
                        banana[i] = new Banana("banana.png");
                        banana[i].setPosicao(i, j);
                        this.addPersonagem(banana[i]);
                    }

                    if(faseAtual.matriz[i][j] == 5){
                        greentroll[i] = new GreenTroll("greentroll.png");
                        greentroll[i].setPosicao(i, j);
                        this.addPersonagem(greentroll[i]);
                    }

                    if(faseAtual.matriz[i][j] == 7){
                        narwhals[i] = new Narwhals("narwhals.png");
                        narwhals[i].setPosicao(i, j);
                        this.addPersonagem(narwhals[i]);
                    }

                    if(faseAtual.matriz[i][j] == 10){
                        narwhals[i] = new Narwhals("narwhals.png", "esquerda");
                        narwhals[i].setPosicao(i, j);
                        this.addPersonagem(narwhals[i]);
                    }
                }
                
                else if(this.subfaseAtual == 1){
                    Kiwi kiwi[] = new Kiwi[20]; //index 11
                    if(faseAtual.matriz[i][j] == 11){
                        kiwi[i] = new Kiwi("kiwi.png");
                        kiwi[i].setPosicao(i, j);
                        this.addPersonagem(kiwi[i]);
                    } 
                }

                else if(this.subfaseAtual == 2) {
                    Uva uva[] = new Uva[20]; //index 2

                    if(faseAtual.matriz[i][j] == 2){
                        uva[i] = new Uva("grape.png", faseAtual.array);
                        uva[i].setPosicao(i, j);
                        this.addPersonagem(uva[i]);
                    }          
                }
            }
        }        

    }
    
    public void setFase1(){
        faseAtual.matriz = new int[][]{
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1},
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1},
            {-1,   -1,     -1,      9,      0,      3,      0,      0,      0,       0,      3,      0,      8,    -1,    -1,    -1},
            {-1,   -1,     -1,      4,     -1,      0,     -1,      1,      1,      -1,      0,     -1,      4,    -1,    -1,    -1},
            {-1,   -1,     -1,      2,      1,      0,      3,      3,      3,       3,      0,      1,      2,    -1,    -1,    -1},
            {-1,   -1,     -1,      1,     -1,      1,     -1,     -1,     -1,      -1,      1,     -1,      1,    -1,    -1,    -1},
            {-1,   -1,     -1,      2,      1,      0,     -1,     -1,     -1,      -1,      0,      1,      2,    -1,    -1,    -1},
            {-1,   -1,     -1,      1,     -1,      1,     -1,     -1,     -1,      -1,      1,     -1,      1,    -1,    -1,    -1},
            {-1,   -1,     -1,      2,      1,      0,     -1,     -1,     -1,      -1,      0,      1,      2,    -1,    -1,    -1},
            {-1,   -1,     -1,      1,     -1,      1,      3,      3,      3,       3,      1,     -1,      1,    -1,    -1,    -1},
            {-1,   -1,     -1,      2,      1,      0,      0,      0,      0,       0,      0,      1,      2,    -1,    -1,    -1},
            {-1,   -1,     -1,      4,     -1,      0,     -1,      1,      1,      -1,      0,     -1,      4,    -1,    -1,    -1},
            {-1,   -1,     -1,     -1,      8,      3,      0,      0,      0,       0,      3,      9,     -1,    -1,    -1,    -1},
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1},
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1},
            {-1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,     -1,     -1,    -1,    -1,    -1}
        };  

        Ice bloco[] = new Ice[256]; //index -1
        Narwhals nW[] = new Narwhals[256]; //index -1
        Banana banana[] = new Banana[20]; //index 1
        GreenTroll greentroll[] = new GreenTroll[20]; //index 4
        BlueTroll bluetroll[] = new BlueTroll[20]; //index 4
        WhiteTroll whitetroll[] = new WhiteTroll[20];

        
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                if(this.subfaseAtual == 0){
                    this.addPersonagem(hero);
                    hero.setPosicao(2, 7);
                    
                    if(faseAtual.matriz[i][j] == -1){
                        bloco[i] = new Ice("ice.png");
                        bloco[i].setPosicao(i,j);
                        this.addPersonagem(bloco[i]);
                    }

                    if(faseAtual.matriz[i][j] == 1){
                        banana[i] = new Banana("banana.png");
                        banana[i].setPosicao(i, j);
                        this.addPersonagem(banana[i]);
                    }

                    if(faseAtual.matriz[i][j] == 8){
                        whitetroll[i] = new WhiteTroll("whitetroll.png", hero);
                        whitetroll[i].setPosicao(i,j);
                        this.addPersonagem(whitetroll[i]);
                    }

                    if(faseAtual.matriz[i][j] == 9){
                        whitetroll[i] = new WhiteTroll("whitetroll.png", hero, true);
                        whitetroll[i].setPosicao(i,j);
                        this.addPersonagem(whitetroll[i]);
                    }
                }

                else if(this.subfaseAtual == 1){
                    Morango morango[] = new Morango[20]; //index 2
                    if(faseAtual.matriz[i][j] == 2){
                        morango[i] = new Morango("morango.png");
                        morango[i].setPosicao(i, j);
                        this.addPersonagem(morango[i]);
                    }
                }

                else if(this.subfaseAtual == 2){
                    Uva uva[] = new Uva[20]; //index 2
                    if(faseAtual.matriz[i][j] == 2){
                        uva[i] = new Uva("grape.png", faseAtual.array);
                        uva[i].setPosicao(i, j);
                        this.addPersonagem(uva[i]);
                    }
                }
            }
        }
    }
    
    public void setFase2(){

        faseAtual.matriz = new int[][]{
            {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,    -1,    -1,    -1},
            {-1,     1,      0,      1,      0,      1,      0,     -1,     -1,      3,      3,       3,      3,     3,     3,    -1},
            {-1,     0,      1,      0,      1,      0,      1,     -1,     -1,      0,      0,       0,      0,     0,     0,    -1},
            {-1,     1,      0,      1,      0,      1,      0,     -1,     -1,      0,      0,       0,      0,     0,     0,    -1},
            {-1,     0,      1,      0,      1,      0,      1,      0,      0,      0,      0,       0,      0,     0,     0,    -1},
            {-1,     1,      0,      1,      0,     -1,     -1,      0,      0,     -1,     -1,       0,      0,     0,     0,    -1},
            {-1,     0,      1,      0,      1,     -1,     -1,      6,      0,     -1,     -1,       0,      0,     0,     0,    -1},
            {-1,    -1,     -1,     -1,      0,     -1,     -1,      0,      0,     -1,     -1,       0,     -1,    -1,    -1,    -1},
            {-1,     4,      0,      0,      0,     -1,     -1,      0,      0,     -1,     -1,       1,      0,     1,     4,    -1},
            {-1,     0,      0,      0,      0,     -1,     -1,      0,      0,     -1,     -1,       0,      1,     0,     1,    -1},
            {-1,     0,      0,      0,      0,      0,      0,      0,      0,      1,      0,       1,      0,     1,     0,    -1},
            {-1,     0,      0,      0,      0,      0,      0,     -1,     -1,      0,      1,       0,      1,     0,     1,    -1},
            {-1,     0,      0,      0,      0,      0,      0,     -1,     -1,      1,      0,       1,      0,     1,     0,    -1},
            {-1,     3,      3,      3,      3,      3,      3,     -1,     -1,      0,      1,       0,      1,     0,     1,    -1},
            {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,    -1,    -1,    -1},
            {-1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,    -1,    -1,    -1}
        };

        Ice bloco[] = new Ice[256]; //index -1
        Narwhals nW[] = new Narwhals[256]; //index -1

        Banana banana[] = new Banana[20]; //index 1

        GreenTroll greentroll[] = new GreenTroll[20]; //index 4
        BlueTroll bluetroll[] = new BlueTroll[20]; //index 4
        WhiteTroll whitetroll[] = new WhiteTroll[20];


        
        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){

                if(this.subfaseAtual == 0){
                    this.addPersonagem(hero);
                    hero.setPosicao(13, 2);

                    if(faseAtual.matriz[i][j] == -1){
                        bloco[i] = new Ice("ice.png");
                        bloco[i].setPosicao(i,j);
                        this.addPersonagem(bloco[i]);
                    }

                    if(faseAtual.matriz[i][j] == 1){
                        banana[i] = new Banana("banana.png");
                        banana[i].setPosicao(i, j);
                        this.addPersonagem(banana[i]);
                    }

                    if(faseAtual.matriz[i][j] == 5){
                        greentroll[i] = new GreenTroll("greentroll.png");
                        greentroll[i].setPosicao(i, j);
                        this.addPersonagem(greentroll[i]);
                    }

                    if(faseAtual.matriz[i][j] == 6){
                        bluetroll[i] = new BlueTroll("bluetroll.png", hero);
                        bluetroll[i].setPosicao(i, j);
                        this.addPersonagem(bluetroll[i]);
                    }

                    if(faseAtual.matriz[i][j] == 7){
                        nW[i] = new Narwhals("narwhals.png");
                        nW[i].setPosicao(i,j);
                        this.addPersonagem(nW[i]);
                    }

                    if(faseAtual.matriz[i][j] == 8){
                        whitetroll[i] = new WhiteTroll("whitetroll.png", hero);
                        whitetroll[i].setPosicao(i,j);
                        this.addPersonagem(whitetroll[i]);
                    }
                }

                else if(this.subfaseAtual == 1){
                    Morango morango[] = new Morango[20]; //index 3
                    if(faseAtual.matriz[i][j] == 3){
                        morango[i] = new Morango("morango.png");
                        morango[i].setPosicao(i, j);
                        this.addPersonagem(morango[i]);     
                    }
                }

                else if(this.subfaseAtual == 2){
                    Cereja cereja[] = new Cereja[20];
                    if(faseAtual.matriz[i][j] == 4){
                        cereja[i] = new Cereja("cereja.png", hero);
                        cereja[i].setPosicao(i, j);
                        this.addPersonagem(cereja[i]);
                    }
                }
            }
        }
    }
    
    public void setFase3(){
        
        faseAtual.matriz = new int[][]{
            {4,     0,      0,      0,      0,      0,      0,      0,      0,      0,      0,       0,      0,     0,     4,     7},
            {0,    -1,      2,     -1,      2,     -1,      2,     -1,      2,     -1,      2,      -1,      2,    -1,     2,     -1},
            {0,     2,      1,     -1,      0,     -1,      0,     -1,      0,     -1,      0,      -1,      0,    -1,     0,     -1},
            {0,    -1,     -1,     -1,      2,     -1,      2,     -1,      2,     -1,      2,      -1,      2,    -1,     2,     -1},
            {0,     2,      0,      2,      1,     -1,      0,     -1,      0,     -1,      0,      -1,      0,    -1,     0,     -1},
            {0,    -1,     -1,     -1,     -1,     -1,      2,     -1,      2,     -1,      2,      -1,      2,    -1,     2,     -1},
            {0,     2,      0,      2,      0,      2,      1,     -1,      0,     -1,      0,      -1,      0,    -1,     0,     -1},
            {0,    -1,     -1,     -1,     -1,     -1,     -1,     -1,      2,     -1,      2,      -1,      2,    -1,     2,     -1},
            {0,     2,      0,      2,      0,      2,      0,      2,      1,     -1,      0,      -1,      0,    -1,     0,     -1},
            {0,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      2,      -1,      2,    -1,     2,     -1},
            {0,     2,      0,      2,      0,      2,      0,      2,      0,      2,      1,      -1,      0,    -1,     0,     -1},
            {0,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,      2,    -1,     2,     -1},
            {0,     2,      0,      2,      0,      2,      0,      2,      0,      2,      0,       2,      1,    -1,     0,     -1},
            {0,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,    -1,     2,     -1},
            {4,     2,      0,      2,      0,      2,      0,      2,      0,      2,      0,       2,      0,     2,     1,     -1},
            {8,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,     -1,    -1,    -1,     -1}
        };

        Ice bloco[] = new Ice[256]; //index -1

        Banana banana[] = new Banana[20];

        Narwhals narwhals[] = new Narwhals[20]; //index 4
        BlueTroll bluetroll[] = new BlueTroll[20]; //index 4


        //Heroi --> index 9, diamond --> index 7
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){

                if(this.subfaseAtual == 0){
                    this.addPersonagem(hero);
                    hero.setPosicao(14, 14);

                    if(faseAtual.matriz[i][j] == -1){
                        bloco[i] = new Ice("ice.png");
                        bloco[i].setPosicao(i,j);
                        this.addPersonagem(bloco[i]);
                    }

                    if(faseAtual.matriz[i][j] == 2){
                                banana[i] = new Banana("banana.png");
                                banana[i].setPosicao(i, j);
                                this.addPersonagem(banana[i]);
                    }

                    if(faseAtual.matriz[i][j] == 6){
                        bluetroll[i] = new BlueTroll("bluetroll.png", hero);
                        bluetroll[i].setPosicao(i, j);
                        this.addPersonagem(bluetroll[i]);
                    }

                    if(faseAtual.matriz[i][j] == 7){
                        narwhals[i] = new Narwhals("narwhals.png", "esquerda");
                        narwhals[i].setPosicao(i,j);
                        this.addPersonagem(narwhals[i]);
                    }

                    if(faseAtual.matriz[i][j] == 8){
                        narwhals[i] = new Narwhals("narwhals.png", "cima");
                        narwhals[i].setPosicao(i,j);
                        this.addPersonagem(narwhals[i]);
                    }
                }

                else if(this.subfaseAtual == 1){
                    Uva uva[] = new Uva[20]; //index 2
                    if(faseAtual.matriz[i][j] == 1){
                        uva[i] = new Uva("grape.png", faseAtual.array);
                        uva[i].setPosicao(i, j);
                        this.addPersonagem(uva[i]);
                    }
                }

                else if(this.subfaseAtual == 2) {
                    Cereja cereja[] = new Cereja[20];
                    if(faseAtual.matriz[i][j] == 4){
                        cereja[i] = new Cereja("cereja.png", hero);
                        cereja[i].setPosicao(i, j);
                        this.addPersonagem(cereja[i]);
                    }
                }
            }
        }
    }
    
    

    public boolean ehPosicaoValida(Posicao p){
        return cj.ehPosicaoValida(this.faseAtual.array, p);
    }
    
    public void addPersonagem(Personagem umPersonagem) {
        faseAtual.array.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.array.remove(umPersonagem);
    }

    public Graphics getGraphicsBuffer(){
        return g2;
    }
    
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        switch(this.cj.gameState){
            case 1: //play game
                for (int i = 0; i < Consts.RES; i++) {
                    for (int j = 0; j < Consts.RES; j++) {
                        try {
                            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "map.png");
                            g2.drawImage(newImage,
                                    j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                        } catch (IOException ex) {
                            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (!faseAtual.array.isEmpty()) {
                    int aux = this.cj.processaTudo(faseAtual.array);
                    this.cj.desenhaTudo(faseAtual.array, false);
                    
                    if(aux == 1){
                        this.subfaseAtual++;
                        if(this.subfaseAtual >= faseAtual.numSubfases){ //entrou aqui
                            if(this.fase == 3) {
                                this.cj.gameState = this.cj.fimState;
                            }
                            else{
                                this.cj.gameState = this.cj.proxFaseState; 
                            }
                              
                        } else {
                            setFase();                     
                        }

                    }
                    else if(aux == -1){
                        this.subfaseAtual = 0;
                        this.cj.setFase(fase);
                        faseAtual.array.clear();
                           //hero.resetaAllHero();
                        setFase();
                    }
                }
                break;
                
            case 2: //pause game
                for (int i = 0; i < Consts.RES; i++) {
                    for (int j = 0; j < Consts.RES; j++) {
                        try {
                            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "map.png");
                            g2.drawImage(newImage,
                                    j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                        } catch (IOException ex) {
                            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (!faseAtual.array.isEmpty()) {
                    this.cj.desenhaTudo(faseAtual.array, true);                 
                    try{
                        Image img = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "popup_pause.png");
                        g2.setFont(new Font("Arial", Font.BOLD, 40));
                        g2.setColor(Color.BLACK);

                        FontMetrics metrics = g2.getFontMetrics();

                        // Determinar as coordenadas para a imagem
                        int larguraImagem = img.getWidth(this);
                        int alturaImagem = img.getHeight(this);

                        int x = (getWidth() - larguraImagem) / 2;
                        int y = (getHeight() - alturaImagem) / 2 + metrics.getAscent();

                        // Desenhar a imagem no centro
                        g2.drawImage(img, x, y, this);
                    } catch(IOException ex){
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
            case 3: //ini game
                try {
                Image imagemDeFundo = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Consts.PATH + "tela_inicio.png");
                g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            
            case 4: //prox fase game
                for (int i = 0; i < Consts.RES; i++) {
                    for (int j = 0; j < Consts.RES; j++) {
                        try {
                            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "map.png");
                            g2.drawImage(newImage,
                                    j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                        } catch (IOException ex) {
                            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (!faseAtual.array.isEmpty()) {
                    this.cj.desenhaTudo(faseAtual.array, true);                 
                    try{
                        Image img = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "popup_next.png");
                        g2.setFont(new Font("Arial", Font.BOLD, 40));
                        g2.setColor(Color.BLACK);

                        FontMetrics metrics = g2.getFontMetrics();

                        // Determinar as coordenadas para a imagem
                        int larguraImagem = img.getWidth(this);
                        int alturaImagem = img.getHeight(this);

                        int x = (getWidth() - larguraImagem) / 2;
                        int y = (getHeight() - alturaImagem) / 2 + metrics.getAscent();

                        // Desenhar a imagem no centro
                        g2.drawImage(img, x, y, this);
                    } catch(IOException ex){
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            
            case 5: //fim game
                try {
                Image imagemDeFundo = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Consts.PATH + "tela_fim.png");
                g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }
    

    public void keyPressed(KeyEvent e) {
        IceCream hero = (IceCream) this.faseAtual.array.get(0);
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_S:
                saveGame("saveProgress.ser");
                break;
                
            case KeyEvent.VK_C:
                this.faseAtual.array.clear();
                break;
                
            case KeyEvent.VK_R:
                if(this.cj.gameState == this.cj.proxFaseState){
                    this.cj.gameState = this.cj.playState;
                    this.subfaseAtual = 0;
                    this.cj.setFase(fase);
                    faseAtual.array.clear();
                    setFase();
                } else{
                    this.subfaseAtual = 0;
                    this.cj.setFase(fase);
                    faseAtual.array.clear();
                    setFase();
                }
                break;
                
            case KeyEvent.VK_N:
                if(this.cj.gameState == this.cj.iniState){
                    this.cj.gameState = this.cj.playState;
                }
                break;
                
            case KeyEvent.VK_L:
                if(this.cj.gameState == this.cj.iniState){
                    this.cj.gameState = this.cj.playState;
                    load("saveProgress.ser");
                }
                break;
                
            case KeyEvent.VK_P:
                if(this.cj.gameState == this.cj.playState){
                    this.cj.gameState = this.cj.pauseState;
                } else{
                    this.cj.gameState = this.cj.playState;
                }
                break;
                
            case KeyEvent.VK_ENTER:
                if(this.cj.gameState == this.cj.proxFaseState){
                    this.cj.gameState = this.cj.playState;
                    this.subfaseAtual = 0;
                    this.fase++;
                    this.cj.setFase(fase);
                    faseAtual.array.clear();
                    setFase();
                }
                break;
                
            case KeyEvent.VK_SPACE:
                if(this.cj.gameState == this.cj.fimState){
                    this.cj.gameState = this.cj.playState;
                    this.subfaseAtual = 0;
                    this.fase = 0;
                    this.cj.setFase(fase);
                    faseAtual.array.clear();
                    setFase();
                }
                break;
                
            case KeyEvent.VK_E:
                if(this.cj.gameState == this.cj.fimState){
                    System.exit(0);
                }
                break;
                
            case KeyEvent.VK_UP:
                if(this.cj.gameState != this.cj.pauseState && this.cj.gameState != this.cj.proxFaseState){
                    hero.changeImg("icecream_back.png");
                    hero.moveUp();
                }
                break;
                
            case KeyEvent.VK_DOWN:
                if(this.cj.gameState != this.cj.pauseState && this.cj.gameState != this.cj.proxFaseState){
                    hero.changeImg("icecream.png");
                    hero.moveDown();
                }
                break;
                
            case KeyEvent.VK_LEFT:
                if(this.cj.gameState != this.cj.pauseState && this.cj.gameState != this.cj.proxFaseState){
                    hero.changeImg("icecream_left.png");
                    hero.moveLeft();
                }
                break;
                
            case KeyEvent.VK_RIGHT:
                if(this.cj.gameState != this.cj.pauseState && this.cj.gameState != this.cj.proxFaseState){
                    hero.changeImg("icecream_right.png");
                    hero.moveRight();
                }
                break;
        }
        
        this.setTitle("-> Cell: " + (hero.getPosicao().getColuna()) + ", "
                + (hero.getPosicao().getLinha()));

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }

    public void mousePressed(MouseEvent e) {
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
