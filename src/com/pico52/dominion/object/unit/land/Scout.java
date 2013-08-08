package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Scout extends Unit{
	public Scout(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.scout.name");
		speed = config.getInt("units.scout.speed");
		health = config.getInt("units.scout.health");
		offense = config.getInt("units.scout.offense");
		defense = config.getInt("units.scout.defense");
		range = config.getInt("units.scout.range");
		foodConsumption = config.getInt("units.scout.food_consumption");
		upkeep = config.getInt("units.scout.upkeep");
		buildCost = config.getInt("units.scout.build_cost");
		trainingTime = config.getInt("units.scout.training_time");
		capacity = config.getInt("units.scout.capacity");
		civilian = config.getBoolean("units.scout.civilian");
		material1 = config.getString("units.scout.material_1.type");
		material1Quantity = config.getInt("units.scout.material_1.quantity");
		material2 = config.getString("units.scout.material_2.type");
		material2Quantity = config.getInt("units.scout.material_2.quantity");
	}
}
