package com.pico52.dominion.object.building;

/** 
 * <b>Granary</b><br>
 * <br>
 * &nbsp;&nbsp;public class Granary extends {@link Building}
 * <br>
 * <br>
 * The object controller for all granaries.
 */
public class Granary extends Building{
	public static int capacity;
	public static String stores;
	
	/** 
	 * <b>Granary</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Granary()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Granary} class.
	 */
	public Granary(){  // The constructor will be used in the future for loading the custom configurations.
		super(40, 1);  // This is 40 base - no extra benefit per level.
		capacity = 1792;  // This is the storage of food - the other spaces are used for snow.
		stores = "food";  // Does not store items or wealth.
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
		if(resource.equalsIgnoreCase("storage"))
			return capacity;
		else
			return 0;
	}
}