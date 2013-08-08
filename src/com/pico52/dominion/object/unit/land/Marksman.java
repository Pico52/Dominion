package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Marksman extends Unit{
	public Marksman(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.marksman.name");
		speed = config.getInt("units.marksman.speed");
		health = config.getInt("units.marksman.health");
		offense = config.getInt("units.marksman.offense");
		defense = config.getInt("units.marksman.defense");
		range = config.getInt("units.marksman.range");
		foodConsumption = config.getInt("units.marksman.food_consumption");
		upkeep = config.getInt("units.marksman.upkeep");
		buildCost = config.getInt("units.marksman.build_cost");
		trainingTime = config.getInt("units.marksman.training_time");
		capacity = config.getInt("units.marksman.capacity");
		civilian = config.getBoolean("units.marksman.civilian");
		material1 = config.getString("units.marksman.material_1.type");
		material1Quantity = config.getInt("units.marksman.material_1.quantity");
		material2 = config.getString("units.marksman.material_2.type");
		material2Quantity = config.getInt("units.marksman.material_2.quantity");
	}
}
