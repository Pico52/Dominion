package com.pico52.dominion.object.building;

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
	public Mine(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 40, true);
		passiveDirt = true;
		passiveGravel = true;
		gravelProduction = 2.6; // - 64 gravel per 24 hours.
		dirtProduction = 2.6;
		redstoneProduction = 15;
		cobblestoneProduction = 15;
		coalProduction = 6.5;
		ironProduction = 1;
		obsidianProduction = .875;
		lapisProduction = .5;
		goldProduction = .5;
		diamondProduction = .25;
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