package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>CattleRanch</b><br>
 * <br>
 * &nbsp;&nbsp;public class CattleRanch extends {@link Building}
 * <br>
 * <br>
 * The object controller for all cattle ranches.
 */
public class CattleRanch extends Building{

	public static double leatherProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>CattleRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public CattleRanch()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link CattleRanch} class.
	 */
	public CattleRanch() {
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.cattle_ranch.defense.value");
		defenseBase = config.getBoolean("buildings.cattle_ranch.defense.base");
		workers = config.getInt("buildings.cattle_ranch.workers");
		structure = config.getBoolean("buildings.cattle_ranch.structure");
		leatherProduction = config.getDouble("buildings.cattle_ranch.leather_production");
		maxLevel = config.getInt("buildings.cattle_ranch.maximum_level");
		minimumRange = config.getInt("buildings.cattle_ranch.minimum_range");
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
		if(resource.equalsIgnoreCase("leather"))
			return leatherProduction;
		return 0;
	}
}
