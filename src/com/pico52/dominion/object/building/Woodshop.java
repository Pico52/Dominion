package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Woodshop</b><br>
 * <br>
 * &nbsp;&nbsp;public class Woodshop extends {@link Building}
 * <br>
 * <br>
 * The object controller for all woodshops.
 */
public class Woodshop extends Building{
	public static double 
	woodRate, 
	smeltingRate, 
	fuelOutput, 
	sellingBonus, 
	charcoalRate, 
	dirtRate, 
	woodToCharcoalConsumption, 
	woodToDirtConsumption;
	public static String fuelResource;
	public static boolean consumeFuel;
	
	/** 
	 * <b>Woodshop</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Woodshop()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Woodshop} class.
	 */
	public Woodshop(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.woodshop.defense.value");
		defenseBase = config.getBoolean("buildings.woodshop.defense.base");
		workers = config.getInt("buildings.woodshop.workers");
		structure = config.getBoolean("buildings.woodshop.structure");
		sellingBonus = config.getDouble("buildings.woodshop.selling_bonus");
		woodRate = config.getDouble("buildings.woodshop.wood_production");
		smeltingRate = config.getDouble("buildings.woodshop.smelting_rate");
		consumeFuel = config.getBoolean("buildings.woodshop.consume_fuel");
		fuelResource = config.getString("buildings.woodshop.fuel_source");
		fuelOutput = config.getDouble("buildings.woodshop.fuel_output");
		charcoalRate = config.getDouble("buildings.woodshop.production.charcoal");
		dirtRate = config.getDouble("buildings.woodshop.production.dirt");
		woodToCharcoalConsumption = config.getDouble("buildings.woodshop.consumption.wood_to_charcoal");
		woodToDirtConsumption = config.getDouble("buildings.woodshop.consumption.wood_to_dirt");
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
		if(resource.equalsIgnoreCase("wood"))
			return woodRate;
		if(resource.equalsIgnoreCase("charcoal"))
			return charcoalRate * smeltingRate;
		if(resource.equalsIgnoreCase("dirt"))
			return dirtRate * smeltingRate;
		
		return 0;
	}
	
	/** 
	 * <b>getConsumptionResource</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getConsumptionResource({@link String} resource)
	 * <br>
	 * <br>
	 * @param resource - The resource intended to get the consumption resource for.
	 * @return The name of the resource being consumed.  Null if no resource is found.
	 */
	public String getConsumptionResource(String resource) {
		if(resource == null)
			return null;
		if(resource.equalsIgnoreCase("charcoal"))
			return "wood";
		else if(resource.equalsIgnoreCase("dirt"))
			return "wood";
		
		return null;
	}
	
	/** 
	 * <b>getConsumption</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getConsumption({@link String} resource)
	 * <br>
	 * <br>
	 * @param resource - The resource intended to get the rate of consumption for.
	 * @return The number of a resource being consumed.
	 */
	public double getConsumption(String resource) {
		if(resource == null)
			return 0;
		if(resource.equalsIgnoreCase("charcoal"))
			return woodToCharcoalConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("dirt"))
			return woodToDirtConsumption * smeltingRate;
		
		return 0;
	}
}