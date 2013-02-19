package com.amphibian.tank;

import com.amphibian.environment.Damage;
import com.amphibian.tankwars.Returnvals;

import android.graphics.Bitmap;

public abstract class Armament {
	protected Bitmap sprite;
	protected Damage damage;
	protected double effect_radius;
	
	protected Returnvals on_detonate(){
		return Returnvals.SUCCESS;
	}
}
