package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Knight extends Unit{
	public Knight(){
		speed = 25;
		offense = 8;
		defense = 14;
		range = 10;
		foodConsumption = 8;
		upkeep = 12;
		buildCost = 400;
		trainingTime = 144;
		material = "armor";
	}
}
