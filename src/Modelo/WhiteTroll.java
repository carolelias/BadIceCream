/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;

/**
 *
 * @author carolina elias
 */

// Anda aleatoriamente
public class WhiteTroll extends Monster{
    private int contador;
    private int muda_direcao;
    private final IceCream hero;
    private boolean dir_horaria = false;
    
    public WhiteTroll(String sNomeImagePNG, IceCream hero) {
        super(sNomeImagePNG);
        this.hero = hero;
        this.contador = 0;
    }
    
    public WhiteTroll(String sNomeImagePNG, IceCream hero, boolean d) {
        super(sNomeImagePNG);
        this.hero = hero;
        this.contador = 0;
        this.dir_horaria = d;
    }
    
    @Override
    public void autoDesenho(){ 
        if(!this.pause){
            contador ++;
        
        
            if(contador == Consts.MONSTER_SPEED) {
                contador = 0;
                int direcao = 0;
                muda_direcao++;


                if(this.pPosicao.getColunaAnterior() == this.pPosicao.getColuna()) {
                    if(this.pPosicao.getLinhaAnterior() > this.pPosicao.getLinha())
                        direcao = 0;
                    else
                        direcao = 2;
                } else {
                    if(this.pPosicao.getColunaAnterior() > this.pPosicao.getColuna())
                        direcao = 1;
                    else
                        direcao = 3;           
                }

                if(muda_direcao == 6) {
                    direcao++;
                    muda_direcao = 0;
                }


                if(!dir_horaria) {
                    for(int i = 0; i < 2; i++) {
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
                else {
                    for(int i = 0; i < 2; i++) {
                        switch (direcao) {
                            case 0:
                                if(this.moveUp()) {
                                    super.autoDesenho();
                                    return;
                                }
                            case 3:
                                if(this.moveRight()){
                                    super.autoDesenho();
                                    return;
                                }
                            case 2:
                                if(this.moveDown()) {
                                    super.autoDesenho();
                                    return;
                                }
                            case 1:
                                if(this.moveLeft()) {
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
        }
        
        super.autoDesenho();
    }
    
}
