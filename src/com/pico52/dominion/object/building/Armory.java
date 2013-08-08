package com.pico52.dominion.object.building;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Armory</b><br>
 * <br>
 * &nbsp;&nbsp;public class Armory extends {@link Building}
 * <br>
 * <br>
 * The object controller for all armories.
 */
public class Armory extends Building{
	public static int capacity;
	public static double weaponProduction, weaponIronConsumption, weaponLeatherConsumption, 
	weaponWoodConsumption, armorProduction, armorIronConsumption, armorLeatherConsumption, 
	armorWoodConsumption;
	public static String[] stores;
	
	/** 
	 * <b>Armory</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Armory()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Armory} class.
	 */
	public Armory(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.armory.defense.value");
		defenseBase = config.getBoolean("buildings.armory.defense.base");
		workers = config.getInt("buildings.armory.workers");
		structure = config.getBoolean("buildings.armory.structure");
		capacity = config.getInt("buildings.armory.capacity");
		List<String> storage = config.getStringList("buildings.armory.stores");
		stores = storage.toArray(new String[storage.size()]);
		weaponProduction = config.getDouble("buildings.armory.weapon.production_rate");
		weaponIronConsumption = config.getDouble("buildings.armory.weapon.costs.iron");
		weaponLeatherConsumption = config.getDouble("buildings.armory.weapon.costs.leather");
		weaponWoodConsumption = config.getDouble("buildings.armory.weapon.costs.wood");
		armorProduction = config.getDouble("buildings.armory.armor.production_rate");
		armorIronConsumption = config.getDouble("buildings.armory.armor.costs.iron");
		armorLeatherConsumption = config.getDouble("buildings.armory.armor.costs.leather");
		armorWoodConsumption = config.getDouble("buildings.armory.armor.costs.wood");
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
		if(resource.equalsIgnoreCase("weapon"))
			return weaponProduction;
		if(resource.equalsIgnoreCase("armor"))
			return armorProduction;
		return 0;
	}
}