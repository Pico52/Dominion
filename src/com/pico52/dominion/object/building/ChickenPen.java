package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>ChickenPen</b><br>
 * <br>
 * &nbsp;&nbsp;public class ChickenPen extends {@link Building}
 * <br>
 * <br>
 * The object controller for all chicken pens.
 */
public class ChickenPen extends Building{

	public static double featherProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>ChickenPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ChickenPen()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ChickenPen} class.
	 */
	public ChickenPen() {
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.chicken_pen.defense.value");
		defenseBase = config.getBoolean("buildings.chicken_pen.defense.base");
		workers = config.getInt("buildings.chicken_pen.workers");
		structure = config.getBoolean("buildings.chicken_pen.structure");
		featherProduction = config.getDouble("buildings.chicken_pen.feather_production");
		maxLevel = config.getInt("buildings.chicken_pen.maximum_level");
		minimumRange = config.getInt("buildings.chicken_pen.minimum_range");
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
		if(resource == null)
			return 0;
		if(resource.equalsIgnoreCase("feather"))
			return featherProduction;
		return 0;
	}
}