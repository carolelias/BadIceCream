package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.util.Random;

// Monstro que persegue o herói
public class BlueTroll extends Monster {
    private int contador;   // contador para regular a velocidade do monstro
    private IceCream hero;  // herói que será perseguido
    
    public BlueTroll(String sNomeImagePNG, IceCream hero) {
        super(sNomeImagePNG);
        this.hero = hero;
        this.contador = 0;
    }
    
    @Override
    public void autoDesenho(){ 
        
        if(!this.pause){
            contador++;
        
            if(contador == Consts.MONSTER_SPEED){
                contador = 0;

                int distanciaVertical = this.pPosicao.getLinha() - hero.pPosicao.getLinha();    // distancia vertical entre o heroi e o monstro
                int distanciaHorizontal = this.pPosicao.getColuna() - hero.pPosicao.getColuna();    // distancia horizontal entre o heroi e o monstro


                boolean andou = false;  // variável para verificar se o monstro já andou (ele só pode andar uma posição por vez)
                
                // se a distância vertical for maior que a horizontal, tenta diminuir ela
                if(Math.abs(distanciaVertical)> Math.abs(distanciaHorizontal)){
                    if(distanciaVertical >= 0) {    // se o herói estiver acima, o monstro anda pra cima
                        andou = this.moveUp();
                    } 
                    if(distanciaVertical <= 0 && !andou){   // se o herói estiver pra baixo o monstro anda pra baixo
                        andou = this.moveDown();
                    }
                }
                
                 // se a distância horizontal for maior ou se a movimentação na vertical tiver sido inválida, o monstro tenta se aprximar do herói na horizontal
                if(!andou) {
                    if(distanciaHorizontal >= 0) {
                        andou = this.moveLeft();    // se o herói estiver à esquerda o monstro anda pra esquerda
                    } if(distanciaHorizontal <= 0 && !andou){   // se o herói estiver à direita o monstro anda pra direita
                        this.moveRight();
                    }
                }
                
                // se a distância horizontal for maior, mas a movimentação nessa direção tiver sido inválida, tenta diminuir a distância vertical
                if(!andou) {
                    if(distanciaVertical >= 0) {
                        andou = this.moveUp();
                    } 
                    if(distanciaVertical <= 0 && !andou){
                        this.moveDown();
                    }
                }
            }
        }

        super.autoDesenho();
    }
}