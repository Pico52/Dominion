package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Sandworks</b><br>
 * <br>
 * &nbsp;&nbsp;public class Sandworks extends {@link Building}
 * <br>
 * <br>
 * The object controller for all sandworks.
 */
public class Sandworks extends Building{
	public static double 
	smeltingRate, 
	fuelOutput, 
	sellingBonus, 
	glassRate, 
	sandstoneRate, 
	gravelRate, 
	dirtRate, 
	sandToGlassConsumption, 
	sandToSandstoneConsumption, 
	sandstoneToGravelConsumption, 
	gravelToDirtConsumption;
	public static String fuelResource;
	public static boolean consumeFuel;
	
	/** 
	 * <b>Sandworks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Sandworks()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Sandworks} class.
	 */
	public Sandworks(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.sandworks.defense.value");
		defenseBase = config.getBoolean("buildings.sandworks.defense.base");
		workers = config.getInt("buildings.sandworks.workers");
		structure = config.getBoolean("buildings.sandworks.structure");
		sellingBonus = config.getDouble("buildings.sandworks.selling_bonus");
		smeltingRate = config.getDouble("buildings.sandworks.smelting_rate");
		consumeFuel = config.getBoolean("buildings.sandworks.consume_fuel");
		fuelResource = config.getString("buildings.sandworks.fuel_source");
		fuelOutput = config.getDouble("buildings.sandworks.fuel_output");
		glassRate = config.getDouble("buildings.sandworks.production.glass");
		sandstoneRate = config.getDouble("buildings.sandworks.production.sandstone");
		gravelRate = config.getDouble("buildings.sandworks.production.gravel");
		dirtRate = config.getDouble("buildings.sandworks.production.dirt");
		sandToGlassConsumption = config.getDouble("buildings.sandworks.consumption.sand_to_glass");
		sandToSandstoneConsumption = config.getDouble("buildings.sandworks.consumption.sand_to_sandstone");
		sandstoneToGravelConsumption = config.getDouble("buildings.sandworks.consumption.sandstone_to_gravel");
		gravelToDirtConsumption = config.getDouble("buildings.sandworks.consumption.gravel_to_dirt");
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
		if(resource.equalsIgnoreCase("glass") | resource.equalsIgnoreCase("sandtoglass"))
			return glassRate * smeltingRate;
		if(resource.equalsIgnoreCase("sandstone") | resource.equalsIgnoreCase("sandtosandstone"))
			return sandstoneRate * smeltingRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("sandstonetogravel"))
			return gravelRate * smeltingRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
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
		if(resource.equalsIgnoreCase("glass") | resource.equalsIgnoreCase("sandtoglass"))
			return "sand";
		if(resource.equalsIgnoreCase("sandstone") | resource.equalsIgnoreCase("sandtosandstone"))
			return "sand";
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("sandstonetogravel"))
			return "sandstone";
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return "gravel";
		
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
		if(resource.equalsIgnoreCase("glass") | resource.equalsIgnoreCase("sandtoglass"))
			return sandToGlassConsumption * smeltingRate;
		if(resource.equalsIgnoreCase("sandstone") | resource.equalsIgnoreCase("sandtosandstone"))
			return sandToSandstoneConsumption * smeltingRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("sandstonetogravel"))
			return sandstoneToGravelConsumption * smeltingRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return gravelToDirtConsumption * smeltingRate;
		
		return 0;
	}
}