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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author jd45
 */
public class Tablero extends JFrame {

    /**
     * Es el canvas donde se dibujará el mapa.
     */
    public Canvas canvas;

    /**
     * Es la clase que contendrá el espacio de juego y sus componentes.
     */
    public Tablero() {
        initComponents();
        tableroController.addDrawMapa(canvas);
        this.setVisible(true);
        tableroController.drawTread.start();
    }

    private void initComponents() {
        this.setSize(menuController.menu.getWidth() + 40, menuController.menu.getHeight() + 40);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        JButton button = new JButton();
        button.setSize(50, 30);
        button.setText("Next");
        this.add(button);
        button.setAction(next);

        canvas = new Canvas();
        canvas.setSize(this.getWidth(), this.getHeight() - 29);
        this.add(canvas);

        tableroController.level = 0;
        //this.addComponentListener(componentListener);
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
            tableroController.drawTread.stop();
            tableroController.tablero.removeAll();
            canvas = new Canvas();
            canvas.setSize(tableroController.tablero.getWidth(), tableroController.tablero.getHeight() - 29);
            tableroController.tablero.add(canvas);
            tableroController.addDrawMapa(canvas);
            tableroController.drawTread.start();
            tableroController.tablero.addNotify();
            System.out.println(tableroController.tablero.getWidth() + " " + tableroController.tablero.getHeight());
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
