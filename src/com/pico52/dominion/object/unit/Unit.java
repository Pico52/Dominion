package com.pico52.dominion.object.unit;

public abstract class Unit {
	protected double speed, health;
	protected int 
	offense, defense, range, vision, foodConsumption, 
	upkeep, buildCost, trainingTime, capacity, 
	material1Quantity, material2Quantity;
	protected String material1, material2, name;
	protected boolean civilian;
	
	public double getSpeed(){
		return speed;
	}
	public double getHealth(){
		return health;
	}
	public int getOffense(){
		return offense;
	}
	public int getDefense(){
		return defense;
	}
	public int getRange(){
		return range;
	}
	public int getVision(){
		return vision;
	}
	public int getFoodConsumption(){
		return foodConsumption;
	}
	public int getUpkeep(){
		return upkeep;
	}
	public int getBuildCost(){
		return buildCost;
	}
	public int getTrainingTime(){
		return trainingTime;
	}
	public int getCapacity(){
		return capacity;
	}
	public String getMaterial1(){
		return material1;
	}
	public String getMaterial2(){
		return material2;
	}
	public String getName(){
		return name;
	}
	public int getMaterial1Quantity(){
		return material1Quantity;
	}
	public int getMaterial2Quantity(){
		return material2Quantity;
	}
	public boolean isCivilian(){
		return civilian;
	}
}
