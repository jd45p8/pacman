/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.controller;

import java.awt.event.KeyEvent;
import javafx.scene.input.KeyCode;

/**
 *
 * @author jd45
 */
public class menuController {
    /**
     * Es la opción seleccionada por el usuario en el menú
     */
    public int optionSelected = 1;
    /**
     * ES la cantidad de opciones que hay en el menú.
     */
    public int numOptions = 2;
    
    /**
     * Cambia la opción mostrada en el menú por la seleccionada por el usuario usando el teclado.
     * @param evt Contiene la información del evento activado.
     */
    public static void changeOptionDisplayed(KeyEvent evt){
        if(KeyCode.CHANNEL_UP.equals(evt.getKeyCode())){
            
        }else if(KeyCode.CHANNEL_DOWN.equals(evt.getKeyCode())){
            
        }
    }
}
