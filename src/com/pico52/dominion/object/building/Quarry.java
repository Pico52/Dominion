package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Quarry</b><br>
 * <br>
 * &nbsp;&nbsp;public class Quarry extends {@link Building}
 * <br>
 * <br>
 * The object controller for all quarries.
 */
public class Quarry extends Building{
	public static double clayProduction, sandProduction, stoneProduction;
	public static int maxLevel;
	
	
	/** 
	 * <b>Quarry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Quarry()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Quarry} class.
	 */
	public Quarry(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.quarry.defense.value");
		defenseBase = config.getBoolean("buildings.quarry.defense.base");
		workers = config.getInt("buildings.quarry.workers");
		structure = config.getBoolean("buildings.quarry.structure");
		maxLevel = config.getInt("buildings.quarry.maximum_level");
		clayProduction = config.getDouble("buildings.quarry.production.clay");
		sandProduction = config.getDouble("buildings.quarry.production.sand");
		stoneProduction = config.getDouble("buildings.quarry.production.stone");
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
		if(resource.equalsIgnoreCase("clay"))
			return clayProduction;
		if(resource.equalsIgnoreCase("sand"))
			return sandProduction;
		if(resource.equalsIgnoreCase("stone"))
			return stoneProduction;
		
		return 0;
	}
}