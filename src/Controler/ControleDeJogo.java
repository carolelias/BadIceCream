package Controler;

import Modelo.Personagem;
import Modelo.IceCream;
import Modelo.Monster;
import Modelo.Banana;
import Modelo.Spiral;
import Modelo.Fruta;
import auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    protected int fase = 0;
    protected boolean isFinished;
    protected Posicao posicaoEsquerda;
    protected Posicao posicaoDireita;
    protected Posicao posicaoBaixo;
    protected Posicao posicaoCima;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int iniState = 3;
    public final int proxFaseState = 4;
    public final int fimState = 5;
    
    public ControleDeJogo(){
        this.gameState = iniState;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }
    
    public void desenhaTudo(ArrayList<Personagem> e, boolean pause){
        for(int i = 0; i < e.size(); i++){
            e.get(i).ModePause(pause);
            e.get(i).autoDesenho();
        }
    }
    
    public int processaTudo(ArrayList<Personagem> umaFase) {
        IceCream hero = (IceCream) umaFase.get(0);
        Posicao posHeroi = hero.getPosicao();
        
        isFinished = true;
        for(int i = 1; i < umaFase.size(); i++){
            if(hero.getPosicao().igual(umaFase.get(i).getPosicao())){
                if(umaFase.get(i).isbTransponivel()) {
                    if(umaFase.get(i) instanceof Fruta) {
                        umaFase.remove(umaFase.get(i));
                    }
                    else if(umaFase.get(i).bMortal) {
                        return -1;
                    }                     
                }
            }
            if(umaFase.get(i) instanceof Fruta)
                isFinished = false;
        }

        if(isFinished){
            return 1;
        }

        return 0;
    }
    

    
    /*Retorna true se a posicao p é válida para IceCream com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p){
        Personagem pIesimoPersonagem;
        for(int i = 1; i < umaFase.size(); i++){
            pIesimoPersonagem = umaFase.get(i);            
            if(!pIesimoPersonagem.isbTransponivel())
                if(pIesimoPersonagem.getPosicao().igual(p))
                    return false;
        }        
        return true;
    }
}