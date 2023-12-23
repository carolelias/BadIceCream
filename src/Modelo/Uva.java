package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Uva extends Fruta implements Serializable{
    private int contador;
    private ArrayList<Personagem> umaFase;

    public Uva(String sNomeImagePNG, ArrayList<Personagem> umaFase) {
        super(sNomeImagePNG);
        this.umaFase = umaFase;
        contador = 0;
    }
    
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p){
        Personagem pIesimoPersonagem;
        for(int i = 1; i < umaFase.size(); i++){
            pIesimoPersonagem = umaFase.get(i);            
            if(!pIesimoPersonagem.isbTransponivel() || pIesimoPersonagem instanceof Fruta)
                if(pIesimoPersonagem.getPosicao().igual(p))
                    return false;
        }        
        return true;
    }
    
    
    public void autoDesenho(){
        if(!this.pause){
            contador++; 
        
            if(this.contador == Consts.UVA_DESLOCA - 2) {
                this.changeImg("grape_brilhante.png");
            }

            if(this.contador == Consts.UVA_DESLOCA - 1) {
                this.changeImg("grape_brilhante1.png");
            }

            if(this.contador == Consts.UVA_DESLOCA) {
                this.changeImg("grape.png");
                contador = 0;
                boolean mudou = false;

                while(!mudou){
                   Random rand = new Random();
                   int i = rand.nextInt(16); 
                   int j = rand.nextInt(16); 

                   Posicao novaPosicao = new Posicao(i, j);
                   if(ehPosicaoValida(this.umaFase, novaPosicao)){
                       this.setPosicao(i, j);
                       mudou = true;
                   }   
                }
            }    
        }
        
        super.autoDesenho();
    }
}
