package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Lighthouse</b><br>
 * <br>
 * &nbsp;&nbsp;public class Lighthouse extends {@link Building}
 * <br>
 * <br>
 * The object controller for all lighthouses.
 */
public class Lighthouse extends Building{
	
	public static double speedBonus;
	public static int maxLevel, range;
	
	/** 
	 * <b>Lighthouse</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Lighthouse()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Lighthouse} class.
	 */
	public Lighthouse(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.lighthouse.defense.value");
		defenseBase = config.getBoolean("buildings.lighthouse.defense.base");
		workers = config.getInt("buildings.lighthouse.workers");
		structure = config.getBoolean("buildings.lighthouse.structure");
		maxLevel = config.getInt("buildings.lighthouse.maximum_level");
		range = config.getInt("buildings.lighthouse.range");
		speedBonus = config.getDouble("buildings.lighthouse.speed_bonus");
	}
	
	/** 
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction({@link String} resource)
	 * <br>
	 * <br>
	 * @param resource - The resource intended to get the rate of production for.
	 * @return The rate that this building produces the specified resource every building tick.
	 */
	@Override
	public double getProduction(String resource) {
		return 0;  // Lighthouses don't produce anything; they simply give local benefits.
	}
}