package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class ManAtArms extends Unit{
	public ManAtArms(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.man_at_arms.name");
		speed = config.getInt("units.man_at_arms.speed");
		health = config.getInt("units.man_at_arms.health");
		offense = config.getInt("units.man_at_arms.offense");
		defense = config.getInt("units.man_at_arms.defense");
		range = config.getInt("units.man_at_arms.range");
		vision = config.getInt("units.man_at_arms.vision");
		foodConsumption = config.getInt("units.man_at_arms.food_consumption");
		upkeep = config.getInt("units.man_at_arms.upkeep");
		buildCost = config.getInt("units.man_at_arms.build_cost");
		trainingTime = config.getInt("units.man_at_arms.training_time");
		capacity = config.getInt("units.man_at_arms.capacity");
		civilian = config.getBoolean("units.man_at_arms.civilian");
		material1 = config.getString("units.man_at_arms.material_1.type");
		material1Quantity = config.getInt("units.man_at_arms.material_1.quantity");
		material2 = config.getString("units.man_at_arms.material_2.type");
		material2Quantity = config.getInt("units.man_at_arms.material_2.quantity");
	}
}
