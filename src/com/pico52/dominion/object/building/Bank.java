package com.pico52.dominion.object.building;

/** 
 * <b>Bank</b><br>
 * <br>
 * &nbsp;&nbsp;public class Bank extends {@link Building}
 * <br>
 * <br>
 * The object controller for all banks.
 */
public class Bank extends Building{
	public static int capacity;
	public static String stores;
	
	/** 
	 * <b>Bank</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Bank()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Bank} class.
	 */
	public Bank(){  // The constructor will be used in the future for loading the custom configurations.
		super(40, 1);  // This is 40 base - no extra benefit per level.
		capacity = 3456;  // 54 spaces at 64 items each = 3456.
		stores = "wealth";  // Does not store items or wealth.
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