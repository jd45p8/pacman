/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jd45
 */
public class Nodo {

    Point location;
    int id;
    ArrayList<Nodo> Adyacentes;

    public Nodo(Point location, int id) {
        this.location = location;
        this.id = id;
        Adyacentes = new ArrayList<>();
    }

    /**
     * Transforma la matriz que representa el mapa del juego en un grafo en
     * forma de matriz de adyacencia.
     *
     * @param mapa es la matriz que representa el mapa a transformar.
     * @return retorna un grafo en forma de ArrayList con la información que
     * extrajo del mapa.
     */
    public static ArrayList<Nodo> fromArrayToGraph(int[][] mapa) {
        int cant = 0;
        ArrayList<Nodo> graph = new ArrayList<>();

        while (true) {
            try {
                if (mapa[cant][0] != Integer.MAX_VALUE) {
                    cant++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = 0; i < mapa[0].length; i++) {
            for (int j = 0; j < cant; j++) {
                if (mapa[j][i] != 1) {
                    Nodo p = searchInArray(graph, new Point(j, i));
                    if (p == null && mapa[j][i] != 1) {
                        graph.add(new Nodo(new Point(j, i), j + i));
                        p = searchInArray(graph, new Point(j, i));
                        p.Adyacentes = new ArrayList<>();
                    }

                    if (p != null) {
                        //Mira si tiene un descendiente debajo
                        if (i >= 0 && i < mapa[0].length - 1) {
                            Nodo q = searchInArray(graph, new Point(j, i + 1));
                            if (q == null && mapa[j][i + 1] != 1) {
                                graph.add(new Nodo(new Point(j, i + 1), j + i + 1));
                                q = searchInArray(graph, new Point(j, i + 1));
                                q.Adyacentes = new ArrayList<>();
                                p.Adyacentes.add(q);
                            } else if (q != null) {
                                p.Adyacentes.add(q);
                            }

                        }

                        //Mira si tiene un antecespor arriba
                        if (i > 0 && i < mapa[0].length) {
                            Nodo q = searchInArray(graph, new Point(j, i - 1));
                            if (q == null && mapa[j][i - 1] != 1) {
                                graph.add(new Nodo(new Point(j, i - 1), j + i - 1));
                                q = searchInArray(graph, new Point(j, i - 1));
                                q.Adyacentes = new ArrayList<>();
                                p.Adyacentes.add(q);
                            } else if (q != null) {
                                p.Adyacentes.add(q);
                            }

                        }

                        //Mira si tiene un descendiente delante
                        if (j >= 0 && j < cant - 1) {
                            Nodo q = searchInArray(graph, new Point(j + 1, i));
                            if (q == null && mapa[j + 1][i] != 1) {
                                graph.add(new Nodo(new Point(j + 1, i), j + 1 + i));
                                q = searchInArray(graph, new Point(j + 1, i));
                                q.Adyacentes = new ArrayList<>();
                                p.Adyacentes.add(q);
                            } else if (q != null) {
                                p.Adyacentes.add(q);
                            }

                        }

                        //Mira si tiene un antecesor detrás
                        if (j > 0 && j < cant) {
                            Nodo q = searchInArray(graph, new Point(j - 1, i));
                            if (q == null && mapa[j - 1][i] != 1) {
                                graph.add(new Nodo(new Point(j - 1, i), j - 1 + i));
                                q = searchInArray(graph, new Point(j - 1, i));
                                q.Adyacentes = new ArrayList<>();
                                p.Adyacentes.add(q);
                            } else if (q != null) {
                                p.Adyacentes.add(q);
                            }

                        }
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Busca un nodo en un arraylist de acuerdo con su posición en la matriz.
     *
     * @param graph es el array donde se encuentran los nodos.
     * @param location Es la ubicación en la matriz del nodo que se busca.
     * @return retorn a el nodo si fué encontrado o retorna null si no fué
     * posible encontrarlo.
     */
    public static Nodo searchInArray(ArrayList<Nodo> graph, Point location) {
        for (Nodo nodo : graph) {
            if (nodo.location.x == location.x && nodo.location.y == location.y) {
                return nodo;
            }
        }
        return null;
    }

}
