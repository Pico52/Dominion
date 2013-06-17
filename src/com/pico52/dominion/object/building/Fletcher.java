package com.pico52.dominion.object.building;

/** 
 * <b>Fletcher</b><br>
 * <br>
 * &nbsp;&nbsp;public class Fletcher extends {@link Building}
 * <br>
 * <br>
 * The object controller for all fletcheries.
 */
public class Fletcher extends Building{
	public static double arrowProduction;
	public static double flintConsumption;
	public static double woodConsumption;
	public static double featherConsumption;
	
	/** 
	 * <b>Fletcher</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Fletcher()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Fletcher} class.
	 */
	public Fletcher(){  // The constructor will be used in the future for loading the custom configurations.
		super(40, 20);
		arrowProduction = 20;
		flintConsumption = 20;
		woodConsumption = 2.5;
		featherConsumption = 20;
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
		if(resource.equalsIgnoreCase("arrow") | resource.equalsIgnoreCase("arrows"))
			return arrowProduction;
		return 0;
	}
}