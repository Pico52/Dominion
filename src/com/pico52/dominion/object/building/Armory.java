package com.pico52.dominion.object.building;

/** 
 * <b>Armory</b><br>
 * <br>
 * &nbsp;&nbsp;public class Armory extends {@link Building}
 * <br>
 * <br>
 * The object controller for all armories.
 */
public class Armory extends Building{
	public static int storage;
	public static double weaponProduction, weaponIronConsumption, weaponLeatherConsumption, 
	weaponWoodConsumption, armorProduction, armorIronConsumption, armorLeatherConsumption, 
	armorWoodConsumption;
	
	/** 
	 * <b>Armory</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Armory()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Armory} class.
	 */
	public Armory(){  // The constructor will be used in the future for loading the custom configurations.
		super(80, 10);
		storage = 3456;  // - Holds weapons and armors.
		weaponProduction = .1;
		armorProduction = .1;
		weaponIronConsumption = .4;
		weaponLeatherConsumption = .2;
		weaponWoodConsumption = .4;
		armorIronConsumption = .8;
		armorLeatherConsumption = .2;
		armorWoodConsumption = .1;
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
		if(resource.equalsIgnoreCase("weapon"))
			return weaponProduction;
		if(resource.equalsIgnoreCase("armor"))
			return armorProduction;
		return 0;
	}
}