package com.pico52.dominion.object.building;

/** 
 * <b>ChickenPen</b><br>
 * <br>
 * &nbsp;&nbsp;public class ChickenPen extends {@link Building}
 * <br>
 * <br>
 * The object controller for all chicken pens.
 */
public class ChickenPen extends Building{

	public static double featherProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>ChickenPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ChickenPen()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ChickenPen} class.
	 */
	public ChickenPen() {
		super(0, 1, true);
		featherProduction = .25;
		maxLevel = 30;
		minimumRange = 100;
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
		if(resource.equalsIgnoreCase("feather"))
			return featherProduction;
		return 0;
	}
}