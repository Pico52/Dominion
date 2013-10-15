package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.datasheet.BiomeData;
import com.pico52.dominion.datasheet.ProductionSheet;
import com.pico52.dominion.object.building.ArcheryRange;
import com.pico52.dominion.object.building.Armory;
import com.pico52.dominion.object.building.Bank;
import com.pico52.dominion.object.building.Barracks;
import com.pico52.dominion.object.building.CattleRanch;
import com.pico52.dominion.object.building.ChickenPen;
import com.pico52.dominion.object.building.Dockyard;
import com.pico52.dominion.object.building.Farm;
import com.pico52.dominion.object.building.Fletcher;
import com.pico52.dominion.object.building.Granary;
import com.pico52.dominion.object.building.Home;
import com.pico52.dominion.object.building.Inn;
import com.pico52.dominion.object.building.Library;
import com.pico52.dominion.object.building.Lighthouse;
import com.pico52.dominion.object.building.Market;
import com.pico52.dominion.object.building.Masonry;
import com.pico52.dominion.object.building.Mine;
import com.pico52.dominion.object.building.PigPen;
import com.pico52.dominion.object.building.Quarry;
import com.pico52.dominion.object.building.Sandworks;
import com.pico52.dominion.object.building.SheepRanch;
import com.pico52.dominion.object.building.Shipyard;
import com.pico52.dominion.object.building.Spire;
import com.pico52.dominion.object.building.Tower;
import com.pico52.dominion.object.building.TrainingGrounds;
import com.pico52.dominion.object.building.Warehouse;
import com.pico52.dominion.object.building.Woodshop;

/** 
 * <b>BuildingManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class BuildingManager
 * <br>
 * <br>
 * Controller for buildings.
 */
public class BuildingManager extends DominionObjectManager{
	
	private ArcheryRange archeryRange;
	private Armory armory;
	private Bank bank;
	private Barracks barracks;
	private CattleRanch cattleRanch;
	private ChickenPen chickenPen;
	private Dockyard dockyard;
	private Farm farm;
	private Fletcher fletcher;
	private Granary granary;
	private Home home;
	private Inn inn;
	private Library library;
	private Lighthouse lighthouse;
	private Market market;
	private Masonry masonry;
	private Mine mine;
	private PigPen pigPen;
	private Quarry quarry;
	private Sandworks sandworks;
	private SheepRanch sheepRanch;
	private Shipyard shipyard;
	private Spire spire;
	private Tower tower;
	private TrainingGrounds trainingGrounds;
	private Warehouse warehouse;
	private Woodshop woodshop;
	private String[] buildingsList = {"archery_range", "armory", "bank", "barracks", "cattle_ranch", "chicken_pen", "dockyard", "farm", "fletcher", "granary", "home", "inn", "library", "lighthouse", 
			"market", "masonry", "mine", "pig_pen", "quarry", "sandworks", "sheep_ranch", "shipyard", "spire", "tower", "training_grounds", "warehouse", "woodshop"};
	
	/** 
	 * <b>BuildingManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public BuildingManager({@link Dominion} plugin)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link BuildingManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public BuildingManager(Dominion plugin){
		super(plugin);
		archeryRange = new ArcheryRange();
		armory = new Armory();
		bank = new Bank();
		barracks = new Barracks();
		cattleRanch = new CattleRanch();
		chickenPen = new ChickenPen();
		dockyard = new Dockyard();
		farm = new Farm();
		fletcher = new Fletcher();
		granary = new Granary();
		home = new Home();
		inn = new Inn();
		library = new Library();
		lighthouse = new Lighthouse();
		market = new Market();
		masonry = new Masonry();
		mine = new Mine();
		pigPen = new PigPen();
		quarry = new Quarry();
		sandworks = new Sandworks();
		sheepRanch = new SheepRanch();
		shipyard = new Shipyard();
		spire = new Spire();
		tower = new Tower();
		trainingGrounds = new TrainingGrounds();
		warehouse = new Warehouse();
		woodshop = new Woodshop();
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlementId, {@link String} classification)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlementId - The settlement id this building will be associated with
	 * @param classification - The classification of the building.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, String classification){
		double xCoord = plugin.getSettlementManager().getX(settlementId), zCoord = plugin.getSettlementManager().getZ(settlementId);
		return createBuilding(settlementId, classification, xCoord, zCoord);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlement, {@link String} classification, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlementId - The settlement id this building will be associated with
	 * @param classification - The classification of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, String classification, double xcoord, double zcoord){
		int ownerId = db.getOwnerId("settlement", settlementId);
		return createBuilding(settlementId, ownerId,classification, xcoord, zcoord);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlementId, ownerId, {@link String} classification)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlementId - The id settlement this building will be associated with
	 * @param ownerId - The player id that will own this building.
	 * @param classification - The classification of the building.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, int ownerId, String classification){
		double xCoord = plugin.getSettlementManager().getX(settlementId), 
				zCoord = plugin.getSettlementManager().getZ(settlementId);
		return createBuilding(settlementId, ownerId,classification, xCoord, zCoord);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlementId, int owner, {@link String} classification, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.
	 * @param settlementId - The settlement id this building will be associated with.
	 * @param ownerId - The id of the player that will own this building.
	 * @param classification - The classification of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, int ownerId, String classification, double xcoord, double zcoord){
		return createBuilding(settlementId, ownerId, classification, 1, xcoord, zcoord);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlementId, int owner, {@link String} classification, int level, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.
	 * @param settlementId - The settlement id this building will be associated with.
	 * @param ownerId - The id of the player that will own this building.
	 * @param classification - The classification of the building.
	 * @param level - The starting level of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, int ownerId, String classification, int level, double xcoord, double zcoord){
		return db.createBuilding(settlementId, ownerId, classification, level, xcoord, zcoord);
	}
	
	/** 
	 * <b>getProductions</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ProductionSheet} getProductions({@link String} settlement)
	 * <br>
	 * <br>
	 * @param settlement - The name of the settlement to find the production sheet for.
	 * @return An object indicating all raw values to be added and removed.
	 */
	public ProductionSheet getProductions(String settlement){
		return getProductions(plugin.getDBHandler().getSettlementId(settlement));
	}
	
	/** 
	 * <b>getProductions</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ProductionSheet} getProductions(int settlement_id)
	 * <br>
	 * <br>
	 * @param settlement_id - The id number of the settlement to find the production sheet for.
	 * @return A object indicating all raw values to be added and removed.
	 */
	public ProductionSheet getProductions(int settlement_id){
		ProductionSheet production = new ProductionSheet();
		String query = "SELECT * FROM building WHERE settlement_id=" + settlement_id;
		ResultSet buildingData = plugin.getDBHandler().querySelect(query);
		int buildingId = 0, level = 0, employed = 0, workersPerLevel = 0, maxWorkers = 0;
		double employmentRatio = 0, multiplier = 0;
		String classType="", resource="";
		production.settlement = plugin.getDBHandler().getSettlementName(settlement_id);
		ResultSet getBiome = plugin.getDBHandler().getSettlementData(production.settlement, "biome");
		try{
			getBiome.next();
			String biome = getBiome.getString("biome");
			getBiome.getStatement().close();
			while(buildingData.next()){
				buildingId = buildingData.getInt("building_id");
				level = buildingData.getInt("level");
				employed = buildingData.getInt("employed");
				classType = buildingData.getString("class").toLowerCase();
				resource = buildingData.getString("resource");
				workersPerLevel = getWorkers(classType);
				if(plugin.getSpellManager().getActiveSpells(buildingId, "building", "destroy_building").length > 0)
					continue;
				if(level == 0)
					continue;
				maxWorkers = workersPerLevel * level;
				if(maxWorkers > 0)
					employmentRatio = employed / maxWorkers;
				else
					employmentRatio = 1;
				multiplier = level * employmentRatio;
				if(classType.equalsIgnoreCase("archeryrange") | classType.equalsIgnoreCase("archery_range")){
					// - Archery Ranges produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("armory")){
					double ironIngot = 0, leather = 0, wood = 0;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						if(settlement.next()){
							ironIngot = settlement.getDouble("iron_ingot");
							leather = settlement.getDouble("leather");
							wood = settlement.getDouble("wood");
						}
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						continue;
					}
					if(resource.equalsIgnoreCase("armor")){
						double consumeIronIngot = multiplier * Armory.armorIronConsumption;
						double consumeLeather = multiplier * Armory.armorLeatherConsumption;
						double consumeWood = multiplier * Armory.armorWoodConsumption;
						if(ironIngot - consumeIronIngot < 0 | leather - consumeLeather < 0 | wood - consumeWood < 0)
							break;
						production.armor += multiplier * Armory.armorProduction;
						production.ironIngot -= multiplier * Armory.armorIronConsumption;
						production.leather -= multiplier * Armory.armorLeatherConsumption;
						production.wood -= multiplier * Armory.armorWoodConsumption;
					}else if (resource.equalsIgnoreCase("weapon")){
						double consumeIronIngot = multiplier * Armory.weaponIronConsumption;
						double consumeLeather = multiplier * Armory.weaponLeatherConsumption;
						double consumeWood = multiplier * Armory.weaponWoodConsumption;
						if(ironIngot - consumeIronIngot < 0 | leather - consumeLeather < 0 | wood - consumeWood < 0)
							break;
						production.weapon += multiplier * Armory.weaponProduction;
						production.ironIngot -= multiplier * Armory.weaponIronConsumption;
						production.leather -= multiplier * Armory.weaponLeatherConsumption;
						production.wood -= multiplier * Armory.weaponWoodConsumption;
					}
					continue;
				}
				else if(classType.equalsIgnoreCase("bank")){
					// - Banks produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("barracks")){
					// - Barracks produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("cattleranch") | classType.equalsIgnoreCase("cattle_ranch")){
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "herding_grounds");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * cattleRanch.getProduction(resource);
					production.addResource(resource,  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("chickenpen") | classType.equalsIgnoreCase("chicken_pen")){
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "herding_grounds");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * chickenPen.getProduction(resource);
					production.addResource(resource,  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("dockyard")){
					// - Dockyards produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("farm")){
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsFarmBonus);
					else if(biome.equalsIgnoreCase("mountain") & resource.equalsIgnoreCase("wheat"))
						multiplier *= (1 - BiomeData.mountainWheatPenalty);
					else if(biome.equalsIgnoreCase("snow")){
						if(resource.equalsIgnoreCase("wheat"))
							multiplier *= (1 - BiomeData.snowWheatPenalty);
						else if(resource.equalsIgnoreCase("melon"))
							multiplier *= (1 - BiomeData.snowMelonPenalty);
						else if(resource.equalsIgnoreCase("mushroom"))
							multiplier *= (1 - BiomeData.snowMushroomPenalty);
					}else if(biome.equalsIgnoreCase("jungle") & resource.equalsIgnoreCase("melon"))
						multiplier *= (1 + BiomeData.jungleMelonBonus);
					else if (biome.equalsIgnoreCase("swamp") & resource.equalsIgnoreCase("wheat"))
						multiplier *= (1 - BiomeData.swampWheatPenalty);
					else if(biome.equalsIgnoreCase("mushroom")){
						if(resource.equalsIgnoreCase("mushroom"))
							multiplier *= (1 + BiomeData.mushroomMushroomBonus);
						else if (resource.equalsIgnoreCase("wheat"))
							multiplier *= (1 - BiomeData.mushroomWheatPenalty);
						else if (resource.equalsIgnoreCase("pumpkin"))
							multiplier *= (1 - BiomeData.mushroomPumpkinPenalty);
						else if (resource.equalsIgnoreCase("melon"))
							multiplier *= (1 - BiomeData.mushroomMelonPenalty);
					}else if (biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertFarmPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "farm");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * farm.getProduction(resource);
					production.addResource("food",  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("fletcher")){
					double flint = 0, wood = 0, feather = 0;
					double consumeFlint = multiplier * Fletcher.flintConsumption;
					double consumeWood = multiplier * Fletcher.woodConsumption;
					double consumeFeather = multiplier * Fletcher.featherConsumption;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						if(settlement.next()){
							flint = settlement.getDouble("flint");
							wood = settlement.getDouble("wood");
							feather = settlement.getDouble("feather");
						}
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						continue;
					}
					if(flint - consumeFlint < 0 | wood - consumeWood < 0 | feather - consumeFeather < 0)
						continue;
					production.arrow += multiplier * Fletcher.arrowProduction;
					production.flint -= consumeFlint;
					production.wood -= consumeWood;
					production.feather -= consumeFeather;
					continue;
				}
				else if(classType.equalsIgnoreCase("granary")){
					// - Granaries produce nothing.
					continue;
				}
				else if(classType.equalsIgnoreCase("home")){
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "birth_rate");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					production.population += multiplier * Home.populationProduction;
					continue;
				}
				else if(classType.equalsIgnoreCase("inn")){
					if(biome.equalsIgnoreCase("forest"))
						multiplier *= (1 + BiomeData.forestInnBonus);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "trade");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					production.wealth += multiplier * Inn.wealthProduction;
					continue;
				}
				else if(classType.equalsIgnoreCase("library")){
					if(biome.equalsIgnoreCase("swamp"))
						multiplier *= (1 + BiomeData.swampManaRegenerationBonus);
					production.mana += multiplier * Library.manaProduction;
					continue;
				}
				else if(classType.equalsIgnoreCase("lighthouse")){
					// - Lighthouses produce nothing.
					continue;
				}
				else if(classType.equalsIgnoreCase("market")){
					/* - This will only produce wealth when trading is working.
					double multiplier = level * (employed / (market.workers * level));
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "trade");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					*/
					continue;
				}
				else if(classType.equalsIgnoreCase("masonry")){
					if(biome.equalsIgnoreCase("mountain"))
						multiplier *= (1 + BiomeData.mountainMasonryBonus);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "masonry");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					
					String consumeResource = masonry.getConsumptionResource(resource);
					if(consumeResource == null)
						continue;
					String consumeBase = Masonry.fuelResource;
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					try{
						consumeResourceValue = plugin.getSettlementManager().getMaterial(settlement_id, consumeResource);
						consumeBaseValue = plugin.getSettlementManager().getMaterial(settlement_id, Masonry.fuelResource);
					} catch (NullPointerException ex){
						ex.printStackTrace();
						continue;
					}
					subtractConsumption = multiplier * masonry.getConsumption(resource);
					if(consumeResourceValue - subtractConsumption < 0)
						continue;
					if(Masonry.consumeFuel && Masonry.fuelOutput > 0)
						subtractBase = subtractConsumption / Masonry.fuelOutput;
					if(consumeBaseValue - subtractBase < 0)
						continue;
					double addValue =multiplier * masonry.getProduction(resource);
					production.addResource(consumeResource, -subtractConsumption);
					production.addResource(consumeBase, -subtractBase);
					production.addResource(resource, addValue);

					continue;
				}
				else if(classType.equalsIgnoreCase("mine")){
					if(biome.equalsIgnoreCase("mountain"))
						multiplier *= (1 + BiomeData.mountainMiningBonus);
					if(biome.equalsIgnoreCase("ocean"))
						multiplier *= (1 - BiomeData.oceanMiningPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "mining");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * mine.getProduction(resource);
					production.addResource(resource, value);
					if(Mine.passiveDirt)
						production.addResource("dirt", (Mine.dirtProduction * multiplier));
					if(Mine.passiveGravel)
						production.addResource("gravel", (Mine.gravelProduction * multiplier));
					continue;
				}
				else if(classType.equalsIgnoreCase("pigpen") | classType.equalsIgnoreCase("pig_pen")){
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "herding_grounds");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * pigPen.getProduction(resource);
					production.addResource(resource,  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("quarry")){
					double value = multiplier * quarry.getProduction(resource);
					production.addResource(resource,  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("sandworks")){
					String consumeResource = sandworks.getConsumptionResource(resource);
					if(consumeResource == null)
						continue;
					if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 + BiomeData.desertSandworksBonus);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "sandworks");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					String consumeBase = Sandworks.fuelResource;
					consumeResourceValue = plugin.getSettlementManager().getMaterial(settlement_id, consumeResource);
					consumeBaseValue = plugin.getSettlementManager().getMaterial(settlement_id, Sandworks.fuelResource);
					subtractConsumption = multiplier * sandworks.getConsumption(resource);
					if(consumeResourceValue - subtractConsumption < 0)
						continue;
					if(Sandworks.consumeFuel && Sandworks.fuelOutput > 0)
						subtractBase = subtractConsumption / Sandworks.fuelOutput;
					if(consumeBaseValue - subtractBase < 0)
						continue;
					double addValue = multiplier * sandworks.getProduction(resource);
					production.addResource(consumeResource, -subtractConsumption);
					production.addResource(consumeBase, -subtractBase);
					production.addResource(resource, addValue);
					continue;
				}
				else if(classType.equalsIgnoreCase("sheepranch") | classType.equalsIgnoreCase("sheep_ranch")){
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "herding_grounds");
					multiplier *= (multiplier > 0) ? 1 + spellBonus : 1 - spellBonus;
					double value = multiplier * sheepRanch.getProduction(resource);
					production.addResource(resource,  value);
					continue;
				}
				else if(classType.equalsIgnoreCase("shipyard")){
					// - Shipyards produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("spire")){
					// - Spires produce nothing currently.
					continue;
				}
				else if(classType.equalsIgnoreCase("traininggrounds") | classType.equalsIgnoreCase("training_grounds")){
					if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 + BiomeData.desertTrainingGroundsBonus);
					double value = multiplier * trainingGrounds.getProduction(resource);
					double consumeWealth = TrainingGrounds.wealthConsumption * multiplier;
					double consumeFood = TrainingGrounds.foodConsumption * multiplier;
					double consumePopulation = TrainingGrounds.populationConsumption * multiplier;
					double wealth = 0, food = 0, population = 0;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						if(settlement.next()){
							wealth = settlement.getDouble("wealth");
							food = settlement.getDouble("food");
							population = settlement.getDouble("population");
						}
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						continue;
					}
					if(wealth - consumeWealth < 0 | food - consumeFood < 0 | population - consumePopulation < 0)
						continue;
					production.addResource(resource,  value);
					production.addResource("wealth", -consumeWealth);
					production.addResource("food", -consumeFood);
					production.addResource("population", -consumePopulation);
					continue;
				}
				else if(classType.equalsIgnoreCase("warehouse")){
					// - Warehouses produce nothing.
					continue;
				}
				else if(classType.equalsIgnoreCase("woodshop")){
					String consumeResource = woodshop.getConsumptionResource(resource);
					if(consumeResource == null)
						continue;
					if(biome.equalsIgnoreCase("forest"))
						multiplier *= (1 + BiomeData.forestWoodshopBonus);
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					String consumeBase = Woodshop.fuelResource;
					consumeResourceValue = plugin.getSettlementManager().getMaterial(settlement_id, consumeResource);
					consumeBaseValue = plugin.getSettlementManager().getMaterial(settlement_id, consumeBase);
					subtractConsumption = multiplier * woodshop.getConsumption(resource);
					if(Woodshop.consumeFuel && Woodshop.fuelOutput > 0)
						subtractBase = subtractConsumption / Woodshop.fuelOutput;
					if(consumeResourceValue - subtractConsumption < 0 | consumeBaseValue - subtractBase < 0)
						continue;
					double addValue = multiplier * woodshop.getProduction(resource);
					production.addResource(consumeResource,  -subtractConsumption);
					production.addResource(consumeBase,  -subtractBase);
					production.addResource(resource,  addValue);
					continue;
				}
			}
			buildingData.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("SQLException occured while trying to retrieve building data.");
			ex.printStackTrace();
			return null;
		}
		double spellBonus = plugin.getSpellManager().getBonus(settlement_id, "settlement", "production");
		double spellPenalty = plugin.getSpellManager().getPenalty(settlement_id, "settlement", "production");
		double spellChange = spellBonus - spellPenalty;
		production.multiplier(spellChange);
		return production;
	}
	
	/** 
	 * <b>getWorkers</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getWorkers({@link String} classification)
	 * <br>
	 * <br>
	 * @param classification - The class of the building.
	 * @return The number of workers required per level for this building.
	 */
	public int getWorkers(String classification){
		classification = classification.toLowerCase();
		
		if(classification.equalsIgnoreCase("archeryrange")) return archeryRange.workers;
		else if(classification.equalsIgnoreCase("armory")) return armory.workers;
		else if(classification.equalsIgnoreCase("bank")) return bank.workers;
		else if(classification.equalsIgnoreCase("barracks")) return barracks.workers;
		else if(classification.equalsIgnoreCase("cattleranch")) return cattleRanch.workers;
		else if(classification.equalsIgnoreCase("chickenpen")) return chickenPen.workers;
		else if(classification.equalsIgnoreCase("dockyard")) return dockyard.workers;
		else if(classification.equalsIgnoreCase("farm")) return farm.workers;
		else if(classification.equalsIgnoreCase("fletcher")) return fletcher.workers;
		else if(classification.equalsIgnoreCase("granary")) return granary.workers;
		else if(classification.equalsIgnoreCase("home")) return home.workers;
		else if(classification.equalsIgnoreCase("inn")) return inn.workers;
		else if(classification.equalsIgnoreCase("library")) return library.workers;
		else if(classification.equalsIgnoreCase("lighthouse")) return lighthouse.workers;
		else if(classification.equalsIgnoreCase("market")) return market.workers;
		else if(classification.equalsIgnoreCase("masonry")) return masonry.workers;
		else if(classification.equalsIgnoreCase("mine")) return mine.workers;
		else if(classification.equalsIgnoreCase("pigpen")) return pigPen.workers;
		else if(classification.equalsIgnoreCase("quarry")) return quarry.workers;
		else if(classification.equalsIgnoreCase("sandworks")) return sandworks.workers;
		else if(classification.equalsIgnoreCase("sheepranch")) return sheepRanch.workers;
		else if(classification.equalsIgnoreCase("shipyard")) return shipyard.workers;
		else if(classification.equalsIgnoreCase("spire")) return spire.workers;
		else if(classification.equalsIgnoreCase("tower")) return tower.workers;
		else if(classification.equalsIgnoreCase("traininggrounds")) return trainingGrounds.workers;
		else if(classification.equalsIgnoreCase("warehouse")) return warehouse.workers;
		else if(classification.equalsIgnoreCase("woodshop")) return woodshop.workers;
		return 0;
	}
	
	/** 
	 * <b>isBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isBuilding({@link String} classification)
	 * <br>
	 * <br>
	 * @param classification - The class of the building to test.
	 * @return True if the classification is a building; false if it is not.
	 */
	public boolean isBuilding(String classification){
		for(String building: buildingsList){
			if(classification.equalsIgnoreCase(building))
				return true;
		}
		return false;
	}
	
//---ACCESSORS---//
	/** 
	 * <b>getSettlementId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSettlementId(int buildingId)
	 * <br>
	 * <br>
	 * @param buildingId - The id of the building.
	 * @return The id of the settlement the building resides in.
	 */
	public int getSettlementId(int buildingId){
		return db.getSingleColumnInt("building", "settlement_id", buildingId, "building_id");
	}
	
	/** 
	 * <b>getBuildingsList</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String}[] getBuildingsList()
	 * <br>
	 * <br>
	 * @return The list of acceptable buildings.
	 */
	public String[] getBuildingsList(){
		return buildingsList;
	}
	
	/** 
	 * <b>getArcheryRange</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ArcheryRange} getArcheryRange()
	 * <br>
	 * <br>
	 * @return The ArcheryRange building.
	 */
	public ArcheryRange getArcheryRange(){
		return archeryRange;
	}
	
	/** 
	 * <b>getArmory</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Armory} getArmory()
	 * <br>
	 * <br>
	 * @return The Armory building.
	 */
	public Armory getArmory(){
		return armory;
	}
	
	/** 
	 * <b>getBank</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Bank} getBank()
	 * <br>
	 * <br>
	 * @return The Bank building.
	 */
	public Bank getBank(){
		return bank;
	}
	
	/** 
	 * <b>getBarracks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Barracks} getBarracks()
	 * <br>
	 * <br>
	 * @return The Barracks building.
	 */
	public Barracks getBarracks(){
		return barracks;
	}
	
	/** 
	 * <b>getCattleRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link CattleRanch} getCattleRanch()
	 * <br>
	 * <br>
	 * @return The CattleRanch building.
	 */
	public CattleRanch getCattleRanch(){
		return cattleRanch;
	}
	
	/** 
	 * <b>getChickenPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ChickenPen} getChickenPen()
	 * <br>
	 * <br>
	 * @return The ChickenPen building.
	 */
	public ChickenPen getChickenPen(){
		return chickenPen;
	}
	
	/** 
	 * <b>getDockyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Dockyard} getDockyard()
	 * <br>
	 * <br>
	 * @return The Dockyard building.
	 */
	public Dockyard getDockyard(){
		return dockyard;
	}
	
	/** 
	 * <b>getFarm</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Farm} getFarm()
	 * <br>
	 * <br>
	 * @return The Farm building.
	 */
	public Farm getFarm(){
		return farm;
	}
	
	/** 
	 * <b>getFletcher</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Fletcher} getFletcher()
	 * <br>
	 * <br>
	 * @return The Fletcher building.
	 */
	public Fletcher getFletcher(){
		return fletcher;
	}
	
	/** 
	 * <b>getGranary</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Granary} getGranary()
	 * <br>
	 * <br>
	 * @return The Granary building.
	 */
	public Granary getGranary(){
		return granary;
	}
	
	/** 
	 * <b>getHome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Home} getHome()
	 * <br>
	 * <br>
	 * @return The Home building.
	 */
	public Home getHome(){
		return home;
	}
	
	/** 
	 * <b>getInn</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Inn} getInn()
	 * <br>
	 * <br>
	 * @return The Inn building.
	 */
	public Inn getInn(){
		return inn;
	}
	
	/** 
	 * <b>getLibrary</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Library} getLibrary()
	 * <br>
	 * <br>
	 * @return The Library building.
	 */
	public Library getLibrary(){
		return library;
	}
	
	/** 
	 * <b>getLighthouse</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Lighthouse} getLighthouse()
	 * <br>
	 * <br>
	 * @return The Lighthouse building.
	 */
	public Lighthouse getLighthouse(){
		return lighthouse;
	}
	
	/** 
	 * <b>getMarket</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Market} getMarket()
	 * <br>
	 * <br>
	 * @return The Market building.
	 */
	public Market getMarket(){
		return market;
	}
	
	/** 
	 * <b>getMasonry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Masonry} getMasonry()
	 * <br>
	 * <br>
	 * @return The Masonry building.
	 */
	public Masonry getMasonry(){
		return masonry;
	}
	
	/** 
	 * <b>getMine</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Mine} getMine()
	 * <br>
	 * <br>
	 * @return The Mine building.
	 */
	public Mine getMine(){
		return mine;
	}
	
	/** 
	 * <b>getPigPen</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link PigPen} getPigPen()
	 * <br>
	 * <br>
	 * @return The PigPen building.
	 */
	public PigPen getPigPen(){
		return pigPen;
	}
	
	/** 
	 * <b>getQuarry</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Quarry} getQuarry()
	 * <br>
	 * <br>
	 * @return The Quarry building.
	 */
	public Quarry getQuarry(){
		return quarry;
	}
	
	/** 
	 * <b>getSandworks</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Sandworks} getSandworks()
	 * <br>
	 * <br>
	 * @return The Sandworks building.
	 */
	public Sandworks getSandworks(){
		return sandworks;
	}
	
	/** 
	 * <b>getSheepRanch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link SheepRanch} getSheepRanch()
	 * <br>
	 * <br>
	 * @return The SheepRanch building.
	 */
	public SheepRanch getSheepRanch(){
		return sheepRanch;
	}
	
	/** 
	 * <b>getShipyard</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Shipyard} getShipyard()
	 * <br>
	 * <br>
	 * @return The Shipyard building.
	 */
	public Shipyard getShipyard(){
		return shipyard;
	}
	
	/** 
	 * <b>getSpire</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Spire} getSpire()
	 * <br>
	 * <br>
	 * @return The Spire building.
	 */
	public Spire getSpire(){
		return spire;
	}
	
	/** 
	 * <b>getTrainingGrounds</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link TrainingGrounds} getTrainingGrounds()
	 * <br>
	 * <br>
	 * @return The TrainingGrounds building.
	 */
	public TrainingGrounds getTrainingGrounds(){
		return trainingGrounds;
	}
	
	/** 
	 * <b>getWarehouse/b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Warehouse} getWarehouse()
	 * <br>
	 * <br>
	 * @return The Warehouse building.
	 */
	public Warehouse getWarehouse(){
		return warehouse;
	}
	
	/** 
	 * <b>getWoodshop</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link Woodshop} getWoodshop()
	 * <br>
	 * <br>
	 * @return The Woodshop building.
	 */
	public Woodshop getWoodshop(){
		return woodshop;
	}
}