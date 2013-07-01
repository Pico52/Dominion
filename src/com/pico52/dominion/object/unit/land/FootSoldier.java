package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class FootSoldier extends Unit{
	public FootSoldier(){
		speed = 10;
		offense = 3;
		defense = 5;
		range = 10;
		foodConsumption = 3;
		upkeep = 6;
		buildCost = 120;
		trainingTime = 24;
		material = "weapon";
	}
}
