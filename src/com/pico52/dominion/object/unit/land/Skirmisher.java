package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Skirmisher extends Unit{
	public Skirmisher(){
		speed = 11;
		offense = 2;
		defense = 2;
		range = 40;
		foodConsumption = 2;
		upkeep = 3;
		buildCost = 80;
		trainingTime = 6;
		material = "wood";
	}
}
