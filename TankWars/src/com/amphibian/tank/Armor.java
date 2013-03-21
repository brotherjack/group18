package com.amphibian.tank;

public abstract class Armor {
	protected int hit_points;
	protected int damage;
	protected double resist_explosive;
	protected double resist_ballistic;
	protected double resist_radiation;
	protected double resist_thermal;
	protected int cost;

	public TankCondition takeDamage(int damageadd, DamageType type){ 
		this.damage += damageadd;
		//TODO add code that takes into account resistance
		if(this.damage >= this.hit_points){
			return TankCondition.DESTROYED; //TODO add code to initiate tank destruction
		}
		else{
			return TankCondition.OPERATIONAL;
		}
	}
	
	public int getHPLeft(){
		return this.hit_points - this.damage;
	}
	
	public static class StandardArmor extends Armor{
		protected final int hit_points = 100;
		public StandardArmor(){
			this.damage = 0;
			this.resist_ballistic = 0.0;
			this.resist_explosive = 0.0;
			this.resist_thermal = 0.0;
			this.cost = 0;
		}
		
		public int getHPLeft(){
			return this.hit_points - this.damage;
		}		
	}
}
