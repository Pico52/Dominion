package com.pico52.dominion.object.unit.sea;

public class Galleon extends SeaUnit{
	public Galleon(){
		speed = 40;
		offense = 85;
		defense = 1000;
		range = 60;
		foodConsumption = 20;
		upkeep = 20;
		buildCost = 1250;
		trainingTime = 288;
		material = "wood";
		tradeShip = true;
		warShip = true;
		shipSize = "large";
	}
}
