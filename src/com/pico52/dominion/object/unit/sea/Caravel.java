package com.pico52.dominion.object.unit.sea;

public class Caravel extends SeaUnit{
	public Caravel(){
		speed = 40;
		offense = 30;
		defense = 500;
		range = 45;
		foodConsumption = 5;
		upkeep = 5;
		buildCost = 500;
		trainingTime = 144;
		material = "wood";
		tradeShip = true;
		warShip = false;
		shipSize = "medium";
	}
}
