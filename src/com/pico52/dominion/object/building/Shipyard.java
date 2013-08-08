package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Shipyard</b><br>
 * <br>
 * &nbsp;&nbsp;public class Shipyard extends {@link Building}
 * <br>
 * <br>
 * The object controller for all shipyards.
 */
public class Shipyard extends Building{
	public static double 
	smallShipStorage, mediumShipStorage, largeShipStorage, 
    smallRepairRate, mediumRepairRate, largeRepairRate;
    public static boolean
    repairCivilian, repairWar;
    public static int range;
	
	/** 
	 * <b>Shipyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Shipyard()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Shipyard} class.
	 */
	public Shipyard(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.shipyard.defense.value");
		defenseBase = config.getBoolean("buildings.shipyard.defense.base");
		workers = config.getInt("buildings.shipyard.workers");
		structure = config.getBoolean("buildings.shipyard.structure");
		smallShipStorage = config.getDouble("buildings.shipyard.capacity.small");
		mediumShipStorage = config.getDouble("buildings.shipyard.capacity.medium");
		largeShipStorage = config.getDouble("buildings.shipyard.capacity.large");
		repairCivilian = config.getBoolean("buildings.shipyard.repair.civilian");
		repairWar = config.getBoolean("buildings.shipyard.repair.war");
		range = config.getInt("buildings.shipyard.repair.range");
		smallRepairRate = config.getDouble("buildings.shipyard.repair.rate.small");
		mediumRepairRate = config.getDouble("buildings.shipyard.repair.rate.medium");
		largeRepairRate = config.getDouble("buildings.shipyard.repair.rate.large");
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