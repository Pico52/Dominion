package com.pico52.dominion.datasheet;

import com.pico52.dominion.DominionSettings;

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
		// - This data's here more for ease of access than anything else.
		plainsFarmBonus = DominionSettings.plainsFarmBonus;
		plainsHerdingGroundsBonus = DominionSettings.plainsHerdingGroundsBonus;
		plainsSpellPowerPenalty = DominionSettings.plainsSpellPowerPenalty;
		plainsFishingPenalty = DominionSettings.plainsFishingPenalty;
		plainsDefensePenalty = DominionSettings.plainsDefensePenalty;
		forestWoodshopBonus = DominionSettings.forestWoodshopBonus;
		forestHuntingBonus = DominionSettings.forestHuntingBonus;
		forestInnBonus = DominionSettings.forestInnBonus;
		forestFoodConsumptionPenalty = DominionSettings.forestFoodConsumptionPenalty;
		forestAttritionDamage = DominionSettings.forestAttritionDamage;
		mountainMasonryBonus = DominionSettings.mountainMasonryBonus;
		mountainMiningBonus = DominionSettings.mountainMiningBonus;
		mountainDefenseBonus = DominionSettings.mountainDefenseBonus;
		mountainWheatPenalty = DominionSettings.mountainWheatPenalty;
		mountainTradePenalty = DominionSettings.mountainTradePenalty;
		snowAttritionDamage = DominionSettings.snowAttritionDamage;
		snowFoodDecompositionBonus = DominionSettings.snowFoodDecompositionBonus;
		snowWheatPenalty = DominionSettings.snowWheatPenalty;
		snowMelonPenalty = DominionSettings.snowMelonPenalty;
		snowMushroomPenalty = DominionSettings.snowMushroomPenalty;
		jungleFoodConsumptionBonus = DominionSettings.jungleFoodConsumptionBonus;
		jungleMelonBonus = DominionSettings.jungleMelonBonus;
		jungleUnitFoodConsumptionPenalty = DominionSettings.jungleUnitFoodConsumptionPenalty;
		jungleUnitUpkeepPenalty = DominionSettings.jungleUnitUpkeepPenalty;
		swampManaCapacityBonus = DominionSettings.swampManaCapacityBonus;
		swampManaRegenerationBonus = DominionSettings.swampManaRegenerationBonus;
		swampWheatPenalty = DominionSettings.swampWheatPenalty;
		swampHerdingGroundsPenalty = DominionSettings.swampHerdingGroundsPenalty;
		mushroomSpellPowerBonus = DominionSettings.mushroomSpellPowerBonus;
		mushroomMushroomBonus = DominionSettings.mushroomMushroomBonus;
		mushroomWheatPenalty = DominionSettings.mushroomWheatPenalty;
		mushroomPumpkinPenalty = DominionSettings.mushroomPumpkinPenalty;
		mushroomMelonPenalty = DominionSettings.mushroomMelonPenalty;
		oceanFishingBonus = DominionSettings.oceanFishingBonus;
		oceanTradeBonus = DominionSettings.oceanTradeBonus;
		oceanManaRegenerationPenalty = DominionSettings.oceanManaRegenerationPenalty;
		oceanMiningPenalty = DominionSettings.oceanMiningPenalty;
		desertTrainingGroundsBonus = DominionSettings.desertTrainingGroundsBonus;
		desertSandworksBonus = DominionSettings.desertSandworksBonus;
		desertFarmPenalty = DominionSettings.desertFarmPenalty;
		desertHerdingGroundsPenalty = DominionSettings.desertHerdingGroundsPenalty;
	}

	@Override
	public String outputData() {
		String output = 
		"�aPlains Farms:�f +" + plainsFarmBonus + "  " + 
		"�aHerding Grounds:�f +" + plainsHerdingGroundsBonus + "\n" + 
		"�aPlains Spell Power:�f -" + plainsSpellPowerPenalty + "  " + 
		"�aFishing:�f -" + plainsFishingPenalty + "  " +
		"�aDefense:�f -" + plainsDefensePenalty + "\n" +
		"�aForest Woodshop:�f +" + forestWoodshopBonus + "  " +
		"�aHunting:�f +" + forestHuntingBonus + "  " + 
		"�aInn:�f +" + forestInnBonus + "\n" + 
		"�aForest Food Consumption:�f -" + forestFoodConsumptionPenalty + "  " + 
		"�aAttrition:�f " + forestAttritionDamage + "\n" + 
		"�aMountain Masonry:�f +" + mountainMasonryBonus + "  " + 
		"�aMining:�f +" + mountainMiningBonus + "  " + 
		"�aDefense::�f +" + mountainDefenseBonus + "\n" + 
		"�aMountain Wheat:�f -" + mountainWheatPenalty + "  " + 
		"�aTrade:�f -" + mountainTradePenalty + "\n" + 
		"�aSnow Attrition:�f +" + snowAttritionDamage + "  " + 
		"�aFood Decomposition:�f x" + snowFoodDecompositionBonus + "\n" + 
		"�aSnow Wheat:�f -" + snowWheatPenalty + "  " + 
		"�aMelon:�f -" + snowMelonPenalty + "  " + 
		"�aMushroom:�f -" + snowMushroomPenalty + "\n" + 
		"�aJungle Food Consumption:�f -" + jungleFoodConsumptionBonus + "  " + 
		"�aMelon:�f +" + jungleMelonBonus + "\n" + 
		"�aJungle Unit Food Consumption:�f +" + jungleUnitFoodConsumptionPenalty + "  " + 
		"�aUnit Upkeep:�f +" + jungleUnitUpkeepPenalty + "\n" + 
		"�aSwamp Mana Capacity:�f +" + swampManaCapacityBonus + "  " + 
		"�aMana Regen:�f +" + swampManaRegenerationBonus + "\n" + 
		"�aSwamp Wheat:�f -" + swampWheatPenalty + "  " + 
		"�aHerding Grounds:�f -" + swampHerdingGroundsPenalty + "\n" + 
		"�aMushroom Spell Power:�f +" + mushroomSpellPowerBonus + "  " + 
		"�aMushroom:�f +" + mushroomMushroomBonus + "\n" + 
		"�aMushroom Wheat:�f -" + mushroomWheatPenalty + "  " + 
		"�aPumpkin:�f -" + mushroomPumpkinPenalty + "  " + 
		"�aMelon:�f -" + mushroomMelonPenalty + "\n" + 
		"�aOcean Fishing:�f +" + oceanFishingBonus + "  " + 
		"�aTrade:�f +" + oceanTradeBonus + "\n" + 
		"�aOcean Mana Regen:�f -" + oceanManaRegenerationPenalty + "  " + 
		"�aMining:�f -" + oceanMiningPenalty + "\n" + 
		"�aDesert Training Grounds:�f +" + desertTrainingGroundsBonus + "  " + 
		"�aSandworks:�f +" + desertSandworksBonus + "\n" + 
		"�aDesertFarm:�f -" + desertFarmPenalty + "  " + 
		"�aHerding Grounds:�f -" + desertHerdingGroundsPenalty + "\n";
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
