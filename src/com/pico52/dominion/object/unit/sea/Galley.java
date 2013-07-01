package com.pico52.dominion.object.unit.sea;

public class Galley extends SeaUnit{
	public Galley(){
		speed = 35;
		offense =60;
		defense = 450;
		range = 10;
		foodConsumption = 5;
		upkeep = 5;
		buildCost = 400;
		trainingTime = 90;
		material = "wood";
		tradeShip = false;
		warShip = true;
		shipSize = "small";
	}
}
