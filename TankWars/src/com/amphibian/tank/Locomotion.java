package com.amphibian.tank;

import android.graphics.Bitmap;

public abstract class Locomotion {
	protected int speed;
	protected Bitmap sprite;
	protected int cost;
	protected int weight;
	
	public static class Treads extends Locomotion{
		public Treads(){
			this.speed = 5; //TODO make an enum for this?
			//TODO this.sprite = NEED SPRITE!
			this.cost = 0;
			this.weight = 0; //TODO worry about this latter!
		}
	}
}
