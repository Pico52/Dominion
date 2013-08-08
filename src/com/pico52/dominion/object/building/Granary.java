package com.pico52.dominion.object.building;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Granary</b><br>
 * <br>
 * &nbsp;&nbsp;public class Granary extends {@link Building}
 * <br>
 * <br>
 * The object controller for all granaries.
 */
public class Granary extends Building{
	public static int capacity;
	public static String[] stores;
	
	/** 
	 * <b>Granary</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Granary()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Granary} class.
	 */
	public Granary(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.granary.defense.value");
		defenseBase = config.getBoolean("buildings.granary.defense.base");
		workers = config.getInt("buildings.granary.workers");
		structure = config.getBoolean("buildings.granary.structure");
		capacity = config.getInt("buildings.granary.capacity");
		List<String> storage = config.getStringList("buildings.granary.stores");
		stores = storage.toArray(new String[storage.size()]);
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
		if(resource.equalsIgnoreCase("storage"))
			return capacity;
		else
			return 0;
	}
}