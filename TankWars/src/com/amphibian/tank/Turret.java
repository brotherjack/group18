package com.amphibian.tank;

import com.amphibian.environment.Environment;
import com.amphibian.tank.Shell.Standard_Shell;

public abstract class Turret {
	protected int power_rating;
	protected int angle;
	protected Accessory.Weapons selected_weapon;
	
	private double calculate_trajectory(Environment environ){
		double trajectory = 0.0;
		return trajectory;
	}
	
	private void fire(){
		
	}
	
	public static class Standard_Turret extends Turret{ //TODO Should Armor,Armament and this be static?
		public Standard_Turret(Accessory.Weapons weapon){ //TODO add this constructor to TANK_CLASS uml diagram  
			this.power_rating = 1;
			this.angle = 0;
			this.selected_weapon = weapon;
		}
	}
}
