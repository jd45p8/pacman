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
    public static final int VEL = 2;    

    public Player(Point position) {
        this.position = position;
    }
    
    public void up(){
        position.y-=VEL;        
    }
    
    public void down(){
        position.y+=VEL;
    }
    
    public void left(){
        position.x-=VEL;
    }
    
    public void rigth(){
        position.x+=VEL;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
    
}
