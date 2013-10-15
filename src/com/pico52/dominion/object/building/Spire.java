package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Spire</b><br>
 * <br>
 * &nbsp;&nbsp;public class Spire extends {@link Building}
 * <br>
 * <br>
 * The object controller for all dockyards.
 */
public class Spire extends Building{
	public static int spellPower;
	
	/** 
	 * <b>Spire</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Spire()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Spire} class.
	 */
	public Spire(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		defense = config.getInt("buildings.spire.defense.value");
		defenseBase = config.getBoolean("buildings.spire.defense.base");
		workers = config.getInt("buildings.spire.workers");
		structure = config.getBoolean("buildings.spire.structure");
		spellPower = config.getInt("buildings.spire.spell_power");
	}
	
	/** 
	 * <b>getProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getProduction({@link String} resource)
	 * <br>
	 * <br>
	 * @param resource - The resource intended to get the rate of production for;
	 * @return The rate that this building produces the specified resource every building tick.
	 */
	@Override
	public double getProduction(String resource) {
		return 0;
	}
	
	/** 
	 * <b>getSpellPower</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static int getSpellPower(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the building.
	 * @return The rate that this building produces the specified resource every building tick.
	 */
	public static int getSpellPower(int level){
		if(level <= 0)
			return 0;
		int total = 0;
		// - Every 10 levels, reduce the additional spell power by 1 for each level.
		for(int i=spellPower;i>0 && level > 0;i--){
			for(int x=0;x<10;x++){
				total += i;
				if(--level <= 0)
					return total;
			}
		}
		return total;
	}
}