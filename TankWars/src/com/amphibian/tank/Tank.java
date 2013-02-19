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
	private Turret turret_entity;
	
	public Tank(String name, int color, ArrayList<Armor> armors, ArrayList<Armament> weapons, 
				Bitmap bmap, Locomotion lmotion, Turret turret){ //TODO Add constuctor to TANK_CLASS diagram 
		this.name = name;
		this.color = color;
		
		//Make defult weapons, all tanks have infinite standard_shells and standard_armor
		Armor default_armor = new Armor.Standard_Armor();
		Shell default_shell = new Shell.Standard_Shell();
		
		//Add weapons and armor
		this.armor_inventory.add(default_armor);
		if(weapons != null)
			this.armor_inventory.addAll(armors);
		this.weapon_inventory.add(default_shell);
		if(armors != null)
			this.weapon_inventory.addAll(weapons);
		
		//Add means of locomotion and type of turret; also add sprite for tank body 
		this.locomotion_entity = lmotion;
		this.turret_entity = turret;
		this.base_sprite = bmap;
	}
	
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
