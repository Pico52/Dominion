package com.pico52.dominion.object.building;

/** 
 * <b>Barracks</b><br>
 * <br>
 * &nbsp;&nbsp;public class Barracks extends {@link Building}
 * <br>
 * <br>
 * The object controller for all barracks.
 */
public class Barracks extends Building{
	public static double storage;
	
	/** 
	 * <b>Barracks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Barracks()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Barracks} class.
	 */
	public Barracks(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 0);
		storage = 1;
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
		return 0;
	}
}