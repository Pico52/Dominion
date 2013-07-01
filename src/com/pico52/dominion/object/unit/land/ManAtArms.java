package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class ManAtArms extends Unit{
	public ManAtArms(){
		speed = 30;
		offense = 6;
		defense = 10;
		range = 10;
		foodConsumption = 6;
		upkeep = 10;
		buildCost = 350;
		trainingTime = 96;
		material = "armor";
	}
}
