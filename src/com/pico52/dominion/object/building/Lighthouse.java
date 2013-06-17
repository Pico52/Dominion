package com.pico52.dominion.object.building;

/** 
 * <b>Lighthouse</b><br>
 * <br>
 * &nbsp;&nbsp;public class Lighthouse extends {@link Building}
 * <br>
 * <br>
 * The object controller for all lighthouses.
 */
public class Lighthouse extends Building{
	
	/** 
	 * <b>Lighthouse</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Lighthouse()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Lighthouse} class.
	 */
	public Lighthouse(){  // The constructor will be used in the future for loading the custom configurations.
		super(5, 10);
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
		return 0;  // Lighthouses don't produce anything; they simply give local benefits.
	}
}