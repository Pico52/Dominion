package com.pico52.dominion.object.building;

/** 
 * <b>Warehouse</b><br>
 * <br>
 * &nbsp;&nbsp;public class Warehouse extends {@link Building}
 * <br>
 * <br>
 * The object controller for all warehouses.
 */
public class Warehouse extends Building{
	public static int capacity;
	public static String stores;
	
	/** 
	 * <b>Warehouse</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Warehouse()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Warehouse} class.
	 */
	public Warehouse(){  // The constructor will be used in the future for loading the custom configurations.
		super(40, 1);  // This is 40 base - no extra benefit per level.
		capacity = 54 * 64;  // 54 spaces each holding 64 items totalling to 3456 storage per level.
		stores = "items";  // This does not include food or wealth.
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