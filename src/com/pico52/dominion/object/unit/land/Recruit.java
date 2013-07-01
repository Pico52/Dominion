package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Recruit extends Unit{
	public Recruit(){
		speed = 10;
		offense = 1;
		defense = 1;
		range = 10;
		foodConsumption = 1;
		upkeep = 1;
		buildCost = 10;
		trainingTime = 2;
		material = "population";
	}
}
