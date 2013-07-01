package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Militia extends Unit{
	public Militia(){
		speed = 10;
		offense = 2;
		defense = 3;
		range = 10;
		foodConsumption = 1;
		upkeep = 4;
		buildCost = 100;
		trainingTime = 6;
		material = "leather";
	}
}
