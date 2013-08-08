package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Recruit extends Unit{
	public Recruit(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.recruit.name");
		speed = config.getInt("units.recruit.speed");
		health = config.getInt("units.recruit.health");
		offense = config.getInt("units.recruit.offense");
		defense = config.getInt("units.recruit.defense");
		range = config.getInt("units.recruit.range");
		foodConsumption = config.getInt("units.recruit.food_consumption");
		upkeep = config.getInt("units.recruit.upkeep");
		buildCost = config.getInt("units.recruit.build_cost");
		trainingTime = config.getInt("units.recruit.training_time");
		capacity = config.getInt("units.recruit.capacity");
		civilian = config.getBoolean("units.recruit.civilian");
		material1 = config.getString("units.recruit.material_1.type");
		material1Quantity = config.getInt("units.recruit.material_1.quantity");
		material2 = config.getString("units.recruit.material_2.type");
		material2Quantity = config.getInt("units.recruit.material_2.quantity");
	}
}
