package com.amphibian.tank;

public abstract class Armor {
	protected int hit_points;
	protected int damage;
	protected double resist_explosive;
	protected double resist_ballistic;
	protected double resist_radiation;
	protected double resist_thermal;
	protected int cost;
	
	public static class Standard_Armor extends Armor{
		public Standard_Armor(){
			this.hit_points = 100;
			this.damage = 0;
			this.resist_ballistic = 0.0;
			this.resist_explosive = 0.0;
			this.resist_thermal = 0.0;
			this.cost = 0;
		}
	}
}
