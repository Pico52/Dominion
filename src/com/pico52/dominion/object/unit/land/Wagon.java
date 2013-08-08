package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Wagon extends Unit{
	public Wagon(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.wagon.name");
		speed = config.getInt("units.wagon.speed");
		health = config.getInt("units.wagon.health");
		offense = config.getInt("units.wagon.offense");
		defense = config.getInt("units.wagon.defense");
		range = config.getInt("units.wagon.range");
		foodConsumption = config.getInt("units.wagon.food_consumption");
		upkeep = config.getInt("units.wagon.upkeep");
		buildCost = config.getInt("units.wagon.build_cost");
		trainingTime = config.getInt("units.wagon.training_time");
		capacity = config.getInt("units.wagon.capacity");
		civilian = config.getBoolean("units.wagon.civilian");
		material1 = config.getString("units.wagon.material_1.type");
		material1Quantity = config.getInt("units.wagon.material_1.quantity");
		material2 = config.getString("units.wagon.material_2.type");
		material2Quantity = config.getInt("units.wagon.material_2.quantity");
	}
}
