package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Market</b><br>
 * <br>
 * &nbsp;&nbsp;public class Market extends {@link Building}
 * <br>
 * <br>
 * The object controller for all markets.
 */
public class Market extends Building{
	public static String resource;
	public static int tradeRoutes;
	public static double wealthProduction;
	
	/** 
	 * <b>Market</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Market()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Market} class.
	 */
	public Market(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.market.defense.value");
		defenseBase = config.getBoolean("buildings.market.defense.base");
		workers = config.getInt("buildings.market.workers");
		structure = config.getBoolean("buildings.market.structure");
		resource = config.getString("buildings.market.resource");
		wealthProduction = config.getDouble("buildings.market.wealth_production");
		tradeRoutes = config.getInt("buildings.market.trade_routes");
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
		if(resource.equalsIgnoreCase(Market.resource))
			return wealthProduction;
		else
			return 0;
	}
}