package model;

import controller.tableroController;
import java.util.ArrayList;
import java.util.LinkedList;
import model.Nodo;

public class DijkstraMatriz {

    Nodo v;
    int pesos[] = new int[tableroController.graph.size() + 1];

    /**
     *
     * @param v
     */
    public DijkstraMatriz(Nodo v) {
        this.v = v;
        for (int i = 0; i < tableroController.graph.size() + 1; i++) {
            pesos[i] = Integer.MAX_VALUE;
        }
    }

    /**
     * Obtener vértice padre.
     *
     * @return
     */
    public Nodo getV() {
        return v;
    }

    /**
     *
     * @param v
     */
    public void setV(Nodo v) {
        this.v = v;
    }

    /**
     *
     * @return
     */
    public int[] getPesos() {
        return pesos;
    }

    /**
     *
     * @param pesos
     */
    public void setPesos(int[] pesos) {
        this.pesos = pesos;
    }

    /**
     * Aplicar algoritmo de Dijkstra.
     *
     * @param start
     * @return
     */
    public static LinkedList<DijkstraMatriz> dijkstra(Nodo start) {
        boolean visitado[] = new boolean[tableroController.graph.size() + 1];
        LinkedList<DijkstraMatriz> dijk = new LinkedList<>();
        dijk.add(new DijkstraMatriz(start));
        dijk.get(0).getPesos()[start.getId()] = 0;
        int indiceMenor = 0;
        int pesoSeleccionado = 0;
        int pesoMenor = Integer.MAX_VALUE;

        int i = 0;

        while (dijk.size() <= tableroController.graph.size() + 1) {
            DijkstraMatriz q = dijk.getLast();
            visitado[q.v.getId()] = true;

            int j = i;

            while (j >= 0 && dijk.get(j).getPesos()[q.getV().getId()] == Integer.MAX_VALUE) {
                j--;
            }

            pesoSeleccionado = dijk.get(j).getPesos()[q.getV().getId()];

            for (Nodo adyacente : q.getV().getAdyacentes()) {
                if (!visitado[adyacente.getId()]) {
                    j = i;

                    while (j > 0 && dijk.get(j).getPesos()[q.getV().getId()] == Integer.MAX_VALUE) {
                        j--;
                    }

                    int pesoCamino = dijk.get(j).getPesos()[adyacente.getId()];

                    if (pesoSeleccionado + Nodo.dEntreNyN(q.getV(), adyacente) < pesoCamino) {
                        dijk.get(i).getPesos()[adyacente.getId()] = (int) (pesoSeleccionado + Nodo.dEntreNyN(q.getV(), adyacente));
                    }
                }

            }

            pesoMenor = Integer.MAX_VALUE;

            for (int k = 0; k < tableroController.graph.size() + 1; k++) {
                j = i;

                while (j > 0 && dijk.get(j).getPesos()[k] == Integer.MAX_VALUE) {
                    j--;
                }

                int pesoCamino = dijk.get(j).getPesos()[k];
                if (pesoCamino < pesoMenor) {
                    if (!visitado[k]) {
                        pesoMenor = pesoCamino;
                        indiceMenor = k;
                    }
                }
            }

            dijk.add(new DijkstraMatriz(tableroController.graph.get(indiceMenor)));
            i++;
        }

        return dijk;
    }

    /**
     * Obtener camino mínimo.
     *
     * @param dijk
     * @param fin
     * @return
     */
    public static LinkedList<Nodo> CaminoMinimo(LinkedList<DijkstraMatriz> dijk, Nodo fin) {
        LinkedList<Nodo> camino = new LinkedList<>();
        camino.add(fin);
        while (camino.getLast().getId() != dijk.get(0).getV().getId()) {

            int r = dijk.size() - 1;
            while (r >= 0 && dijk.get(r).pesos[camino.getLast().getId()] == Integer.MAX_VALUE) {
                r--;
            }

            camino.add(dijk.get(r).getV());

        }
        return camino;
    }

    

}
