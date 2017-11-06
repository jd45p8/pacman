/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import controller.menuController;
import controller.tableroController;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.CanvasMap;
import model.Nodo;
import model.Player;

/**
 *
 * @author jd45
 */
public class Tablero extends JFrame {

    /**
     * Es el canvas donde se dibujará el mapa.
     */
    public CanvasMap canvas;

    public Player pacman;

    /**
     * Es la clase que contendrá el espacio de juego y sus componentes.
     */
    public Tablero() {
        initComponents();
        this.addComponentListener(componentListener);
        this.addKeyListener(keyListener);
    }

    private void initComponents() {
        this.setSize(menuController.menu.getWidth(), menuController.menu.getHeight());
        this.setMinimumSize(new Dimension(menuController.menu.getWidth(), menuController.menu.getHeight()));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        tableroController.leerMapas();
        canvas = new CanvasMap(this);

        tableroController.level = 0;
        tableroController.score = 0;
        tableroController.graph = Nodo.fromArrayToGraph(tableroController.mapas.get(0));
        pacman = new Player(new Point(1, 1));
    }

    /**
     * Listenner que detectará cuando el tamaño del tablero es modificado.
     */
    ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            canvas.drawThread.interrupt();
            tableroController.tablero.remove(canvas);
            canvas = new CanvasMap(tableroController.tablero);
            if (tableroController.tablero.isDisplayable() && canvas.isDisplayable()) {
                canvas.drawThread.start();
                pacman.position =  new Point(tableroController.graph.get(50).location.x * canvas.tamX,
                tableroController.graph.get(50).location.y * canvas.tamY);
            }
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void componentShown(ComponentEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     * Listener que escuchará el teclado para mover el jugador.
     */
    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (Nodo.canMoveInGraph(tableroController.graph,
                        new Point(tableroController.tablero.pacman.getPosition().x,
                                tableroController.tablero.pacman.getPosition().y - Player.VEL),
                        tableroController.tablero.canvas.tamX, tableroController.rigth,
                        tableroController.tablero.canvas.tamY, tableroController.top + tableroController.extraTop) != null) {
                    pacman.up();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (Nodo.canMoveInGraph(tableroController.graph,
                        new Point(tableroController.tablero.pacman.getPosition().x,
                                tableroController.tablero.pacman.getPosition().y + Player.VEL),
                        tableroController.tablero.canvas.tamX, tableroController.rigth,
                        tableroController.tablero.canvas.tamY, tableroController.top + tableroController.extraTop) != null) {
                    pacman.down();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (Nodo.canMoveInGraph(tableroController.graph,
                        new Point(tableroController.tablero.pacman.getPosition().x - Player.VEL,
                                tableroController.tablero.pacman.getPosition().y),
                        tableroController.tablero.canvas.tamX, tableroController.rigth,
                        tableroController.tablero.canvas.tamY, tableroController.top + tableroController.extraTop) != null) {
                    pacman.left();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (Nodo.canMoveInGraph(tableroController.graph,
                        new Point(tableroController.tablero.pacman.getPosition().x + Player.VEL,
                                tableroController.tablero.pacman.getPosition().y),
                        tableroController.tablero.canvas.tamX, tableroController.rigth,
                        tableroController.tablero.canvas.tamY, tableroController.top + tableroController.extraTop) != null) {
                    pacman.rigth();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }

}
