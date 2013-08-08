package com.pico52.dominion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/** 
 * <b>DominionSettings</b><br>
 * <br>
 * &nbsp;&nbsp;public class DominionSettings
 * <br>
 * <br>
 * The main file for primary Dominion settings.
 */
public class DominionSettings {
	
	private static Dominion plugin;
	
	public static int 
	productionTaskTime, 
	unitTaskTime, 
	spellTaskTime, 
	townDefense, 
	cityDefense, 
	fortressDefense;
	
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
	desertFarmPenalty, desertHerdingGroundsPenalty, 
	unitPickUpFromSettlementRange, 
	unitDropOffInSettlementRange, 
	unitPickUpRange, 
	foodDecay, 
	foodConsumption, 
	stealingRate, 
	incomeTax;
	
	public static boolean 
	productionTimerWaitToNextHour, 
	unitTimerWaitToNextMinute, 
	spellTimerWaitToNextMinute, 
	broadcastProductionTick, 
	broadcastUnitTick, 
	broadcastSpellTick;
	
    private static FileConfiguration unitsConfig = null;
    private static File unitsConfigFile = null;
    private static FileConfiguration spellsConfig = null;
    private static File spellsConfigFile = null;
    private static FileConfiguration buildingsConfig = null;
    private static File buildingsConfigFile = null;

	public static void onEnable(Dominion instance){
		plugin = instance;
		FileConfiguration config = plugin.getConfig();
		saveDefaultUnitsConfig();
		saveDefaultSpellsConfig();
		saveDefaultBuildingsConfig();
		
		//---UNITS---//
		unitPickUpFromSettlementRange = config.getInt("units.pick_up_from_settlement_range");
		unitDropOffInSettlementRange = config.getInt("units.drop_off_in_settlement_range");
		unitPickUpRange = config.getInt("units.pick_up_range");
		
		//---TASKS---//
		productionTaskTime = config.getInt("tasks.production_time");
		unitTaskTime = config.getInt("tasks.unit_time");
		spellTaskTime = config.getInt("tasks.spell_time");
		productionTimerWaitToNextHour = config.getBoolean("tasks.production_wait_to_next_hour");
		unitTimerWaitToNextMinute = config.getBoolean("tasks.unit_wait_to_next_hour");
		spellTimerWaitToNextMinute = config.getBoolean("tasks.spell_wait_to_next_hour");
		broadcastProductionTick = config.getBoolean("tasks.broadcast.production_tick");
		broadcastUnitTick = config.getBoolean("tasks.broadcast.unit_tick");
		broadcastSpellTick = config.getBoolean("tasks.broadcast.spell_tick");
		
		//---SETTLEMENTS---//
		townDefense = config.getInt("settlements.defense.town");
		cityDefense = config.getInt("settlements.defense.city");
		fortressDefense = config.getInt("settlements.defense.fortress");
		foodDecay = config.getDouble("settlements.food_decay");
		foodConsumption = config.getDouble("settlements.food_consumption");
		stealingRate = config.getDouble("settlements.stealing_rate");
		incomeTax = config.getDouble("settlements.income_tax");
		
		//---BIOMES---//
		plainsFarmBonus = config.getDouble("biomes.plains.farm_bonus");
		plainsHerdingGroundsBonus = config.getDouble("biomes.plains.herding_grounds_bonus");
		plainsSpellPowerPenalty = config.getDouble("biomes.plains.spell_power_penalty");
		plainsFishingPenalty = config.getDouble("biomes.plains.fishing_penalty");
		plainsDefensePenalty = config.getDouble("biomes.plains.defense_penalty");
		
		forestWoodshopBonus = config.getDouble("biomes.forest.woodshop_bonus");
		forestHuntingBonus = config.getDouble("biomes.forest.hunting_bonus");
		forestInnBonus = config.getDouble("biomes.forest.inn_bonus");
		forestFoodConsumptionPenalty = config.getDouble("biomes.forest.food_consumption_penalty");
		forestAttritionDamage = config.getDouble("biomes.forest.attrition_damage");
		
		mountainMasonryBonus = config.getDouble("biomes.mountain.masonry_bonus");
		mountainMiningBonus = config.getDouble("biomes.mountain.mining_bonus");
		mountainDefenseBonus = config.getDouble("biomes.mountain.defense_bonus");
		mountainWheatPenalty = config.getDouble("biomes.mountain.wheat_penalty");
		mountainTradePenalty = config.getDouble("biomes.mountain.trade_penalty");
		
		snowAttritionDamage = config.getDouble("biomes.snow.attrition_damage");
		snowFoodDecompositionBonus = config.getDouble("biomes.snow.food_decomposition_bonus");
		snowWheatPenalty = config.getDouble("biomes.snow.wheat_penalty");
		snowMelonPenalty = config.getDouble("biomes.snow.melon_penalty");
		snowMushroomPenalty = config.getDouble("biomes.snow.mushroom_penalty");
		
		jungleFoodConsumptionBonus = config.getDouble("biomes.jungle.food_consumption_bonus");
		jungleMelonBonus = config.getDouble("biomes.jungle.melon_bonus");
		jungleUnitFoodConsumptionPenalty = config.getDouble("biomes.jungle.unit_food_consumption_penalty");
		jungleUnitUpkeepPenalty = config.getDouble("biomes.jungle.unit_upkeep_penalty");
		
		swampManaCapacityBonus = config.getDouble("biomes.swamp.mana_capacity_bonus");
		swampManaRegenerationBonus = config.getDouble("biomes.swamp.mana_regeneration_bonus");
		swampWheatPenalty = config.getDouble("biomes.swamp.wheat_penalty");
		swampHerdingGroundsPenalty = config.getDouble("biomes.swamp.herding_grounds_penalty");
		
		mushroomSpellPowerBonus = config.getDouble("biomes.mushroom.spell_power_bonus");
		mushroomMushroomBonus = config.getDouble("biomes.mushroom.mushroom_bonus");
		mushroomWheatPenalty = config.getDouble("biomes.mushroom.wheat_penalty");
		mushroomPumpkinPenalty = config.getDouble("biomes.mushroom.pumpkin_penalty");
		mushroomMelonPenalty = config.getDouble("biomes.mushroom.melon_penalty");
		
		oceanFishingBonus = config.getDouble("biomes.ocean.fishing_bonus");
		oceanTradeBonus = config.getDouble("biomes.ocean.trade_bonus");
		oceanManaRegenerationPenalty = config.getDouble("biomes.ocean.mana_regeneration_penalty");
		oceanMiningPenalty = config.getDouble("biomes.ocean.mining_penalty");
		
		desertTrainingGroundsBonus = config.getDouble("biomes.desert.training_grounds_bonus");
		desertSandworksBonus = config.getDouble("biomes.desert.sandworks_bonus");
		desertFarmPenalty = config.getDouble("biomes.desert.farm_penalty");
		desertHerdingGroundsPenalty = config.getDouble("biomes.desert.herding_grounds_penalty");
	}
	
    public static void reloadUnitsConfig() {
        if (unitsConfigFile == null)
        	unitsConfigFile = new File(plugin.getDataFolder(), "Units.yml");
        unitsConfig = YamlConfiguration.loadConfiguration(unitsConfigFile);
        
        InputStream defConfigStream = plugin.getResource("Units.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            unitsConfig.setDefaults(defConfig);
        }
    }
    
    public static void reloadSpellsConfig() {
        if (spellsConfigFile == null)
        	spellsConfigFile = new File(plugin.getDataFolder(), "Spells.yml");
        spellsConfig = YamlConfiguration.loadConfiguration(spellsConfigFile);
        
        InputStream defConfigStream = plugin.getResource("Spells.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            spellsConfig.setDefaults(defConfig);
        }
    }
    
    public static void reloadBuildingsConfig() {
        if (buildingsConfigFile == null)
        	buildingsConfigFile = new File(plugin.getDataFolder(), "Buildings.yml");
        buildingsConfig = YamlConfiguration.loadConfiguration(buildingsConfigFile);
        
        InputStream defConfigStream = plugin.getResource("Buildings.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            buildingsConfig.setDefaults(defConfig);
        }
    }
    
    public static FileConfiguration getUnitsConfig() {
        if (unitsConfig == null)
        	reloadUnitsConfig();
        return unitsConfig;
    }
    
    public static FileConfiguration getSpellsConfig() {
        if (spellsConfig == null)
        	reloadSpellsConfig();
        return spellsConfig;
    }
    
    public static FileConfiguration getBuildingsConfig() {
        if (buildingsConfig == null)
        	reloadBuildingsConfig();
        return buildingsConfig;
    }
    
    public static void saveUnitsConfig() {
    	if (unitsConfig == null || unitsConfigFile == null)
    		return;
        try {
            getUnitsConfig().save(unitsConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + unitsConfigFile, ex);
        }
    }
    
    public static void saveSpellsConfig() {
    	if (spellsConfig == null || spellsConfigFile == null)
    		return;
        try {
            getSpellsConfig().save(spellsConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + spellsConfigFile, ex);
        }
    }
    
    public static void saveBuildingsConfig() {
    	if (buildingsConfig == null || buildingsConfigFile == null)
    		return;
        try {
            getBuildingsConfig().save(buildingsConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + buildingsConfigFile, ex);
        }
    }
    
    public static void saveDefaultUnitsConfig() {
        if (unitsConfigFile == null)
            unitsConfigFile = new File(plugin.getDataFolder(), "Units.yml");
        if (!unitsConfigFile.exists())
             plugin.saveResource("Units.yml", false);
    }
    
    public static void saveDefaultSpellsConfig() {
        if (spellsConfigFile == null)
            spellsConfigFile = new File(plugin.getDataFolder(), "Spells.yml");
        if (!spellsConfigFile.exists())
             plugin.saveResource("Spells.yml", false);
    }
    
    public static void saveDefaultBuildingsConfig() {
        if (buildingsConfigFile == null)
            buildingsConfigFile = new File(plugin.getDataFolder(), "Buildings.yml");
        if (!buildingsConfigFile.exists())
             plugin.saveResource("Buildings.yml", false);
    }
}
