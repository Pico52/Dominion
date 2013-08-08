package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Mine</b><br>
 * <br>
 * &nbsp;&nbsp;public class Mine extends {@link Building}
 * <br>
 * <br>
 * The object controller for all mines.
 */
public class Mine extends Building{
	public static double gravelProduction, dirtProduction, redstoneProduction, cobblestoneProduction, 
	coalProduction, ironProduction, obsidianProduction, lapisProduction, goldProduction, diamondProduction;
	public static boolean passiveDirt, passiveGravel;
	
	
	/** 
	 * <b>Mine</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Mine()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Mine} class.
	 */
	public Mine(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.mine.defense.value");
		defenseBase = config.getBoolean("buildings.mine.defense.base");
		workers = config.getInt("buildings.mine.workers");
		structure = config.getBoolean("buildings.mine.structure");
		passiveDirt = config.getBoolean("buildings.mine.passive_dirt");
		passiveGravel = config.getBoolean("buildings.mine.passive_gravel");
		gravelProduction = config.getDouble("buildings.mine.production.gravel");
		dirtProduction = config.getDouble("buildings.mine.production.dirt");
		redstoneProduction = config.getDouble("buildings.mine.production.redstone");
		cobblestoneProduction = config.getDouble("buildings.mine.production.cobblestone");
		coalProduction = config.getDouble("buildings.mine.production.coal");
		ironProduction = config.getDouble("buildings.mine.production.iron_ore");
		obsidianProduction = config.getDouble("buildings.mine.production.obsidian");
		lapisProduction = config.getDouble("buildings.mine.production.lapis_lazuli");
		goldProduction = config.getDouble("buildings.mine.production.gold_ore");
		diamondProduction = config.getDouble("buildings.mine.production.diamond");
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
		if(resource.equalsIgnoreCase("gravel"))
			return gravelProduction;
		if(resource.equalsIgnoreCase("dirt"))
			return dirtProduction;
		if(resource.equalsIgnoreCase("redstone"))
			return redstoneProduction;
		if(resource.equalsIgnoreCase("cobblestone") | resource.equalsIgnoreCase("cobble"))
			return cobblestoneProduction;
		if(resource.equalsIgnoreCase("coal"))
			return coalProduction;
		if(resource.equalsIgnoreCase("iron") | resource.equalsIgnoreCase("iron_ore"))
			return ironProduction;
		if(resource.equalsIgnoreCase("obsidian"))
			return obsidianProduction;
		if(resource.equalsIgnoreCase("lapislazuli") | resource.equalsIgnoreCase("lapis"))
			return lapisProduction;
		if(resource.equalsIgnoreCase("gold") | resource.equalsIgnoreCase("gold_ore"))
			return goldProduction;
		if(resource.equalsIgnoreCase("diamond"))
			return diamondProduction;
		
		return 0;
	}
}