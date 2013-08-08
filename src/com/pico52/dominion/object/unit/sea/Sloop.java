package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Sloop extends SeaUnit{
	public Sloop(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.sloop.name");
		speed = config.getInt("units.sloop.speed");
		health = config.getInt("units.sloop.health");
		offense = config.getInt("units.sloop.offense");
		defense = config.getInt("units.sloop.defense");
		range = config.getInt("units.sloop.range");
		foodConsumption = config.getInt("units.sloop.food_consumption");
		upkeep = config.getInt("units.sloop.upkeep");
		buildCost = config.getInt("units.sloop.build_cost");
		trainingTime = config.getInt("units.sloop.training_time");
		capacity = config.getInt("units.sloop.capacity");
		civilian = config.getBoolean("units.sloop.civilian");
		material1 = config.getString("units.sloop.material_1.type");
		material1Quantity = config.getInt("units.sloop.material_1.quantity");
		material2 = config.getString("units.sloop.material_2.type");
		material2Quantity = config.getInt("units.sloop.material_2.quantity");
		shipSize = config.getString("units.sloop.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
