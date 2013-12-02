package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.EntityEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>SpellManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class SpellManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for spells.
 */
public class SpellManager extends DominionObjectManager{
	FileConfiguration config;
	SettlementManager settlementManager;

	/** 
	 * <b>SpellManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SpellManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SpellManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public SpellManager(Dominion plugin) {
		super(plugin);
		config = DominionSettings.getSpellsConfig();
	}
	
	public boolean castSpell(int settlementId, int casterId, String spell, int targetId, String arg){
		spell = spell.toLowerCase();
		if(!isSpell(spell))
			return false;
		settlementManager = plugin.getSettlementManager();
		if(db.getOwnerId("settlement", settlementId) != casterId && 
				!plugin.getPermissionManager().hasPermission(casterId, "cast", settlementId)){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix()+ "You do not have permission to cast this spell!");
			return false;
		}
		String settlementName = db.getSettlementName(settlementId);
		String playerName = db.getPlayerName(casterId);
		double spellPower = settlementManager.getSpellPower(settlementId);
		
		List<String> effectsList = config.getStringList("spells." + spell + ".effects");
		String[] effects = effectsList.toArray(new String[effectsList.size()]);
		double strength = config.getDouble("spells." + spell + ".strength");
		double areaOfEffect = config.getDouble("spells." + spell + ".area_of_effect");
		double duration = config.getDouble("spells." + spell + ".duration");
		double wealthCost = config.getDouble("spells." + spell + ".cost.wealth");
		double manaCost = config.getDouble("spells." + spell + ".cost.mana");
		double redstoneCost = config.getDouble("spells." + spell + ".cost.redstone");
		String material = config.getString("spells." + spell + ".cost.material.type");
		double materialCost = config.getDouble("spells." + spell + ".cost.material.quantity");
		
		//---CHECK COSTS---//
		//---Wealth---//
		if(wealthCost > 0 && !settlementManager.hasMaterial(settlementId, "wealth", wealthCost)){
			String message = plugin.getLogPrefix() + " This spell costs " + wealthCost + " wealth, but " + settlementName + " only has " + settlementManager.getMaterial(settlementId, "wealth") + " wealth.";
			plugin.getServer().getPlayer(playerName).sendMessage(message);
			return false;
		}
		//---Mana---//
		if(manaCost > 0 && !settlementManager.hasMaterial(settlementId, "mana", manaCost)){
			String message = plugin.getLogPrefix() + " This spell costs " + manaCost + " mana, but " + settlementName + " only has " + settlementManager.getMaterial(settlementId, "mana") + " mana.";
			plugin.getServer().getPlayer(playerName).sendMessage(message);
			return false;
		}
		//---Redstone---//
		if(redstoneCost > 0 && !settlementManager.hasMaterial(settlementId, "redstone", redstoneCost)){
			String message = plugin.getLogPrefix() + " This spell costs " + redstoneCost + " redstone, but " + settlementName + " only has " + settlementManager.getMaterial(settlementId, "redstone") + " redstone.";
			plugin.getServer().getPlayer(playerName).sendMessage(message);
			return false;
		}
		//---Material---//
		if(materialCost > 0 && !material.equalsIgnoreCase("none") && !settlementManager.hasMaterial(settlementId, material, materialCost)){
			String message = plugin.getLogPrefix() + " This spell costs " + materialCost + " " + material + ", but " + settlementName + " only has " + settlementManager.getMaterial(settlementId, material) + " " + material + ".";
			plugin.getServer().getPlayer(playerName).sendMessage(message);
			return false;
		}
		//---CAST EFFECTS---//
		strength *= spellPower;
		for(String effect: effects){
			if(isInstant(effect)){
				if(!castInstantEffect(effect, targetId, casterId, strength, areaOfEffect, arg))
					return false;
			} else if(isLasting(effect)){
				if(!castLastingEffect(effect, settlementId, targetId, casterId, strength, duration, areaOfEffect, arg))
					return false;
			}
		}
		//---SUBTRACT COSTS---//
		boolean wealthSuccess = true, manaSuccess = true, redstoneSuccess = true, materialSuccess = true;
		wealthSuccess = settlementManager.subtractMaterial(settlementId, "wealth", wealthCost);
		manaSuccess = settlementManager.subtractMaterial(settlementId, "mana", manaCost);
		redstoneSuccess = settlementManager.subtractMaterial(settlementId, "redstone", redstoneCost);
		if(materialCost > 0 & !material.equalsIgnoreCase("none"))
			materialSuccess = settlementManager.subtractMaterial(settlementId, material, materialCost);
		if(!wealthSuccess | !manaSuccess | !redstoneSuccess | !materialSuccess){
			plugin.getLogger().info("Failed to take resources from the settlement.\n" +
					"Wealth: " + wealthSuccess + " Mana: " + manaSuccess + " Redstone: " + redstoneSuccess + " Material: " + materialSuccess);
			return false;
		}
		
		return true;
	}
	
	public boolean castInstantEffect(String effect, int targetId, int casterId, double strength, String argument){
		return castInstantEffect(effect, targetId, casterId, strength, 0, argument);
	}
	
	public boolean castInstantEffect(String effect, int targetId, int casterId, double strength, double areaOfEffect, String argument){
		if(effect.equalsIgnoreCase("kill_peasants"))
			return killPeasants(targetId, casterId, strength);
		if(effect.equalsIgnoreCase("aoe_unit_damage"))
			return aoeUnitDamage(targetId, casterId, strength, areaOfEffect);
		if(effect.equalsIgnoreCase("sea_unit_damage")){
			if(plugin.getUnitManager().getUnitType(plugin.getUnitManager().getClass(targetId)).equalsIgnoreCase("sea"))
				return damageUnit(targetId, casterId, strength);
			else
				return true;
		}
		if(effect.equalsIgnoreCase("create_resource"))
			return createResource(targetId, casterId, strength, argument);
		if(effect.equalsIgnoreCase("clear_weather"))
			return clearWeather(targetId, casterId);
		if(effect.equalsIgnoreCase("rain_weather"))
			return rainWeather(targetId, casterId);
		return false;
	}
	
	public boolean castLastingEffect(String effect, int settlementId, int targetId, int casterId, double strength, double duration, String argument){
		return castLastingEffect(effect, settlementId, targetId, casterId, strength, duration, 0, argument);
	}
	
	public boolean castLastingEffect(String effect, int settlementId, int targetId, int casterId, double strength, double duration, double areaOfEffect, String argument){
		if(effect.equalsIgnoreCase("defense_bonus"))
			return castDefenseBonus(settlementId, targetId, casterId, strength, duration);
		if(effect.equalsIgnoreCase("mining_bonus"))
			return castMiningBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("trade_bonus"))
			return castTradeBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("farm_bonus"))
			return castFarmBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("herding_grounds_bonus"))
			return castHerdingGroundsBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("masonry_bonus"))
			return castMasonryBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("sandworks_bonus"))
			return castSandworksBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("birth_rate_bonus"))
			return castBirthRateBonus(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("spell_power_penalty"))
			return castSpellPowerPenalty(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("production_penalty"))
			return castProductionPenalty(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("destroy_building"))
			return castDestroyBuilding(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("aoe_unit_slow"))
			return castAoeUnitSlow(settlementId, targetId,  casterId, strength, duration, areaOfEffect);
		if(effect.equalsIgnoreCase("create_fake_unit"))
			return castCreateFakeUnit(settlementId, targetId,  casterId, strength, duration);
		if(effect.equalsIgnoreCase("aoe_reveal_invisible_units"))
			return castAoeRevealInvisibleUnits(settlementId, targetId,  casterId, strength, duration, areaOfEffect);
		if(effect.equalsIgnoreCase("aoe_reveal_fake_units"))
			return castAoeRevealFakeUnits(settlementId, targetId,  casterId, strength, duration, areaOfEffect);
		if(effect.equalsIgnoreCase("unit_invisibility"))
			return castUnitInvisibility(settlementId, targetId,  casterId, strength, duration);
		return false;
	}
	
	//---LASTING---//
	public boolean castDefenseBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double defenseBonus = config.getDouble("effects.defense_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "defense_bonus", defenseBonus, duration);
	}

	public boolean castMiningBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double miningBonus = config.getDouble("effects.mining_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "mining_bonus", miningBonus, duration);
	}

	public boolean castTradeBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double tradeBonus = config.getDouble("effects.trade_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "trade_bonus", tradeBonus, duration);
	}

	public boolean castFarmBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double farmBonus = config.getDouble("effects.farm_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "farm_bonus", farmBonus, duration);
	}

	public boolean castHerdingGroundsBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double herdingGroundsBonus = config.getDouble("effects.herding_grounds_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "herding_grounds_bonus", herdingGroundsBonus, duration);
	}

	public boolean castMasonryBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double masonryBonus = config.getDouble("effects.masonry_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "masonry_bonus", masonryBonus, duration);
	}

	public boolean castSandworksBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double sandworksBonus = config.getDouble("effects.sandworks_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "sandworks_bonus", sandworksBonus, duration);
	}

	public boolean castBirthRateBonus(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double birthRateBonus = config.getDouble("effects.birth_rate_bonus") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "birth_rate_bonus", birthRateBonus, duration);
	}

	public boolean castSpellPowerPenalty(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double spellPowerPenalty = config.getDouble("effects.spell_power_penalty") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "spell_power_penalty", spellPowerPenalty, duration);
	}
	
	public boolean castProductionPenalty(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double productionPenalty = config.getDouble("effects.production_penalty") * strength;
		return db.createSpell(settlementId, casterId, targetId, "settlement", "production_penalty", productionPenalty, duration);
	}

	public boolean castDestroyBuilding(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "building")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The building provided does not exist.");
			return false;
		}
		double chance = config.getDouble("effects.destroy_building.base_chance") + (config.getDouble("effects.destroy_building.chance") * strength);
		String caster = db.getPlayerName(casterId);
		if(chance < 1 & chance < Math.random()){
			plugin.sendMessage(caster, plugin.getLogPrefix() + "The spell fizzled with a " + (chance * 100) + "% chance of success..");
			return true;
		}
		int finalDuration = (int) (duration + config.getDouble("effects.destroy_building.base_duration") + (config.getDouble("effects.destroy_building.duration") * strength));
		boolean success = db.createSpell(settlementId, casterId, targetId, "building", "destroy_building", strength, finalDuration);
		if(success){
			plugin.sendMessage(caster, plugin.getLogPrefix() + "Successfully destroyed building " + targetId + " for " + finalDuration + " production ticks.");
			String owner = db.getOwnerName("building", targetId);
			plugin.sendMessage(owner, plugin.getLogPrefix() + "Your building " + targetId + " has been destroyed for " + finalDuration + " production ticks due to a spell!");
		} else
			plugin.sendMessage(caster, plugin.getLogPrefix() + "Failed to destroy building " + targetId);
		return success;
	}

	public boolean castAoeUnitSlow(int settlementId, int targetId, int casterId, double strength, double duration, double areaOfEffect) {
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		UnitManager um = plugin.getUnitManager();
		double xCoord = um.getUnitX(targetId);
		double zCoord = um.getUnitZ(targetId);
		double slow = config.getDouble("effects.aoe_unit_slow") * strength;
		return db.createSpell(settlementId, casterId, targetId, "unit", "aoe_unit_slow", slow, duration, areaOfEffect, xCoord, zCoord);
	}

	public boolean castCreateFakeUnit(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		UnitManager um = plugin.getUnitManager();
		double xCoord = um.getUnitX(targetId);
		double zCoord = um.getUnitZ(targetId);
		double health = um.getHealth(targetId);
		String targetClass = um.getClass(targetId);
		int finalDuration = (int) (duration + (config.getDouble("effects.create_fake_unit_duration") * strength));
		int newUnitId = um.createUnit(settlementId, casterId, targetClass, health, false);
		return db.createSpell(settlementId, casterId, newUnitId, "unit", "create_fake_unit", strength, finalDuration, 0, xCoord, zCoord);
	}

	public boolean castAoeRevealInvisibleUnits(int settlementId, int targetId, int casterId, double strength, double duration, double areaOfEffect) {
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		UnitManager um = plugin.getUnitManager();
		double xCoord = um.getUnitX(targetId);
		double zCoord = um.getUnitZ(targetId);
		double finalArea = areaOfEffect + (config.getDouble("effects.aoe_reveal_invisible_units") * strength);
		return db.createSpell(settlementId, casterId, targetId, "unit", "aoe_reveal_invisible_units", strength, duration, finalArea, xCoord, zCoord);
	}

	public boolean castAoeRevealFakeUnits(int settlementId, int targetId, int casterId, double strength, double duration, double areaOfEffect) {
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		UnitManager um = plugin.getUnitManager();
		double xCoord = um.getUnitX(targetId);
		double zCoord = um.getUnitZ(targetId);
		double finalArea = areaOfEffect + (config.getDouble("effects.aoe_reveal_fake_units") * strength);
		return db.createSpell(settlementId, casterId, targetId, "unit", "aoe_reveal_fake_units", strength, duration, finalArea, xCoord, zCoord);
	}

	public boolean castUnitInvisibility(int settlementId, int targetId, int casterId, double strength, double duration) {
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		UnitManager um = plugin.getUnitManager();
		double attackPower = um.getUnit(um.getClass(targetId)).offense;
		if((config.getDouble("effects.unit_invisibility.strength_required") * attackPower) > strength)
			return false;
		double xCoord = um.getUnitX(targetId);
		double zCoord = um.getUnitZ(targetId);
		int finalDuration = (int) (duration + (config.getDouble("effects.unit_invisibility") * strength));
		return db.createSpell(settlementId, casterId, targetId, "unit", "unit_invisibility", strength, finalDuration, 0, xCoord, zCoord);
	}

	//---INSTANTS---//
	public boolean killPeasants(int targetId, int casterId, double strength){
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double quantity = config.getDouble("effects.kill_peasants") * strength;
		return plugin.getSettlementManager().killPeasants(targetId, quantity);
	}
	
	public boolean aoeUnitDamage(int targetId, int casterId, double strength, double areaOfEffect){
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		double damage = config.getDouble("effects.aoe_unit_damage") * strength;
		UnitManager unitManager = plugin.getUnitManager();
		int[] units = unitManager.getUnitsWithinRange(targetId, areaOfEffect);
		boolean success = true;
		for(int unit: units){
			if(!unitManager.damage(unit, damage))
				success = false;
		}
		return success;
	}
	
	public boolean damageUnit(int targetId, int casterId, double strength){
		if(!db.thingExists(targetId, "unit")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The unit provided does not exist.");
			return false;
		}
		double damage = config.getDouble("effects.unit_damage") * strength;
		return plugin.getUnitManager().damage(targetId, damage);
	}
	
	public boolean createResource(int targetId, int casterId, double strength, String resource){
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		double quantity = config.getDouble("effects.create_resource." + resource) * strength;
		return plugin.getSettlementManager().addMaterial(targetId, resource, quantity);
	}
	
	public boolean clearWeather(int targetId, int casterId){
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		String playerName = db.getPlayerName(targetId);
		try{
			plugin.getServer().getPlayer(playerName).getWorld().setStorm(false);
			plugin.getServer().getPlayer(playerName).playEffect(EntityEffect.HURT);
		} catch (IllegalArgumentException ex){
			// - An issue with the player name, most likely.
			return false;
		}
		return true;
	}
	
	public boolean rainWeather(int targetId, int casterId){
		if(!db.thingExists(targetId, "settlement")){
			plugin.sendMessage(db.getPlayerName(casterId), plugin.getLogPrefix() + "The settlement provided does not exist.");
			return false;
		}
		String playerName = db.getPlayerName(targetId);
		try{
			plugin.getServer().getPlayer(playerName).getWorld().setStorm(true);
			plugin.getServer().getPlayer(playerName).playEffect(EntityEffect.HURT);
		} catch (IllegalArgumentException ex){
			// - An issue with the player name, most likely.
			return false;
		}
		return true;
	}
	//--------------//
	
	public boolean isSpell(String spell){
		String name = "";
		ConfigurationSection section = config.getConfigurationSection("spells");
		for(String key: section.getKeys(false)){
			name = config.getString(key + ".name");
			if(spell.equalsIgnoreCase(key) | spell.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public boolean removeSpell(int spellId){
		String type = getType(spellId);
		if(type.equalsIgnoreCase("create_fake_unit"))
			plugin.getUnitManager().kill(getTargetId(spellId), "spell");
		return db.remove("spell", spellId);
	}
	
	public boolean castRecurringEffect(int spellId){
		// - This will be used later for spells that do damage over time that would normally be instants.
		return true;
	}
	
	public boolean isSpellEffect(String effect){
		if(effect.equalsIgnoreCase("defense_bonus") | 
				effect.equalsIgnoreCase("mining_bonus") | 
				effect.equalsIgnoreCase("trade_bonus") | 
				effect.equalsIgnoreCase("farm_bonus") | 
				effect.equalsIgnoreCase("herding_grounds_bonus") | 
				effect.equalsIgnoreCase("masonry_bonus") | 
				effect.equalsIgnoreCase("sandworks_bonus") | 
				effect.equalsIgnoreCase("birth_rate_bonus") | 
				effect.equalsIgnoreCase("spell_power_penalty") | 
				effect.equalsIgnoreCase("kill_peasants") | 
				effect.equalsIgnoreCase("production_penalty") | 
				effect.equalsIgnoreCase("aoe_unit_damage") | 
				effect.equalsIgnoreCase("destroy_building") | 
				effect.equalsIgnoreCase("aoe_unit_slow") | 
				effect.equalsIgnoreCase("sea_unit_damage") | 
				effect.equalsIgnoreCase("create_resource") | 
				effect.equalsIgnoreCase("clear_weather") | 
				effect.equalsIgnoreCase("rain_weather") | 
				effect.equalsIgnoreCase("create_fake_unit") | 
				effect.equalsIgnoreCase("aoe_reveal_invisible_units") | 
				effect.equalsIgnoreCase("aoe_reveal_fake_units") | 
				effect.equalsIgnoreCase("unit_invisibility"))
			return true;
		return false;
	}
	
	public boolean isInstant(String effect){
		if(effect.equalsIgnoreCase("kill_peasants") | 
				effect.equalsIgnoreCase("aoe_unit_damage") | 
				effect.equalsIgnoreCase("sea_unit_damage") | 
				effect.equalsIgnoreCase("create_resource") | 
				effect.equalsIgnoreCase("clear_weather") | 
				effect.equalsIgnoreCase("rain_weather"))
			return true;
		return false;
	}
	
	public boolean isLasting(String effect){
		if(effect.equalsIgnoreCase("defense_bonus") |
				effect.equalsIgnoreCase("mining_bonus") | 
				effect.equalsIgnoreCase("trade_bonus") | 
				effect.equalsIgnoreCase("farm_bonus") | 
				effect.equalsIgnoreCase("herding_grounds_bonus") | 
				effect.equalsIgnoreCase("masonry_bonus") | 
				effect.equalsIgnoreCase("sandworks_bonus") | 
				effect.equalsIgnoreCase("birth_rate_bonus") | 
				effect.equalsIgnoreCase("spell_power_penalty") | 
				effect.equalsIgnoreCase("production_penalty") | 
				effect.equalsIgnoreCase("destroy_building") | 
				effect.equalsIgnoreCase("aoe_unit_slow") | 
				effect.equalsIgnoreCase("create_fake_unit") | 
				effect.equalsIgnoreCase("aoe_reveal_invisible_units") | 
				effect.equalsIgnoreCase("aoe_reveal_fake_units") | 
				effect.equalsIgnoreCase("unit_invisibility") | 
				effect.equalsIgnoreCase("trade_bonus"))
			return true;
		return false;
	}
	
	public double getBonus(int objectId, String objectType, String bonus){
		double spellBonus = 0, thisBonus = 0;
		for(int spell: getActiveSpells(objectId, objectType, bonus + "_bonus")){
			thisBonus = getPower(spell);
			spellBonus = (thisBonus > spellBonus) ? thisBonus : spellBonus;
		}
		return spellBonus;
	}
	
	public double getPenalty(int objectId, String objectType, String penalty){
		double spellBonus = 0, thisBonus = 0;
		for(int spell: getActiveSpells(objectId, objectType, penalty + "_penalty")){
			thisBonus = getPower(spell);
			spellBonus = (thisBonus > spellBonus) ? thisBonus : spellBonus;
		}
		return spellBonus;
	}
	
	public int[] getActiveSpells(int objectId, String objectType){
		return getActiveSpells(objectId, objectType, "*");
	}
	
	public int[] getActiveSpells(int objectId, String objectType, String spellType){
		String where = "object_id=" + objectId + " and object=\'" + objectType + "\' and class=\'" + spellType + "\'";
		if(spellType.equalsIgnoreCase("*"))
			where = "object_id=" + objectId + " and object=\'" + objectType + "\'";
		ResultSet spells = plugin.getDBHandler().getTableData("spell", "spell_id", where);
		ArrayList<Integer> list = new ArrayList<Integer>();
		try{
			while(spells.next()){
				list.add(spells.getInt("spell_id"));
			}
			spells.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] activeSpells = new int[list.size()];
		int i = 0;
	    for (Integer num : list){
	        activeSpells[i++] = num;
	    }
		return activeSpells;
	}
	
	public int[] getAllSpells(String effect){
		String where = "class=\'" + effect + "\'";
		ResultSet spells;
		if(effect.equalsIgnoreCase("*"))
			spells = plugin.getDBHandler().getTableData("spell", "spell_id");
		else
			spells = plugin.getDBHandler().getTableData("spell", "spell_id", where);
		ArrayList<Integer> list = new ArrayList<Integer>();
		try{
			while(spells.next()){
				list.add(spells.getInt("spell_id"));
			}
			spells.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] activeSpells = new int[list.size()];
		int i = 0;
		if(activeSpells.length > 0){
			for (Integer num : list){
	        	activeSpells[i++] = num;
	    	}
		}
		return activeSpells;
	}
	
	public int getSettlementId(int spellId){
		int settlementId = -1;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "settlement_id", "spell_id");
		try{
			if(spell.next())
				settlementId = spell.getInt("settlement_id");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return settlementId;
	}
	
	public int getCasterId(int spellId){
		int casterId = -1;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "owner_id", "spell_id");
		try{
			if(spell.next())
				casterId = spell.getInt("owner_id");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return casterId;
	}
	
	public int getTargetId(int spellId){
		int targetId = -1;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "object_id", "spell_id");
		try{
			if(spell.next())
				targetId = spell.getInt("object_id");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return targetId;
	}
	
	public String getTargetType(int spellId){
		String targetType = "";
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "object", "spell_id");
		try{
			if(spell.next())
				targetType = spell.getString("object");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return targetType;
	}
	
	public String getType(int spellId){
		String type = "";
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "class", "spell_id");
		try{
			if(spell.next())
				type = spell.getString("class");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return type;
	}
	
	public double getPower(int spellId){
		double power = 0;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "power", "spell_id");
		try{
			if(spell.next()){
				power = spell.getDouble("power");
			}
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return power;
	}
	
	public double getDuration(int spellId){
		double duration = 0;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "duration", "spell_id");
		try{
			if(spell.next())
				duration = spell.getDouble("duration");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return duration;
	}
	
	public double getAreaOfEffect(int spellId){
		double areaOfEffect = 0;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "area_of_effect", "spell_id");
		try{
			if(spell.next())
				areaOfEffect = spell.getDouble("area_of_effect");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return areaOfEffect;
	}
	
	public double getX(int spellId){
		double xCoord = 0;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "xcoord", "spell_id");
		try{
			if(spell.next())
				xCoord = spell.getDouble("xcoord");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return xCoord;
	}
	
	public double getZ(int spellId){
		double zCoord = 0;
		ResultSet spell = plugin.getDBHandler().getTableData("spell", spellId, "zcoord", "spell_id");
		try{
			if(spell.next())
				zCoord = spell.getDouble("zcoord");
			spell.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return zCoord;
	}
	
	public boolean isWithinAreaOfEffect(double objectX, double objectZ, int spellId){
		double spellX = getX(spellId), spellZ = getZ(spellId), areaOfEffect = getAreaOfEffect(spellId), 
				xDifference = 0, zDifference = 0;
		xDifference = Math.abs(spellX - objectX);
		zDifference = Math.abs(spellZ - objectZ);
		if(xDifference < areaOfEffect & zDifference < areaOfEffect)
			return true;
		return false;
	}
	
	public String outputSpellData(){
		String output = "", name = "", material = "", type = "";
		double strength = 0, duration = 0, mana = 0, redstone = 0, wealth = 0, quantity = 0;
		ConfigurationSection section = config.getConfigurationSection("spells");
		for(String spell: section.getKeys(false)){
			name = config.getString("spells." + spell + ".name");
			type = config.getString("spells." + spell + ".type");
			strength = config.getDouble("spells." + spell +".strength");
			duration = config.getDouble("spells." + spell +".duration");
			mana = config.getDouble("spells." + spell +".cost.mana");
			wealth = config.getDouble("spells." + spell +".cost.wealth");
			redstone = config.getDouble("spells." + spell +".cost.redstone");
			material = config.getString("spells." + spell +".cost.material.type");
			quantity = config.getDouble("spells." + spell +".cost.material.quantity");
			List<String> effects = config.getStringList("spells." + spell + ".effects");
			char colorCode = '5';
			if(type.equalsIgnoreCase("buff"))
				colorCode = '2';
			else if (type.equalsIgnoreCase("utility"))
				colorCode = '9';
			else if (type.equalsIgnoreCase("curse"))
				colorCode = 'c';
			output += "§" + colorCode + name + "§f - §aStr:§f " + strength + "  ";
			if(duration > 0)
				output += "§aDur:§f " + duration + "  ";
			if(mana > 0)
				output += "§aMana:§f " + mana + "  ";
			if(wealth > 0)
				output += "§aWealth:§f " + wealth + "  ";
			if(redstone > 0)
				output += "§aRedstone:§f " + redstone + "  ";
			if(!material.equalsIgnoreCase("none") & quantity > 0)
				output += "§aMat:§f " + material + "  §aNum:§f " + quantity + "  ";
			for(String effect: effects){
				output += "§aEffect:§f " + effect + "  ";
			}
			output += "\n§a--------------------------§r\n";
		}
		if(output == "")
			output = "No spells are currently available.";
		return output;
	}
}
