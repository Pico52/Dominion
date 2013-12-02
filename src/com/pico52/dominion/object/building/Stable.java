package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Stable</b><br>
 * <br>
 * &nbsp;&nbsp;public class Stable extends {@link Building}
 * <br>
 * <br>
 * The object controller for all Stables.
 */
public class Stable extends Building{
	
	public static double capacity, foodConsumption;
	
	/** 
	 * <b>Stable</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Stable()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Stable} class.
	 */
	public Stable(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.stable.defense.value");
		defenseBase = config.getBoolean("buildings.stable.defense.base");
		workers = config.getInt("buildings.stable.workers");
		structure = config.getBoolean("buildings.stable.structure");
		capacity = config.getDouble("buildings.stable.capacity");
		foodConsumption = config.getDouble("buildings.stable.food_consumption");
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
