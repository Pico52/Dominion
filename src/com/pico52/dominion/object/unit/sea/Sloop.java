package com.pico52.dominion.object.unit.sea;

public class Sloop extends SeaUnit{
	public Sloop(){
		speed = 45;
		offense = 40;
		defense = 400;
		range = 45;
		foodConsumption = 8;
		upkeep = 8;
		buildCost = 450;
		trainingTime = 168;
		material = "wood";
		tradeShip = true;
		warShip = false;
		shipSize = "medium";
	}
}
