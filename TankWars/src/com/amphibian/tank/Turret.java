package com.amphibian.tank;

import com.amphibian.environment.Environment;
import com.amphibian.tank.Shell.Standard_Shell;

public abstract class Turret {
	private final double DEGREE_AMMOUNT = 1.0;
	protected int angle;
    protected double power;
	protected static Armament<?> selected_weapon;
	
	public Armament<?> fire(boolean direction, float tankposX){ //TODO add a ypos
	    //Gets the bullets position.
	    if(direction)
	        Turret.selected_weapon.setBulletPos(
	                (float) (tankposX + 28 + 28 * Math.cos(
	                            Math.toRadians(angle))), 
	                (float) (130 + 28 * Math.sin(
	                            Math.toRadians(-angle))));
	    else
	    	Turret.selected_weapon.setBulletPos(
	                (float) (tankposX + 16 - 28 * Math.cos(
	                        Math.toRadians(angle))),
	                (float) (130 + 28 * Math.sin(
	                        Math.toRadians(-angle))));
	    Turret.selected_weapon.angle = this.angle;
	    return Turret.selected_weapon;
	}
	
    /**
     * Moves the tank's turret.
     * @param isUp
     */
    public void turret_move(boolean isUp) {
        if(isUp && this.angle <= (90 - DEGREE_AMMOUNT))
            this.angle += DEGREE_AMMOUNT;
        else if(!isUp && this.angle >= 0 + DEGREE_AMMOUNT)
            this.angle -= DEGREE_AMMOUNT;
    }
    
    /**
     * @return Returns the this.angle of the turret.
     */
    public float getAngle() {
        return this.angle;
    }
	
	public static class Standard_Turret extends Turret{ //TODO Should Armor,Armament and this be static?
		public <Arms extends Armament<Arms>> Standard_Turret(Arms weapon){ //TODO add this constructor to TANK_CLASS uml diagram  
			this.power = 100;
			this.angle = 0;
			Standard_Turret.selected_weapon = weapon;
		}
	}
}
