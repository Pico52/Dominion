package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Fletcher</b><br>
 * <br>
 * &nbsp;&nbsp;public class Fletcher extends {@link Building}
 * <br>
 * <br>
 * The object controller for all fletcheries.
 */
public class Fletcher extends Building{
	public static double 
	arrowProduction, flintConsumption, woodConsumption, featherConsumption;
	
	/** 
	 * <b>Fletcher</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Fletcher()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Fletcher} class.
	 */
	public Fletcher(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.fletcher.defense.value");
		defenseBase = config.getBoolean("buildings.fletcher.defense.base");
		workers = config.getInt("buildings.fletcher.workers");
		structure = config.getBoolean("buildings.fletcher.structure");
		arrowProduction = config.getDouble("buildings.fletcher.arrow_production");
		flintConsumption = config.getDouble("buildings.fletcher.costs.flint");
		woodConsumption = config.getDouble("buildings.fletcher.costs.wood");
		featherConsumption = config.getDouble("buildings.fletcher.costs.feather");
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
		if(resource.equalsIgnoreCase("arrow") | resource.equalsIgnoreCase("arrows"))
			return arrowProduction;
		return 0;
	}
}