package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Consts;
import auxiliar.Posicao;
import java.util.Random;
import java.util.ArrayList;

// Monstro que tenta manter um movimento regular, ou seja, andar na mesma direção que estava andando antes
public class GreenTroll extends Monster{
    private int contador;  // contador para regular a velocidade do monstro
    
    public GreenTroll(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.contador = 0;
    }
    
    @Override
    public void autoDesenho(){  
        
        if(!this.pause){
            contador ++;

            if(contador == Consts.MONSTER_SPEED) {
                contador = 0; // zera o contador
                int direcao;

                // verifica a última direção para a qual o monstro andou
                if(this.pPosicao.getColunaAnterior() == this.pPosicao.getColuna()) {
                    if(this.pPosicao.getLinhaAnterior() > this.pPosicao.getLinha())
                        direcao = 0;  //cima
                    else
                        direcao = 2;    // baixo
                } else {
                    if(this.pPosicao.getColunaAnterior() > this.pPosicao.getColuna())
                        direcao = 1;    // esquerda
                    else
                        direcao = 3;   // direita     
                }

                int tentativas = 2;
                for(int i = 0; i < tentativas; i++) {
                    switch (direcao) {
                        case 0:
                            if(this.moveUp()) {
                                super.autoDesenho();
                                return;
                            }    
                        case 1:
                            if(this.moveLeft()) {
                                super.autoDesenho();
                                return;
                            }
                        case 2:
                            if(this.moveDown()) {
                                super.autoDesenho();
                                return;
                            } 
                        case 3:
                            if(this.moveRight()){
                                super.autoDesenho();
                                return;
                            }
                            else 
                                direcao = 0;
                        default:
                    }
                }
            }
        }  
        super.autoDesenho();
    }
}