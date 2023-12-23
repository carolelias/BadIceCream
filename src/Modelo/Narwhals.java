/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author agnes
 */

// Personagem que lança o foqo/spiral que pode matar o herói
public class Narwhals extends Personagem implements Serializable{
    private int iContaIntervalos;
    private String sentido;  // sentido para o qual a spiral é lançada
    
    public Narwhals(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
        this.iContaIntervalos = 0;
        this.sentido = "direita";
    }
    
    public Narwhals(String sNomeImagePNG, String sentido) {
        super(sNomeImagePNG);
        this.sentido = sentido;
        this.bTransponivel = false;
        this.iContaIntervalos = 0;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 0;
            Spiral s = new Spiral("firebol_"+this.sentido+".png", this.sentido);
            
            switch(sentido){
                case "direita":
                    s.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()+1);
                    break;
                case "esquerda":
                    s.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
                    break;
                case "baixo":
                    s.setPosicao(pPosicao.getLinha()+1,pPosicao.getColuna());
                    break;
                case "cima":
                    s.setPosicao(pPosicao.getLinha()-1,pPosicao.getColuna());
                    break;
            }
            
            Desenho.acessoATelaDoJogo().addPersonagem(s);
        }
    }
}