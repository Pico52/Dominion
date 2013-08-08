package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Frigate1stRate extends SeaUnit{
	public Frigate1stRate(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.frigate_1st_rate.name");
		speed = config.getInt("units.frigate_1st_rate.speed");
		health = config.getInt("units.frigate_1st_rate.health");
		offense = config.getInt("units.frigate_1st_rate.offense");
		defense = config.getInt("units.frigate_1st_rate.defense");
		range = config.getInt("units.frigate_1st_rate.range");
		foodConsumption = config.getInt("units.frigate_1st_rate.food_consumption");
		upkeep = config.getInt("units.frigate_1st_rate.upkeep");
		buildCost = config.getInt("units.frigate_1st_rate.build_cost");
		trainingTime = config.getInt("units.frigate_1st_rate.training_time");
		capacity = config.getInt("units.frigate_1st_rate.capacity");
		civilian = config.getBoolean("units.frigate_1st_rate.civilian");
		material1 = config.getString("units.frigate_1st_rate.material_1.type");
		material1Quantity = config.getInt("units.frigate_1st_rate.material_1.quantity");
		material2 = config.getString("units.frigate_1st_rate.material_2.type");
		material2Quantity = config.getInt("units.frigate_1st_rate.material_2.quantity");
		shipSize = config.getString("units.frigate_1st_rate.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
