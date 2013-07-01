package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Swordsman extends Unit{
	public Swordsman(){
		speed = 10;
		offense = 4;
		defense = 6;
		range = 10;
		foodConsumption = 4;
		upkeep = 8;
		buildCost = 150;
		trainingTime = 48;
		material = "weapon";
	}
}
