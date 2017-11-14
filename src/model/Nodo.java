/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.tableroController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jd45
 */
public class Nodo {

    public boolean objetive;
    public Point location;
    int id;
    ArrayList<Nodo> Adyacentes;

    public Nodo(Point location, int id) {
        this.location = location;
        this.id = id;
        Adyacentes = new ArrayList<>();
        objetive = false;
    }

    public boolean isObjetive() {
        return objetive;
    }

    public void setObjetive(boolean objetive) {
        this.objetive = objetive;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Nodo> getAdyacentes() {
        return Adyacentes;
    }

    public void setAdyacentes(ArrayList<Nodo> Adyacentes) {
        this.Adyacentes = Adyacentes;
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
        int cant = 0, vertexCant = 0;
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
                        graph.add(new Nodo(new Point(j, i), vertexCant));
                        vertexCant++;
                        p = searchInArray(graph, new Point(j, i));
                        p.Adyacentes = new ArrayList<>();
                    }

                    if (p != null) {
                        //Mira si tiene un descendiente debajo
                        if (i >= 0 && i < mapa[0].length - 1) {
                            Nodo q = searchInArray(graph, new Point(j, i + 1));
                            if (q == null && mapa[j][i + 1] != 1) {
                                graph.add(new Nodo(new Point(j, i + 1), vertexCant));
                                vertexCant++;
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
                                graph.add(new Nodo(new Point(j, i - 1), vertexCant));
                                vertexCant++;
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
                                graph.add(new Nodo(new Point(j + 1, i), vertexCant));
                                vertexCant++;
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
                                graph.add(new Nodo(new Point(j - 1, i), vertexCant));
                                vertexCant++;
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
        int[] objetives = new int[]{12,14,29,85,52,112,152,107,165,172};
        for (int objetive : objetives) {
            graph.get(objetive).setObjetive(true);
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

    /**
     * Busca un nodo en un grafo de acuerdo con una ubicación dada para sus
     * coordenadas y unas relaciones de escala que hacen posible encontrar el
     * mismo.
     *
     * @param graph es el grafo en el que se realizará la búsqueda.
     * @param location es la ubicación del punto donde se buscará el nodo en el
     * grafo.
     * @param scaleX es la escala del punto en el grafo con respecto a un punto
     * con dimensiones en x iguales a 1.
     * @param extraX es la desviación en x del punto a casusa del dibujado.
     * @param scaleY es la escala del punto en el grafo con respecto a un punto
     * con dimensiones en y iguales a 1.
     * @param extraY es la desviación en y del punto a casusa del dibujado.
     * @return retorna el nodo buscado si fué encontrado.
     */
     public static Nodo searchInGraph(ArrayList<Nodo> graph, Point location, int scaleX, int extraX, int scaleY, int extraY) {
        for (Nodo nodo : graph) {
            if (location.x >= nodo.location.x * scaleX + extraX && location.x < nodo.location.x * scaleX + scaleX + extraX) {
                if (location.y >= nodo.location.y * scaleY + extraY && location.y < nodo.location.y * scaleY + scaleY + extraY) {                    
                    return nodo;
                }
            }
        }
        return null;
    }

    /**
     * Determina un objeto de magnnitudes conocidas puede entrar en un nodo(si
     * existe) asociado a un punto dado que se encuentra en él.
     *
     * @param graph es el grafo en el que se realizará la búsqueda.
     * @param location es la ubicación del punto donde se buscará el nodo en el
     * grafo.
     * @param scaleX es la escala del punto en el grafo con respecto a un punto
     * con dimensiones en x iguales a 1.
     * @param extraX es la desviación en x del punto a casusa del dibujado.
     * @param scaleY es la escala del punto en el grafo con respecto a un punto
     * con dimensiones en y iguales a 1.
     * @param extraY es la desviación en y del punto a casusa del dibujado.
     * @return retorna el nodo relacionado con el punto si los puntos que
     * definen las esquinas del objeto se encuentran en el grafo.
     */
    public static Nodo canMoveInGraph(ArrayList<Nodo> graph, Point location, int scaleX, int extraX, int scaleY, int extraY) {
        Nodo q = searchInGraph(graph, location, scaleX, extraX, scaleY, extraY);
        if (q != null && searchInGraph(graph, new Point(location.x + scaleX - 2, location.y), scaleX, extraX, scaleY, extraY) == null) {
            return null;
        }
        if (q != null && searchInGraph(graph, new Point(location.x, location.y + scaleY - 2), scaleX, extraX, scaleY, extraY) == null) {
            return null;
        }
        if (q != null && searchInGraph(graph, new Point(location.x + scaleX - 2, location.y + scaleY - 2), scaleX, extraX, scaleY, extraY) == null) {
            return null;
        }
        return q;
    }

    /**
     * Determina cuanto puede moverse el usuario en una dirección antes de
     * colisionar con el grafo en que se mueve.
     *
     * @return
     */
    public static int howMuchICanMove(ArrayList<Nodo> graph, Point location, int scaleX, int extraX, int scaleY, int extraY, Point direction) {
        int amount = 0;
        Nodo menor = null;
        for (Nodo nodo : graph) {
            if (menor == null) {
                menor = nodo;
                if (direction.x != 0) {
                    if (direction.x == 1) {
                        amount = nodo.location.x * scaleX + extraX - location.x;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    } else {
                        amount = location.x - nodo.location.x * scaleX + extraX;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    }
                } else // Si se moverá en y
                {
                    if (direction.y == 1) {
                        amount = location.y - nodo.location.y * scaleY + extraY;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    } else {
                        amount = nodo.location.y * scaleY + extraY - location.y;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    }
                }
            } else //Si se moverá en x
            {
                if (direction.x != 0) {
                    if (direction.x == 1 && nodo.location.x * scaleX + extraX - location.x < menor.location.x * scaleX + extraX - location.x) {
                        menor = nodo;
                        amount = nodo.location.x * scaleX + extraX - location.x;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    } else if (location.x - nodo.location.x * scaleX + extraX < location.x - menor.location.x * scaleX + extraX) {
                        menor = nodo;
                        amount = location.x - nodo.location.x * scaleX + extraX;
                        if (amount < 0 && amount > Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    }
                } else // Si se moverá en y
                {
                    if (direction.y == 1 && nodo.location.y * scaleY + extraY - location.y < menor.location.y * scaleY + extraY - location.y) {
                        menor = nodo;
                        amount = nodo.location.y * scaleY + extraY - location.y;
                        if (amount < 0 && amount > -Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    } else if (location.y - nodo.location.y * scaleY + extraY < menor.location.y * scaleY + extraY - location.y) {
                        menor = nodo;
                        amount = location.y - nodo.location.y * scaleY + extraY;
                        if (amount > 0 && amount < Player.VEL) {
                            return amount;
                        } else {
                            nodo = null;
                            amount = 0;
                        }
                    }
                }
            }
        }
        return amount;
    }

    /**
     * Calcula la distancia en pixeles entre dos nodos
     *
     * @param n
     * @param n2
     * @return
     */
    public static int dEntreNyN(Nodo n, Nodo n2) {
        return (int) Math.sqrt(Math.pow(n2.location.x - n.location.x, 2) + Math.pow(n2.location.y - n.location.y, 2));
    }
    
    /**
     * Calcula la distancia en pixeles entre un nodo y un punto
     *
     * @param n
     * @param p
     * @return
     */
    public static int dEntreNyV(Nodo n, Point p) {
        return (int) Math.sqrt(Math.pow(p.x - n.location.x, 2) + Math.pow(p.y - n.location.y, 2));
    }

}
