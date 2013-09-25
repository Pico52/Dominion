package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Schooner extends SeaUnit{
	public Schooner(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.schooner.name");
		speed = config.getInt("units.schooner.speed");
		health = config.getInt("units.schooner.health");
		offense = config.getInt("units.schooner.offense");
		defense = config.getInt("units.schooner.defense");
		range = config.getInt("units.schooner.range");
		vision = config.getInt("units.schooner.vision");
		foodConsumption = config.getInt("units.schooner.food_consumption");
		upkeep = config.getInt("units.schooner.upkeep");
		buildCost = config.getInt("units.schooner.build_cost");
		trainingTime = config.getInt("units.schooner.training_time");
		capacity = config.getInt("units.schooner.capacity");
		civilian = config.getBoolean("units.schooner.civilian");
		material1 = config.getString("units.schooner.material_1.type");
		material1Quantity = config.getInt("units.schooner.material_1.quantity");
		material2 = config.getString("units.schooner.material_2.type");
		material2Quantity = config.getInt("units.schooner.material_2.quantity");
		shipSize = config.getString("units.schooner.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
