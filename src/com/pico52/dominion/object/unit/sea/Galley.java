package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Galley extends SeaUnit{
	public Galley(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.galley.name");
		speed = config.getInt("units.galley.speed");
		health = config.getInt("units.galley.health");
		offense = config.getInt("units.galley.offense");
		defense = config.getInt("units.galley.defense");
		range = config.getInt("units.galley.range");
		vision = config.getInt("units.galley.vision");
		foodConsumption = config.getInt("units.galley.food_consumption");
		upkeep = config.getInt("units.galley.upkeep");
		buildCost = config.getInt("units.galley.build_cost");
		trainingTime = config.getInt("units.galley.training_time");
		capacity = config.getInt("units.galley.capacity");
		civilian = config.getBoolean("units.galley.civilian");
		material1 = config.getString("units.galley.material_1.type");
		material1Quantity = config.getInt("units.galley.material_1.quantity");
		material2 = config.getString("units.galley.material_2.type");
		material2Quantity = config.getInt("units.galley.material_2.quantity");
		shipSize = config.getString("units.galley.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
