/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author agnes
 */
public class Save implements Serializable {
    private ArrayList<Personagem> personagens;
    private int fase;
    private int subFase;

    public Save(ArrayList<Personagem> personagens, int fase, int subFase) {
        this.personagens = personagens;
        this.fase = fase;
        this.subFase = subFase;
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public int getFase() {
        return fase;
    }
    
    public int getsubFase(){
        return subFase;
    }
}
