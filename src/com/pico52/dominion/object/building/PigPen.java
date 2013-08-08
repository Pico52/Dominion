package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>PigPen</b><br>
 * <br>
 * &nbsp;&nbsp;public class PigPen extends {@link Building}
 * <br>
 * <br>
 * The object controller for all pig pens.
 */
public class PigPen extends Building{

	public static double foodProduction;
	public static int maxLevel, minimumRange;
	public static String resource;
	
	/** 
	 * <b>PigPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PigPen()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PigPen} class.
	 */
	public PigPen() {
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.pig_pen.defense.value");
		defenseBase = config.getBoolean("buildings.pig_pen.defense.base");
		workers = config.getInt("buildings.pig_pen.workers");
		structure = config.getBoolean("buildings.pig_pen.structure");
		resource = config.getString("buildings.pig_pen.resource");
		foodProduction = config.getDouble("buildings.pig_pen.food_production");
		maxLevel = config.getInt("buildings.pig_pen.maximum_level");
		minimumRange = config.getInt("buildings.pig_pen.minimum_range");
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
		if(resource.equalsIgnoreCase(PigPen.resource))
			return foodProduction;
		return 0;
	}
}