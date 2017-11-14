/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.menuController;
import controller.tableroController;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    public static int tamX = 26;

    /**
     * Son los sprites del mapa
     */
    public static BufferedImage[] spritesPlayers;

    /**
     * Tamaño de los bloques en y
     */
    public static int tamY = 26;

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
                        for (int i = 0; i < mapa[0].length; i++) {
                            int j = 0;
                            while (true) {
                                try {
                                    if (mapa[j][0] != Integer.MAX_VALUE) {
                                        g.setColor(Color.BLACK);
                                        g.fillRect(tableroController.rigth + j * tamX, tableroController.top + i * tamY + tableroController.extraTop, tamX, tamY);
                                        if (mapa[j][i] == 1) {
                                            g.drawImage(CanvasMap.spritesPlayers[5], tableroController.rigth + j * tamX,
                                                    tableroController.top + i * tamY + tableroController.extraTop, null);
                                        }
                                        j++;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    break;
                                }
                            }
                        }

                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Rockwell", Font.PLAIN, 13 + tamY / 4));
                        g.drawString("Score: " + tableroController.score, tableroController.rigth, tableroController.top - 10);
                        g.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 15 + tamY / 4));
                        String name = "PAC-MAN";
                        g.drawString(name, canvas.getWidth() - tableroController.rigth - name.length() * (10 + tamY / 4), tableroController.top - 12);
                        g.setFont(new Font("Rockwell", Font.PLAIN, 6 + tamY / 4));
                        g.drawString("José David Polo", canvas.getWidth() - tableroController.rigth - name.length() * (10 + tamY / 4), tableroController.top - 12 + (3 + tamY / 4));
                        g.setFont(new Font("Rockwell", Font.PLAIN, 12 + tamY / 4));
                        g.drawString("Lives: " + tableroController.lives, tableroController.rigth, canvas.getHeight() - 12);

                        //int i = 0;
//                        for (Nodo nodo : tableroController.graph) {
//                            for (Nodo adyacente : nodo.Adyacentes) {
//                                g.drawLine(tableroController.rigth + nodo.location.x * tamX,
//                                        tableroController.top + tableroController.extraTop + nodo.location.y * tamY,
//                                        tableroController.rigth + adyacente.location.x * tamX,
//                                        tableroController.top + tableroController.extraTop + adyacente.location.y * tamY);                                
//                            }
//                            //g.drawString(String.valueOf(i), tableroController.rigth + nodo.location.x * tamX, tableroController.top + nodo.location.y * tamY + tableroController.extraTop);
//                            //i++;
//                        }
//                        g.setColor(Color.red);
//                        Nodo q = Nodo.canMoveInGraph(tableroController.graph,
//                                tableroController.tablero.pacman.position, tamX,
//                                tableroController.rigth, tamY, tableroController.top + tableroController.extraTop);
//                        if (q != null) {
//                            g.fillOval(tableroController.rigth + q.location.x * tamX, tableroController.top
//                                    + tableroController.extraTop + q.location.y * tamY, 5, 5);
//                        }
                        for (Nodo nodo : tableroController.graph) {
                            if (nodo.objetive) {
                                g.setColor(Color.WHITE);
                                g.drawOval(nodo.location.x * tamX + tableroController.rigth + tamX / 4,
                                        nodo.location.y * tamY + tableroController.top + tableroController.extraTop + tamY / 4,
                                        tamX / 2, tamY / 2);
                                g.fillOval(nodo.location.x * tamX + tableroController.rigth + tamX / 4,
                                        nodo.location.y * tamY + tableroController.top + tableroController.extraTop + tamY / 4,
                                        tamX / 2, tamY / 2);
                            }
                        }

                        if (tableroController.tablero.pacman != null) {
//                            g.setColor(Color.YELLOW);
//                            g.fillOval(tableroController.tablero.pacman.position.x,
//                                    tableroController.tablero.pacman.position.y, tamX, tamY);
                            g.drawImage(tableroController.tablero.pacman.sprite, tableroController.tablero.pacman.position.x,
                                    tableroController.tablero.pacman.position.y, null);
                        }

                        if (tableroController.tablero.fantasma != null) {
//                            g.setColor(Color.RED);
//                            g.fillOval(tableroController.tablero.fantasma.position.x,
//                                    tableroController.tablero.fantasma.position.y, tamX, tamY);
                            g.drawImage(tableroController.tablero.fantasma.sprite, tableroController.tablero.fantasma.position.x,
                                    tableroController.tablero.fantasma.position.y, null);
                        }

//                        for (Nodo nodo : tableroController.tablero.fantasma.ghostWay) {
//                            g.drawRect(tableroController.rigth + nodo.location.x * tamX, tableroController.top + nodo.location.y * tamY + tableroController.extraTop, tamX, tamY);
//
//                        }
//                        int i = 0;
//                        for (BufferedImage image : spritesMap) {
//                            g.drawImage(image,i, 0, null);
//                            i+=tamX;
//                        }                        
                        if (tableroController.tablero.pacman.actualNodo.getId() == tableroController.tablero.fantasma.actualNodo.getId()) {
                            tableroController.lives--;
                            if (tableroController.lives <= 0) {
                                tableroController.deathSound.loop(1);
                                g.setColor(Color.WHITE);
                                g.setFont(new Font("Rockwell", Font.PLAIN, 12 + tamY / 4));
                                g.drawString("Lives: " + 0, tableroController.rigth, canvas.getHeight() - 12);
                                g.setFont(new Font("Rockwell", Font.BOLD, canvas.getWidth() / 6));
                                g.drawString("Game Over", canvas.getHeight() / 20, canvas.getWidth() / 6 + 50);
                                canvas.getBufferStrategy().show();
                                Thread.sleep(3000);
                                tableroController.tablero.dispose();
                                menuController.menu.setVisible(true);
                                break;
                            }
                        }

                        if (tableroController.score == 2000) {
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("Rockwell", Font.BOLD, canvas.getWidth() / 5));
                            g.drawString("You Win", canvas.getHeight() / 9, canvas.getWidth() / 5 + 50);
                            canvas.getBufferStrategy().show();
                            Thread.sleep(3000);
                            tableroController.tablero.dispose();
                            menuController.menu.setVisible(true);
                            break;
                        }
                        canvas.getBufferStrategy().show();

                        Thread.sleep(20);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    /**
     * Corta una imagen y extrae los exprites que pueda
     *
     * @param name
     * @return
     */
    public static BufferedImage[] cutMapaImage(String name, int X, int Y) {
        BufferedImage[] sprites = null;
        try {
            BufferedImage imagen = ImageIO.read(new File(CanvasMap.class.getClassLoader().getResource(name).getFile()));
            final int filas = imagen.getHeight() / X;
            final int columnas = imagen.getWidth() / Y;
            sprites = new BufferedImage[filas * columnas];

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    sprites[(i * columnas) + j] = imagen.getSubimage(j * X, i * Y, X, Y);
                    Image img = sprites[(i * columnas) + j].getScaledInstance(tamX, tamY, BufferedImage.SCALE_DEFAULT);
                    sprites[(i * columnas) + j] = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = (Graphics2D) sprites[(i * columnas) + j].getGraphics();
                    g.drawImage(img, 0, 0, null);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CanvasMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sprites;
    }
}
