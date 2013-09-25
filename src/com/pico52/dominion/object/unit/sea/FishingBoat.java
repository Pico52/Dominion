package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class FishingBoat extends SeaUnit{
	public FishingBoat(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.fishing_boat.name");
		speed = config.getInt("units.fishing_boat.speed");
		health = config.getInt("units.fishing_boat.health");
		offense = config.getInt("units.fishing_boat.offense");
		defense = config.getInt("units.fishing_boat.defense");
		range = config.getInt("units.fishing_boat.range");
		vision = config.getInt("units.fishing_boat.vision");
		foodConsumption = config.getInt("units.fishing_boat.food_consumption");
		upkeep = config.getInt("units.fishing_boat.upkeep");
		buildCost = config.getInt("units.fishing_boat.build_cost");
		trainingTime = config.getInt("units.fishing_boat.training_time");
		capacity = config.getInt("units.fishing_boat.capacity");
		civilian = config.getBoolean("units.fishing_boat.civilian");
		material1 = config.getString("units.fishing_boat.material_1.type");
		material1Quantity = config.getInt("units.fishing_boat.material_1.quantity");
		material2 = config.getString("units.fishing_boat.material_2.type");
		material2Quantity = config.getInt("units.fishing_boat.material_2.quantity");
		shipSize = config.getString("units.fishing_boat.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
