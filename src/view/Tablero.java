/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Canvas;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import controller.menuController;
import controller.tableroController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import model.CanvasMap;

/**
 *
 * @author jd45
 */
public class Tablero extends JFrame {

    /**
     * Es el canvas donde se dibujará el mapa.
     */
    public CanvasMap canvas;

    /**
     * Es la clase que contendrá el espacio de juego y sus componentes.
     */
    public Tablero() {
        initComponents();

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
        this.addComponentListener(componentListener);
    }

    Action next = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tableroController.level++;
        }
    };

    /**
     * Evento que detectará cuando el tamaño del tablero es modificado.
     */
    ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            canvas.drawThread.interrupt();
            tableroController.tablero.remove(canvas);
            canvas = new CanvasMap(tableroController.tablero);
            if(tableroController.tablero.isDisplayable() && canvas.isDisplayable() )
                canvas.drawThread.start();
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

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }

}
