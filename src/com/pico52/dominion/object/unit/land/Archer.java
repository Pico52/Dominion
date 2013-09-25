package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Archer extends Unit{
	public Archer(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.archer.name");
		speed = config.getInt("units.archer.speed");
		health = config.getInt("units.archer.health");
		offense = config.getInt("units.archer.offense");
		defense = config.getInt("units.archer.defense");
		range = config.getInt("units.archer.range");
		vision = config.getInt("units.archer.vision");
		foodConsumption = config.getInt("units.archer.food_consumption");
		upkeep = config.getInt("units.archer.upkeep");
		buildCost = config.getInt("units.archer.build_cost");
		trainingTime = config.getInt("units.archer.training_time");
		capacity = config.getInt("units.archer.capacity");
		civilian = config.getBoolean("units.archer.civilian");
		material1 = config.getString("units.archer.material_1.type");
		material1Quantity = config.getInt("units.archer.material_1.quantity");
		material2 = config.getString("units.archer.material_2.type");
		material2Quantity = config.getInt("units.archer.material_2.quantity");
	}
}
