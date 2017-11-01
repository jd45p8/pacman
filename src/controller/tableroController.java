package controller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import view.Tablero;

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
     * Es el hilo sobre el cual se ejecuta el dibujado del mapa.
     */
    public static Thread drawTread;

    /**
     * Es el ArrayList que contiene los mapas del juego.
     */
    static ArrayList<int[][]> mapas = new ArrayList<>();

    /**
     * Es el nivel donde se encuentra el jugador.
     */
    public static int level;

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
    public static ArrayList<int[][]> generarMapas(int numMapas) {
        ArrayList<int[][]> mapas = new ArrayList<>();
        int k = 0;

        while (k <= numMapas) {
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
    public static void leerMapas() {
        BufferedReader rd = null;

        rd = new BufferedReader(new InputStreamReader(tableroController.class.getClassLoader().getResourceAsStream("extras/mapas.txt")));
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
                if (i == n) {
                    mapas.add(mapa);
                    mapa = new int[n][n];
                    i = 0;
                }
                line = rd.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Algo inesperado ocurrió mientra se leía el archivo.");
        }

    }

    /**
     * Añade el hilo para el dibujado del mapa
     */
    public static void addDrawMapa(Canvas canvas) {
        leerMapas();
        drawTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    canvas.createBufferStrategy(2);
                    Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
                    while (true) {
                        int[][] mapa = mapas.get(level);
                        int tamaño = canvas.getHeight() / n;
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < n; j++) {
                                if (mapa[i][j] == 0) {
                                    g.setColor(Color.WHITE);
                                } else {
                                    g.setColor(Color.BLACK);
                                }
                                g.fillRect(i * tamaño, j * tamaño,tamaño,tamaño);
                            }
                        }
                        canvas.getBufferStrategy().show();
                        Thread.sleep(20);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(tableroController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
