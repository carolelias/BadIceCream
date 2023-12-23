package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


// herói do jogo
public class IceCream extends PersonagemDinamico implements Serializable{
    public IceCream(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }   
}
