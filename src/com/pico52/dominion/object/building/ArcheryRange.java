package com.pico52.dominion.object.building;

/** 
 * <b>ArcheryRange</b><br>
 * <br>
 * &nbsp;&nbsp;public class ArcheryRange extends {@link Building}
 * <br>
 * <br>
 * The object controller for all archery ranges.
 */
public class ArcheryRange extends Building{
	public static double archerUnitRate;
	public static double wealthConsumption;
	public static double foodConsumption;
	public static double bowConsumption;
	public static double leatherConsumption;
	public static double arrowConsumption;
	public static double recruitConsumption;
	public static double baseExperienceGain;
	
	/** 
	 * <b>ArcheryRange</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ArcheryRange()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ArcheryRange} class.
	 */
	public ArcheryRange(){  // The constructor will be used in the future for loading the custom configurations.
		super(5, 0);
		archerUnitRate = .05;
		wealthConsumption = archerUnitRate * 80;
		foodConsumption = archerUnitRate * 20;
		bowConsumption = archerUnitRate;
		leatherConsumption = archerUnitRate * 7;
		arrowConsumption = archerUnitRate * 200;
		recruitConsumption = archerUnitRate;
		baseExperienceGain = 5;
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
		if(resource.equalsIgnoreCase("archer"))
			return archerUnitRate;
		
		return 0;
	}
}