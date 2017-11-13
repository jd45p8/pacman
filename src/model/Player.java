/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;

/**
 *
 * @author jd45
 */
public class Player {
    public Point position;
    public Nodo actualNodo;
    public static final int VEL = 2;    

    public Player(Point position) {
        this.position = position;
    }
    
    public void up(int amount){
        position.y-=amount;        
    }
    
    public void down(int amount){
        position.y+=amount;
    }
    
    public void left(int amount){
        position.x-=amount;
    }
    
    public void rigth(int amount){
        position.x+=amount;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
    
}
