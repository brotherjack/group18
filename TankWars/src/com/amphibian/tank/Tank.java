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

import java.util.HashMap;

import com.amphibian.environment.Environment;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class Tank {
    final double GRAVITY = 1.0; //TODO this should not be here (certainly in environment)
    public RectF rect = new RectF(0, 0, 0, 0);
    public boolean hasCollided = false;
    public boolean wasShot = false;
    private int positionx; //TODO this probably should be changed to point
    private int positiony;
    private boolean rotateLeft;
    public Locomotion locomotive_entity; //TODO change to private?
    public Bitmap sprite; //TODO should make private
    public Armor armor;
    public Turret turret;
    
    enum WEAPON{GUN};
    
    /**
     * Creates a new tank at a given position and direction.
     * @param position
     * @param rotateLeft
     */
    public Tank(int position, boolean rotateLeft, Bitmap mySprite) {
        
        this.rotateLeft = rotateLeft; //TODO change with Player owner
        this.positionx = position;
        
        this.locomotive_entity = new Locomotion.Treads();
        this.armor = new Armor.StandardArmor();
        this.turret = new Turret.Standard_Turret(new Shell.Standard_Shell());
        
        if (!this.rotateLeft) {
            rect.set(positionx+2, 128, positionx + 50, 150);
        }
        else {
            rect.set(positionx, 128, positionx + 48, 150);
        }
        
        this.sprite = mySprite;
    }
    
    public TankCondition strikeTank(Armament weapon){
    	DamageTuple dmg = weapon.directHit();
    	TankCondition tankStatus = this.armor.takeDamage(dmg.dmg, dmg.type);
    	return tankStatus;
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
     * @return Returns the tank's x position.
     */
    public float getPosX() {
        return positionx;
    }
    
    /**
     * @return Returns the tank's y position.
     */
    public float getPosY() {
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
    public boolean wasShot() { //TODO probably need to delete this
        return this.wasShot;
    }
    
    public int[] getPosition(){ return new int[] {this.positionx, this.positiony}; }
}
