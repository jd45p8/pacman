/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import view.Menu;
import view.Tablero;

/**
 *
 * @author jd45
 */
public class menuController {

    /**
     * Es la variable que contiene un apuntador a la ventana de inicio del
     * juego.
     */
    public static Menu menu;

    /**
     * Es la opción seleccionada por el usuario en el menú
     */
    public static int optionSelected = 1;

    /**
     * Es la cantidad de opciones que hay en el menú.
     */
    public static int numOptions;

    /**
     * Cambia la opción mostrada en el menú por la seleccionada por el usuario
     * usando el teclado.
     *
     * @param evt Contiene la información del evento activado.
     */
    public static void changeOptionDisplayed(KeyEvent evt, ArrayList<JLabel> options) {
        numOptions = options.size();
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (optionSelected == 1) {
                optionSelected = numOptions;
            } else {
                optionSelected--;
            }
            for (JLabel option : options) {
                option.setText("");
            }
            options.get(optionSelected - 1).setText(">");
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (optionSelected == numOptions) {
                optionSelected = 1;
            } else {
                optionSelected++;
            }
            for (JLabel option : options) {
                option.setText("");
            }
            options.get(optionSelected - 1).setText(">");
        }
    }

    /**
     * Inicia o cierra el juego dependiendo de la opción seleccionada.
     *
     * @param evt Es el evento que contiene la tecla presionada.
     * @param parent Es el Menu desde donde se solicita el inicio del juego.
     */
    public static void launchOption(KeyEvent evt, Menu parent) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (optionSelected) {
                case 1:
                    parent.setVisible(false);
                    tableroController.tablero = new Tablero();
                    break;
                default:
                    menu.dispose();
            }
        }
    }

    /**
     * Es el reproductor de la pista de audio del juego.
     */
    public static Clip gameSound;

    public static Thread GameMusic = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                AudioInputStream as1 = AudioSystem.getAudioInputStream(Menu.class.getClassLoader().getResourceAsStream("extras/Pac-man theme remix - By Arsenic1987.wav"));
                gameSound = AudioSystem.getClip();
                gameSound.open(as1);
                gameSound.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    });
}
