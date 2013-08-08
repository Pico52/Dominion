package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;
import com.pico52.dominion.object.unit.land.Archer;
import com.pico52.dominion.object.unit.land.DreadKnight;
import com.pico52.dominion.object.unit.land.FootSoldier;
import com.pico52.dominion.object.unit.land.Knight;
import com.pico52.dominion.object.unit.land.ManAtArms;
import com.pico52.dominion.object.unit.land.Marksman;
import com.pico52.dominion.object.unit.land.Militia;
import com.pico52.dominion.object.unit.land.Recruit;
import com.pico52.dominion.object.unit.land.Scout;
import com.pico52.dominion.object.unit.land.Skirmisher;
import com.pico52.dominion.object.unit.land.Swordsman;
import com.pico52.dominion.object.unit.land.Trader;
import com.pico52.dominion.object.unit.land.Wagon;
import com.pico52.dominion.object.unit.sea.Caravel;
import com.pico52.dominion.object.unit.sea.Carrack;
import com.pico52.dominion.object.unit.sea.Cog;
import com.pico52.dominion.object.unit.sea.FishingBoat;
import com.pico52.dominion.object.unit.sea.Frigate1stRate;
import com.pico52.dominion.object.unit.sea.Frigate2ndRate;
import com.pico52.dominion.object.unit.sea.Frigate3rdRate;
import com.pico52.dominion.object.unit.sea.Galleon;
import com.pico52.dominion.object.unit.sea.Galley;
import com.pico52.dominion.object.unit.sea.ManOWar;
import com.pico52.dominion.object.unit.sea.Schooner;
import com.pico52.dominion.object.unit.sea.SeaUnit;
import com.pico52.dominion.object.unit.sea.Sloop;

public class UnitManager extends DominionObjectManager{
	FileConfiguration config;
	private double baseHealPercentage;
	private String landUnitTypes[];
	private String seaUnitTypes[];
	private Archer archer;
	private DreadKnight dreadKnight;
	private FootSoldier footSoldier;
	private Knight knight;
	private ManAtArms manAtArms;
	private Marksman marksman;
	private Militia militia;
	private Recruit recruit;
	private Scout scout;
	private Skirmisher skirmisher;
	private Swordsman swordsman;
	private Trader trader;
	private Wagon wagon;
	private Caravel caravel;
	private Carrack carrack;
	private Cog cog;
	private FishingBoat fishingBoat;
	private Frigate1stRate frigate1stRate;
	private Frigate2ndRate frigate2ndRate;
	private Frigate3rdRate frigate3rdRate;
	private Galleon galleon;
	private Galley galley;
	private ManOWar manOWar;
	private Schooner schooner;
	private Sloop sloop;
	
	public UnitManager(Dominion plugin){
		super(plugin);
		config = DominionSettings.getUnitsConfig();
		baseHealPercentage = .01;
		landUnitTypes = new String[]{"archer", "dreadknight", "footsoldier", "knight", "manatarms", "marksman", "militia", "recruit", "scout", "skirmisher", "swordsman", "trader", "wagon"};
		seaUnitTypes = new String[]{"caravel", "carrack", "cog", "fishingboat", "frigate1strate", "frigate2ndrate", "frigate3rdrate", "galleon", "galley", "manowar", "schooner", "sloop"};
		archer = new Archer();
		dreadKnight = new DreadKnight();
		footSoldier = new FootSoldier();
		knight = new Knight();
		manAtArms = new ManAtArms();
		marksman = new Marksman();
		militia = new Militia();
		recruit = new Recruit();
		scout = new Scout();
		skirmisher = new Skirmisher();
		swordsman = new Swordsman();
		trader = new Trader();
		wagon = new Wagon();
		caravel = new Caravel();
		carrack = new Carrack();
		cog = new Cog();
		fishingBoat = new FishingBoat();
		frigate1stRate = new Frigate1stRate();
		frigate2ndRate = new Frigate2ndRate();
		frigate3rdRate = new Frigate3rdRate();
		galleon = new Galleon();
		manOWar = new ManOWar();
		schooner = new Schooner();
		sloop = new Sloop();
	}
	
	public boolean damage(int unit_id, double damage){
		if(!isReal(unit_id))
			return true;
		double health = getHealth(unit_id) - damage;
		if(health > 0)
			return plugin.getDBHandler().update("unit", "health", health, "unit_id", unit_id);
		else
			return kill(unit_id, "slain");
	}
	
	public boolean heal(int unit_id, double amount){
		if(amount == 0 | !isReal(unit_id))
			return true;
		ResultSet unit = plugin.getDBHandler().getUnitData(unit_id, "*");
		int ownerId = 0;
		double currentHealth = 0, newHealth = 0;
		String classType = "", ownerName = "";
		try{
			if(unit.next()){
				currentHealth = unit.getDouble("health");
				classType = unit.getString("class");
				ownerId = unit.getInt("owner_id");
			}
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		ownerName = plugin.getDBHandler().getPlayerName(ownerId);
		double maxHealth = getUnit(classType).getHealth();
		newHealth = currentHealth + amount;
		if(newHealth > maxHealth){
			amount = maxHealth - currentHealth;
			newHealth = maxHealth;
		}
		if(plugin.getDBHandler().update("unit", "health", newHealth, "unit_id", unit_id)){
			if(amount == 0)
				return true;
			if(plugin.isPlayerOnline(ownerName))
				plugin.getServer().getPlayer(ownerName).sendMessage(classType + " unit #" + unit_id + " has been healed by " + amount + " health points.");
			plugin.getLogger().info(classType + " unit #" + unit_id + " owned by " + ownerName + " has been healed by " + amount + " health points.");
			return true;
		} else {
			plugin.getLogger().info("Failed to heal " + classType + " unit #" + unit_id + " owned by " + ownerName + ".");
			return false;
		}
	}
	
	public boolean moveUnitTowards(int unitId, double toXCoord, double toZCoord){
		double xCoord = 0, zCoord = 0;
		String classType = "";
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "*");
		try{
			if(unit.next()){
				classType = unit.getString("class");
				xCoord = unit.getDouble("xcoord");
				zCoord = unit.getDouble("zCoord");
			}
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		double speed = getUnit(classType).getSpeed();
		SpellManager sm = plugin.getSpellManager();
		for(int spell: sm.getAllSpells("aoe_unit_slow")){
			// For future, add an option in the config to determine if the slow should affect all units, enemy units, or any unit that isn't their own.
			if(sm.isWithinAreaOfEffect(xCoord, zCoord, spell))
				if((speed *= 1 - sm.getPower(spell)) < 0)
					speed = 0;
		}
		if(Math.abs(xCoord - toXCoord) < speed)
			xCoord = toXCoord;
		else if(xCoord < toXCoord)
			xCoord += speed;
		else
			xCoord -= speed;
		
		if(Math.abs(zCoord - toZCoord) < speed)
			zCoord = toZCoord;
		else if(zCoord < toZCoord)
			zCoord += speed;
		else
			zCoord -= speed;
		
		// - Arriving at destination..
		if(xCoord == toXCoord & zCoord == toZCoord & getCommand(getCurrentCommandId(unitId)).equalsIgnoreCase("move"))
			commandToCamp(unitId);
		
		return moveUnit(unitId, xCoord, zCoord);
	}
	
	public boolean moveUnit(int unit_id, double x_coord, double z_coord){
		boolean success = true;
		if(!plugin.getDBHandler().update("unit", "xcoord", x_coord, "unit_id", unit_id))
			success = false;
		if(!plugin.getDBHandler().update("unit", "zcoord", z_coord, "unit_id", unit_id))
			success = false;
		return success;
	}
	
	public boolean kill(int unitId){
		return kill(unitId,"none");
	}
	
	public boolean kill(int unitId, String reason){
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "*");
		int ownerId=0;
		String classType="", playerName="";
		try{
			if(unit.next()){
				classType = unit.getString("class");
				ownerId = unit.getInt("owner_id");
				playerName = plugin.getDBHandler().getPlayerName(ownerId);
			}
			unit.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Failed to remove a unit.");
			ex.printStackTrace();
		}
		if(classType.equalsIgnoreCase("wagon")){
			if(!plugin.getItemManager().destroyAllItemsOnUnit(unitId))
				return false;
		} else if(!plugin.getItemManager().dropAllItems(unitId))
			return false;
		if(plugin.getDBHandler().remove("unit",  unitId)){
			removeCurrentCommand(unitId);
			String playerMessage = plugin.getLogPrefix() + "Your " + classType + " unit #" + unitId + " ";
			String logMessage = classType + " owned by " + playerName + " ";
			if(reason.equalsIgnoreCase("none") | reason.equalsIgnoreCase("slain")){
				playerMessage += "has been slain!";
				logMessage += " has been slain.";
			} else if (reason.equalsIgnoreCase("starvation")){
				playerMessage += "has starved!";
				logMessage += " has starved.";
			} else if (reason.equalsIgnoreCase("spell")){
				playerMessage += "has sbeen killed by a spell!";
				logMessage += " has been killed by a spell.";
			}else if (reason.equalsIgnoreCase("disband")){
				playerMessage += "has been disbanded.";
				logMessage += " has been disbanded.";
			} else if (reason.equalsIgnoreCase("payment")){
				playerMessage += "has left due to not being paid!";
				logMessage += " has left due to not being paid.";
			} else if (reason.equalsIgnoreCase("admin")){
				 playerMessage += "has been killed by an administrator!";
				logMessage += " has been killed by an administrator.";
			} else {
				playerMessage += "has been slain!";
				logMessage += " has been slain.";
			}
			if(plugin.isPlayerOnline(playerName))
				plugin.getServer().getPlayer(playerName).sendMessage(plugin.getLogPrefix() + playerMessage);
			plugin.getLogger().info(logMessage);
			return true;
		}else{
			plugin.getLogger().info("Failed to remove the unit: \"" + classType + "\" #" + unitId +" owned by " + playerName + ".");
			return false;
		}
	}
	
	public int createUnit(int settlementId, int ownerId, String classification){
		double health = getUnit(classification).getHealth();
		return createUnit(settlementId, ownerId, classification, health, true);
	}
	
	public int createUnit(int settlementId, int ownerId, String classification, boolean real){
		double health = getUnit(classification).getHealth();
		return createUnit(settlementId, ownerId, classification, health, real);
	}
	
	public int createUnit(int settlementId, int ownerId, String classification, double health, boolean real){
		int newId = -1;
		double xcoord=0, zcoord=0;
		ResultSet settlementData = plugin.getDBHandler().getSettlementData(settlementId, "*");
		try{
			if(settlementData.next()){
				xcoord = settlementData.getDouble("xcoord");
				zcoord = settlementData.getDouble("zcoord");
			}
			settlementData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		plugin.getDBHandler().createUnit(settlementId, classification, ownerId, xcoord, zcoord, health, 0, real);
		newId = plugin.getDBHandler().getNewestId("unit");
		String playerName = plugin.getDBHandler().getPlayerName(ownerId);
		if(playerName != null & plugin.isPlayerOnline(playerName))
			plugin.getServer().getPlayer(playerName).sendMessage(plugin.getLogPrefix() + "Your " + classification + " unit has been created and has been assigned as unit #" + newId);
		
		return newId;
	}
	
	public boolean startProduction(String settlement, String unit){
		boolean success = false;
		int settlementId = plugin.getDBHandler().getSettlementId(settlement);
		double currentFood = plugin.getSettlementManager().getMaterial(settlementId, "food");
		double currentWealth = plugin.getSettlementManager().getMaterial(settlementId, "wealth");
		int foodReq = getUnit(unit).getFoodConsumption() * 10;
		int wealthReq = getUnit(unit).getBuildCost();
		if(currentFood < foodReq | currentWealth < wealthReq)
			return false;
		boolean food = plugin.getDBHandler().subtractMaterial(settlementId, "food", foodReq);
		boolean wealth = plugin.getDBHandler().subtractMaterial(settlementId, "wealth", wealthReq);
		boolean prod = plugin.getDBHandler().createProduction(settlement, unit);
		if(food & wealth & prod)
			success = true;
		return success;
	}
	
	public boolean setTarget(int unitId, int targetId){
		return plugin.getDBHandler().update("command", "target_id", targetId, "unit_id", unitId);
	}
	
	public int getCurrentCommandId(int unitId){
		ResultSet command = plugin.getDBHandler().getTableData("command", "command_id", "unit_id=" + unitId);
		int id = -1;
		try{
			if(command.next())
				id = command.getInt("command_id");
			command.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return id;
	}

	public String getCommand(int commandId){
		ResultSet commands = plugin.getDBHandler().getTableData("command", commandId, "command", "command_id");
		String command = "";
		try{
			if(commands.next())
				command = commands.getString("command");
			commands.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return command;
	}
	
	public boolean removeCurrentCommand(int unitId){
		return plugin.getDBHandler().remove("command", getCurrentCommandId(unitId));
	}
	
	public boolean commandToAttack(int unitId, int targetId){
		boolean success = false;
		int commandId = getCurrentCommandId(unitId);
		if(commandId > 0){
			boolean commandSuccess = plugin.getDBHandler().update("command", "command", "attack", "command_id", commandId);
			boolean afflictSuccess = plugin.getDBHandler().update("command", "target_id", targetId, "command_id", commandId);
			if(commandSuccess & afflictSuccess)
				success = true;
			else
				success = false;
		} else success = plugin.getDBHandler().createCommand(unitId, "attack", targetId);
		if(success){
			String ownerName = plugin.getDBHandler().getOwnerName("unit", unitId);
			if(plugin.isPlayerOnline(ownerName))
				plugin.getServer().getPlayer(ownerName).sendMessage(plugin.getLogPrefix() + "Unit #" + unitId + " is now targeting unit #" + targetId + " for attack.");
			return true;
		}
		return false;
	}
	
	public boolean commandToCamp(int unitId){
		boolean success = false;
		int commandId = getCurrentCommandId(unitId);
		if(commandId > 0)
			success = plugin.getDBHandler().update("command", "command", "camp", "command_id", commandId);
		else
			success = plugin.getDBHandler().createCommand(unitId, "camp");
		if(success){
			String ownerName = plugin.getDBHandler().getOwnerName("unit", unitId);
			if(plugin.isPlayerOnline(ownerName))
				plugin.getServer().getPlayer(ownerName).sendMessage(plugin.getLogPrefix() + "Unit #" + unitId + " is now camping.");
			return true;
		}
		return false;
	}
	
	public boolean commandToMove(int unitId, double xCoord, double zCoord){
		boolean success = false;
		int commandId = getCurrentCommandId(unitId);
		if(commandId > 0){
			boolean commandSuccess = plugin.getDBHandler().update("command", "command", "move", "command_id", commandId);
			boolean xCoordSuccess = setCommandX(commandId, xCoord);
			boolean zCoordSuccess = setCommandZ(commandId, zCoord);
			if(commandSuccess & xCoordSuccess & zCoordSuccess)
				success = true;
			else
				success = false;
		} else success = plugin.getDBHandler().createCommand(unitId, "move", xCoord, zCoord);
		if(success){
			String ownerName = plugin.getDBHandler().getOwnerName("unit", unitId);
			if(plugin.isPlayerOnline(ownerName))
				plugin.getServer().getPlayer(ownerName).sendMessage(plugin.getLogPrefix() + "Unit #" + unitId + " is now on its way to x:" + xCoord + " z:" + zCoord + ".");
			return true;
		}
		return false;
	}
	
	public boolean dropItem(int unitId, int itemId){
		double quantity = plugin.getItemManager().getItemQuantity(itemId);
		return dropItem(unitId, itemId, quantity);
	}
	
	public boolean dropItem(int unitId, int itemId, double quantity){
		int settlementId = getClosestSettlement(unitId);
		if(getDistanceFromSettlement(unitId, settlementId) <= DominionSettings.unitDropOffInSettlementRange)
			return plugin.getItemManager().giveItemToSettlement(settlementId, itemId, quantity);
		else
			return plugin.getItemManager().dropItem(itemId, quantity);
	}
	
	public boolean pickUpItem(int unitId, String material){
		int[] items = getItemsWithinRange(unitId, DominionSettings.unitPickUpRange);
		ArrayList<Integer> list = new ArrayList<Integer>();
		String itemMat="", unitClass="";
		int holderId = 0;
		double quantity = 0;
		for(int item: items){
			itemMat = plugin.getItemManager().getItemType(item);
			holderId = plugin.getItemManager().getHolderId(item);
			unitClass = getClass(holderId);
			if(itemMat.equalsIgnoreCase(material) & holderId != unitId & unitClass.equalsIgnoreCase("wagon")){
				list.add(new Integer(item));
				quantity += plugin.getItemManager().getItemQuantity(item);
			}
		}
		int[] sendItems = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        sendItems[i++] = n;
	    }
	    return pickUpItem(unitId, sendItems, quantity);
	}
	
	public boolean pickUpItem(int unitId, String material, double quantity){
		int[] items = getItemsWithinRange(unitId, DominionSettings.unitPickUpRange);
		ArrayList<Integer> list = new ArrayList<Integer>();
		String itemMat="", unitClass="";
		int holderId=0;
		for(int item: items){
			itemMat = plugin.getItemManager().getItemType(item);
			holderId = plugin.getItemManager().getHolderId(item);
			unitClass = getClass(holderId);
			if(itemMat.equalsIgnoreCase(material) & holderId != unitId & unitClass.equalsIgnoreCase("wagon"))
				list.add(new Integer(item));
		}
		int[] sendItems = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        sendItems[i++] = n;
	    }
	    return pickUpItem(unitId, sendItems, quantity);
	}
	
	public boolean pickUpItem(int unitId, int[] itemIds, double quantity){
		double thisQuantity = 0;
		double count = quantity;
		boolean success = true;
		for(int item: itemIds){
			if(count <= 0)
				break;
			thisQuantity = plugin.getItemManager().getItemQuantity(item);
			if(count <= thisQuantity){
				if(pickUpItem(unitId, item, count))
					count -= count;
				else
					success = false;
			} else {
				if(pickUpItem(unitId, item, thisQuantity))
					count -= thisQuantity;
				else
					success = false;
			}
		}
		return success;
	}
	
	public boolean pickUpItem(int unitId, int itemId){
		double quantity = plugin.getItemManager().getItemQuantity(itemId);
		return pickUpItem(unitId, itemId, quantity);
	}
	
	public boolean pickUpItem(int unitId, int itemId, double quantity){
		if(getDistanceFromItem(unitId, itemId) > DominionSettings.unitPickUpRange)
			return false;
		int holderId = plugin.getItemManager().getHolderId(itemId);
		if(!getClass(holderId).equalsIgnoreCase("wagon"))
			return false;
		return plugin.getItemManager().giveItemToUnit(itemId, unitId, quantity);
	}
	
	public int[] getItemsWithinRange(int unitId, double range){
		int[] items = db.getAllIds("item");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int item: items){
			if(getDistanceFromItem(unitId, item) <= range)
				list.add(new Integer(item));
		}
		int[] sendItems = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        sendItems[i++] = n;
	    }
		return sendItems;
	}
	
	public double getDistanceFromItem(int unitId, int itemId){
		int holderId = plugin.getItemManager().getHolderId(itemId);
		return getDistanceFromUnit(unitId, holderId);
	}
	
	public int getClosestSettlement(int unitId){
		int settlementId = 0;
		double distance = 0, closest = -1;
		for(int sid: db.getAllIds("settlement")){
			distance = getDistanceFromSettlement(unitId, sid);
			if(closest == -1)
				closest = distance;
			if(distance <= closest){
				closest = distance;
				settlementId = sid;
			}
		}
		return settlementId;
	}
	
	public double getDistanceFromSettlement(int unitId, int settlementId){
		double settlementX = plugin.getSettlementManager().getX(settlementId);
		double settlementZ = plugin.getSettlementManager().getZ(settlementId);
		double unitX = getUnitX(unitId);
		double unitZ = getUnitZ(unitId);
		double xDifference = Math.abs(settlementX - unitX);
		double zDifference = Math.abs(settlementZ - unitZ);
		if(xDifference > zDifference)
			return xDifference;
		else
			return zDifference;
	}
	
	public int[] getUnitsWithinRange(int unitId, double range) {
		int[] units = db.getAllIds("unit");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int unit: units){
			if(getDistanceFromUnit(unitId, unit) <= range)
				list.add(new Integer(unit));
		}
		int[] sendUnits = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        sendUnits[i++] = n;
	    }
		return sendUnits;
	}
	
	public double getDistanceFromUnit(int unitId, int targetUnit){
		// - For later, make this callculation make more sense.  Make it go by the radius.
		double targetX = getUnitX(targetUnit);
		double targetZ = getUnitZ(targetUnit);
		double unitX = getUnitX(unitId);
		double unitZ = getUnitZ(unitId);
		double xDifference = Math.abs(targetX - unitX);
		double zDifference = Math.abs(targetZ - unitZ);
		if(xDifference > zDifference)
			return xDifference;
		else
			return zDifference;
	}
	
	public boolean setCommandX(int commandId, double xCoord){
		return plugin.getDBHandler().update("command", "xcoord", xCoord, "command_id", commandId);
	}
	
	public boolean setCommandZ(int commandId, double zCoord){
		return plugin.getDBHandler().update("command", "zcoord", zCoord, "command_id", commandId);
	}
	
	/** 
	 * <b>isVisible</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isVisible(int unitId, int playerId)
	 * <br>
	 * <br>
	 * @param unitId - The id of the unit whose visibility is in question.
	 * @param playerId - The id of the player attempting to view this unit.
	 * @return True if the player can see the unit; false if they cannot.
	 */
	public boolean isVisible(int unitId, int playerId){
		int ownerId = getOwner(unitId);
		if(ownerId == playerId)
			return true;
		SpellManager sm = plugin.getSpellManager();
		if(sm.getActiveSpells(unitId, "unit", "unit_invisibility").length > 0){
			double unitX = getUnitX(unitId), unitZ = getUnitZ(unitId);
			for(int spell: sm.getAllSpells("aoe_reveal_invisible_units")){
				if(sm.isWithinAreaOfEffect(unitX, unitZ, spell))
					return true;
			}
			return false;
		}
		return true;
	}
		
	public boolean isUnit(String unit){
		return (isLandUnit(unit) | isSeaUnit(unit));
	}
	
	public boolean isLandUnit(String unit){
		for(String type: landUnitTypes){
			if(unit.equalsIgnoreCase(type))
				return true;
		}
		return false;
	}
	
	public boolean isSeaUnit(String unit){
		for(String type: seaUnitTypes){
			if(unit.equalsIgnoreCase(type))
				return true;
		}
		return false;
	}
	
	public String getUnitType(String unit){
		if(isLandUnit(unit))
			return "land";
		else if (isSeaUnit(unit))
			return "sea";
		else return null;
	}
	
	public Unit getUnit(String unit){
		String type = getUnitType(unit);
		if(type.equalsIgnoreCase("land"))
			return getLandUnit(unit);
		else if (type.equalsIgnoreCase("sea"))
			return getSeaUnit(unit);
		else return null;
	}
	
	public String getClass(int unitId){
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "class");
		String classification = "";
		try{
			if(unit.next())
				classification = unit.getString("class");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return classification;
	}
	
	public int getOwner(int unitId){
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "owner_id");
		int ownerId = 0;
		try{
			if(unit.next())
				ownerId = unit.getInt("owner_id");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return ownerId;
	}
	
	public double getHealth(int unitId){
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "health");
		double health=0;
		try{
			if(unit.next())
				health = unit.getDouble("health");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return health;
	}
	
	public double getUnitX(int unitId){
		ResultSet unit = plugin.getDBHandler().getTableData("unit", unitId, "xcoord", "unit_id");
		double xCoord = 0;
		try{
			if(unit.next())
				xCoord = unit.getDouble("xcoord");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return xCoord;
	}
	
	public double getUnitZ(int unitId){
		ResultSet unit = plugin.getDBHandler().getTableData("unit", unitId, "zcoord", "unit_id");
		double zCoord = 0;
		try{
			if(unit.next())
				zCoord = unit.getDouble("zcoord");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return zCoord;
	}
	
	public int getTargetId(int unitId){
		int targetId = -1;
		int commandId = getCurrentCommandId(unitId);
		if(commandId == -1)
			return targetId;
		ResultSet command = plugin.getDBHandler().getTableData("command", commandId, "target_id", "command_id");
		try{
			if(command.next())
				targetId = command.getInt("target_id");
			command.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return targetId;
	}
	
	public double getTargetX(int unitId){
		int targetId = getTargetId(unitId);
		if(targetId == -1)
			return 0;
		ResultSet command = plugin.getDBHandler().getUnitData(targetId, "xcoord");
		double xCoord = 0;
		try{
			if(command.next())
				xCoord = command.getDouble("xcoord");
			command.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return xCoord;
	}
	
	public double getTargetZ(int unitId){
		int targetId = getTargetId(unitId);
		if(targetId == -1)
			return 0;
		ResultSet command = plugin.getDBHandler().getUnitData(targetId, "zcoord");
		double zCoord = 0;
		try{
			if(command.next())
				zCoord = command.getDouble("zcoord");
			command.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return zCoord;
	}
	
	public boolean isReal(int unitId){
		ResultSet unit = plugin.getDBHandler().getUnitData(unitId, "real");
		boolean real = true;
		try{
			if(unit.next())
				real = unit.getBoolean("real");
			unit.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return real;
	}
	
	public boolean withinRange(int unitId, int targetId){
		double unitX = getUnitX(unitId);
		double unitZ = getUnitZ(unitId);
		double targetX = getUnitX(targetId);
		double targetZ = getUnitZ(targetId);
		int range = getUnit(getClass(unitId)).getRange();
		if(Math.abs(unitX - targetX) <= range & Math.abs(unitZ - targetZ) <= range){
			return true;
		}
		return false;
	}
	
	public Unit getLandUnit(String unit){
		if(unit.equalsIgnoreCase("archer"))
			return archer;
		else if (unit.equalsIgnoreCase("dreadknight"))
			return dreadKnight;
		else if (unit.equalsIgnoreCase("footsoldier"))
			return footSoldier;
		else if (unit.equalsIgnoreCase("knight"))
			return knight;
		else if (unit.equalsIgnoreCase("manataarms"))
			return manAtArms;
		else if (unit.equalsIgnoreCase("marksman"))
			return marksman;
		else if (unit.equalsIgnoreCase("militia"))
			return militia;
		else if (unit.equalsIgnoreCase("recruit"))
			return recruit;
		else if (unit.equalsIgnoreCase("scout"))
			return scout;
		else if (unit.equalsIgnoreCase("skirmisher"))
			return skirmisher;
		else if (unit.equalsIgnoreCase("swordsman"))
			return swordsman;
		else if (unit.equalsIgnoreCase("trader"))
			return trader;
		else if (unit.equalsIgnoreCase("wagon"))
			return wagon;
		return null;
	}
	
	public SeaUnit getSeaUnit(String unit){
		if (unit.equalsIgnoreCase("caravel"))
			return caravel;
		else if (unit.equalsIgnoreCase("carrack"))
			return carrack;
		else if (unit.equalsIgnoreCase("cog"))
			return cog;
		else if (unit.equalsIgnoreCase("fishingboat"))
			return fishingBoat;
		else if (unit.equalsIgnoreCase("frigate1strate"))
			return frigate1stRate;
		else if (unit.equalsIgnoreCase("frigate2ndrate"))
			return frigate2ndRate;
		else if (unit.equalsIgnoreCase("frigate3rdrate"))
			return frigate3rdRate;
		else if (unit.equalsIgnoreCase("galleon"))
			return galleon;
		else if (unit.equalsIgnoreCase("galley"))
			return galley;
		else if (unit.equalsIgnoreCase("manowar"))
			return manOWar;
		else if (unit.equalsIgnoreCase("schooner"))
			return schooner;
		else if (unit.equalsIgnoreCase("sloop"))
			return sloop;
		return null;
	}
	
	public String outputUnitData(){
		String output = "", name = "", type = "", mat1 = "", mat2 = "";
		double speed = 0, health = 0, offense = 0, defense = 0, range = 0, foodConsumption = 0, upkeep = 0, buildCost = 0, 
				trainingTime = 0, capacity = 0, mat1Num = 0, mat2Num = 0;
		ConfigurationSection section = config.getConfigurationSection("units");
		for(String unit: section.getKeys(false)){
			name = config.getString("units." + unit + ".name");
			type = config.getString("units." + unit + ".type");
			speed = config.getDouble("units." + unit + ".speed");
			health = config.getDouble("units." + unit + ".health");
			offense = config.getDouble("units." + unit + ".offense");
			defense = config.getDouble("units." + unit + ".defense");
			range = config.getDouble("units." + unit + ".range");
			foodConsumption = config.getDouble("units." + unit + ".food_consumption");
			upkeep = config.getDouble("units." + unit + ".upkeep");
			buildCost = config.getDouble("units." + unit + ".build_cost");
			trainingTime = config.getDouble("units." + unit + ".training_time");
			capacity = config.getDouble("units." + unit + ".capacity");
			mat1 = config.getString("units." + unit + ".material_1.type");
			mat2 = config.getString("units." + unit + ".material_2.type");
			mat1Num = config.getDouble("units." + unit + ".material_1.quantity");
			mat2Num = config.getDouble("units." + unit + ".material_2.quantity");
			char colorCode = '5';
			if(type.equalsIgnoreCase("land"))
				colorCode = '2';
			else if (type.equalsIgnoreCase("sea"))
				colorCode = '9';
			output += "§" + colorCode + name + "§f - §aHP:§f " + health + "  §aOff:§f " + offense + "  §aDef:§f " + defense + "  §aSpe:§f " + speed + 
					"  §aRange:§f " + range + "  §aFood:§f " + foodConsumption + "  §aUpK:§f " + upkeep + "  §aBuild:§f " + buildCost + "  §aTime:§f " + 
					trainingTime + "  §aCap:§f " + capacity;
			if(!mat1.equalsIgnoreCase("none") & mat1Num > 0)
				output +=  "  §aMat1:§f " + mat1Num + " " + mat1;
			if(!mat2.equalsIgnoreCase("none") & mat2Num > 0)
				output += "  §aMat2:§f " + mat2Num + " " + mat2;
			output += "\n§a--------------------------§r\n";
		}
		if(output == "")
			output = "No units are currently available.";
		return output;
	}
	
	public double getBaseHealPercentage(){
		return baseHealPercentage;
	}
	public String[] getLandUnitTypes(){
		return landUnitTypes;
	}
	public String[] getSeaUnitTypes(){
		return seaUnitTypes;
	}
	public Archer getArcher(){
		return archer;
	}
	public DreadKnight getDreadKnight(){
		return dreadKnight;
	}
	public FootSoldier getFootSoldier(){
		return footSoldier;
	}
	public Knight getKnight(){
		return knight;
	}
	public ManAtArms getManAtArms(){
		return manAtArms;
	}
	public Marksman getMarksman(){
		return marksman;
	}
	public Militia getMilitia(){
		return militia;
	}
	public Recruit getRecruit(){
		return recruit;
	}
	public Scout getScout(){
		return scout;
	}
	public Skirmisher getSkirmisher(){
		return skirmisher;
	}
	public Swordsman getSwordsman(){
		return swordsman;
	}
	public Trader getTrader(){
		return trader;
	}
	public Wagon getWagon(){
		return wagon;
	}
	public Caravel getCaravel(){
		return caravel;
	}
	public Carrack getCarrack(){
		return carrack;
	}
	public Cog getCog(){
		return cog;
	}
	public FishingBoat getFishingBoat(){
		return fishingBoat;
	}
	public Frigate1stRate getFrigate1stRate(){
		return frigate1stRate;
	}
	public Frigate2ndRate getFrigate2ndRate(){
		return frigate2ndRate;
	}
	public Frigate3rdRate getFrigate3rdRate(){
		return frigate3rdRate;
	}
	public Galleon getGalleon(){
		return galleon;
	}
	public Galley getGalley(){
		return galley;
	}
	public ManOWar getManOWar(){
		return manOWar;
	}
	public Schooner getSchooner(){
		return schooner;
	}
	public Sloop getSloop(){
		return sloop;
	}
}
