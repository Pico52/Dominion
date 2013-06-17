package com.pico52.dominion.object.building;

/** 
 * <b>Inn</b><br>
 * <br>
 * &nbsp;&nbsp;public class Inn extends {@link Building}
 * <br>
 * <br>
 * The object controller for all Inns.
 */
public class Inn extends Building{
	public static String resource;
	public static double wealthProduction;
	public static double visitorStorage;
	
	/** 
	 * <b>Inn</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Inn()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Inn} class.
	 */
	public Inn(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 1);
		resource = "wealth";
		wealthProduction = .25; // 6 per 24 hours.
		visitorStorage = 2;
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
		if(resource.equalsIgnoreCase(Inn.resource))
			return wealthProduction;
		else
			return 0;
	}
}