package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Swordsman extends Unit{
	public Swordsman(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.swordsman.name");
		speed = config.getInt("units.swordsman.speed");
		health = config.getInt("units.swordsman.health");
		offense = config.getInt("units.swordsman.offense");
		defense = config.getInt("units.swordsman.defense");
		range = config.getInt("units.swordsman.range");
		vision = config.getInt("units.swordsman.vision");
		foodConsumption = config.getInt("units.swordsman.food_consumption");
		upkeep = config.getInt("units.swordsman.upkeep");
		buildCost = config.getInt("units.swordsman.build_cost");
		trainingTime = config.getInt("units.swordsman.training_time");
		capacity = config.getInt("units.swordsman.capacity");
		civilian = config.getBoolean("units.swordsman.civilian");
		material1 = config.getString("units.swordsman.material_1.type");
		material1Quantity = config.getInt("units.swordsman.material_1.quantity");
		material2 = config.getString("units.swordsman.material_2.type");
		material2Quantity = config.getInt("units.swordsman.material_2.quantity");
	}
}
