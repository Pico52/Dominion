package com.pico52.dominion.object.building;

/** 
 * <b>Masonry</b><br>
 * <br>
 * &nbsp;&nbsp;public class Masonry extends {@link Building}
 * <br>
 * <br>
 * The object controller for all masonries.
 */
public class Masonry extends Building{
	public static double baseRate;
	public static double baseConsumption;
	public static double sellingBonus;
	public static double stoneRate;
	public static double gravelRate;
	public static double dirtRate;
	public static double flintRate;
	public static double ironRate;
	public static double goldRate;
	public static double cobbleToStoneConsumption;
	public static double cobbleToGravelConsumption;
	public static double gravelToDirtConsumption;
	public static double gravelToFlintConsumption;
	public static double ironOreToIronConsumption;
	public static double goldOreToGoldConsumption;
	public static String baseConsumptionResource;
	public static boolean consumeBase;
	
	/** 
	 * <b>Masonry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Masonry()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Masonry} class.
	 */
	public Masonry(){  // The constructor will be used in the future for loading the custom configurations.
		super(5, 5);
		baseRate = 64;  // - 64 items per level can be smelted here.
		consumeBase = true; // - Whether or not the Masonry will always consume the base resource.
		baseConsumptionResource = "coal";
		baseConsumption = 8; // - 1 coal per 8.
		sellingBonus = .1;  // - % increase in gold value for trading stone-based resources.
		stoneRate = 1;
		gravelRate = 3;
		dirtRate = 2;
		flintRate = 1;
		ironRate = 1;
		goldRate = 1;
		cobbleToStoneConsumption = 1;
		cobbleToGravelConsumption = 1;
		gravelToDirtConsumption = 1;
		gravelToFlintConsumption = 2;
		ironOreToIronConsumption = 1;
		goldOreToGoldConsumption = 1;
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
			return stoneRate * baseRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return gravelRate * baseRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return dirtRate * baseRate;
		if(resource.equalsIgnoreCase("flint")| resource.equalsIgnoreCase("graveltoflint"))
			return flintRate * baseRate;
		if(resource.equalsIgnoreCase("iron")| resource.equalsIgnoreCase("ironoretoiron"))
			return ironRate * baseRate;
		if(resource.equalsIgnoreCase("gold")| resource.equalsIgnoreCase("goldoretogold"))
			return goldRate * baseRate;
		
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
		if(resource.equalsIgnoreCase("stone") | resource.equalsIgnoreCase("cobbletostone"))
			return "cobblestone";
		else if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return "cobblestone";
		else if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return "gravel";
		else if(resource.equalsIgnoreCase("flint")| resource.equalsIgnoreCase("graveltoflint"))
			return "gravel";
		else if(resource.equalsIgnoreCase("iron")| resource.equalsIgnoreCase("ironoretoiron"))
			return "iron_ore";
		else if(resource.equalsIgnoreCase("gold")| resource.equalsIgnoreCase("goldoretogold"))
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
		if(resource.equalsIgnoreCase("stone") | resource.equalsIgnoreCase("cobbletostone"))
			return cobbleToStoneConsumption * baseRate;
		else if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("cobbletogravel"))
			return cobbleToGravelConsumption * baseRate;
		else if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return gravelToDirtConsumption * baseRate;
		else if(resource.equalsIgnoreCase("flint")| resource.equalsIgnoreCase("graveltoflint"))
			return gravelToFlintConsumption * baseRate;
		else if(resource.equalsIgnoreCase("iron")| resource.equalsIgnoreCase("ironoretoiron"))
			return ironOreToIronConsumption * baseRate;
		else if(resource.equalsIgnoreCase("gold")| resource.equalsIgnoreCase("goldoretogold"))
			return goldOreToGoldConsumption * baseRate;
		
		return 0;
	}
}