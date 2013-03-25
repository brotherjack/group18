package com.amphibian.player;

import java.util.ArrayList;

import com.amphibian.tank.Tank;
import com.amphibian.tank.Armor;
import com.amphibian.tank.Armament;

public abstract class Player {
	protected String name;
	protected int cash;
	protected int rounds_won;
	protected Tank controlled_tank;
	protected ArrayList<? super Armor> armor_inventory; //TODO fix with HashMap<WeaponType, Integer>
	protected ArrayList<? super Armament> weapon_inventory; //TODO this too
	
	public String get_name(){ return this.name; }
	public void win_cash(int amount) { this.cash += amount; }
	public void inc_rounds_won() {this.rounds_won++;}
	
	public void associate_with_tank(Tank tank){ 
		this.controlled_tank = tank;
	}
	public Tank get_controlled_tank() { return this.controlled_tank; }
	
	public void winRound(){
		this.rounds_won += 1;
		this.cash += 2000;
	}
	
	public abstract boolean getSentience();
}
