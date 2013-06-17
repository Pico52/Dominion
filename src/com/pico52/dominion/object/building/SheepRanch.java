package com.pico52.dominion.object.building;

/** 
 * <b>SheepRanch</b><br>
 * <br>
 * &nbsp;&nbsp;public class SheepRanch extends {@link Building}
 * <br>
 * <br>
 * The object controller for all sheep ranches.
 */
public class SheepRanch extends Building{

	public static double woolProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>SheepRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SheepRanch()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SheepRanch} class.
	 */
	public SheepRanch() {
		super(0, 1, true);
		woolProduction = .1;
		maxLevel = 50;
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
		if(resource.equalsIgnoreCase("wool"))
			return woolProduction;
		return 0;
	}
}