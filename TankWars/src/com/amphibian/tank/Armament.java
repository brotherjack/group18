package com.amphibian.tank;

import android.graphics.Bitmap;

public abstract class Armament {
	protected Bitmap sprite;
	protected int damage;
	protected double effect_radius;
	
	protected abstract void on_detonate();
	protected abstract Accessory.Weapons getWeaponType();
}
