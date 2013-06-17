package com.pico52.dominion.object.building;

/** 
 * <b>Farm</b><br>
 * <br>
 * &nbsp;&nbsp;public class Farm extends {@link Building}
 * <br>
 * <br>
 * The object controller for all farms.
 */
public class Farm extends Building{
	
	public static double wheatProduction, melonProduction, pumpkinProduction, mushroomProduction, 
	autoHarvestBonus;
	
	/** 
	 * <b>Farm</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Farm()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Farm} class.
	 */
	public Farm(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 1, true);
		wheatProduction = 16;
		melonProduction = 16;
		pumpkinProduction = 16;
		mushroomProduction = 16;
		autoHarvestBonus = .15;
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
		if(resource.equalsIgnoreCase("wheat"))
			return wheatProduction;
		if(resource.equalsIgnoreCase("melon"))
			return melonProduction;
		if(resource.equalsIgnoreCase("pumpkin"))
			return pumpkinProduction;
		if(resource.equalsIgnoreCase("mushroom"))
			return mushroomProduction;
		
		return 0;
	}
}