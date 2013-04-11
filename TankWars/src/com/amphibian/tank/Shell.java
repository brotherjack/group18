package com.amphibian.tank;

public abstract class Shell extends Armament {
	
	/**
	 * Called when a shell strikes a tank (ie. intersects with it's border)
	 * @author Thomas Adriaan Hellinger
	 * Returns a DamageTuple containing ( (DamageType)DamageType, (int)DamageAmount )  
	 */
	@Override
	protected DamageTuple directHit() {
		DamageTuple dmg = new DamageTuple();
		dmg.type = this.directDmgType;
		dmg.dmg = this.directDamage;
		return dmg;
	}
	
	public static class Standard_Shell extends Shell{ //TODO make sure this STATIC is OK
		public Standard_Shell(){
			this.directDamage = 10; //Damage for when shell impacts tank
			this.directDmgType = DamageType.KINETIC;
			this.secondaryDamage = 10; //Damage from the explosion the shell causes
			this.secondaryDmgType = DamageType.EXPLOSIVE;
			
			//TODO get a sprite: this.sprite = ;
			this.setEffect_radius(20); //TODO decide on radius
		}

		@Override
		protected void on_detonate() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class Heavy_Shell extends Shell { //TODO make sure this STATIC is OK
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
	
	public static class Massive_Shell extends Shell { //TODO make sure this STATIC is OK
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
