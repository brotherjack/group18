package com.amphibian.tank;

import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class Armament<T> {
	protected Bitmap sprite;
	protected int damage;
	protected double effect_radius;
	protected float projectileXPos; 
    protected float projectileYPos;
    public RectF bulHitBox = new RectF(0, 0, 0, 0);
    protected int power = 100;
    protected int angle;
	
	protected abstract void on_detonate();
	
	private T type;          

    public void set(T t) {
        this.type = t;
    }
	
	/**
     * @param t
     * @return Returns the bullets X positions when time t is input.
     */
    public float getBulletX(double t, boolean rotatedLeft) {
    	float xPos = 0;
        if(rotatedLeft){ //TODO rotatedLeft should be replaced with current direction of turret
        	xPos =(float)(projectileXPos + power / 40 *
                    Math.cos(Math.toRadians(angle)) * t);
        } else {
            xPos = (float)(float)(projectileXPos - power / 40 *
                    Math.cos(Math.toRadians(angle)) * t);
        }
        return xPos;
    }
    
    /**
     * @param t Time
     * @return returns the bullets y position.
     */
    public float getBulletY(double t, final double GRAVITY) {
        float yPos = (float) (projectileYPos-power / 40 *
                Math.sin(Math.toRadians(angle)) * t + GRAVITY / 100 * t * t);
        return yPos;
    }
	
	/**
     * This sets the initial bullet position of the tanks.
     * @param xPos
     * @param yPos
     */
    public void setBulletPos(float xPos, float yPos) {
        projectileXPos = xPos;
        projectileYPos = yPos;
        if(projectileXPos < 0)
            projectileXPos = 0;
        if(projectileYPos < 0)
            projectileYPos = 0;
    };
}
