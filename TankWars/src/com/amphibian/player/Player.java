package com.amphibian.player;

import com.amphibian.tank.Tank;

public abstract class Player {
	protected String name;
	protected int cash;
	protected int rounds_won;
	protected Tank controlled_tank;
	
	public String get_name(){ return this.name; }
	public void win_cash(int amount) { this.cash += amount; }
	public void inc_rounds_won() {this.rounds_won++;}
	
	public void associate_with_tank(Tank tank){ 
		this.controlled_tank = tank;
	}
	public Tank get_controlled_tank() { return this.controlled_tank; }
}
