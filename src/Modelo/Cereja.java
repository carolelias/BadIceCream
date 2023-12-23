/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author carolina elias
 */

// Fruta que foge do herói
public class Cereja extends Fruta implements Serializable{
    private IceCream hero;  // Herói do qual a cereja irá fugir
    private int contador;   // contador para regular a velocidade da cereja
    
    public Cereja(String sNomeImagePNG, IceCream hero) {
        super(sNomeImagePNG);
        this.hero = hero;
    }
    
    @Override
    public void autoDesenho(){     
        if(!this.pause){
            contador++;
        
            if(contador == Consts.VEL_CEREJA){
                contador = 0;

                int distanciaVertical = this.pPosicao.getLinha() - hero.pPosicao.getLinha();    // distancia vertical entre o heroi e o monstro
                int distanciaHorizontal = this.pPosicao.getColuna() - hero.pPosicao.getColuna();    // distancia horizontal entre o heroi e o monstro


                boolean andou = false;
                if(Math.abs(distanciaVertical)< Math.abs(distanciaHorizontal)) {
                    if(distanciaVertical <= 0) {
                        andou = this.moveUp();
                    } 
                    if(distanciaVertical >= 0 && !andou){
                        andou = this.moveDown();
                    }
                }

                if(!andou) {
                    if(distanciaHorizontal <= 0) {
                        andou = this.moveLeft();
                    } if(distanciaHorizontal >= 0 && !andou){
                        andou = this.moveRight();
                    }
                }

                if(!andou) {
                    if(distanciaVertical <= 0) {
                        andou = this.moveUp();
                    } 
                    if(distanciaVertical >= 0 && !andou){
                        this.moveDown();
                    }
                }    
            }
        }

        super.autoDesenho();
    }
}
