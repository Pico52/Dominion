package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Skirmisher extends Unit{
	public Skirmisher(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.skirmisher.name");
		speed = config.getInt("units.skirmisher.speed");
		health = config.getInt("units.skirmisher.health");
		offense = config.getInt("units.skirmisher.offense");
		defense = config.getInt("units.skirmisher.defense");
		range = config.getInt("units.skirmisher.range");
		foodConsumption = config.getInt("units.skirmisher.food_consumption");
		upkeep = config.getInt("units.skirmisher.upkeep");
		buildCost = config.getInt("units.skirmisher.build_cost");
		trainingTime = config.getInt("units.skirmisher.training_time");
		capacity = config.getInt("units.skirmisher.capacity");
		civilian = config.getBoolean("units.skirmisher.civilian");
		material1 = config.getString("units.skirmisher.material_1.type");
		material1Quantity = config.getInt("units.skirmisher.material_1.quantity");
		material2 = config.getString("units.skirmisher.material_2.type");
		material2Quantity = config.getInt("units.skirmisher.material_2.quantity");
	}
}
