package com.pico52.dominion.object.unit;

public abstract class Unit {
	protected double speed, health;
	protected int 
	offense, defense, range, foodConsumption, 
	upkeep, buildCost, trainingTime, storageCapacity;
	protected String material;
	
	public double getSpeed(){
		return speed;
	}
	public double getHealth(){
		return defense; // This will be health
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
	public int getFoodConsumption(){
		return foodConsumption;
	}
	public int getUpkeep(){
		return upkeep;
	}
	public int getBuildCost(){
		return buildCost;
	}
	/*
	 * Training time is the number of unit ticks to produce this unit.
	 */
	public int getTrainingTime(){
		return trainingTime;
	}
	public int getStorageCapacity(){
		return storageCapacity;
	}
	public String getMaterial(){
		return material;
	}
}
