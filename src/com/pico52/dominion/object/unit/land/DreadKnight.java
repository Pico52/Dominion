package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class DreadKnight extends Unit{
	public DreadKnight(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.dread_knight.name");
		speed = config.getInt("units.dread_knight.speed");
		health = config.getInt("units.dread_knight.health");
		offense = config.getInt("units.dread_knight.offense");
		defense = config.getInt("units.dread_knight.defense");
		range = config.getInt("units.dread_knight.range");
		foodConsumption = config.getInt("units.dread_knight.food_consumption");
		upkeep = config.getInt("units.dread_knight.upkeep");
		buildCost = config.getInt("units.dread_knight.build_cost");
		trainingTime = config.getInt("units.dread_knight.training_time");
		capacity = config.getInt("units.dread_knight.capacity");
		civilian = config.getBoolean("units.dread_knight.civilian");
		material1 = config.getString("units.dread_knight.material_1.type");
		material1Quantity = config.getInt("units.dread_knight.material_1.quantity");
		material2 = config.getString("units.dread_knight.material_2.type");
		material2Quantity = config.getInt("units.dread_knight.material_2.quantity");
	}
}
