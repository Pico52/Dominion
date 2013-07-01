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
	
	private static ArcheryRange archeryRange;
	private static Armory armory;
	private static Bank bank;
	private static Barracks barracks;
	private static CattleRanch cattleRanch;
	private static ChickenPen chickenPen;
	private static Dockyard dockyard;
	private static Farm farm;
	private static Fletcher fletcher;
	private static Granary granary;
	private static Home home;
	private static Inn inn;
	private static Library library;
	private static Lighthouse lighthouse;
	private static Market market;
	private static Masonry masonry;
	private static Mine mine;
	private static PigPen pigPen;
	private static Quarry quarry;
	private static Sandworks sandworks;
	private static SheepRanch sheepRanch;
	private static Shipyard shipyard;
	private static Spire spire;
	private static Tower tower;
	private static TrainingGrounds trainingGrounds;
	private static Warehouse warehouse;
	private static Woodshop woodshop;
	
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
		int level = 0, employed = 0;
		String classType="", resource="";
		production.settlement = plugin.getDBHandler().getSettlementName(settlement_id);
		ResultSet getBiome = plugin.getDBHandler().getSettlementData(production.settlement, "biome");
		try{
			getBiome.next();
			String biome = getBiome.getString("biome");
			getBiome.getStatement().close();
			while(buildingData.next()){
				level = buildingData.getInt("level");
				employed = buildingData.getInt("employed");
				classType = buildingData.getString("class").toLowerCase();
				resource = buildingData.getString("resource");
				
				if(classType.equalsIgnoreCase("archeryrange")){
					// - Archery Ranges produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("armory")){
					double multiplier = level * (employed / (armory.workers * level));
					double ironIngot = 0, leather = 0, wood = 0;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						settlement.next();
						ironIngot = settlement.getDouble("iron_ingot");
						leather = settlement.getDouble("leather");
						wood = settlement.getDouble("wood");
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
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
					break;
				}
				else if(classType.equalsIgnoreCase("bank")){
					// - Banks produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("barracks")){
					// - Barracks produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("cattleranch")){
					double multiplier  = level * (employed / (cattleRanch.workers * level));
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double value = multiplier * cattleRanch.getProduction(resource);
					production.addResource(resource,  value);
					break;
				}
				else if(classType.equalsIgnoreCase("chickenpen")){
					double multiplier  = level * (employed / (chickenPen.workers * level));
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double value = multiplier * chickenPen.getProduction(resource);
					production.addResource(resource,  value);
					break;
				}
				else if(classType.equalsIgnoreCase("dockyard")){
					// - Dockyards produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("farm")){
					double multiplier  = level * (employed / (farm.workers * level));
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
					double value = multiplier * farm.getProduction(resource);
					production.addResource("food",  value);
					break;
				}
				else if(classType.equalsIgnoreCase("fletcher")){
					double multiplier = level * (employed / (fletcher.workers * level));
					double flint = 0, wood = 0, feather = 0;
					double consumeFlint = multiplier * Fletcher.flintConsumption;
					double consumeWood = multiplier * Fletcher.woodConsumption;
					double consumeFeather = multiplier * Fletcher.featherConsumption;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						settlement.next();
						flint = settlement.getDouble("flint");
						wood = settlement.getDouble("wood");
						feather = settlement.getDouble("feather");
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
					}
					if(flint - consumeFlint < 0 | wood - consumeWood < 0 | feather - consumeFeather < 0)
						break;
					production.arrow += multiplier * Fletcher.arrowProduction;
					production.flint -= consumeFlint;
					production.wood -= consumeWood;
					production.feather -= consumeFeather;
					break;
				}
				else if(classType.equalsIgnoreCase("granary")){
					// - Granaries produce nothing.
					break;
				}
				else if(classType.equalsIgnoreCase("home")){
					production.population += level * Home.populationProduction;
					break;
				}
				else if(classType.equalsIgnoreCase("inn")){
					double multiplier = level * (employed / (inn.workers * level));
					if(biome.equalsIgnoreCase("forest"))
						multiplier *= (1 + BiomeData.forestInnBonus);
					production.wealth += multiplier * Inn.wealthProduction;
					break;
				}
				else if(classType.equalsIgnoreCase("library")){
					double multiplier = level * (employed / (library.workers * level));
					if(biome.equalsIgnoreCase("swamp"))
						multiplier *= (1 + BiomeData.swampManaRegenerationBonus);
					production.mana += multiplier * Library.manaProduction;
					break;
				}
				else if(classType.equalsIgnoreCase("lighthouse")){
					// - Lighthouses produce nothing.
					break;
				}
				else if(classType.equalsIgnoreCase("market")){
					// - This will only produce wealth when trading is working.
					break;
				}
				else if(classType.equalsIgnoreCase("masonry")){
					double multiplier = level * (employed / (masonry.workers * level));
					if(biome.equalsIgnoreCase("mountain"))
						multiplier *= (1 + BiomeData.mountainMasonryBonus);
					
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					String consumeResource = masonry.getConsumptionResource(resource);
					String consumeBase = Masonry.baseConsumptionResource;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id,  "*");
					try{
						settlement.next();
						consumeResourceValue = settlement.getDouble(consumeResource);
						consumeBaseValue = settlement.getDouble(Masonry.baseConsumptionResource);
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
					}
					subtractConsumption = multiplier * masonry.getConsumption(resource);
					if(consumeResourceValue - subtractConsumption < 0)
						break; //subtractConsumption = consumeResourceValue;
					if(Masonry.consumeBase)
						subtractBase = multiplier * Masonry.baseConsumption;
					if(consumeBaseValue - subtractBase < 0)
						break; //subtractBase = consumeBaseValue;
					/*double ratio = subtractConsumption / multiplier * masonry.getConsumption(resource);
					if(Masonry.consumeBase){
						double baseRatio = subtractBase / multiplier * Masonry.baseConsumption;
						if(baseRatio < ratio)
							ratio = baseRatio;
					}*/
					double addValue =multiplier * masonry.getProduction(resource); // * ratio;
					production.addResource(consumeResource, -subtractConsumption);
					production.addResource(consumeBase, -subtractBase);
					production.addResource(resource, addValue);

					break;
				}
				else if(classType.equalsIgnoreCase("mine")){
					double multiplier = level * (employed / (mine.workers * level));
					if(biome.equalsIgnoreCase("mountain"))
						multiplier *= (1 + BiomeData.mountainMiningBonus);
					if(biome.equalsIgnoreCase("ocean"))
						multiplier *= (1 - BiomeData.oceanMiningPenalty);
					double value = multiplier * mine.getProduction(resource);
					production.addResource(resource, value);
					if(Mine.passiveDirt)
						production.addResource("dirt", (Mine.dirtProduction * multiplier));
					if(Mine.passiveGravel)
						production.addResource("gravel", (Mine.gravelProduction * multiplier));
					break;
				}
				else if(classType.equalsIgnoreCase("pigpen")){
					double multiplier  = level * (employed / (pigPen.workers * level));
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double value = multiplier * pigPen.getProduction(resource);
					production.addResource(resource,  value);
					break;
				}
				else if(classType.equalsIgnoreCase("quarry")){
					double multiplier  = level * (employed / (quarry.workers * level));
					double value = multiplier * quarry.getProduction(resource);
					production.addResource(resource,  value);
					break;
				}
				else if(classType.equalsIgnoreCase("sandworks")){
					double multiplier = level * (employed / (sandworks.workers * level));
					if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 + BiomeData.desertSandworksBonus);
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					String consumeResource = sandworks.getConsumptionResource(resource);
					String consumeBase = Sandworks.baseConsumptionResource;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id,  "*");
					try{
						settlement.next();
						consumeResourceValue = settlement.getDouble(consumeResource);
						consumeBaseValue = settlement.getDouble(Masonry.baseConsumptionResource);
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
					}
					subtractConsumption = multiplier * sandworks.getConsumption(resource);
					if(consumeResourceValue - subtractConsumption < 0)
						break;
					if(Sandworks.consumeBase)
						subtractBase = multiplier * Sandworks.baseConsumption;
					if(consumeBaseValue - subtractBase < 0)
						break;
					double addValue = multiplier * sandworks.getProduction(resource);
					production.addResource(consumeResource, -subtractConsumption);
					production.addResource(consumeBase, -subtractBase);
					production.addResource(resource, addValue);
					break;
				}
				else if(classType.equalsIgnoreCase("sheepranch")){
					double multiplier  = level * (employed / (sheepRanch.workers * level));
					if(biome.equalsIgnoreCase("plains"))
						multiplier *= (1 + BiomeData.plainsHerdingGroundsBonus);
					else if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 - BiomeData.desertHerdingGroundsPenalty);
					double value = multiplier * sheepRanch.getProduction(resource);
					production.addResource(resource,  value);
					break;
				}
				else if(classType.equalsIgnoreCase("shipyard")){
					// - Shipyards produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("spire")){
					// - Spires produce nothing currently.
					break;
				}
				else if(classType.equalsIgnoreCase("traininggrounds")){
					double multiplier  = level * (employed / (trainingGrounds.workers * level));
					if(biome.equalsIgnoreCase("desert"))
						multiplier *= (1 + BiomeData.desertTrainingGroundsBonus);
					double value = multiplier * trainingGrounds.getProduction(resource);
					double consumeWealth = TrainingGrounds.wealthConsumption * multiplier;
					double consumeFood = TrainingGrounds.foodConsumption * multiplier;
					double consumePopulation = TrainingGrounds.populationConsumption * multiplier;
					double wealth = 0, food = 0, population = 0;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						settlement.next();
						wealth = settlement.getDouble("wealth");
						food = settlement.getDouble("food");
						population = settlement.getDouble("population");
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
					}
					if(wealth - consumeWealth < 0 | food - consumeFood < 0 | population - consumePopulation < 0)
						break;
					production.addResource(resource,  value);
					production.addResource("wealth", -consumeWealth);
					production.addResource("food", -consumeFood);
					production.addResource("population", -consumePopulation);
					break;
				}
				else if(classType.equalsIgnoreCase("warehouse")){
					// - Warehouses produce nothing.
					break;
				}
				else if(classType.equalsIgnoreCase("woodshop")){
					double multiplier  = level * (employed / (woodshop.workers * level));
					if(biome.equalsIgnoreCase("forest"))
						multiplier *= (1 + BiomeData.forestWoodshopBonus);
					double subtractConsumption = 0, subtractBase = 0, consumeResourceValue = 0, consumeBaseValue = 0;
					String consumeResource = woodshop.getConsumptionResource(resource);
					String consumeBase = Woodshop.baseConsumptionResource;
					ResultSet settlement = plugin.getDBHandler().getSettlementData(settlement_id, "*");
					try{
						settlement.next();
						consumeResourceValue = settlement.getDouble(consumeResource);
						consumeBaseValue = settlement.getDouble(consumeBase);
						settlement.getStatement().close();
					} catch (SQLException ex){
						ex.printStackTrace();
						break;
					}
					subtractConsumption = multiplier * woodshop.getConsumption(resource);
					if(Woodshop.consumeBase)
						subtractBase = Woodshop.baseConsumption;
					if(consumeResourceValue - subtractConsumption < 0 | consumeBaseValue - subtractBase < 0)
						break;
					double addValue = multiplier * woodshop.getProduction(resource);
					production.addResource(consumeResource,  -subtractConsumption);
					production.addResource(consumeBase,  -subtractBase);
					production.addResource(resource,  addValue);
					break;
				}
			}
			buildingData.getStatement().close();
			return production;
		} catch (SQLException ex){
			plugin.getLogger().info("SQLException occured while trying to retrieve building data.");
			ex.printStackTrace();
			return null;
		}
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
	
//---ACCESSORS---//
	// - These accessors, though the classes are mostly static and public, will hopefully 
	// - allow other classes to use buildings without importing the files.
	
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