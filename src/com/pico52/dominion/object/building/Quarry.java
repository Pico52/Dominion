package com.pico52.dominion.object.building;

/** 
 * <b>Quarry</b><br>
 * <br>
 * &nbsp;&nbsp;public class Quarry extends {@link Building}
 * <br>
 * <br>
 * The object controller for all quarries.
 */
public class Quarry extends Building{
	public static double clayProduction, sandProduction, stoneProduction;
	public static int maxLevel;
	
	
	/** 
	 * <b>Quarry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Quarry()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Quarry} class.
	 */
	public Quarry(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 5, true);
		clayProduction = 4;
		sandProduction = 3;
		stoneProduction = 2;
		maxLevel = 8;
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
		if(resource.equalsIgnoreCase("clay"))
			return clayProduction;
		if(resource.equalsIgnoreCase("sand"))
			return sandProduction;
		if(resource.equalsIgnoreCase("stone"))
			return stoneProduction;
		
		return 0;
	}
}