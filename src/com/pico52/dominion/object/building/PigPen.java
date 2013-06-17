package com.pico52.dominion.object.building;

/** 
 * <b>PigPen</b><br>
 * <br>
 * &nbsp;&nbsp;public class PigPen extends {@link Building}
 * <br>
 * <br>
 * The object controller for all pig pens.
 */
public class PigPen extends Building{

	public static double foodProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>PigPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PigPen()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PigPen} class.
	 */
	public PigPen() {
		super(0, 1, true);
		foodProduction = 1;
		maxLevel = 40;
		minimumRange = 125;
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
		if(resource.equalsIgnoreCase("food"))
			return foodProduction;
		return 0;
	}
}