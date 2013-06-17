package com.pico52.dominion.object.building;

/** 
 * <b>Sandworks</b><br>
 * <br>
 * &nbsp;&nbsp;public class Sandworks extends {@link Building}
 * <br>
 * <br>
 * The object controller for all sandworks.
 */
public class Sandworks extends Building{
	public static double baseRate;
	public static double baseConsumption;
	public static double sellingBonus;
	public static double glassRate;
	public static double sandstoneRate;
	public static double gravelRate;
	public static double dirtRate;
	public static double sandToGlassConsumption;
	public static double sandToSandstoneConsumption;
	public static double sandstoneToGravelConsumption;
	public static double gravelToDirtConsumption;
	public static String baseConsumptionResource;
	public static boolean consumeBase;
	
	/** 
	 * <b>Sandworks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Sandworks()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Sandworks} class.
	 */
	public Sandworks(){  // The constructor will be used in the future for loading the custom configurations.
		super(5, 5);
		baseRate = 64;  // - 64 items per level can be smelted here.
		consumeBase = true; // - Whether or not the Sandworks will always consume the base resource.
		baseConsumptionResource = "coal";
		baseConsumption = 8; // - 1 coal per 8.
		sellingBonus = .1;  // - % increase to gold value from trading with sand-based resources.
		glassRate = 1;
		sandstoneRate = 1;
		gravelRate = 3;
		dirtRate = 2;
		sandToGlassConsumption = 1;
		sandToSandstoneConsumption = 4;
		sandstoneToGravelConsumption = 1;
		gravelToDirtConsumption = 1;
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
		if(resource.equalsIgnoreCase("glass") | resource.equalsIgnoreCase("sandtoglass"))
			return glassRate * baseRate;
		if(resource.equalsIgnoreCase("sandstone") | resource.equalsIgnoreCase("sandtosandstone"))
			return sandstoneRate * baseRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("sandstonetogravel"))
			return gravelRate * baseRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return dirtRate * baseRate;
		
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
		if(resource.equalsIgnoreCase("glass") | resource.equalsIgnoreCase("sandtoglass"))
			return sandToGlassConsumption * baseRate;
		if(resource.equalsIgnoreCase("sandstone") | resource.equalsIgnoreCase("sandtosandstone"))
			return sandToSandstoneConsumption * baseRate;
		if(resource.equalsIgnoreCase("gravel") | resource.equalsIgnoreCase("sandstonetogravel"))
			return sandstoneToGravelConsumption * baseRate;
		if(resource.equalsIgnoreCase("dirt") | resource.equalsIgnoreCase("graveltodirt"))
			return gravelToDirtConsumption * baseRate;
		
		return 0;
	}
}