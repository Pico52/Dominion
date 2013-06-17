package com.pico52.dominion.object.building;

/** 
 * <b>Library</b><br>
 * <br>
 * &nbsp;&nbsp;public class Library extends {@link Building}
 * <br>
 * <br>
 * The object controller for all libraries.
 */
public class Library extends Building{
	public static String resource;
	public static double manaProduction;
	public static int manaCapacity;
	
	/** 
	 * <b>Library</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Library()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Library} class.
	 */
	public Library(){  // The constructor will be used in the future for loading the custom configurations.
		super(40, 1);
		resource = "mana";
		manaProduction = .1;
		manaCapacity = 10;
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
		if(resource.equalsIgnoreCase(Library.resource))
			return manaProduction;
		else
			return 0;
	}
	
	/** 
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the building.
	 * @return The rate that this building produces its resource every building tick.
	 */
	public double getProduction(int level) {
		return manaProduction * level;
	}
}