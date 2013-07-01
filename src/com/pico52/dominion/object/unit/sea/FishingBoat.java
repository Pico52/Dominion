package com.pico52.dominion.object.unit.sea;

public class FishingBoat extends SeaUnit{
	public FishingBoat(){
		speed = 30;
		offense = 0;
		defense = 50;
		range = 10;
		foodConsumption = 0;
		upkeep = 0;
		buildCost = 250;
		trainingTime = 60;
		material = "wood";
		tradeShip = true;
		warShip = false;
		shipSize = "small";
	}
}
