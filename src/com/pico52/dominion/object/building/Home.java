package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Home</b><br>
 * <br>
 * &nbsp;&nbsp;public class Home extends {@link Building}
 * <br>
 * <br>
 * The object controller for all homes.
 */
public class Home extends Building{
	public static String resource;
	public static double populationProduction;
	public static double basePopulation;
	public static int capacity;
	
	/** 
	 * <b>Home</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Home()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Home} class.
	 */
	public Home(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.home.defense.value");
		defenseBase = config.getBoolean("buildings.home.defense.base");
		workers = config.getInt("buildings.home.workers");
		structure = config.getBoolean("buildings.home.structure");
		resource = config.getString("buildings.home.resource");
		populationProduction = config.getDouble("buildings.home.population_production");
		basePopulation = config.getDouble("buildings.home.base_population");
		capacity = config.getInt("buildings.home.capacity");
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
		if(resource.equalsIgnoreCase(Home.resource))
			return populationProduction;
		else
			return 0;
	}
}
