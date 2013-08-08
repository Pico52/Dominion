package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Masonry</b><br>
 * <br>
 * &nbsp;&nbsp;public class Masonry extends {@link Building}
 * <br>
 * <br>
 * The object controller for all masonries.
 */
public class Masonry extends Building{
	public static double smeltingRate, 
	fuelOutput, 
	sellingBonus, 
	stoneRate, 
	gravelRate, 
	dirtRate, 
	flintRate, 
	ironRate, 
	goldRate, 
	cobbleToStoneConsumption, 
	cobbleToGravelConsumption, 
	gravelToDirtConsumption, 
	gravelToFlintConsumption, 
	ironOreToIronConsumption, 
	goldOreToGoldConsumption;
	public static String fuelResource;
	public static boolean consumeFuel;
	
	/** 
	 * <b>Masonry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Masonry()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Masonry} class.
	 */
	public Masonry(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.masonry.defense.value");
		defenseBase = config.getBoolean("buildings.masonry.defense.base");
		workers = config.getInt("buildings.masonry.workers");
		structure = config.getBoolean("buildings.masonry.structure");
		sellingBonus = config.getDouble("buildings.masonry.selling_bonus");
		smeltingRate = config.getDouble("buildings.masonry.smelting_rate");
		consumeFuel = config.getBoolean("buildings.masonry.consume_fuel");
		fuelResource = config.getString("buildings.masonry.fuel_source");
		fuelOutput = config.getDouble("buildings.masonry.fuel_output");
		stoneRate = config.getDouble("buildings.masonry.production.stone");
		gravelRate = config.getDouble("buildings.masonry.production.gravel");
		dirtRate = config.getDouble("buildings.masonry.production.dirt");
		flintRate = config.getDouble("buildings.masonry.production.flint");
		ironRate = config.getDouble("buildings.masonry.production.iron");
		goldRate = config.getDouble("buildings.masonry.production.gold");
		cobbleToStoneConsumption = config.getDouble("buildings.masonry.consumption.cobblestone_to_stone");
		cobbleToGravelConsumption = config.getDouble("buildings.masonry.consumption.cobblestone_to_gravel");
		gravelToDirtConsumption = config.getDouble("buildings.masonry.consumption.gravel_to_dirt");
		gravelToFlintConsumption = config.getDouble("buildings.masonry.consumption.gravel_to_flint");
		ironOreToIronConsumption = config.getDouble("buildings.masonry.consumption.iron_ore_to_iron");
		goldOreToGoldConsumption = config.getDouble("buildings.masonry.consumption.gold_ore_to_gold");
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
		if(resource.equalsIgnoreCase("stone") | resource.equalsIgnoreCase("cobbletostone"))
			return stoneRate * smeltingRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return gravelRate * smeltingRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return dirtRate * smeltingRate;
		if(resource.equalsIgnoreCase("flint")| resource.equalsIgnoreCase("graveltoflint"))
			return flintRate * smeltingRate;
		if(resource.equalsIgnoreCase("iron_ingot")| resource.equalsIgnoreCase("iron") | resource.equalsIgnoreCase("ironoretoiron"))
			return ironRate * smeltingRate;
		if(resource.equalsIgnoreCase("gold_ingot")| resource.equalsIgnoreCase("gold") | resource.equalsIgnoreCase("goldoretogold"))
			return goldRate * smeltingRate;
		
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
		if(resource.equalsIgnoreCase("stone") | resource.equalsIgnoreCase("cobbletostone"))
			return "cobblestone";
		else if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return "cobblestone";
		else if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return "gravel";
		else if(resource.equalsIgnoreCase("flint")| resource.equalsIgnoreCase("graveltoflint"))
			return "gravel";
		else if(resource.equalsIgnoreCase("iron")| resource.equalsIgnoreCase("ironoretoiron") | resource.equalsIgnoreCase("iron_ingot"))
			return "iron_ore";
		else if(resource.equalsIgnoreCase("gold")| resource.equalsIgnoreCase("goldoretogold") | resource.equalsIgnoreCase("gold_ingot"))
			return "gold_ore";
		
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
		if(resource.equalsIgnoreCase("stone") | resource.equalsIgnoreCase("cobbletostone"))
			return cobbleToStoneConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return cobbleToGravelConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return gravelToDirtConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("flint") | resource.equalsIgnoreCase("graveltoflint"))
			return gravelToFlintConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("iron_ingot") | resource.equalsIgnoreCase("iron")| resource.equalsIgnoreCase("ironoretoiron"))
			return ironOreToIronConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("gold_ingot") | resource.equalsIgnoreCase("gold") | resource.equalsIgnoreCase("goldoretogold"))
			return goldOreToGoldConsumption * smeltingRate;
		
		return 0;
	}
}