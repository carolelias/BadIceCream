/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author agnes
 */
public class Spiral extends PersonagemDinamico implements Serializable{
    private String sentido = "direita";
    
    public Spiral(String sNomeImagePNG, String sentido) {
        super(sNomeImagePNG);
        this.bMortal = true;
        this.sentido = sentido;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();
        if(!this.pause){
           switch(sentido){
            case "direita":
                if(!this.moveRight())
                    Desenho.acessoATelaDoJogo().removePersonagem(this);
                break;
            case "esquerda":
                if(!this.moveLeft())
                    Desenho.acessoATelaDoJogo().removePersonagem(this);
                break;
            case "cima":
                if(!this.moveUp())
                    Desenho.acessoATelaDoJogo().removePersonagem(this);
                break;
            case "baixo":
                if(!this.moveDown())
                    Desenho.acessoATelaDoJogo().removePersonagem(this);
                break;         
            } 
        }
    }
}
