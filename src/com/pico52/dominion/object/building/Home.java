package com.pico52.dominion.object.building;

/** 
 * <b>Home</b><br>
 * <br>
 * &nbsp;&nbsp;public class Home extends {@link Building}
 * <br>
 * <br>
 * The object controller for all homes.
 */
public class Home extends Building{
	public static String resource;
	public static double populationProduction;
	public static double basePopulation; // - Number of peasants automatically added when a home is built.
	public static int storage;
	
	/** 
	 * <b>Home</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Home()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Home} class.
	 */
	public Home(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 0);
		resource = "population";
		populationProduction = .2;  // - 4.8 population in 24 hours.
		basePopulation = 3;
		storage = 5;
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
		if(resource.equalsIgnoreCase(Home.resource))
			return populationProduction;
		else
			return 0;
	}
}
