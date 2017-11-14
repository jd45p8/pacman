/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.tableroController;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author jd45
 */
public class Player {

    public Point position;
    public Nodo actualNodo;
    public final static int VEL = 4;
    public BufferedImage sprite = CanvasMap.spritesPlayers[0];    

    public Player(Point position) {
        this.position = position;
    }

    public void up(int amount) {
        position.y -= amount;        
        sprite = CanvasMap.spritesPlayers[0];
    }

    public void down(int amount) {
        position.y += amount;
        sprite = CanvasMap.spritesPlayers[1];
    }

    public void left(int amount) {
        position.x -= amount;
        sprite = CanvasMap.spritesPlayers[3];
    }

    public void rigth(int amount) {
        position.x += amount;
        sprite = CanvasMap.spritesPlayers[2];
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
