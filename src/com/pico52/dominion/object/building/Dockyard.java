package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Dockyard</b><br>
 * <br>
 * &nbsp;&nbsp;public class Dockyard extends {@link Building}
 * <br>
 * <br>
 * The object controller for all dockyards.
 */
public class Dockyard extends Building{
    public double tradeBonus, 
    smallShipStorage, mediumShipStorage, largeShipStorage, 
    smallRepairRate, mediumRepairRate, largeRepairRate;
    public boolean
    repairCivilian, repairWar;
    public int range;
    
	/** 
	 * <b>Dockyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Dockyard()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Dockyard} class.
	 */
	public Dockyard(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.dockyard.defense.value");
		defenseBase = config.getBoolean("buildings.dockyard.defense.base");
		workers = config.getInt("buildings.dockyard.workers");
		structure = config.getBoolean("buildings.dockyard.structure");
		tradeBonus = config.getDouble("buildings.dockyard.trade_bonus");
		smallShipStorage = config.getDouble("buildings.dockyard.capacity.small");
		mediumShipStorage = config.getDouble("buildings.dockyard.capacity.medium");
		largeShipStorage = config.getDouble("buildings.dockyard.capacity.large");
		repairCivilian = config.getBoolean("buildings.dockyard.repair.civilian");
		repairWar = config.getBoolean("buildings.dockyard.repair.war");
		range = config.getInt("buildings.dockyard.repair.range");
		smallRepairRate = config.getDouble("buildings.dockyard.repair.rate.small");
		mediumRepairRate = config.getDouble("buildings.dockyard.repair.rate.medium");
		largeRepairRate = config.getDouble("buildings.dockyard.repair.rate.large");
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