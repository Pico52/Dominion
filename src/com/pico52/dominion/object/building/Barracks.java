package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Barracks</b><br>
 * <br>
 * &nbsp;&nbsp;public class Barracks extends {@link Building}
 * <br>
 * <br>
 * The object controller for all barracks.
 */
public class Barracks extends Building{
	public static double capacity;
	
	/** 
	 * <b>Barracks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Barracks()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Barracks} class.
	 */
	public Barracks(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.barracks.defense.value");
		defenseBase = config.getBoolean("buildings.barracks.defense.base");
		workers = config.getInt("buildings.barracks.workers");
		structure = config.getBoolean("buildings.barracks.structure");
		capacity = config.getDouble("buildings.barracks.capacity");
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
		return 0;
	}
}