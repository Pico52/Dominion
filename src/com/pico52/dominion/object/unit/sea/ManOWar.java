package com.pico52.dominion.object.unit.sea;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

public class ManOWar extends SeaUnit{
	public ManOWar(){
		FileConfiguration config = DominionSettings.getUnitsConfig();
		name = config.getString("units.man_o_war.name");
		speed = config.getInt("units.man_o_war.speed");
		health = config.getInt("units.man_o_war.health");
		offense = config.getInt("units.man_o_war.offense");
		defense = config.getInt("units.man_o_war.defense");
		range = config.getInt("units.man_o_war.range");
		vision = config.getInt("units.man_o_war.vision");
		foodConsumption = config.getInt("units.man_o_war.food_consumption");
		upkeep = config.getInt("units.man_o_war.upkeep");
		buildCost = config.getInt("units.man_o_war.build_cost");
		trainingTime = config.getInt("units.man_o_war.training_time");
		capacity = config.getInt("units.man_o_war.capacity");
		civilian = config.getBoolean("units.man_o_war.civilian");
		material1 = config.getString("units.man_o_war.material_1.type");
		material1Quantity = config.getInt("units.man_o_war.material_1.quantity");
		material2 = config.getString("units.man_o_war.material_2.type");
		material2Quantity = config.getInt("units.man_o_war.material_2.quantity");
		shipSize = config.getString("units.man_o_war.size");
		if(civilian){
			tradeShip = true;
			warShip = false;
		} else {
			tradeShip = false;
			warShip = true;
		}
	}
}
