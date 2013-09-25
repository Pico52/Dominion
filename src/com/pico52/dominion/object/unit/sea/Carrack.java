package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class Carrack extends SeaUnit{
	public Carrack(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.carrack.name");
		speed = config.getInt("units.carrack.speed");
		health = config.getInt("units.carrack.health");
		offense = config.getInt("units.carrack.offense");
		defense = config.getInt("units.carrack.defense");
		range = config.getInt("units.carrack.range");
		vision = config.getInt("units.carrack.vision");
		foodConsumption = config.getInt("units.carrack.food_consumption");
		upkeep = config.getInt("units.carrack.upkeep");
		buildCost = config.getInt("units.carrack.build_cost");
		trainingTime = config.getInt("units.carrack.training_time");
		capacity = config.getInt("units.carrack.capacity");
		civilian = config.getBoolean("units.carrack.civilian");
		material1 = config.getString("units.carrack.material_1.type");
		material1Quantity = config.getInt("units.carrack.material_1.quantity");
		material2 = config.getString("units.carrack.material_2.type");
		material2Quantity = config.getInt("units.carrack.material_2.quantity");
		shipSize = config.getString("units.carrack.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
