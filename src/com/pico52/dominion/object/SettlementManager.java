package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.datasheet.BiomeData;
import com.pico52.dominion.datasheet.ProductionSheet;
import com.pico52.dominion.object.building.Bank;
import com.pico52.dominion.object.building.Granary;
import com.pico52.dominion.object.building.Home;
import com.pico52.dominion.object.building.Library;
import com.pico52.dominion.object.building.Spire;

/** 
 * <b>SettlementManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class SettlementManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for settlements.
 */
public class SettlementManager extends DominionObjectManager{
	private static double baseFoodDecay, baseFoodConsumption, baseStealingRate, baseIncomeTax;
	
	/** 
	 * <b>SettlementManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public SettlementManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link SettlementManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public SettlementManager(Dominion instance){
		super(instance);
		baseFoodDecay = DominionSettings.foodDecay;
		baseFoodConsumption = DominionSettings.foodConsumption;
		baseStealingRate = DominionSettings.stealingRate;
		baseIncomeTax = DominionSettings.incomeTax;
	}
	

	/** 
	 * <b>createSettlement</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createSettlement(String name)
	 * <br>
	 * <br>
	 * Creates a settlement in the database with default values and the specified name.
	 * @param name - The name of the new settlement.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createSettlement(String name){
		return createSettlement(name, "none", "town");
	}
	
	/** 
	 * <b>createSettlement</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createSettlement(String name)
	 * <br>
	 * <br>
	 * Creates a settlement in the database with default values and the specified name.
	 * @param name - The name of the new settlement.
	 * @param biome - The biome the settlement resides in.
	 * @param classification - The type of settlement: Town/City/Fortress
	 * @return The sucess of the execution of this command.
	 */
	public boolean createSettlement(String name, String biome, String classification){
		return createSettlement(name, 0, biome, classification, 0, 0);
	}
	
	/** 
	 * <b>createSettlement</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createSettlement(String name)
	 * <br>
	 * <br>
	 * Creates a settlement in the database with default values and the specified name.
	 * @param name - The name of the new settlement.
	 * @param ownerId - The id of the owner of the new settlement.
	 * @param biome - The biome the settlement resides in.
	 * @param classification - The type of settlement: Town/City/Fortress
	 * @param xcoord - The x-coordinate of the new settlement.
	 * @param zcoord - The z-coordinate of the new settlement.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createSettlement(String name, int ownerId, String biome, String classification, double xcoord, double zcoord){
		if(name == "" | name == null | db.settlementExists(name))
			return false;
		return db.createSettlement(name, ownerId, biome, classification, xcoord, zcoord);
	}
	
	/** 
	 * <b>updateAll</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean updateAll()
	 * <br>
	 * <br>
	 * Updates all materials in all settlements.
	 * @return True if all of the settlements have been successfully updated.  False if they have not.
	 */
	public boolean updateAll(){
		ResultSet settlements = db.getTableData("settlement", "settlement_id");
		boolean success = true;
		try{
			while(settlements.next()){
				int settlementId = settlements.getInt("settlement_id");
				if(!update(settlementId))
					success = false;
			}
			settlements.getStatement().close();
			return success;
		} catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update({@link String} settlement)
	 * <br>
	 * <br>
	 * Updates all materials in a settlement.
	 * @param settlement - The name of the settlement to update.
	 * @return True if the settlement has been successfully updated.  False if it has not.
	 */
	public boolean update(String settlement){
		return update(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update(int settlement_id)
	 * <br>
	 * <br>
	 * Updates all materials in a settlement.
	 * @param settlement - The id of the settlement to update.
	 * @return True if the settlement has been successfully updated.  False if it has not.
	 */
	public boolean update(int settlement_id){
		ProductionSheet update = plugin.getBuildingManager().getProductions(settlement_id);
		update.wealth += getIncomeTax(settlement_id);
		update.wealth += getTradeValue(settlement_id);
		ResultSet settlementData = db.getSettlementData(settlement_id, "*");
		double maxMana=0, mana=0, maxPopulation=0, population=0, wealth=0, food=0, wood=0, cobblestone=0, 
				stone=0, sand=0, gravel=0, dirt=0, ironIngot=0, ironOre=0, emerald=0, emeraldOre=0, goldIngot=0, goldOre=0, 
				flint=0, feather=0, lapisOre=0, diamond=0, obsidian=0, netherrack=0, netherBrick=0, redstone=0, brick=0, 
				glowstone=0, clay=0, coal=0, wool=0, leather=0, arrow=0, armor=0, weapon=0, snow=0, recruit=0, prisoner=0;
		String name = "";
		try{
			if(settlementData.next()){
				name = settlementData.getString("name");
				mana = settlementData.getDouble("mana");
				population = settlementData.getDouble("population");
				wealth = settlementData.getDouble("wealth");
				food = settlementData.getDouble("food");
				wood = settlementData.getDouble("wood");
				cobblestone = settlementData.getDouble("cobblestone");
				stone = settlementData.getDouble("stone");
				sand = settlementData.getDouble("sand");
				gravel = settlementData.getDouble("gravel");
				dirt = settlementData.getDouble("dirt");
				ironIngot = settlementData.getDouble("iron_ingot");
				ironOre = settlementData.getDouble("iron_ore");
				emerald = settlementData.getDouble("emerald");
				emeraldOre = settlementData.getDouble("emerald_ore");
				goldIngot = settlementData.getDouble("gold_ingot");
				goldOre = settlementData.getDouble("gold_ore");
				flint = settlementData.getDouble("flint");
				feather = settlementData.getDouble("feather");
				lapisOre = settlementData.getDouble("lapis_ore");
				diamond = settlementData.getDouble("diamond");
				obsidian = settlementData.getDouble("obsidian");
				netherrack = settlementData.getDouble("netherrack");
				netherBrick = settlementData.getDouble("nether_brick");
				redstone = settlementData.getDouble("redstone");
				brick = settlementData.getDouble("brick");
				glowstone = settlementData.getDouble("glowstone");
				clay = settlementData.getDouble("clay");
				coal = settlementData.getDouble("coal");
				wool = settlementData.getDouble("wool");
				leather = settlementData.getDouble("leather");
				arrow = settlementData.getDouble("arrow");
				armor = settlementData.getDouble("armor");
				weapon = settlementData.getDouble("weapon");
				snow = settlementData.getDouble("snow");
				recruit = settlementData.getDouble("recruit");
				prisoner = settlementData.getDouble("prisoner");
			}
			settlementData.getStatement().close();
		}catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		// - Remember that the ProductionSheet "update" can hold negative values.
		maxMana = getMaxMana(settlement_id);
		if(mana + update.mana > maxMana)
			update.mana = maxMana - mana;
		else if(mana + update.mana < 0)
			update.mana = -mana;
		maxPopulation = getMaxPopulation(settlement_id);
		if(population + update.population > maxPopulation)
			update.population = maxPopulation - population;
		else if (population + update.population < 0)
			update.population = -population;
		if(wealth + update.wealth < 0)
			update.wealth = -wealth;
		if(food + update.food < 0)
			update.food = -food;
		if(wood + update.wood < 0)
			update.wood = -wood;
		if(cobblestone + update.cobblestone < 0)
			update.cobblestone = -cobblestone;
		if(stone + update.stone < 0)
			update.stone = -stone;
		if(sand + update.sand < 0)
			update.sand = -sand;
		if(gravel + update.gravel < 0)
			update.gravel = -gravel;
		if(dirt + update.dirt < 0)
			update.dirt = -dirt;
		if(ironIngot + update.ironIngot < 0)
			update.ironIngot = -ironIngot;
		if(ironOre + update.ironOre < 0)
			update.ironOre = -ironOre;
		if(emerald + update.emerald < 0)
			update.emerald = -emerald;
		if(emeraldOre + update.emeraldOre < 0)
			update.emeraldOre = -emeraldOre;
		if(goldIngot + update.goldIngot < 0)
			update.goldIngot = -goldIngot;
		if(goldOre + update.goldOre < 0)
			update.goldOre = -goldOre;
		if(flint + update.flint < 0)
			update.flint = -flint;
		if(feather + update.feather < 0)
			update.feather = -feather;
		if(lapisOre + update.lapisOre < 0)
			update.lapisOre = -lapisOre;
		if(diamond + update.diamond < 0)
			update.diamond = -diamond;
		if(obsidian + update.obsidian < 0)
			update.obsidian = -obsidian;
		if(netherrack + update.netherrack < 0)
			update.netherrack = -netherrack;
		if(netherBrick + update.netherBrick < 0)
			update.netherBrick = -netherBrick;
		if(redstone + update.redstone < 0)
			update.redstone = -redstone;
		if(brick + update.brick < 0)
			update.brick = -brick;
		if(glowstone + update.glowstone < 0)
			update.glowstone = -glowstone;
		if(clay + update.clay < 0)
			update.clay = -clay;
		if(coal + update.coal < 0)
			update.coal = -coal;
		if(wool + update.wool < 0)
			update.wool = -wool;
		if(leather + update.leather < 0)
			update.leather = -leather;
		if(arrow + update.arrow < 0)
			update.arrow = -arrow;
		if(armor + update.armor < 0)
			update.armor = -armor;
		if(weapon + update.weapon < 0)
			update.weapon = -weapon;
		if(snow + update.snow < 0)
			update.snow = -snow;
		if(recruit + update.recruit < 0)
			update.recruit = -recruit;
		if(prisoner + update.prisoner < 0)
			update.prisoner = -prisoner;
		
		//---Add existing resources---//
		update.mana += mana;
		update.population += population;
		update.wealth += wealth;
		update.food += food;
		update.wood += wood;
		update.cobblestone += cobblestone;
		update.stone += stone;
		update.sand += sand;
		update.gravel += gravel;
		update.dirt += dirt;
		update.ironIngot += ironIngot;
		update.ironOre += ironOre;
		update.emerald += emerald;
		update.emeraldOre += emeraldOre;
		update.goldIngot += goldIngot;
		update.goldOre += goldOre;
		update.flint += flint;
		update.feather += feather;
		update.lapisOre += lapisOre;
		update.diamond += diamond;
		update.obsidian += obsidian;
		update.netherrack += netherrack;
		update.netherBrick += netherBrick;
		update.redstone += redstone;
		update.brick += brick;
		update.glowstone += glowstone;
		update.clay += clay;
		update.coal += coal;
		update.wool += wool;
		update.leather += leather;
		update.arrow += arrow;
		update.armor += armor;
		update.weapon += weapon;
		update.snow += snow;
		update.recruit += recruit;
		update.prisoner += prisoner;
		
		//---Update---//
		String query = "UPDATE settlement SET mana=" + update.mana + ", population=" + update.population + ", wealth=" + update.wealth + ", food=" + update.food + ", wood=" + update.wood + 
				", cobblestone=" + update.cobblestone + ", stone=" + update.stone + ", sand=" + update.sand + ", gravel=" + update.gravel + ", dirt=" + update.dirt + ", iron_ingot=" + update.ironIngot + 
				", iron_ore=" + update.ironOre + ", emerald=" + update.emerald + ", emerald_ore=" + update.emeraldOre + ", gold_ingot=" + update.goldIngot + ", gold_ore=" + update.goldOre + ", flint=" + 
				update.flint + ", feather=" + update.feather + ", lapis_ore=" + update.lapisOre + ", diamond=" + update.diamond + ", obsidian=" + update.obsidian + ", netherrack=" + update.netherrack + 
				", nether_brick=" + update.netherBrick + ", redstone=" + update.redstone + ", brick=" + update.brick + ", glowstone=" + update.glowstone + ", clay=" + update.clay + ", coal=" + update.coal + 
				", wool=" + update.wool + ", leather=" + update.leather + ", arrow=" + update.arrow + ", armor=" + update.armor + ", weapon=" + update.weapon + ", snow=" + update.snow +
				", recruit=" + update.recruit + ", prisoner=" + update.prisoner + " WHERE settlement_id=" + settlement_id;
		if(!db.queryWithResult(query)){
			plugin.getLogger().info("An error occured while trying to update the resource production in " + name + ".");
			return false;
		}
		plugin.getLogger().info("Successfully updated " + name + " productions.");
		
		//---Upkeep---//
		update = getAllLosses(settlement_id);
		settlementData = db.getSettlementData(settlement_id, "*");
		try{
			settlementData.next();
			food = settlementData.getDouble("food");
			wealth = settlementData.getDouble("wealth");
			settlementData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		if(food + update.food < 0)
			update.food = -food;
		if(wealth + update.wealth < 0)
			update.wealth = -wealth;
		
		update.food += food;
		update.wealth += wealth;
		
		query = "UPDATE settlement SET food=" + update.food + ", wealth=" + update.wealth + " WHERE settlement_id=" + settlement_id;
		if(!db.queryWithResult(query)){
			plugin.getLogger().info("An error occured while trying to update the upkeep in " + name + ".");
			return false;
		}
		plugin.getLogger().info("Successfully updated " + name + " upkeep.");
		
		return true;
	}
	
	/** 
	 * <b>setBiome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean setBiome(int settlementId, {@link String} biome)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement.
	 * @param biome - The name of the biome.
	 * @return True if the biome was properly set.  False if it was not.
	 */
	public boolean setBiome(String settlement, String biome){
		return setBiome(db.getSettlementId(settlement), biome);
	}
	
	/** 
	 * <b>setBiome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean setBiome(int settlementId, {@link String} biome)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @return True if the biome was properly set.  False if it was not.
	 */
	public boolean setBiome(int settlementId, String biome){
		biome = biome.toLowerCase();
		if(!plugin.getBiomeData().isBiome(biome))
			return false;
		if(db.update("settlement", "biome", biome, "settlement_id", settlementId))
			return true;
		return false;
	}
	
	/** 
	 * <b>getIncomeTax</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getIncomeTax({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the income tax for.
	 * @return The amount of wealth generated from income tax.
	 */
	public double getIncomeTax(String settlement){
		return getIncomeTax(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getIncomeTax</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getIncomeTax(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the income tax for.
	 * @return The amount of wealth generated from income tax.
	 */
	public double getIncomeTax(int settlement_id){
		double incomeTax = 0;
		double employed = getCurrentlyEmployed(settlement_id);
		if(employed > 0)
			incomeTax = employed * baseIncomeTax;
		double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "trade");
		incomeTax*= (incomeTax > 0) ? 1 + spellBonus : 1 - spellBonus;
		return incomeTax;
	}
	
	/** 
	 * <b>getTradeValue</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getTradeValue(int settlementId)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement to find the trade value for.
	 * @return The amount of wealth generated from trade.
	 */
	public double getTradeValue(int settlementId){
		double total = 0;
		for(int tradeId: db.getAllIds("trade")){
			total += plugin.getTradeManager().getSettlementIncome(tradeId, settlementId);
		}
		return total;
	}
	
	/** 
	 * <b>getAllLosses</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ProductionSheet} getAllLosses({@link String} settlement)
	 * <br>
	 * <br>
	 * Generally, this is to be called after the {@link BuildingManager} getProductions method.
	 * @param settlement - The name of the settlement to find the production sheet for.
	 * @return An object indicating all decomposing/stolen/eaten materials.
	 */
	public ProductionSheet getAllLosses(String settlement){
		return getAllLosses(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getAllLosses</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ProductionSheet} getAllLosses(int settlement_id)
	 * <br>
	 * <br>
	 * Generally, this is to be called after the {@link BuildingManager} getProductions method.
	 * @param settlement_id - The id number of the settlement to find the production sheet for.
	 * @return An object indicating all decomposing/stolen/eaten materials.
	 */
	public ProductionSheet getAllLosses(int settlement_id){
		ProductionSheet losses = new ProductionSheet();
		losses.food -= getFoodConsumption(settlement_id);
		losses.food -= getFoodDecay(settlement_id);
		losses.wealth -= getWealthStolen(settlement_id);
		return losses;
	}
	
	/** 
	 * <b>getFoodConsumption</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getFoodConsumption({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the food consumption for.
	 * @return The amount of food eaten by peasants each tick.
	 */
	public double getFoodConsumption(String settlement){
		return getIncomeTax(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getFoodConsumption</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getFoodConsumption(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the food consumption for.
	 * @return The amount of wealth generated from income tax.
	 */
	public double getFoodConsumption(int settlement_id){
		double foodConsumption = 0, population = 0;
		String biome ="";
		ResultSet settlementData = db.getSettlementData(settlement_id, "*");
		try{
			settlementData.next();
			population = settlementData.getDouble("population");
			biome = settlementData.getString("biome");
			settlementData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		if(population > 0){
			double biomeMultiplier = 1;
			if(biome.equalsIgnoreCase("forest"))
				biomeMultiplier += BiomeData.forestFoodConsumptionPenalty;
			else if (biome.equalsIgnoreCase("jungle"))
				biomeMultiplier -= BiomeData.jungleFoodConsumptionBonus;
			foodConsumption = population * baseFoodConsumption * biomeMultiplier;
		}
		return foodConsumption;
	}
	
	/** 
	 * <b>getFoodDecay</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getFoodDecay({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the food decay for.
	 * @return The amount of food that will decay.
	 */
	public double getFoodDecay(String settlement){
		return getFoodDecay(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getFoodDecay</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getFoodDecay(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the food decay for.
	 * @return The amount of food that will decay.
	 */
	public double getFoodDecay(int settlement_id){
		double foodDecay = 0;
		String settlementQuery = "SELECT * FROM settlement WHERE settlement_id=" + settlement_id;
		ResultSet settlementData = db.querySelect(settlementQuery);
		try{
			settlementData.next();
			double currentFood = settlementData.getDouble("food");
			String biome = settlementData.getString("biome");
			settlementData.getStatement().close();
			if(biome.equalsIgnoreCase("snow"))
				return foodDecay;
			
			String buildingQuery = "SELECT * FROM building WHERE settlement_id=" + settlement_id + " AND class=\'granary\'";
			ResultSet buildingData = db.querySelect(buildingQuery);
			int granaryLevels = 0, employed = 0;
			while(buildingData.next()){
				granaryLevels += buildingData.getInt("level");
				employed += buildingData.getInt("employed");
			}
			buildingData.getStatement().close();
			
			double multiplier = 0;
			int workers = plugin.getBuildingManager().getGranary().workers;
			if(granaryLevels > 0 & workers > 0)
				multiplier = granaryLevels * (employed / (workers * granaryLevels));
			double storage = multiplier * Granary.capacity;
			double availableToDecay = currentFood - getFoodConsumption(settlement_id) - storage;
			if(availableToDecay > 0)
				foodDecay = availableToDecay * baseFoodDecay;
			
			return foodDecay;
		} catch (SQLException ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/** 
	 * <b>getWealthStolen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getWealthStolen({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the stolen wealth for.
	 * @return The amount of wealth stolen each update tick.
	 */
	public double getWealthStolen(String settlement){
		return getWealthStolen(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getWealthStolen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getWealthStolen(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the stolen wealth for.
	 * @return The amount of wealth stolen each update tick.
	 */
	public double getWealthStolen(int settlement_id){
		double stolenWealth = 0, currentWealth = getMaterial(settlement_id, "wealth");
		int bankLevels = 0, employed = 0;
		String buildingQuery = "SELECT * FROM building WHERE settlement_id=" + settlement_id + " AND class=\'bank\'";
		ResultSet buildingData = db.querySelect(buildingQuery);	
		try{
			while(buildingData.next()){
				bankLevels += buildingData.getInt("level");
				employed += buildingData.getInt("employed");
			}
			buildingData.getStatement().close();
			double multiplier = 0;
			int workers = plugin.getBuildingManager().getBank().workers;
			if(bankLevels > 0 && workers > 0)
				multiplier = bankLevels * (employed / (workers * bankLevels));
			double storage = multiplier * Bank.capacity;
			double availableToSteal = currentWealth - storage;
			if(availableToSteal > 0)
				stolenWealth = availableToSteal * baseStealingRate;
			
			return stolenWealth;
		} catch (SQLException ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/** 
	 * <b>addMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean addMaterial(int settlementId, {@link String} material, double quantity)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @param material - The material or type of the item.
	 * @param quantity - The amount of the material to add.
	 * @return True if the material was successfully added.  False if it was not.
	 */
	public boolean addMaterial(int settlementId, String material, double quantity){
		String owner = db.getOwnerName("settlement", settlementId);
		if(!db.addMaterial(settlementId, material, quantity))
			return false;
		if(plugin.isPlayerOnline(owner))
			plugin.getServer().getPlayer(owner).sendMessage(plugin.getLogPrefix() + quantity + " " + material + " has been added to " + db.getSettlementName(settlementId) + ".");
		return true;
	}
	
	/** 
	 * <b>subtractMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean subtractMaterial(int settlementId, {@link String} material, double quantity)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @param material - The material or type of the item.
	 * @param quantity - The amount of the material to subtract.
	 * @return True if the material was successfully subtracted.  False if it was not.
	 */
	public boolean subtractMaterial(int settlementId, String material, double quantity){
		String owner = db.getOwnerName("settlement", settlementId);
		if(!db.subtractMaterial(settlementId, material, quantity))
			return false;
		if(plugin.isPlayerOnline(owner))
			plugin.getServer().getPlayer(owner).sendMessage(plugin.getLogPrefix() + quantity + " " + material + " has been subtracted from " + db.getSettlementName(settlementId) + ".");
		return true;
	}
	
	/** 
	 * <b>killPeasants</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean killPeasants(int settlementId, double quantity)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @param quantity - The number of peasants.
	 * @return True if the peasants were successfully killed.  False if they were not.
	 */
	public boolean killPeasants(int settlementId, double quantity){
		String owner = db.getOwnerName("settlement", settlementId);
		if(!hasMaterial(settlementId, "population", quantity))
			quantity = this.getMaterial(settlementId, "population");
		if(!db.subtractMaterial(settlementId, "population", quantity))
			return false;
		if(plugin.isPlayerOnline(owner)){
			String message = plugin.getLogPrefix() + " ";
			String settlementName = db.getSettlementName(settlementId);
			if(quantity != 1)
				message += quantity + " peasants have been killed in " + settlementName + ".";
			else
				message += "1 peasant has been killed in " + settlementName + ".";
			plugin.getServer().getPlayer(owner).sendMessage(message);
		}
		
		if(getCurrentlyEmployed(settlementId) > getMaterial(settlementId,  "population")){
			// TO-DO: Remove employees from buildings.
		}
		return true;
	}
	
	/** 
	 * <b>getMaxMana</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getMaxMana({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the maximum mana for.
	 * @return The maximum mana of the settlement.
	 */
	public double getMaxMana(String settlement){
		if(!db.settlementExists(settlement))
			return 0;
		return getMaxMana(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getMaxMana</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getMaxMana(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the maximum mana for.
	 * @return The maximum mana of the settlement.
	 */
	public double getMaxMana(int settlement_id){
		double mana = 0;
		String query = "SELECT * FROM building WHERE settlement_id=" + settlement_id + " AND class=\'library\'";
		ResultSet buildingData = db.querySelect(query);
		int level = 0, employed = 0, workers = 0;
		double multiplier = 0;
		String classType="";
		ResultSet getBiome = db.getSettlementData(settlement_id, "biome");
		try{
			getBiome.next();
			String biome = getBiome.getString("biome");
			getBiome.getStatement().close();
			while(buildingData.next()){
				classType = buildingData.getString("class").toLowerCase();
				if(classType.equalsIgnoreCase("library")){
					level = buildingData.getInt("level");
					employed = buildingData.getInt("employed");
					workers = plugin.getBuildingManager().getLibrary().workers;
					if(workers > 0)
						multiplier = level * (employed / workers);
					else
						multiplier = 0;
					if(biome.equalsIgnoreCase("swamp"))
						multiplier *= (1 + BiomeData.swampManaCapacityBonus);
					mana += multiplier * Library.manaCapacity;
				}
			}
			buildingData.getStatement().close();
			return mana;
		} catch (SQLException ex){
			plugin.getLogger().info("SQLException occured while trying to retrieve building data.");
			ex.printStackTrace();
			return 0;
		}
	}
	
	/** 
	 * <b>getSpellPower</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getSpellPower(int settlementId)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @return The calculated spell power of the settlement.
	 */
	public double getSpellPower(int settlementId){
		double spellPower = 0;
		String biome = getBiome(settlementId);
		ResultSet buildings = db.getTableData("building", "*", "settlement_id=" + settlementId + " AND class=\'spire\'");
		int level = 0, employed = 0, workers = 0;
		double multiplier = 0;
		String classType = "";
		try{
			while(buildings.next()){
				classType = buildings.getString("class").toLowerCase();
				if(classType.equalsIgnoreCase("spire")){
					level = buildings.getInt("level");
					employed = buildings.getInt("employed");
					workers = plugin.getBuildingManager().getSpire().workers;
					if(workers > 0)
						multiplier = level * (employed / workers);
					else
						multiplier = 0;
					if(biome.equalsIgnoreCase("mushroom"))
						multiplier *= (1 + BiomeData.mushroomSpellPowerBonus);
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 - BiomeData.plainsSpellPowerPenalty);
					spellPower += multiplier * Spire.getSpellPower(level);
				}
			}
			buildings.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("SQLException occured while trying to calculate spell power.");
			ex.printStackTrace();
		}
		double spellBonus = plugin.getSpellManager().getPenalty(settlementId, "settlement", "spell_power");
		spellPower *= (spellPower > 0) ? 1 - spellBonus : 1 + spellBonus;
		return spellPower;
	}
	
	/** 
	 * <b>getMaxPopulation</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getMaxPopulation({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the maximum population for.
	 * @return The maximum population the settlement can have.
	 */
	public int getMaxPopulation(String settlement){
		if(!db.settlementExists(settlement))
			return 0;
		return getMaxPopulation(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getMaxPopulation</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getMaxPopulation(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the maximum population for.
	 * @return The maximum population the settlement can have.
	 */
	public int getMaxPopulation(int settlement_id){
		int maxPopulation = 0;
		String query = "SELECT * FROM building WHERE settlement_id=" + settlement_id + " AND class=\'home\'";
		ResultSet buildingData = db.querySelect(query);
		int level = 0;
		try{
			while(buildingData.next()){
				level = buildingData.getInt("level");
				maxPopulation += level * Home.capacity;
			}
			buildingData.getStatement().close();
			return maxPopulation;
		} catch (SQLException ex){
			plugin.getLogger().info("SQLException occured while trying to retrieve building data.");
			ex.printStackTrace();
			return 0;
		}
	}
	
	/** 
	 * <b>getX</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getX(int settlementId)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @return The x-coordinate of the settlement.
	 */
	public double getX(int settlementId){
		ResultSet settlement = db.getSettlementData(settlementId, "xcoord");
		double xCoord = 0;
		try{
			if(settlement.next())
				xCoord = settlement.getDouble("xcoord");
			settlement.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return xCoord;
	}
	
	/** 
	 * <b>getZ</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getZ(int settlementId)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @return The z-coordinate of the settlement.
	 */
	public double getZ(int settlementId){
		ResultSet settlement = db.getSettlementData(settlementId, "zcoord");
		double zCoord = 0;
		try{
			if(settlement.next())
				zCoord = settlement.getDouble("zcoord");
			settlement.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return zCoord;
	}
	
	/** 
	 * <b>getDefense</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getDefense(int settlementId)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @return The defense of the city.
	 */
	public double getDefense(int settlementId){
		ResultSet settlement = db.getSettlementData(settlementId, "*");
		String type="";
		double walls=0, baseDefense=0;
		try{
			if(settlement.next()){
				type = settlement.getString("class");
				walls = settlement.getDouble("wall");
			}
			settlement.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		if(type.equalsIgnoreCase("town"))
			baseDefense = DominionSettings.townDefense;
		else if (type.equalsIgnoreCase("city"))
			baseDefense = DominionSettings.cityDefense;
		else if (type.equalsIgnoreCase("fortress"))
			baseDefense = DominionSettings.fortressDefense;
		else
			baseDefense = 0;
		double spellBonus = plugin.getSpellManager().getBonus(settlementId, "settlement", "defense");
		double defense = walls + baseDefense;
		defense *= (defense > 0) ? 1 + spellBonus : 1 - spellBonus;
		return defense;
	}
	
	/** 
	 * <b>getCurrentlyEmployed</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getCurrentlyEmployed({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the total employed population for.
	 * @return The total employed population. -1 if an error occured.
	 */
	public int getCurrentlyEmployed(String settlement){
		return getCurrentlyEmployed(db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>getCurrentlyEmployed</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getCurrentlyEmployed(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement to find the total employed population for.
	 * @return The total employed population. -1 if an error occured.
	 */
	public int getCurrentlyEmployed(int settlement_id){
		String query = "SELECT * FROM building WHERE settlement_id=" + settlement_id;
		ResultSet buildingData = db.querySelect(query);
		int employed = 0;
		try{
			while(buildingData.next()){
				employed += buildingData.getInt("employed");
			}
			buildingData.getStatement().close();
			return employed;
		} catch (SQLException ex){
			ex.printStackTrace();
			return -1;
		}
	}
	
	/** 
	 * <b>getMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public double getMaterial(int settlement_id, {@link String} material)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement.
	 * @param material - The name of the material.
	 * @return The amount of the specified material the settlement currently has.
	 */
	public double getMaterial(int settlement_id, String material){
		material = material.toLowerCase();
		if(material.isEmpty())
			return 0;
		ResultSet materialData = db.getSettlementData(settlement_id, material);
		double value = 0;
		if(materialData == null)
			return value;
		try{
			if(materialData.next())
				value = materialData.getDouble(material);
			materialData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		} catch (NullPointerException ex){}
		return value;
	}
	
	/** 
	 * <b>getBiome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getBiome(int settlementId)
	 * <br>
	 * <br>
	 * @param settlement_id - The id of the settlement.
	 * @return The biome of the settlement.
	 */
	public String getBiome(int settlementId){
		String biome = "";
		ResultSet data = db.getSettlementData(settlementId, "biome");
		try{
			if(data.next())
				biome = data.getString("biome");
			data.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return biome;
	}
	
	/** 
	 * <b>hasMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean hasMaterial({@link String} settlement, {@link String} material, double quantity)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement.
	 * @param material - The type of material.
	 * @param quantity - The amount of the material.
	 * @return True if the settlement has at least that much material; false if it does not.
	 */
	public boolean hasMaterial(String settlement, String material, double quantity){
		return hasMaterial(db.getSettlementId(settlement), material, quantity);
	}
	
	/** 
	 * <b>hasMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean hasMaterial(int settlementId, {@link String} material, double quantity)
	 * <br>
	 * <br>
	 * @param settlementId - The id of the settlement.
	 * @param material - The type of material.
	 * @param quantity - The amount of the material.
	 * @return True if the settlement has at least that much material; false if it does not.
	 */
	public boolean hasMaterial(int settlementId, String material, double quantity){
		double currentAmount = getMaterial(settlementId, material);
		return currentAmount >= quantity;
	}
	
	/** 
	 * <b>isOwner</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isOwner({@link String} player, {@link String} settlement)
	 * <br>
	 * <br>
	 * @param player - The name of the player.
	 * @param settlement - The name of the settlement.
	 * @return True if the player is the owner of the settlement; false if they are not.
	 */
	public boolean isOwner(String player, String settlement){
		return isOwner(db.getPlayerId(player), db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>isOwner</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isOwner(int playerId, {@link String} settlement)
	 * <br>
	 * <br>
	 * @param playerId - The id of the player.
	 * @param settlement - The name of the settlement.
	 * @return True if the player is the owner of the settlement; false if they are not.
	 */
	public boolean isOwner(int playerId, String settlement){
		return isOwner(playerId, db.getSettlementId(settlement));
	}
	
	/** 
	 * <b>isOwner</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isOwner({@link String} player, int settlementId)
	 * <br>
	 * <br>
	 * @param player - The name of the player.
	 * @param settlementId - The id of the settlement.
	 * @return True if the player is the owner of the settlement; false if they are not.
	 */
	public boolean isOwner(String player, int settlementId){
		return isOwner(db.getPlayerId(player), settlementId);
	}
	
	/** 
	 * <b>isOwner</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isOwner(int playerId, int settlementId)
	 * <br>
	 * <br>
	 * @param playerId - The id of the player.
	 * @param settlementId - The id of the settlement.
	 * @return True if the player is the owner of the settlement; false if they are not.
	 */
	public boolean isOwner(int playerId, int settlementId){
		return db.getOwnerId("settlement", settlementId) == playerId;
	}

	//---ACCESSORS---//
	/** 
	 * <b>getBaseFoodDecay</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static double getBaseFoodDecay()
	 * <br>
	 * <br>
	 * @return The base decay rate of food each tick.
	 */
	public static double getBaseFoodDecay(){
		return baseFoodDecay;
	}
	
	/** 
	 * <b>getBaseFoodConsumption</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static double getBaseFoodConsumption()
	 * <br>
	 * <br>
	 * @return The base consumption rate of food per peasant each tick.
	 */
	public static double getBaseFoodConsumption(){
		return baseFoodConsumption;
	}
	
	/** 
	 * <b>getBaseStealingRate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static double getBaseStealingRate()
	 * <br>
	 * <br>
	 * @return The base percentage of non-protected wealth stolen each tick.
	 */
	public static double getBaseStealingRate(){
		return baseStealingRate;
	}
	
	/** 
	 * <b>getBaseIncomeTax</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static double getBaseIncomeTax()
	 * <br>
	 * <br>
	 * @return The base amount of wealth employed peasants produce for the settlement.
	 */
	public static double getBaseIncomeTax(){
		return baseIncomeTax;
	}
	
	//---MUTATORS---//
	/** 
	 * <b>setBaseFoodDecay</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static void setBaseFoodDecay(double foodDecay)
	 * <br>
	 * <br>
	 * @param foodDecay - The rate at which food will decay each tick.
	 */
	public static void setBaseFoodDecay(double foodDecay){
		baseFoodDecay = foodDecay;
	}
	
	/** 
	 * <b>setBaseFoodConsumption</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static void setBaseFoodConsumption(double foodConsumption)
	 * <br>
	 * <br>
	 * @param foodConsumption - The rate at which food will be consumed per peasant each tick.
	 */
	public static void setBaseFoodConsumption(double foodConsumption){
		baseFoodConsumption = foodConsumption;
	}
	
	/** 
	 * <b>setBaseStealingRate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static void setBaseStealingRate(double stealingRate)
	 * <br>
	 * <br>
	 * @param stealingRate - The rate at which non-protected wealth will be stolen each tick.
	 */
	public static void setBaseStealingRate(double stealingRate){
		baseStealingRate = stealingRate;
	}
	
	/** 
	 * <b>setBaseIncomeTax</b><br>
	 * <br>
	 * &nbsp;&nbsp;public static void setBaseIncomeTax(double incomeTax)
	 * <br>
	 * <br>
	 * @param incomeTax - The base amount of wealth employed peasants produce for the settlement.
	 */
	public static void setBaseIncomeTax(double incomeTax){
		baseIncomeTax = incomeTax;
	}
}
