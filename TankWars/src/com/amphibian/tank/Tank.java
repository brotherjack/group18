/**
 * COP4331 Group18 TankWars
 * @author Ryan Farneski
 * @author John Gilmore
 * @author Thomas Hellinger
 * @author Edward Jezisek
 * @author Brandon Ramsey
 * @author Andrew Watson
 * Tank.java
 * @version 1.0
 * Mar 13, 2013
 * Description: Tank class to monitor a tanks location,
 *              turrets position and health
 * In Progress: Still needs to incorporate hit points 
 * and needs to announce whether it's still alive.
 */
package com.amphibian.tank;

import com.amphibian.environment.Environment;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class Tank {
    final double DEGREE_AMMOUNT = 1.0;
    final double GRAVITY = 1.0;
    public RectF rect = new RectF(0, 0, 0, 0);
    public boolean hasCollided = false;
    public boolean wasShot = false;
    private int positionx; //TODO this probably should be changed to point
    private int positiony;
    private boolean rotateLeft;
    private float bulletXPos; //TODO this probably should be changed to point
    private float bulletYPos;
    private double power;
    private float degrees;
    public Locomotion locomotive_entity; //TODO change to private?
    public Bitmap sprite;
    public Armor armor;
    
    enum WEAPON{GUN};
    
    /**
     * Creates a new tank at a given position and direction.
     * @param position
     * @param rotateLeft
     */
    public Tank(int position, boolean rotateLeft, Bitmap mySprite) {
        power = 100;
        degrees = 0;
        
        this.rotateLeft = rotateLeft; //TODO change with Player owner
        this.positionx = position;
        
        this.locomotive_entity = new Locomotion.Treads();
        this.armor = new Armor.StandardArmor();
        
        if (!this.rotateLeft) {
            rect.set(positionx+2, 128, positionx + 50, 150);
        }
        else {
            rect.set(positionx, 128, positionx + 48, 150);
        }
        
        this.sprite = mySprite;
    }
    
    /**
     * @param t
     * @return Returns the bullets X positions when time t is input.
     */
    public float getBulletX(double t) { 	
        if(rotateLeft)
            return (float)(bulletXPos + power / 40 *
                    Math.cos(Math.toRadians(degrees)) * t);
        else
            return (float)(float)(bulletXPos - power / 40 *
                    Math.cos(Math.toRadians(degrees)) * t);
    }
    
    /**
     * @param t Time
     * @return returns the bullets y position.
     */
    public float getBulletY(double t) {
        float yPos = (float) (bulletYPos-power / 40 *
                Math.sin(Math.toRadians(degrees)) * t + GRAVITY / 100 * t * t);
        return yPos;
    }
    
    /**
     * This sets the initial bullet position of the tanks.
     * @param xPos
     * @param yPos
     */
    public void setBulletPos(float xPos, float yPos) {
        bulletXPos = xPos;
        bulletYPos = yPos;
        if(bulletXPos < 0)
            bulletXPos = 0;
        if(bulletYPos < 0)
            bulletYPos = 0;
    }
    
    /**
     * This returns whether the tank is facing left or right. 
     * @return Returns true if it's facing left, right otherwise.
     */
    public boolean getRotate() {
        return rotateLeft;
    }
    
    /**
     * Moves the tank left or right depending on the input: isRight.
     * If true it moves right, otherwise it moves left.
     * @author Edward Jezisek
     * @author Thomas Adriaan Hellinger
     * @param isRight
     * @param world
     */
    public void move_tank(boolean isRight, Environment world) {
    	if(this.locomotive_entity != null){
	    	if (!this.rotateLeft) {
	            rect.set(positionx+2, 128, positionx + 50, 150);
	        }
	        else {
	            rect.set(positionx, 128, positionx + 48, 150);
	        }
	        if(isRight && world.canMove(this, isRight)) {
	            positionx += this.locomotive_entity.speed;
	        }
	        else if(!isRight && world.canMove(this, isRight)) {
	            positionx -= this.locomotive_entity.speed;
	        }
    	}
    }
    
    
    /**
     * Moves the tank's turret.
     * @param isUp
     */
    public void turret_move(boolean isUp) {
        if(isUp && degrees <= (90 - DEGREE_AMMOUNT))
            degrees += DEGREE_AMMOUNT;
        else if(!isUp && degrees >= 0 + DEGREE_AMMOUNT)
            degrees -= DEGREE_AMMOUNT;
    }
    
    /**
     * @return Returns the degrees of the turret.
     */
    public float getDegrees() {
        return degrees;
    }
    
    /**
     * @return Returns the tank's x position.
     */
    public float getX() {
        return positionx;
    }
    
    /**
     * @return Returns the tank's y position.
     */
    public float getY() {
        return positiony;
    }
    
    /**
     * @return Returns the tanks hit box
     */
    public RectF getRect() {
        return this.rect;
    }
    
    /**
     * @return Whether the tank has collided with environment
     */
    public boolean hasCollided() {
        return this.hasCollided;
    }
    
    /**
     * @return True if the tank was shot
     */
    public boolean wasShot() {
        return this.wasShot;
    }
    
    public int[] getPosition(){ return new int[] {this.positionx, this.positiony}; }
}
