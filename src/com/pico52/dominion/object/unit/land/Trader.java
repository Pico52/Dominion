package com.pico52.dominion.object.unit.land;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class Trader extends Unit{
	public Trader(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.trader.name");
		speed = config.getInt("units.trader.speed");
		health = config.getInt("units.trader.health");
		offense = config.getInt("units.trader.offense");
		defense = config.getInt("units.trader.defense");
		range = config.getInt("units.trader.range");
		foodConsumption = config.getInt("units.trader.food_consumption");
		upkeep = config.getInt("units.trader.upkeep");
		buildCost = config.getInt("units.trader.build_cost");
		trainingTime = config.getInt("units.trader.training_time");
		capacity = config.getInt("units.trader.capacity");
		civilian = config.getBoolean("units.trader.civilian");
		material1 = config.getString("units.trader.material_1.type");
		material1Quantity = config.getInt("units.trader.material_1.quantity");
		material2 = config.getString("units.trader.material_2.type");
		material2Quantity = config.getInt("units.trader.material_2.quantity");
	}
}
