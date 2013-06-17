package com.pico52.dominion.datasheet;

/** 
 * <b>ProductionSheet</b><br>
 * <br>
 * &nbsp;&nbsp;public class ProductionSheet
 * <br>
 * <br>
 * An object to store production values for a settlement.
 */
public class ProductionSheet {
	
	public String settlement;
	public double mana, population, wealth, food, wood, cobblestone, stone, sand, gravel, 
	dirt, ironIngot, ironOre, emerald, emeraldOre, goldIngot, goldOre, flint, feather, lapisOre, 
	diamond, obsidian, netherrack, netherBrick, redstone, brick, glowstone, clay, coal, wool, 
	leather, arrow, armor, weapon, snow, recruit, prisoner, experience;
	
	/** 
	 * <b>ProductionSheet</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ProductionSheet()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ProductionSheet} class.
	 */
	public ProductionSheet(){
		settlement = "";
		mana = 0;
		population = 0;
		wealth = 0;
		food = 0;
		wood = 0;
		cobblestone = 0;
		stone = 0;
		sand = 0;
		gravel = 0;
		dirt = 0;
		ironIngot = 0;
		ironOre = 0;
		emerald = 0;
		emeraldOre = 0;
		goldIngot = 0;
		goldOre = 0;
		flint = 0;
		feather = 0;
		lapisOre = 0;
		diamond = 0;
		obsidian = 0;
		netherrack = 0;
		netherBrick = 0;
		redstone = 0;
		brick = 0;
		glowstone = 0;
		clay = 0;
		coal = 0;
		wool = 0;
		leather = 0;
		arrow = 0;
		armor = 0;
		weapon = 0;
		snow = 0;
		recruit = 0;
		prisoner = 0;
		experience = 0;
	}
	
	/** 
	 * <b>addResource</b><br>
	 * <br>
	 * &nbsp;&nbsp;public void addResource({@link String} resource, double value)
	 * <br>
	 * <br>
	 * @param resource - The resource intended to add.
	 * @param value - The quantity to add.
	 */
	public void addResource(String resource, double value){
		resource = resource.toLowerCase();
		if(resource.equalsIgnoreCase("mana")) mana += value;
		else if(resource.equalsIgnoreCase("population")) population += value;
		else if(resource.equalsIgnoreCase("wealth")) wealth += value;
		else if(resource.equalsIgnoreCase("food")) food += value;
		else if(resource.equalsIgnoreCase("wood")) wood += value;
		else if(resource.equalsIgnoreCase("cobblestone")) cobblestone += value;
		else if(resource.equalsIgnoreCase("stone")) stone += value;
		else if(resource.equalsIgnoreCase("sand")) sand += value;
		else if(resource.equalsIgnoreCase("gravel")) gravel += value;
		else if(resource.equalsIgnoreCase("dirt")) dirt += value;
		else if(resource.equalsIgnoreCase("iron_ingot") | resource.equalsIgnoreCase("ironingot")) ironIngot += value;
		else if(resource.equalsIgnoreCase("iron_ore") | resource.equalsIgnoreCase("ironore")) ironOre += value;
		else if(resource.equalsIgnoreCase("emerald")) emerald += value;
		else if(resource.equalsIgnoreCase("emerald_ore") | resource.equalsIgnoreCase("emeraldore")) emeraldOre += value;
		else if(resource.equalsIgnoreCase("gold_ingot") | resource.equalsIgnoreCase("goldingot")) goldIngot += value;
		else if(resource.equalsIgnoreCase("gold_ore") | resource.equalsIgnoreCase("goldore")) goldOre += value;
		else if(resource.equalsIgnoreCase("flint")) flint += value;
		else if(resource.equalsIgnoreCase("feather")) feather += value;
		else if(resource.equalsIgnoreCase("lapis_ore")) lapisOre += value;
		else if(resource.equalsIgnoreCase("diamond")) diamond += value;
		else if(resource.equalsIgnoreCase("obsidian")) obsidian += value;
		else if(resource.equalsIgnoreCase("netherrack")) netherrack += value;
		else if(resource.equalsIgnoreCase("nether_brick") | resource.equalsIgnoreCase("netherbrick")) netherBrick += value;
		else if(resource.equalsIgnoreCase("redstone")) redstone += value;
		else if(resource.equalsIgnoreCase("brick")) brick += value;
		else if(resource.equalsIgnoreCase("glowstone")) glowstone += value;
		else if(resource.equalsIgnoreCase("clay")) clay += value;
		else if(resource.equalsIgnoreCase("coal")) coal += value;
		else if(resource.equalsIgnoreCase("wool")) wool += value;
		else if(resource.equalsIgnoreCase("leather")) leather += value;
		else if(resource.equalsIgnoreCase("arrow")) arrow += value;
		else if(resource.equalsIgnoreCase("armor")) armor += value;
		else if(resource.equalsIgnoreCase("weapon")) weapon += value;
		else if(resource.equalsIgnoreCase("snow")) snow += value;
		else if(resource.equalsIgnoreCase("recruit")) recruit += value;
		else if(resource.equalsIgnoreCase("prisoner")) prisoner += value;
		else if(resource.equalsIgnoreCase("experience")) experience += value;
	}
	
	/** 
	 * <b>merge</b><br>
	 * <br>
	 * &nbsp;&nbsp;public void merge({@link ProductionSheet} mergeSheet)
	 * <br>
	 * <br>
	 * Merges all values from the given sheet to this sheet.
	 * @param mergeSheet - The {@link ProductionSheet} to merge with.
	 */
	public void merge(ProductionSheet mergeSheet){
		mana += mergeSheet.mana;
		population += mergeSheet.population;
		wealth += mergeSheet.wealth;
		food += mergeSheet.food;
		wood += mergeSheet.wood;
		cobblestone += mergeSheet.cobblestone;
		stone += mergeSheet.stone;
		sand += mergeSheet.sand;
		gravel += mergeSheet.gravel;
		dirt += mergeSheet.dirt;
		ironIngot += mergeSheet.ironIngot;
		ironOre += mergeSheet.ironOre;
		emerald += mergeSheet.emerald;
		emeraldOre += mergeSheet.emeraldOre;
		goldIngot += mergeSheet.goldIngot;
		goldOre += mergeSheet.goldOre;
		flint += mergeSheet.flint;
		feather += mergeSheet.feather;
		lapisOre += mergeSheet.lapisOre;
		diamond += mergeSheet.diamond;
		obsidian += mergeSheet.obsidian;
		netherrack += mergeSheet.netherrack;
		netherBrick += mergeSheet.netherBrick;
		redstone += mergeSheet.redstone;
		brick += mergeSheet.brick;
		glowstone += mergeSheet.glowstone;
		clay += mergeSheet.clay;
		coal += mergeSheet.coal;
		wool += mergeSheet.wool;
		leather += mergeSheet.leather;
		arrow += mergeSheet.arrow;
		armor += mergeSheet.armor;
		weapon += mergeSheet.weapon;
		snow += mergeSheet.snow;
		recruit += mergeSheet.recruit;
		prisoner += mergeSheet.prisoner;
		experience += mergeSheet.experience;
	}
}
