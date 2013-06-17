package com.pico52.dominion.object.building;

/** 
 * <b>CattleRanch</b><br>
 * <br>
 * &nbsp;&nbsp;public class CattleRanch extends {@link Building}
 * <br>
 * <br>
 * The object controller for all cattle ranches.
 */
public class CattleRanch extends Building{

	public static double leatherProduction;
	public static int maxLevel, minimumRange;
	
	/** 
	 * <b>CattleRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public CattleRanch()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link CattleRanch} class.
	 */
	public CattleRanch() {
		super(0, 1, true);
		leatherProduction = .05;
		maxLevel = 50;
		minimumRange = 150;
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
		if(resource.equalsIgnoreCase("leather"))
			return leatherProduction;
		return 0;
	}
}
