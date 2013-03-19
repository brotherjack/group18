package com.amphibian.player;

import com.amphibian.tank.Tank;

public final class HumanPlayer extends Player {
	public HumanPlayer(int myCash, Tank myTank, String myName){
		this.cash = myCash;
		this.controlled_tank = myTank;
		this.name = myName;
		this.rounds_won = 0;
	}
	
	public Tank get_controlled_tank() { return this.controlled_tank; }
}
