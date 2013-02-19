package com.amphibian.tank;

import java.util.ArrayList;
import com.amphibian.environment.*; //<- Don't like this.  Probably not a good idea 
import com.amphibian.tankwars.Returnvals;

import android.graphics.Bitmap;

public class Tank {
	private String name; 
	private int color;
	private ArrayList<Armor> armor_inventory;
	private ArrayList<Armament> weapon_inventory;
	private Armor selected_armor;
	private Bitmap base_sprite;
	private Locomotion locomotion_entity;
	
	public Returnvals take_damage(Damage dmgsrc){
		return Returnvals.SUCCESS;
	}
	
	public Returnvals repair(int hpadded){
		return Returnvals.SUCCESS;
	}
	
	private Returnvals destroy(){
		return Returnvals.SUCCESS;
	}
	
	public Returnvals update_position(Environment environ){
		return Returnvals.SUCCESS;
	}
}
