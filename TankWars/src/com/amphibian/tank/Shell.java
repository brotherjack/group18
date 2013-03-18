package com.amphibian.tank;

public abstract class Shell extends Armament {
	public static class Standard_Shell extends Shell{ //TODO make sure this STATIC is OK 
		public Standard_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 20; //TODO decide on radius
		}
	}
	
	public static class Heavy_Shell extends Shell { //TODO make sure this STATIC is OK
		public Heavy_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 40; //TODO decide on radius
		}
	}
	
	public static class Massive_Shell extends Shell { //TODO make sure this STATIC is OK
		public Massive_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.effect_radius = 60; //TODO decide on radius
		}
	}
}
