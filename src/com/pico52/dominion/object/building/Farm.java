package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Farm</b><br>
 * <br>
 * &nbsp;&nbsp;public class Farm extends {@link Building}
 * <br>
 * <br>
 * The object controller for all farms.
 */
public class Farm extends Building{
	
	public static double autoHarvestBonus, 
	wheatProduction, melonProduction, pumpkinProduction, mushroomProduction, potatoProduction;
	
	/** 
	 * <b>Farm</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Farm()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Farm} class.
	 */
	public Farm(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.farm.defense.value");
		defenseBase = config.getBoolean("buildings.farm.defense.base");
		workers = config.getInt("buildings.farm.workers");
		structure = config.getBoolean("buildings.farm.structure");
		wheatProduction = config.getDouble("buildings.farm.production.wheat");
		melonProduction = config.getDouble("buildings.farm.production.melon");
		pumpkinProduction = config.getDouble("buildings.farm.production.pumpkin");
		mushroomProduction = config.getDouble("buildings.farm.production.mushroom");
		potatoProduction = config.getDouble("buildings.farm.production.potato");
		autoHarvestBonus = config.getDouble("buildings.farm.auto_harvest_bonus");
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
		if(resource.equalsIgnoreCase("wheat"))
			return wheatProduction;
		if(resource.equalsIgnoreCase("melon"))
			return melonProduction;
		if(resource.equalsIgnoreCase("pumpkin"))
			return pumpkinProduction;
		if(resource.equalsIgnoreCase("mushroom"))
			return mushroomProduction;
		if(resource.equalsIgnoreCase("potato"))
			return potatoProduction;
		
		return 0;
	}
}