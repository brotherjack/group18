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

import android.graphics.RectF;

public class Tank {
    final double DEGREE_AMMOUNT = 1;
    final double GRAVITY = 1;
    final int SCREEN_MAX = 500;
    final int SCREEN_MIN = 0;
    public RectF rect = new RectF(0, 0, 0, 0);
    public boolean hasCollided = false;
    public boolean wasShot = false;
    public int health;
    private int positionx;
    private int positiony;
    private boolean rotateLeft;
    private float bulletXPos;
    private float bulletYPos;
    private double power;
    private float degrees;
    public Locomotion locomotive_entity;
    
    enum WEAPON{GUN};
    
    /**
     * Creates a new tank at a given position and direction.
     * @param position
     * @param rotateLeft
     */
    public Tank(int position, boolean rotateLeft) {
        power = 100;
        degrees = 0;
        health = 100;
        this.rotateLeft = rotateLeft;
        this.positionx = position;
        this.locomotive_entity = new Locomotion.Treads();
        if (!this.rotateLeft) {
            rect.set(positionx+2, 128, positionx + 50, 150);
        }
        else {
            rect.set(positionx, 128, positionx + 48, 150);
        }
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
     * @param isRight
     */
    public void move_tank(boolean isRight, Tank otherTank) {
    	if(this.locomotive_entity != null){
	    	if (!this.rotateLeft) {
	            rect.set(positionx+2, 128, positionx + 50, 150);
	        }
	        else {
	            rect.set(positionx, 128, positionx + 48, 150);
	        }
	        if(isRight && canMove(this.locomotive_entity.speed, isRight, otherTank)) {
	            positionx += this.locomotive_entity.speed;
	        }
	        else if(!isRight && canMove(this.locomotive_entity.speed, isRight, otherTank)) {
	            positionx -= this.locomotive_entity.speed;
	        }
    	}
    }
    
    /**
     * Ensures the tank is able to move. 
     * Takes in the direction to move and the move amount.
     * @param moveAmmount
     * @param isRight
     * @return true if tank can move, false otherwise.
     */
    boolean canMove(double moveAmmount, boolean isRight, Tank otherTank) {
        RectF lScreenBorder = new RectF(0, 0, 0, 300);
        RectF rScreenBorder = new RectF(500,0,500,300);
        if (!hasCollided) {    // Collision not detected
            if(isRight)
                return (positionx + moveAmmount < SCREEN_MAX);
            else
                return (positionx - moveAmmount > SCREEN_MIN);
        }
        
        else if (hasCollided) {    // Collision detected try to move away.
            
            if (isRight) {
                this.rect.set((float) (this.rect.left + moveAmmount),
                        this.rect.top,
                        (float) (this.rect.right + moveAmmount),
                        this.rect.bottom);

                if (this.rect.intersect(otherTank.rect)) {
                    this.rect.set((float) (this.rect.left - moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right - moveAmmount),
                            this.rect.bottom);
                    return false;
                }
                else if (this.rect.intersect(lScreenBorder)) {
                    this.rect.set((float) (this.rect.left - moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right - moveAmmount),
                            this.rect.bottom);
                    return false;
                }
                else if (this.rect.intersect(rScreenBorder)) {
                    this.rect.set((float) (this.rect.left - moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right - moveAmmount),
                            this.rect.bottom);
                }
                else {
                    return true;
                }
            }
            
            else if (!isRight) {
                this.rect.set((float) (this.rect.left - moveAmmount),
                        this.rect.top,
                        (float) (this.rect.right - moveAmmount),
                        this.rect.bottom);
                if (this.rect.intersect(otherTank.rect)) {
                    this.rect.set((float) (this.rect.left + moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right + moveAmmount),
                            this.rect.bottom);
                    return false;
                }
                else if (this.rect.intersect(lScreenBorder)) {
                    this.rect.set((float) (this.rect.left - moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right - moveAmmount),
                            this.rect.bottom);
                    return false;
                }
                else if (this.rect.intersect(rScreenBorder)) {
                    this.rect.set((float) (this.rect.left - moveAmmount),
                            this.rect.top,
                            (float) (this.rect.right - moveAmmount),
                            this.rect.bottom);
                }
                else {
                    return true;
                }
            }
        }
        return false;
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
    
    /**
     * @return The tank's current health.
     */
    public int getHealth() {
        return this.health;
    }
}
