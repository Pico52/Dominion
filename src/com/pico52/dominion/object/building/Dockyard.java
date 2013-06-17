package com.pico52.dominion.object.building;

/** 
 * <b>Dockyard</b><br>
 * <br>
 * &nbsp;&nbsp;public class Dockyard extends {@link Building}
 * <br>
 * <br>
 * The object controller for all dockyards.
 */
public class Dockyard extends Building{
    public static double smallShipStorage;
    public static double mediumShipStorage;
    public static double largeShipStorage;
    
	/** 
	 * <b>Dockyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Dockyard()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Dockyard} class.
	 */
	public Dockyard(){  // The constructor will be used in the future for loading the custom configurations.
		super(20, 20, true);
		smallShipStorage = 8;
		mediumShipStorage = 4;
		largeShipStorage = 1;
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
		return 0;  // Dockyards don't produce anything; they simply give local benefits.
	}
}