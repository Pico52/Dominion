package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Frigate3rdRate extends SeaUnit{
	public Frigate3rdRate(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.frigate_3rd_rate.name");
		speed = config.getInt("units.frigate_3rd_rate.speed");
		health = config.getInt("units.frigate_3rd_rate.health");
		offense = config.getInt("units.frigate_3rd_rate.offense");
		defense = config.getInt("units.frigate_3rd_rate.defense");
		range = config.getInt("units.frigate_3rd_rate.range");
		vision = config.getInt("units.frigate_3rd_rate.vision");
		foodConsumption = config.getInt("units.frigate_3rd_rate.food_consumption");
		upkeep = config.getInt("units.frigate_3rd_rate.upkeep");
		buildCost = config.getInt("units.frigate_3rd_rate.build_cost");
		trainingTime = config.getInt("units.frigate_3rd_rate.training_time");
		capacity = config.getInt("units.frigate_3rd_rate.capacity");
		civilian = config.getBoolean("units.frigate_3rd_rate.civilian");
		material1 = config.getString("units.frigate_3rd_rate.material_1.type");
		material1Quantity = config.getInt("units.frigate_3rd_rate.material_1.quantity");
		material2 = config.getString("units.frigate_3rd_rate.material_2.type");
		material2Quantity = config.getInt("units.frigate_3rd_rate.material_2.quantity");
		shipSize = config.getString("units.frigate_3rd_rate.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
