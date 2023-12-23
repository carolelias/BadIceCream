/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import java.util.Random;

/**
 *
 * @author carolina elias
 */

// Fruta que anda aleatoriamente
public class Morango extends Fruta{
    private int contador;
    
    public Morango(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.contador = 0;
    }
    
    @Override
    public void autoDesenho(){
        if(!this.pause){
            contador ++;
        
            if(contador == Consts.VEL_MELANCIA) {
                contador = 0;
                
                //sorteia a direção
                Random random = new Random();
                int direcao = random.nextInt(4); // 0: cima, 1: baixo, 2: esquerda, 3: direita

                int tentativas = 2;
                for(int i = 0; i < tentativas; i++) {
                    switch (direcao) {
                        case 0:
                            if(this.moveUp()) {
                                super.autoDesenho();
                                return;
                            }    
                        case 1:
                            if(this.moveDown()) {
                                super.autoDesenho();
                                return;
                            } 
                        case 2:
                            if(this.moveLeft()) {
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
