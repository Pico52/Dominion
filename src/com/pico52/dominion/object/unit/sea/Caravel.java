package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Caravel extends SeaUnit{
	public Caravel(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.caravel.name");
		speed = config.getInt("units.caravel.speed");
		health = config.getInt("units.caravel.health");
		offense = config.getInt("units.caravel.offense");
		defense = config.getInt("units.caravel.defense");
		range = config.getInt("units.caravel.range");
		vision = config.getInt("units.caravel.vision");
		foodConsumption = config.getInt("units.caravel.food_consumption");
		upkeep = config.getInt("units.caravel.upkeep");
		buildCost = config.getInt("units.caravel.build_cost");
		trainingTime = config.getInt("units.caravel.training_time");
		capacity = config.getInt("units.caravel.capacity");
		civilian = config.getBoolean("units.caravel.civilian");
		material1 = config.getString("units.caravel.material_1.type");
		material1Quantity = config.getInt("units.caravel.material_1.quantity");
		material2 = config.getString("units.caravel.material_2.type");
		material2Quantity = config.getInt("units.caravel.material_2.quantity");
		shipSize = config.getString("units.caravel.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
