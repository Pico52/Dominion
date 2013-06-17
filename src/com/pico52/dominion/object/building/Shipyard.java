package com.pico52.dominion.object.building;

/** 
 * <b>Shipyard</b><br>
 * <br>
 * &nbsp;&nbsp;public class Shipyard extends {@link Building}
 * <br>
 * <br>
 * The object controller for all shipyards.
 */
public class Shipyard extends Building{
    public static double smallShipStorage, mediumShipStorage, largeShipStorage;
	
	/** 
	 * <b>Shipyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Shipyard()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Shipyard} class.
	 */
	public Shipyard(){  // The constructor will be used in the future for loading the custom configurations.
		super(50, 25, true);
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
		return 0;
	}
}