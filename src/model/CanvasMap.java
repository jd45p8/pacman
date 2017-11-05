/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.tableroController;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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

                        //g.setColor(Color.BLUE);
                        //g.fillRect(tableroController.rigth, tableroController.top, canvas.getWidth() - tableroController.rigth*2, canvas.getHeight() - tableroController.top - tableroController.down);
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

                        tamañoY = (canvas.getHeight() - tableroController.top - tableroController.down) / mapa[0].length;
                        tamañoX = tamañoY;
                        tableroController.rigth = (canvas.getWidth() - tamañoX * cant) / 2;
                        tableroController.extraTop = (canvas.getHeight() - tamañoY * mapa[0].length - tableroController.top - tableroController.down) / 2;

                        for (int i = 0; i < mapa[0].length; i++) {
                            for (int j = 0; j < cant; j++) {
                                if (mapa[j][i] == 0) {
                                    g.setColor(Color.BLACK);
                                } else {
                                    g.setColor(Color.BLUE);
                                }
                                g.fillRect(tableroController.rigth + j * tamañoX, tableroController.top + i * tamañoY + tableroController.extraTop, tamañoX, tamañoY);

                            }
                        }

                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Rockwell", Font.PLAIN, 13 + tamañoY / 4));
                        g.drawString("Score: " + tableroController.score, tableroController.rigth, tableroController.top - 10);
                        g.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 15 + tamañoY / 4));
                        String name = "PAC-MAN";
                        g.drawString(name, canvas.getWidth() - tableroController.rigth - name.length() * (10 + tamañoY / 4), tableroController.top - 12);
                        g.setFont(new Font("Rockwell", Font.PLAIN, 6 + tamañoY / 4));
                        g.drawString("José David Polo", canvas.getWidth() - tableroController.rigth - name.length() * (10 + tamañoY / 4) , tableroController.top - 12 + (3 + tamañoY / 4));
                        g.setFont(new Font("Rockwell", Font.PLAIN, 12 + tamañoY / 4));
                        g.drawString("Lives: " + tableroController.score, tableroController.rigth, canvas.getHeight() - 12);

                        g.setColor(Color.YELLOW);
                        g.fillOval(tableroController.tablero.pacman.position.x,
                                tableroController.tablero.pacman.position.y, tamañoX, tamañoY);
                        canvas.getBufferStrategy().show();
                        Thread.sleep(20);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }
}
