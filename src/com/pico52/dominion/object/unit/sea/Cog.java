package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Cog extends SeaUnit{
	public Cog(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.cog.name");
		speed = config.getInt("units.cog.speed");
		health = config.getInt("units.cog.health");
		offense = config.getInt("units.cog.offense");
		defense = config.getInt("units.cog.defense");
		range = config.getInt("units.cog.range");
		foodConsumption = config.getInt("units.cog.food_consumption");
		upkeep = config.getInt("units.cog.upkeep");
		buildCost = config.getInt("units.cog.build_cost");
		trainingTime = config.getInt("units.cog.training_time");
		capacity = config.getInt("units.cog.capacity");
		civilian = config.getBoolean("units.cog.civilian");
		material1 = config.getString("units.cog.material_1.type");
		material1Quantity = config.getInt("units.cog.material_1.quantity");
		material2 = config.getString("units.cog.material_2.type");
		material2Quantity = config.getInt("units.cog.material_2.quantity");
		shipSize = config.getString("units.cog.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
