/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author agnes
 */
public abstract class Monster extends PersonagemDinamico{
    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal = true;
    }
}
