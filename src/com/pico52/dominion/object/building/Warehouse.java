package com.pico52.dominion.object.building;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Warehouse</b><br>
 * <br>
 * &nbsp;&nbsp;public class Warehouse extends {@link Building}
 * <br>
 * <br>
 * The object controller for all warehouses.
 */
public class Warehouse extends Building{
	public static int capacity;
	public static String[] stores;
	
	/** 
	 * <b>Warehouse</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Warehouse()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Warehouse} class.
	 */
	public Warehouse(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.warehouse.defense.value");
		defenseBase = config.getBoolean("buildings.warehouse.defense.base");
		workers = config.getInt("buildings.warehouse.workers");
		structure = config.getBoolean("buildings.warehouse.structure");
		capacity = config.getInt("buildings.warehouse.capacity");
		List<String> storage = config.getStringList("buildings.warehouse.stores");
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