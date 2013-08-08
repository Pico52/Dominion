package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Militia extends Unit{
	public Militia(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.militia.name");
		speed = config.getInt("units.militia.speed");
		health = config.getInt("units.militia.health");
		offense = config.getInt("units.militia.offense");
		defense = config.getInt("units.militia.defense");
		range = config.getInt("units.militia.range");
		foodConsumption = config.getInt("units.militia.food_consumption");
		upkeep = config.getInt("units.militia.upkeep");
		buildCost = config.getInt("units.militia.build_cost");
		trainingTime = config.getInt("units.militia.training_time");
		capacity = config.getInt("units.militia.capacity");
		civilian = config.getBoolean("units.militia.civilian");
		material1 = config.getString("units.militia.material_1.type");
		material1Quantity = config.getInt("units.militia.material_1.quantity");
		material2 = config.getString("units.militia.material_2.type");
		material2Quantity = config.getInt("units.militia.material_2.quantity");
	}
}
