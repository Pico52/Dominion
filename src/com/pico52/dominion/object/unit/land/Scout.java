package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Scout extends Unit{
	public Scout(){
		speed = 15;
		offense = 1;
		defense = 3;
		range = 10;
		foodConsumption = 1;
		upkeep = 3;
		buildCost = 50;
		trainingTime = 6;
		material = "leather";
	}
}
