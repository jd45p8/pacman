package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
     * Altura del banner superior del tablero
     */
    public static int top = 40;
    
    /**
     * Altura del banner inferior del tablero
     */
    public static int down = 30;

     /**
     * Es el ArrayList que contiene los mapas del juego.
     */
    public static ArrayList<int[][]> mapas = new ArrayList<>();

    /**
     * Es el nivel donde se encuentra el jugador.
     */
    public static int level;

    /**
     * Es el tamaño de los mapas del tablero en número de elementos por fila y
     * columna.
     */
    public static int nX = 25;
    public static int nY = 15;

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
            int mapa[][] = new int[nY][nX];
            int cubierto = 0;
            Random rd = new Random();

            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if (i == 0 || j == nX - 1 || j == 0 || i == nY - 1) {
                        mapa[i][j] = 1;
                        cubierto++;
                    } else {
                        mapa[i][j] = 0;
                    }
                }
            }

            int i, j;
            while (cubierto < nY * nX * bloqPercent) {
                i = rd.nextInt(nY);
                j = rd.nextInt(nX);
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
            int i = 0;
            ArrayList<int[]> mapa = new ArrayList<>();
            boolean inicio = false, fin = false;

            while (line != null) {
                String mapaS[] = line.substring(1, line.length() - 1).split(", ");
                mapa.add(new int[mapaS.length]);
                for (int j = 0; j < mapaS.length; j++) {
                    mapa.get(i)[j] = Integer.parseInt(mapaS[j]);
                }

                if (!inicio) {
                    inicio = true;
                } else if (!fin) {
                    fin = true;
                }

                for (int elem : mapa.get(i)) {
                    if (elem == 0) {
                        if (inicio && !fin) {
                            inicio = false;
                        } else if (inicio && fin) {
                            fin = false;
                        }
                        break;
                    }
                }

                i++;
                if (inicio && fin) {
                    int[][] mapaI = new int[mapaS.length][i];
                    for (int j = 0; j < i; j++) {
                        for (int k = 0; k < mapaS.length; k++) {
                            mapaI[k][j] = mapa.get(j)[k];
                        }
                    }
                    mapa.removeAll(mapa);
                    mapas.add(mapaI);
                    i = 0;
                    inicio = false;
                    fin = false;
                }
                line = rd.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Algo inesperado ocurrió mientra se leía el archivo.");
        }

    }

}
