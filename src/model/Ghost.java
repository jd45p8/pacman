/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.tableroController;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jd45
 */
public class Ghost extends Player {

    private final static int VEL = 2;

    public Thread moveGhost;

    public Nodo movingTo;

    /**
     * Es el camino que seguirá el fantasma.
     */
    public LinkedList<Nodo> ghostWay = new LinkedList<>();

    public Ghost(Point position) {
        super(position);
        changeGhostMovement(this);
        sprite = CanvasMap.spritesPlayers[4];
    }

    @Override
    public void up(int amount) {
        position.y -= amount;        
    }

    @Override
    public void down(int amount) {
        position.y += amount;        
    }

    @Override
    public void left(int amount) {
        position.x -= amount;        
    }

    @Override
    public void rigth(int amount) {
        position.x += amount;        
    }

    public void changeGhostMovement(Ghost ghost) {
        moveGhost = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sprite = CanvasMap.spritesPlayers[4];
                    if (tableroController.tablero.fantasma.ghostWay.isEmpty() || tableroController.tablero.pacman.actualNodo.id != tableroController.tablero.fantasma.ghostWay.getFirst().id) {
                        LinkedList<DijkstraMatriz> dijks = DijkstraMatriz.dijkstra(actualNodo);
                        ghost.ghostWay = DijkstraMatriz.CaminoMinimo(dijks, tableroController.tablero.pacman.actualNodo);
                        ghost.actualNodo = ghost.ghostWay.removeLast();
                    }

                    int dirX = ghost.movingTo.getLocation().x * CanvasMap.tamX + tableroController.rigth - ghost.position.x;
                    int dirY = ghost.movingTo.getLocation().y * CanvasMap.tamY + tableroController.top + tableroController.extraTop - ghost.position.y;

                    if (dirX == 0 && dirY == 0) {
                        if (!ghost.ghostWay.isEmpty()) {
                            ghost.actualNodo = ghost.movingTo;
                            ghost.movingTo = ghost.ghostWay.removeLast();
                        }
                    } else {
                        if (dirX != 0) {
                            if (Math.abs(dirX) >= Ghost.VEL) {
                                if (dirX > 0) {
                                    ghost.rigth(Ghost.VEL);
                                } else {
                                    ghost.left(Ghost.VEL);
                                }
                            } else {
                                if (dirX > 0) {
                                    ghost.rigth(Math.abs(dirX));
                                } else {
                                    ghost.left(Math.abs(dirX));
                                }
                            }
                        } else if (dirY != 0) {
                            if (Math.abs(dirY) >= Ghost.VEL) {
                                if (dirY > 0) {
                                    ghost.down(Ghost.VEL);
                                } else {
                                    ghost.up(Ghost.VEL);
                                }
                            } else {
                                if (dirY > 0) {
                                    ghost.down(Math.abs(dirY));
                                } else {
                                    ghost.up(Math.abs(dirY));
                                }
                            }
                        }
                    }

                    if (tableroController.tablero.fantasma.actualNodo.isObjetive()) {
                        Random rd = new Random();
                        int k = rd.nextInt(tableroController.graph.size());
                        while(tableroController.graph.get(k).isObjetive()){
                            k = rd.nextInt(tableroController.graph.size());
                        }
                        tableroController.tablero.fantasma.actualNodo.setObjetive(false);
                        tableroController.graph.get(k).setObjetive(true);                        
                    }
                    
                    if (tableroController.tablero.pacman.actualNodo.getId() == tableroController.tablero.fantasma.actualNodo.getId()) {
                        break;
                    }

                    if (tableroController.score == 10) {
                        break;
                    }

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });
    }
}
