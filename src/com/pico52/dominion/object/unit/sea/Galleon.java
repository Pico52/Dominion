package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Galleon extends SeaUnit{
	public Galleon(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.galleon.name");
		speed = config.getInt("units.galleon.speed");
		health = config.getInt("units.galleon.health");
		offense = config.getInt("units.galleon.offense");
		defense = config.getInt("units.galleon.defense");
		range = config.getInt("units.galleon.range");
		vision = config.getInt("units.galleon.vision");
		foodConsumption = config.getInt("units.galleon.food_consumption");
		upkeep = config.getInt("units.galleon.upkeep");
		buildCost = config.getInt("units.galleon.build_cost");
		trainingTime = config.getInt("units.galleon.training_time");
		capacity = config.getInt("units.galleon.capacity");
		civilian = config.getBoolean("units.galleon.civilian");
		material1 = config.getString("units.galleon.material_1.type");
		material1Quantity = config.getInt("units.galleon.material_1.quantity");
		material2 = config.getString("units.galleon.material_2.type");
		material2Quantity = config.getInt("units.galleon.material_2.quantity");
		shipSize = config.getString("units.galleon.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
