package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>ArcheryRange</b><br>
 * <br>
 * &nbsp;&nbsp;public class ArcheryRange extends {@link Building}
 * <br>
 * <br>
 * The object controller for all archery ranges.
 */
public class ArcheryRange extends Building{
	public static double experienceGain;
	
	/** 
	 * <b>ArcheryRange</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ArcheryRange()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ArcheryRange} class.
	 */
	public ArcheryRange(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.archery_range.defense.value");
		defenseBase = config.getBoolean("buildings.archery_range.defense.base");
		workers = config.getInt("buildings.archery_range.workers");
		structure = config.getBoolean("buildings.archery_range.structure");
		experienceGain = config.getInt("buildings.archery_range.experience");
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
		if(resource.equalsIgnoreCase("experience"))
			return experienceGain;
		
		return 0;
	}
}