package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Inn</b><br>
 * <br>
 * &nbsp;&nbsp;public class Inn extends {@link Building}
 * <br>
 * <br>
 * The object controller for all Inns.
 */
public class Inn extends Building{
	public static String resource;
	public static double wealthProduction;
	public static int visitorCapacity;
	
	/** 
	 * <b>Inn</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Inn()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Inn} class.
	 */
	public Inn(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.inn.defense.value");
		defenseBase = config.getBoolean("buildings.inn.defense.base");
		workers = config.getInt("buildings.inn.workers");
		structure = config.getBoolean("buildings.inn.structure");
		resource = config.getString("buildings.inn.resource");
		wealthProduction = config.getDouble("buildings.inn.wealth_production");
		visitorCapacity = config.getInt("buildings.inn.visitor_capacity");
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
		if(resource.equalsIgnoreCase(Inn.resource))
			return wealthProduction;
		else
			return 0;
	}
}