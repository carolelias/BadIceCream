/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Desenho;

/**
 *
 * @author carolina elias
 */

public abstract class PersonagemDinamico extends Personagem {
    
    public PersonagemDinamico(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }
    
    public void autoDesenho(){
        super.autoDesenho();
    }

    
     public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    
    
    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }
   
    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    
    public boolean moveUp() {
        if(this.pPosicao.moveUp())
            return validaPosicao();
        return false;
    }

    public boolean moveDown() {
        if(this.pPosicao.moveDown())
            return validaPosicao();
        return false;
    }

    public boolean moveRight() {
        if(this.pPosicao.moveRight())
            return validaPosicao();
        return false;
    }

    public boolean moveLeft() {
        if(this.pPosicao.moveLeft())
            return validaPosicao();
        return false;
    }
    

}
