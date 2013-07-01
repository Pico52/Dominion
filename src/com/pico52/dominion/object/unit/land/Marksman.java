package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Marksman extends Unit{
	public Marksman(){
		speed = 11;
		offense = 4;
		defense = 4;
		range = 60;
		foodConsumption = 4;
		upkeep = 7;
		buildCost = 135;
		trainingTime = 48;
		material = "bow";
	}
}
