package com.pico52.dominion.object.unit.land;

import com.pico52.dominion.object.unit.Unit;

public class Trader extends Unit{
	public Trader(){
		speed = 12;
		offense = 1;
		defense = 5;
		range = 10;
		foodConsumption = 1;
		upkeep = 10;
		buildCost = 300;
		trainingTime = 36;
		material = "none";
	}
}
