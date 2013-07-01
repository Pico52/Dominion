package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Archer extends Unit{
	public Archer(){
		speed = 11;
		offense = 3;
		defense = 3;
		range = 50;
		foodConsumption = 3;
		upkeep = 5;
		buildCost = 100;
		trainingTime = 24;
		material = "bow";
	}
}
