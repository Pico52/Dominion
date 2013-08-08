package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>SheepRanch</b><br>
 * <br>
 * &nbsp;&nbsp;public class SheepRanch extends {@link Building}
 * <br>
 * <br>
 * The object controller for all sheep ranches.
 */
public class SheepRanch extends Building{

	public static double woolProduction;
	public static int maxLevel, minimumRange;
	public static String resource;
	
	/** 
	 * <b>SheepRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SheepRanch()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SheepRanch} class.
	 */
	public SheepRanch() {
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.sheep_ranch.defense.value");
		defenseBase = config.getBoolean("buildings.sheep_ranch.defense.base");
		workers = config.getInt("buildings.sheep_ranch.workers");
		structure = config.getBoolean("buildings.sheep_ranch.structure");
		resource = config.getString("buildings.sheep_ranch.resource");
		woolProduction = config.getDouble("buildings.sheep_ranch.wool_production");
		maxLevel = config.getInt("buildings.sheep_ranch.maximum_level");
		minimumRange = config.getInt("buildings.sheep_ranch.minimum_range");
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
		if(resource.equalsIgnoreCase(SheepRanch.resource))
			return woolProduction;
		return 0;
	}
}