package com.pico52.dominion.object.building;

/** 
 * <b>Tower</b><br>
 * <br>
 * &nbsp;&nbsp;public class Tower extends {@link Building}
 * <br>
 * <br>
 * The object controller for all towers.
 */
public class Tower extends Building{
    
	/** 
	 * <b>Tower</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Tower()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Tower} class.
	 */
	public Tower(){  // The constructor will be used in the future for loading the custom configurations.
		super(0, 0, true);
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
		return 0;  // Towers don't produce anything; they simply give local benefits.
	}
}
