/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import pacman.controller.menuController;
import pacman.controller.tableroController;

/**
 *
 * @author jd45
 */
public class Tablero extends JFrame implements Runnable {

    public JPanel panel;
    public JButton atras;

    public Tablero() {
        initComponents();

    }

    private void initComponents() {
        this.setSize(menuController.menu.getWidth(), menuController.menu.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        
        panel = new JPanel();
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(panel);
        
        atras = new JButton();
        atras.setSize(20, 40);
        atras.setText("Atrás");
        atras.setForeground(Color.WHITE);
        this.panel.add(atras);
    }

    @Override
    public void run() {
        while (true) {            
            tableroController.drawMapa();
        }        
    }
}
