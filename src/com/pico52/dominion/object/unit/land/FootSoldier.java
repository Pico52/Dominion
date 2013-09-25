package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class FootSoldier extends Unit{
	public FootSoldier(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.foot_soldier.name");
		speed = config.getInt("units.foot_soldier.speed");
		health = config.getInt("units.foot_soldier.health");
		offense = config.getInt("units.foot_soldier.offense");
		defense = config.getInt("units.foot_soldier.defense");
		range = config.getInt("units.foot_soldier.range");
		vision = config.getInt("units.foot_soldier.vision");
		foodConsumption = config.getInt("units.foot_soldier.food_consumption");
		upkeep = config.getInt("units.foot_soldier.upkeep");
		buildCost = config.getInt("units.foot_soldier.build_cost");
		trainingTime = config.getInt("units.foot_soldier.training_time");
		capacity = config.getInt("units.foot_soldier.capacity");
		civilian = config.getBoolean("units.foot_soldier.civilian");
		material1 = config.getString("units.foot_soldier.material_1.type");
		material1Quantity = config.getInt("units.foot_soldier.material_1.quantity");
		material2 = config.getString("units.foot_soldier.material_2.type");
		material2Quantity = config.getInt("units.foot_soldier.material_2.quantity");
	}
}
