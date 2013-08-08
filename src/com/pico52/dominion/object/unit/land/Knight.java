package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Knight extends Unit{
	public Knight(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.knight.name");
		speed = config.getInt("units.knight.speed");
		health = config.getInt("units.knight.health");
		offense = config.getInt("units.knight.offense");
		defense = config.getInt("units.knight.defense");
		range = config.getInt("units.knight.range");
		foodConsumption = config.getInt("units.knight.food_consumption");
		upkeep = config.getInt("units.knight.upkeep");
		buildCost = config.getInt("units.knight.build_cost");
		trainingTime = config.getInt("units.knight.training_time");
		capacity = config.getInt("units.knight.capacity");
		civilian = config.getBoolean("units.knight.civilian");
		material1 = config.getString("units.knight.material_1.type");
		material1Quantity = config.getInt("units.knight.material_1.quantity");
		material2 = config.getString("units.knight.material_2.type");
		material2Quantity = config.getInt("units.knight.material_2.quantity");
	}
}
