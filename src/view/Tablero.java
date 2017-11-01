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

/**
 *
 * @author jd45
 */
public class Tablero extends JFrame{
    
    public Canvas canvas;
    public JButton atras;

    public Tablero() {
        initComponents();
        tableroController.addDrawMapa(canvas);
        this.setVisible(true); 
        tableroController.drawTread.start();
    }

    private void initComponents() {
        this.setSize(menuController.menu.getWidth(), menuController.menu.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        
        canvas = new Canvas();
        canvas.setSize(this.getSize());
        this.add(canvas);
        
        tableroController.level = 0;
    }

    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }
}
