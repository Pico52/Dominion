package com.pico52.dominion.object.building;

/** 
 * <b>Market</b><br>
 * <br>
 * &nbsp;&nbsp;public class Market extends {@link Building}
 * <br>
 * <br>
 * The object controller for all markets.
 */
public class Market extends Building{
	public static String resource;
	public static double wealthProduction;
	
	/** 
	 * <b>Market</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Market()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Market} class.
	 */
	public Market(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 5);
		resource = "wealth";
		wealthProduction = 1;
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
		if(resource.equalsIgnoreCase(Market.resource))
			return wealthProduction;
		else
			return 0;
	}
}