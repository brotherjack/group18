package com.amphibian.tank;

import com.amphibian.environment.Environment;
import com.amphibian.tank.Shell.Standard_Shell;

public abstract class Turret {
	protected int power_rating;
	protected int angle;
	protected Armament selected_weapon;
	
	private double calculate_trajectory(Environment environ){
		double trajectory = 0.0;
		return trajectory;
	}
	
	private void fire(){
		
	}
	
	private class Standard_Turret extends Turret{
		private Standard_Turret(Armament weapon){ //TODO add this constructor to TANK_CLASS uml diagram  
			this.power_rating = 1;
			this.angle = 0;
			this.selected_weapon = weapon;
		}
	}
}
