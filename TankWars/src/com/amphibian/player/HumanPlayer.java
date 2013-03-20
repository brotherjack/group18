package com.amphibian.player;

import java.util.ArrayList;
import java.util.Collection;

import com.amphibian.tank.Tank;
import com.amphibian.tank.Armament;
import com.amphibian.tank.Shell;
import com.amphibian.tank.Armor;

public final class HumanPlayer extends Player {
	public HumanPlayer(int myCash, Tank myTank, String myName){
		this.cash = myCash;
		this.controlled_tank = myTank;
		this.name = myName;
		this.rounds_won = 0;
		
		this.weapon_inventory = new ArrayList<Armament>();
		this.armor_inventory = new ArrayList<Armor>();
		this.weapon_inventory.add(new Shell.Standard_Shell());
		this.armor_inventory.add(new Armor.StandardArmor());
	}
	
	public Tank get_controlled_tank() { return this.controlled_tank; }
}
