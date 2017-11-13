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
import model.CanvasMap;
import model.Ghost;
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
    
    public Ghost fantasma;

    /**
     * Es la clase que contendrá el espacio de juego y sus componentes.
     */
    public Tablero() {
        initComponents();
        this.addComponentListener(tableroController.componentListener);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                tableroController.tablero.addKeyListener(tableroController.keyListener);
                tableroController.tablero.addWindowStateListener(tableroController.windowsStateListener);

                pacman = new Player(new Point(tableroController.graph.get(100).location.x * canvas.tamX + tableroController.rigth,
                        tableroController.graph.get(100).location.y * canvas.tamY + tableroController.top + tableroController.extraTop)
                );
                
                fantasma = new Ghost((new Point(tableroController.graph.get(150).location.x * canvas.tamX + tableroController.rigth,
                        tableroController.graph.get(150).location.y * canvas.tamY + tableroController.top + tableroController.extraTop))
                );
            }
        });

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

    }
    
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }

}
