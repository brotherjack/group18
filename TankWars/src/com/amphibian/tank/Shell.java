package com.amphibian.tank;

import com.amphibian.tank.Accessory.Weapons;

public abstract class Shell extends Armament {
	public static class Standard_Shell extends Shell{ //TODO make sure this STATIC is OK 
		public Standard_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 20; //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Weapons getWeaponType() {
			return Accessory.Weapons.STANDARD_SHELL;
		}
	}
	
	public static class Heavy_Shell extends Shell { //TODO make sure this STATIC is OK
		public Heavy_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 40; //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Weapons getWeaponType() {
			return Accessory.Weapons.HEAVY_SHELL;
		}
	}
	
	public static class Massive_Shell extends Shell { //TODO make sure this STATIC is OK
		public Massive_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 60; //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Weapons getWeaponType() {
			return Accessory.Weapons.MASSIVE_SHELL;
		}
	}
}
