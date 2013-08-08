package com.pico52.dominion.object.building;

import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.DominionSettings;

/** 
 * <b>Tower</b><br>
 * <br>
 * &nbsp;&nbsp;public class Tower extends {@link Building}
 * <br>
 * <br>
 * The object controller for all towers.
 */
public class Tower extends Building{
	
	public static int maxLevel;
	public static double 
	glassBonus, dirtBonus, gravelBonus, glowstoneBonus, oreBonus, fenceBonus, clayBonus, goldBonus, woodenPlankBonus, netherrackBonus, woodBonus, 
	cobblestoneBonus, stoneBonus, stoneBrickBonus, stoneSlabBonus, sandstoneBonus, sandstoneSlabBonus, endstoneBonus, netherbrickBonus, brickBonus, 
	ironBonus, obsidianBonus, lapisLazuliBonus, diamondBonus;
	private static int[] defense, range;
	private static String[] name;
    
	/** 
	 * <b>Tower</b><br>
	 * <br>
	 * &nbsp;&nbsp;public Tower()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link Tower} class.
	 */
	public Tower(){
		FileConfiguration config = DominionSettings.getBuildingsConfig();
		super.defense = config.getInt("buildings.tower.defense.value");
		defenseBase = config.getBoolean("buildings.tower.defense.base");
		workers = config.getInt("buildings.tower.workers");
		structure = config.getBoolean("buildings.tower.structure");
		maxLevel = config.getInt("buildings.tower.maximum_level");
		glassBonus = config.getDouble("buildings.tower.material_bonuses.glass");
		dirtBonus = config.getDouble("buildings.tower.material_bonuses.dirt");
		gravelBonus = config.getDouble("buildings.tower.material_bonuses.gravel");
		glowstoneBonus = config.getDouble("buildings.tower.material_bonuses.glowstone");
		oreBonus = config.getDouble("buildings.tower.material_bonuses.ore");
		fenceBonus = config.getDouble("buildings.tower.material_bonuses.fence");
		clayBonus = config.getDouble("buildings.tower.material_bonuses.clay");
		goldBonus = config.getDouble("buildings.tower.material_bonuses.gold");
		woodenPlankBonus = config.getDouble("buildings.tower.material_bonuses.wooden_plank");
		netherrackBonus = config.getDouble("buildings.tower.material_bonuses.netherrack");
		woodBonus = config.getDouble("buildings.tower.material_bonuses.wood");
		cobblestoneBonus = config.getDouble("buildings.tower.material_bonuses.cobblestone");
		stoneBonus = config.getDouble("buildings.tower.material_bonuses.stone");
		stoneBrickBonus = config.getDouble("buildings.tower.material_bonuses.stone_brick");
		stoneSlabBonus = config.getDouble("buildings.tower.material_bonuses.stone_slab");
		sandstoneBonus = config.getDouble("buildings.tower.material_bonuses.sandstone");
		sandstoneSlabBonus = config.getDouble("buildings.tower.material_bonuses.sandstone_slab");
		endstoneBonus = config.getDouble("buildings.tower.material_bonuses.endstone");
		netherbrickBonus = config.getDouble("buildings.tower.material_bonuses.nether_brick");
		brickBonus = config.getDouble("buildings.tower.material_bonuses.brick");
		ironBonus = config.getDouble("buildings.tower.material_bonuses.iron");
		obsidianBonus = config.getDouble("buildings.tower.material_bonuses.obsidian");
		lapisLazuliBonus = config.getDouble("buildings.tower.material_bonuses.lapis_lazuli");
		diamondBonus = config.getDouble("buildings.tower.material_bonuses.diamond");
		name = new String[maxLevel];
		defense = new int[maxLevel];
		range = new int[maxLevel];
		try{
			for(int i=0; i < maxLevel; i++){
				name[i] = config.getString("buildings.tower.levels." + (i+1) + ".name");
				defense[i] = config.getInt("buildings.tower.levels." + (i+1) + ".defense");
				range[i] = config.getInt("buildings.tower.levels." + (i+1) + ".range");
			}
		} catch (ArrayIndexOutOfBoundsException ex){
			ex.printStackTrace();
		}
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
		return 0;  // Towers don't produce anything; they simply give local benefits.
	}
	
	/** 
	 * <b>getName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static {@link String} getName(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the tower.
	 * @return The name of the tower at the respective level.  Null if the level is not valid.
	 */
	public static String getName(int level){
		if(checkLevel(level))
			return name[level];
		return null;
	}
	
	/** 
	 * <b>getDefense</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static int getDefense(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the tower.
	 * @return The defense of the tower at the respective level.  -1 if the level is not valid.
	 */
	public static int getDefense(int level){
		if(checkLevel(level))
			return defense[level];
		return -1;
	}
	
	/** 
	 * <b>getRange</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static int getRange(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the tower.
	 * @return The range of the tower at the respective level.  -1 if the level is not valid.
	 */
	public static int getRange(int level){
		if(checkLevel(level))
			return range[level];
		return -1;
	}
	
	/** 
	 * <b>checkLevel</b><br>
	 * <br>
	 * &nbsp;&nbsp;private static boolean checkLevel(int level)
	 * <br>
	 * <br>
	 * @param level - The level of the tower.
	 * @return True if the level is valid.  False if it is not.
	 */
	private static boolean checkLevel(int level){
		try{
			if(level <= maxLevel & level > 0)
				return true;
		} catch (ArrayIndexOutOfBoundsException ex){
			ex.printStackTrace();
		}
		return false;
	}
}
