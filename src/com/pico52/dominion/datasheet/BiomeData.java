package com.pico52.dominion.datasheet;

/** 
 * <b>BiomeData</b><br>
 * <br>
 * &nbsp;&nbsp;public class BiomeData implements {@link DataSheet}
 * <br>
 * <br>
 * The data sheet for all biome information.
 */
public class BiomeData implements DataSheet{
	public static double 
	heavyBonus, normalBonus, lightBonus, 
	harshPenalty, heavyPenalty, normalPenalty, lightPenalty, 
	plainsFarmBonus, plainsHerdingGroundsBonus, 
	plainsSpellPowerPenalty, plainsFishingPenalty, plainsDefensePenalty, 
	forestWoodshopBonus, forestHuntingBonus, forestInnBonus, 
	forestFoodConsumptionPenalty, forestAttritionDamage, 
	mountainMasonryBonus, mountainMiningBonus, mountainDefenseBonus, 
	mountainWheatPenalty, mountainTradePenalty, 
	snowAttritionDamage, snowFoodDecompositionBonus, 
	snowWheatPenalty, snowMelonPenalty, snowMushroomPenalty, 
	jungleFoodConsumptionBonus, jungleMelonBonus,
	jungleUnitFoodConsumptionPenalty, jungleUnitUpkeepPenalty, 
	swampManaCapacityBonus, swampManaRegenerationBonus, 
	swampWheatPenalty, swampHerdingGroundsPenalty, 
	mushroomSpellPowerBonus, mushroomMushroomBonus, 
	mushroomWheatPenalty, mushroomPumpkinPenalty, mushroomMelonPenalty, 
	oceanFishingBonus, oceanTradeBonus, 
	oceanManaRegenerationPenalty, oceanMiningPenalty, 
	desertTrainingGroundsBonus, desertSandworksBonus, 
	desertFarmPenalty, desertHerdingGroundsPenalty;
	
	/** 
	 * <b>BiomeData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public BiomeData()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link BiomeData} class.
	 */
	public BiomeData(){
		heavyBonus = 0.3;
		normalBonus = 0.2;
		lightBonus = 0.1;
		harshPenalty = 0.4;
		heavyPenalty = 0.3;
		normalPenalty = 0.2;
		lightPenalty = 0.1;
		plainsFarmBonus = heavyBonus;
		plainsHerdingGroundsBonus = normalBonus;
		plainsSpellPowerPenalty = normalPenalty;
		plainsFishingPenalty = lightPenalty;
		plainsDefensePenalty = normalPenalty;
		forestWoodshopBonus = normalBonus;
		forestHuntingBonus = normalBonus;
		forestInnBonus = normalBonus;
		forestFoodConsumptionPenalty = heavyPenalty;
		forestAttritionDamage = -.01;
		mountainMasonryBonus = normalBonus;
		mountainMiningBonus = normalBonus;
		mountainDefenseBonus = normalBonus; 
		mountainWheatPenalty = harshPenalty;
		mountainTradePenalty = harshPenalty;
		snowAttritionDamage = .01;
		snowFoodDecompositionBonus = 0; // - Multiplier, so food simply does not decompose.
		snowWheatPenalty = harshPenalty;
		snowMelonPenalty = normalPenalty;
		snowMushroomPenalty = harshPenalty;
		jungleFoodConsumptionBonus = normalBonus;
		jungleMelonBonus = heavyBonus;
		jungleUnitFoodConsumptionPenalty = normalPenalty;
		jungleUnitUpkeepPenalty = normalPenalty; 
		swampManaCapacityBonus = heavyBonus;
		swampManaRegenerationBonus = heavyBonus;
		swampWheatPenalty = harshPenalty;
		swampHerdingGroundsPenalty = heavyPenalty;
		mushroomSpellPowerBonus = heavyBonus;
		mushroomMushroomBonus = heavyBonus;
		mushroomWheatPenalty = harshPenalty;
		mushroomPumpkinPenalty = normalPenalty;
		mushroomMelonPenalty = normalPenalty;
		oceanFishingBonus = heavyBonus;
		oceanTradeBonus = heavyBonus;
		oceanManaRegenerationPenalty = normalPenalty;
		oceanMiningPenalty = harshPenalty;
		desertTrainingGroundsBonus = normalBonus;
		desertSandworksBonus = heavyBonus;
		desertFarmPenalty = normalPenalty;
		desertHerdingGroundsPenalty = normalPenalty;
	}

	@Override
	public String outputData() {
		String output = 
		"§aPlains Farms:§f +" + plainsFarmBonus + "  " + 
		"§aHerding Grounds:§f +" + plainsHerdingGroundsBonus + "\n" + 
		"§aPlains Spell Power:§f -" + plainsSpellPowerPenalty + "  " + 
		"§aFishing:§f -" + plainsFishingPenalty + "  " +
		"§aDefense:§f -" + plainsDefensePenalty + "\n" +
		"§aForest Woodshop:§f +" + forestWoodshopBonus + "  " +
		"§aHunting:§f +" + forestHuntingBonus + "  " + 
		"§aInn:§f +" + forestInnBonus + "\n" + 
		"§aForest Food Consumption:§f -" + forestFoodConsumptionPenalty + "  " + 
		"§aAttrition:§f " + forestAttritionDamage + "\n" + 
		"§aMountain Masonry:§f +" + mountainMasonryBonus + "  " + 
		"§aMining:§f +" + mountainMiningBonus + "  " + 
		"§aDefense::§f +" + mountainDefenseBonus + "\n" + 
		"§aMountain Wheat:§f -" + mountainWheatPenalty + "  " + 
		"§aTrade:§f -" + mountainTradePenalty + "\n" + 
		"§aSnow Attrition:§f +" + snowAttritionDamage + "  " + 
		"§aFood Decomposition:§f x" + snowFoodDecompositionBonus + "\n" + 
		"§aSnow Wheat:§f -" + snowWheatPenalty + "  " + 
		"§aMelon:§f -" + snowMelonPenalty + "  " + 
		"§aMushroom:§f -" + snowMushroomPenalty + "\n" + 
		"§aJungle Food Consumption:§f -" + jungleFoodConsumptionBonus + "  " + 
		"§aMelon:§f +" + jungleMelonBonus + "\n" + 
		"§aJungle Unit Food Consumption:§f +" + jungleUnitFoodConsumptionPenalty + "  " + 
		"§aUnit Upkeep:§f +" + jungleUnitUpkeepPenalty + "\n" + 
		"§aSwamp Mana Capacity:§f +" + swampManaCapacityBonus + "  " + 
		"§aMana Regen:§f +" + swampManaRegenerationBonus + "\n" + 
		"§aSwamp Wheat:§f -" + swampWheatPenalty + "  " + 
		"§aHerding Grounds:§f -" + swampHerdingGroundsPenalty + "\n" + 
		"§aMushroom Spell Power:§f +" + mushroomSpellPowerBonus + "  " + 
		"§aMushroom:§f +" + mushroomMushroomBonus + "\n" + 
		"§aMushroom Wheat:§f -" + mushroomWheatPenalty + "  " + 
		"§aPumpkin:§f -" + mushroomPumpkinPenalty + "  " + 
		"§aMelon:§f -" + mushroomMelonPenalty + "\n" + 
		"§aOcean Fishing:§f +" + oceanFishingBonus + "  " + 
		"§aTrade:§f +" + oceanTradeBonus + "\n" + 
		"§aOcean Mana Regen:§f -" + oceanManaRegenerationPenalty + "  " + 
		"§aMining:§f -" + oceanMiningPenalty + "\n" + 
		"§aDesert Training Grounds:§f +" + desertTrainingGroundsBonus + "  " + 
		"§aSandworks:§f +" + desertSandworksBonus + "\n" + 
		"§aDesertFarm:§f -" + desertFarmPenalty + "  " + 
		"§aHerding Grounds:§f -" + desertHerdingGroundsPenalty + "\n";
		return output;
	}
	
	/** 
	 * <b>isBiome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isBiome({@link String} biome)
	 * <br>
	 * <br>
	 * @param biome - The name of the biome.
	 * @return True if the argument is a biome.  False if it is not.
	 */
	public boolean isBiome(String biome){
		if(biome.equalsIgnoreCase("plains") | biome.equalsIgnoreCase("forest") | biome.equalsIgnoreCase("mountain") | 
				biome.equalsIgnoreCase("snow") | biome.equalsIgnoreCase("jungle") | biome.equalsIgnoreCase("swamp") | 
				biome.equalsIgnoreCase("mushroom") | biome.equalsIgnoreCase("ocean") | biome.equalsIgnoreCase("desert"))
			return true;
		return false;
	}
}
