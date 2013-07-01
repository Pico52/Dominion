package com.pico52.dominion.object.building;

/** 
 * <b>Woodshop</b><br>
 * <br>
 * &nbsp;&nbsp;public class Woodshop extends {@link Building}
 * <br>
 * <br>
 * The object controller for all woodshops.
 */
public class Woodshop extends Building{
	public static double woodRate;
	public static double smeltingRate;
	public static double baseConsumption;
	public static double sellingBonus;
	public static double charcoalRate;
	public static double dirtRate;
	public static double woodToCharcoalConsumption;
	public static double woodToDirtConsumption;
	public static String baseConsumptionResource;
	public static boolean consumeBase;
	
	/** 
	 * <b>Woodshop</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Woodshop()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Woodshop} class.
	 */
	public Woodshop(){  // The constructor will be used in the future for loading the custom configurations.
		super(5, 5, true);
		woodRate = 2.6; // - 32 wood per 24 hours.
		smeltingRate = 64;  // - 64 items per level can be smelted here.
		consumeBase = true;
		baseConsumptionResource = "coal";
		baseConsumption = 8; // - 1 coal per 8.
		sellingBonus = .05;  // - % increase to gold value from trading with wood-based resources.
		charcoalRate = 1;
		dirtRate = 4;
		woodToCharcoalConsumption = 1;
		woodToDirtConsumption = 1;
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
		if(resource.equalsIgnoreCase("charcoal"))
			return woodToCharcoalConsumption * smeltingRate;
		else if(resource.equalsIgnoreCase("dirt"))
			return woodToDirtConsumption * smeltingRate;
		
		return 0;
	}
}