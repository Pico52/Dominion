package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Library</b><br>
 * <br>
 * &nbsp;&nbsp;public class Library extends {@link Building}
 * <br>
 * <br>
 * The object controller for all libraries.
 */
public class Library extends Building{
	public static String resource;
	public static double manaProduction;
	public static int manaCapacity;
	
	/** 
	 * <b>Library</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Library()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Library} class.
	 */
	public Library(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.library.defense.value");
		defenseBase = config.getBoolean("buildings.library.defense.base");
		workers = config.getInt("buildings.library.workers");
		structure = config.getBoolean("buildings.library.structure");
		resource = config.getString("buildings.library.resource");
		manaProduction = config.getDouble("buildings.library.mana_production");
		manaCapacity = config.getInt("buildings.library.mana_capacity");
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
		if(resource.equalsIgnoreCase(Library.resource))
			return manaProduction;
		else
			return 0;
	}
	
	/** 
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the building.
	 * @return The rate that this building produces its resource every building tick.
	 */
	public double getProduction(int level) {
		return manaProduction * level;
	}
}