package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>TrainingGrounds</b><br>
 * <br>
 * &nbsp;&nbsp;public class TrainingGrounds extends {@link Building}
 * <br>
 * <br>
 * The object controller for all training grounds.
 */
public class TrainingGrounds extends Building{
	public static double recruitRate;
	public static double meleeUnitRate;
	public static double wealthConsumption;
	public static double foodConsumption;
	public static double populationConsumption;
	public static double armorConsumption;
	public static double weaponConsumption;
	public static double recruitConsumption;
	public static double baseExperienceGain;
	
	
	/** 
	 * <b>TrainingGrounds</b><br>
	 * <br>
	 * &nbsp;&nbsp;public TrainingGrounds()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link TrainingGrounds} class.
	 */
	public TrainingGrounds(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.training_grounds.defense.value");
		defenseBase = config.getBoolean("buildings.training_grounds.defense.base");
		workers = config.getInt("buildings.training_grounds.workers");
		structure = config.getBoolean("buildings.training_grounds.structure");
		baseExperienceGain = config.getDouble("buildings.training_grounds.experience");
		// - Everything below will be removed/modified at a later date.
		recruitRate = .375;
		meleeUnitRate = .05;
		wealthConsumption = recruitRate * 10;
		foodConsumption = recruitRate * 10;
		populationConsumption = recruitRate;
		armorConsumption = recruitRate;
		weaponConsumption = recruitRate;
		recruitConsumption = meleeUnitRate;
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
		if(resource == null)
			return 0;
		if(resource.equalsIgnoreCase("recruit") | resource.equalsIgnoreCase("recruits"))
			return recruitRate;
		if(resource.equalsIgnoreCase("melee") | resource.equalsIgnoreCase("meleeUnit"))
			return meleeUnitRate;
		
		return 0;
	}
}