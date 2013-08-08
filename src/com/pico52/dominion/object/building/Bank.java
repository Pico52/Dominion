package com.pico52.dominion.object.building;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Bank</b><br>
 * <br>
 * &nbsp;&nbsp;public class Bank extends {@link Building}
 * <br>
 * <br>
 * The object controller for all banks.
 */
public class Bank extends Building{
	public static int capacity;
	public static String[] stores;
	
	/** 
	 * <b>Bank</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Bank()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Bank} class.
	 */
	public Bank(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.bank.defense.value");
		defenseBase = config.getBoolean("buildings.bank.defense.base");
		workers = config.getInt("buildings.bank.workers");
		structure = config.getBoolean("buildings.bank.structure");
		capacity = config.getInt("buildings.bank.capacity");
		List<String> storage = config.getStringList("buildings.bank.stores");
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
		if(resource.equalsIgnoreCase("storage"))
			return capacity;
		else
			return 0;
	}
}