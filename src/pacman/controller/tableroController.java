package pacman.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFileChooser;
import pacman.view.Tablero;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jd45
 */
public class tableroController {
    /**
     * Es el tablero donde se dibuja el juego.
     */
    public static Tablero tablero;
    
    
    /**
     * Es el tamaño de los mapas del tablero en número de elementos por fila y
     * columna.
     */
    public static int n = 30;

    /**
     * Porcentaje del tablero cubierto por bloques
     */
    public static double bloqPercent = 0.4;

    /**
     * Genera mapas aleatorios en un archivo.
     */
    public static ArrayList<int[][]> generarMapas() {
        ArrayList<int[][]> mapas = new ArrayList<>();
        int numMapas = 50, k = 0;

        while (k <= 50) {
            int mapa[][] = new int[n][n];
            int cubierto = 0;
            Random rd = new Random();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || j == n - 1 || j == 0 || i == n - 1) {
                        mapa[i][j] = 1;
                        cubierto++;
                    } else {
                        mapa[i][j] = 0;
                    }
                }
            }

            int i, j;
            while (cubierto < n * n * bloqPercent) {
                i = rd.nextInt(n);
                j = rd.nextInt(n);
                if (mapa[i][j] == 0) {
                    mapa[i][j] = 1;
                    cubierto++;
                }
            }
            k++;
            mapas.add(mapa);
        }

        JFileChooser chooser = new JFileChooser();
        int op = chooser.showOpenDialog(null);
        if (op == JFileChooser.APPROVE_OPTION) {
            BufferedWriter rw = null;
            try {
                File file = chooser.getSelectedFile();
                rw = new BufferedWriter(new FileWriter(file));
                for (int[][] mapa : mapas) {
                    for (int[] fila : mapa) {
                        rw.write(Arrays.toString(fila));
                        rw.newLine();
                    }
                }
                rw.close();
            } catch (IOException ex) {
                System.out.println("Algo inesperado ha ocurrido mientras se escribía el archivo.");
            } finally {
                try {
                    rw.close();
                } catch (IOException ex) {
                    System.out.println("Algo inesperado ha ocurrido mientras se escribía el archivo.");
                }
            }
        }

        return mapas;
    }

    /**
     * Lee mapas de un archivo.
     */
    public static ArrayList<int[][]> leerMapas() {
        ArrayList<int[][]> mapas = new ArrayList<>();
        File file = null;

        JFileChooser chooser = new JFileChooser();
        int op = chooser.showOpenDialog(null);

        if (op == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            BufferedReader rd = null;
            try {
                rd = new BufferedReader(new FileReader(file));
                try {
                    String line = rd.readLine();
                    int i = 1;
                    int mapa[][] = new int[n][n];
                    
                    while (line != null) {
                        String mapaS[] = line.substring(1, line.length() - 1).split(", ");
                        for (int j = 0; j < n; j++) {
                            mapa[i][j] = Integer.parseInt(mapaS[j]);
                        }
                        
                        i++;
                        if(i == n){
                            mapas.add(mapa);
                            mapa = new int[n][n];
                            i = 0;
                        }
                        line =  rd.readLine();                        
                    }
                } catch (IOException ex) {
                    System.out.println("Algo inesperado ocurrió mientra se leía el archivo.");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Algo inesperado ocurrió mientra se abría el archivo.");
            } finally {
                try {
                    rd.close();
                } catch (IOException ex1) {
                    System.out.println("Algo inesperado ocurrió.");
                }
            }
        }
        return mapas;
    }
    
    /**
     * Dibuja un mapa sobre un lienzo
     */
    public static void drawMapa(){
        tablero.panel.setBackground(Color.black);
        Graphics2D g = (Graphics2D) tablero.panel.getGraphics();
        
    }
}
