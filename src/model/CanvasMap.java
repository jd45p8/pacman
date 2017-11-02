/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.tableroController;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 *
 * @author jd45
 */
public class CanvasMap extends Canvas {

    public Thread drawThread;
    /**
     * Tamaño de los bloques en x
     */
    public static int tamañoX;

    /**
     * Tamaño de los bloques en y
     */
    public static int tamañoY;

    public CanvasMap(JFrame padre) {
        this.setSize(padre.getWidth(), padre.getHeight() - 29);
        padre.add(this);
        padre.setVisible(true);
        this.addDrawMapa(this);
    }

    /**
     * Añade el hilo para el dibujado del mapa
     */
    public void addDrawMapa(Canvas canvas) {
        drawThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    canvas.createBufferStrategy(2);
                    Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
                    while (true) {
                        int[][] mapa = tableroController.mapas.get(tableroController.level);
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        
                        g.setColor(Color.BLUE);
                        g.fillRect(0, tableroController.top, canvas.getWidth(), canvas.getHeight() - tableroController.top - tableroController.down);

                        int cant = 0;
                        while (true) {
                            try {
                                if (mapa[cant][0] != Integer.MAX_VALUE) {
                                    cant++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                break;
                            }
                        }
                        tamañoX = canvas.getWidth() / cant;
                        tamañoY = (canvas.getHeight() - tableroController.top - tableroController.down) / mapa[0].length;
                        for (int i = 0; i < mapa[0].length; i++) {
                            for (int j = 0; j < cant; j++) {
                                if (mapa[j][i] == 0) {
                                    g.setColor(Color.BLACK);
                                } else {
                                    g.setColor(Color.BLUE);
                                }
                                g.fillRect(j * tamañoX, tableroController.top + i * tamañoY, tamañoX, tamañoY);

                            }
                        }
                        canvas.getBufferStrategy().show();
                        Thread.sleep(20);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }
}
