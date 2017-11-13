package controller;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
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
import model.CanvasMap;
import static model.CanvasMap.tamX;
import static model.CanvasMap.tamY;
import model.Nodo;
import model.Player;
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
     * Distancia extra del borde superior
     */
    public static int extraTop = 5;

    /**
     * Altura del banner inferior del tablero.
     */
    public static int down = 30;

    /**
     * Distancia del borde derecho del tablero.
     */
    public static int rigth = 44;

    /**
     * Es el ArrayList que contiene los mapas del juego.
     */
    public static ArrayList<int[][]> mapas = new ArrayList<>();

    /**
     * Es el grafo con los puntos accesibles del mapa.
     */
    public static ArrayList<Nodo> graph = new ArrayList<>();

    /**
     * Es el nivel donde se encuentra el jugador.
     */
    public static int level;

    /**
     * Es el puntaje que ha acumulado el jugador en el juego.
     */
    public static int score;

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

        rd = new BufferedReader(new InputStreamReader(
                tableroController.class.getClassLoader().getResourceAsStream("extras/mapas.txt")
        ));
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

    /**
     * Método que le asigna a los sujetos en pantalla la ubicaón del último nodo
     * donde se encontraban.
     */
    public static void resetLocation() {
        if (tablero.pacman != null) {
            if (tablero.pacman.actualNodo == null) {
                tablero.pacman.position = new Point(graph.get(100).location.x * CanvasMap.tamX + rigth,
                        graph.get(100).location.y * CanvasMap.tamY + top + extraTop);
            } else {
                tablero.pacman.position = new Point(tablero.pacman.actualNodo.location.x * CanvasMap.tamX + rigth,
                        tablero.pacman.actualNodo.location.y * CanvasMap.tamY + top + extraTop);
            }
        }
    }

    /**
     * Método que calcula la desviación y el tamaño de las componentes del mapa.
     */
    public static void calculateSizes() {
        int[][] mapa = mapas.get(level);
        int cant = 0;
        while (true) {
            try {
                if (mapa[cant][0] != Integer.MAX_VALUE) {
                    cant++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        tamX = tamY = (tablero.canvas.getHeight() - top - down) / mapa[0].length;
        rigth = (tablero.canvas.getWidth() - tamX * cant) / 2;
        extraTop = (tablero.canvas.getHeight() - tamY * mapa[0].length - top - down) / 2;

    }

    /**
     * Listenner que detectará cuando el tamaño del tablero es modificado.
     */
    public static ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            tablero.canvas.drawThread.interrupt();
            tablero.remove(tablero.canvas);
            tablero.canvas = new CanvasMap(tablero);

            calculateSizes();
            if (tablero.isDisplayable() && tablero.canvas.isDisplayable()) {
                tablero.canvas.drawThread.start();
                resetLocation();
            }

        }

        @Override
        public void componentMoved(ComponentEvent e) {
            resetLocation();
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
    public static KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Nodo q = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    q = Nodo.canMoveInGraph(graph,
                            new Point(tablero.pacman.getPosition().x,
                                    tablero.pacman.getPosition().y - Player.VEL),
                            CanvasMap.tamX, rigth, CanvasMap.tamY, top + extraTop);
                    if (q != null) {
                        tablero.pacman.up(Player.VEL);
                        tablero.pacman.actualNodo = q;
                    } else {
                        tablero.pacman.up(Nodo.howMuchICanMove(graph, tablero.pacman.getPosition(),
                                CanvasMap.tamX, rigth,
                                CanvasMap.tamY, top + extraTop, new Point(0, 1)));
                        System.out.println("Recalculado");
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    q = Nodo.canMoveInGraph(graph,
                            new Point(tablero.pacman.getPosition().x, tablero.pacman.getPosition().y + Player.VEL),
                            CanvasMap.tamX, rigth, CanvasMap.tamY, top + extraTop);
                    if (q != null) {
                        tablero.pacman.down(Player.VEL);
                        tablero.pacman.actualNodo = q;
                    } else {
                        tablero.pacman.down(Nodo.howMuchICanMove(graph, tablero.pacman.getPosition(), CanvasMap.tamX, rigth,
                                CanvasMap.tamY, top + extraTop, new Point(0, -1)));
                        System.out.println("Recalculado");
                    }
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    q = Nodo.canMoveInGraph(graph, new Point(tablero.pacman.getPosition().x - Player.VEL, tablero.pacman.getPosition().y),
                            CanvasMap.tamX, rigth, CanvasMap.tamY, top + extraTop);
                    if (q != null) {
                        tablero.pacman.left(Player.VEL);
                        tablero.pacman.actualNodo = q;
                    } else {
                        tablero.pacman.up(Nodo.howMuchICanMove(graph, tablero.pacman.getPosition(), CanvasMap.tamX, rigth,
                                CanvasMap.tamY, top + extraTop, new Point(-1, 0)));
                        System.out.println("Recalculado");
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    q = Nodo.canMoveInGraph(graph, new Point(tablero.pacman.getPosition().x + Player.VEL,
                            tablero.pacman.getPosition().y), CanvasMap.tamX, rigth, CanvasMap.tamY, top + extraTop);
                    if (q != null) {
                        tablero.pacman.rigth(Player.VEL);
                        tablero.pacman.actualNodo = q;
                    } else {
                        tablero.pacman.up(Nodo.howMuchICanMove(graph, tablero.pacman.getPosition(), CanvasMap.tamX, rigth,
                                CanvasMap.tamY, top + extraTop, new Point(1, 0)));
                        System.out.println("Recalculado");
                    }
                    break;
                }
                default:
                    break;
            }
            if (q != null && q.objetive) {
                score += 200;
                q.objetive = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     * Listenner que detectará cuadno el juego está máximisado o minimizado
     *
     * @param args
     */
    public static WindowStateListener windowsStateListener = new WindowStateListener() {
        @Override
        public void windowStateChanged(WindowEvent e) {
            System.out.println("I have changed " + e.getNewState());
            System.out.println(e.getWindow().getBounds());
            resetLocation();
        }
    };

}
