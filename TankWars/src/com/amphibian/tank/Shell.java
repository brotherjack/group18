package com.amphibian.tank;

public abstract class Shell<T> extends Armament<T> {
	
	@Override
	protected DamageTuple directHit() {
		DamageTuple dmg = new DamageTuple();
		dmg.type = DamageType.KINETIC;
		dmg.dmg = 10;
		return dmg;
	}
	
	public static class Standard_Shell<T> extends Shell<T>{ //TODO make sure this STATIC is OK 
		public Standard_Shell(){
			//TODO get a sprite: this.sprite = ;
			this.setEffect_radius(20); //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class Heavy_Shell<T> extends Shell<T> { //TODO make sure this STATIC is OK
		public Heavy_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.setEffect_radius(40); //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class Massive_Shell<T> extends Shell<T> { //TODO make sure this STATIC is OK
		public Massive_Shell(){
			//TODO add damage class here: this.damage = ;
			//TODO get a sprite: this.sprite = ;
			this.setEffect_radius(60); //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}
	}
}
