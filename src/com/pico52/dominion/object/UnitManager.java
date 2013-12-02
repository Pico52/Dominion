package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;
import com.pico52.dominion.object.unit.Unit;

public class UnitManager extends DominionObjectManager{
	FileConfiguration config;
	private double baseHealPercentage;
	
	public UnitManager(Dominion plugin){
		super(plugin);
		config = DominionSettings.getUnitsConfig();
		baseHealPercentage = config.getDouble("units.base_heal");
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
		if(amount == 0 || !isReal(unit_id))
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
		double maxHealth = getUnit(classType).health;
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
		double speed = getUnit(classType).speed;
		if(isSeaUnit(classType) && isAffectedByLighthouse(unitId))
			speed *= 1 + plugin.getBuildingManager().getLighthouse().speedBonus;
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
				playerMessage += "has been killed by a spell!";
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
		double health = getUnit(classification).health;
		return createUnit(settlementId, ownerId, classification, health, true);
	}
	
	public int createUnit(int settlementId, int ownerId, String classification, boolean real){
		double health = getUnit(classification).health;
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
		if(!DominionSettings.unitsActive)
			return false;
		boolean success = false;
		int settlementId = plugin.getDBHandler().getSettlementId(settlement);
		double currentFood = plugin.getSettlementManager().getMaterial(settlementId, "food");
		double currentWealth = plugin.getSettlementManager().getMaterial(settlementId, "wealth");
		double foodReq = getUnit(unit).foodConsumption * 10;
		double wealthReq = getUnit(unit).buildCost;
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
				return success;
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
		ItemManager im = plugin.getItemManager();
		if(getDistanceFromItem(unitId, itemId) > DominionSettings.unitPickUpRange)
			return false;
		int holderId = im.getHolderId(itemId);
		if(!getClass(holderId).equalsIgnoreCase("wagon"))
			return false;
		double weightRemaining = getWeightRemaining(unitId);
		if(im.getWeight(itemId, quantity) > weightRemaining)
			quantity = im.getQuantityByWeight(itemId, weightRemaining);
		return (im.giveItemToUnit(itemId, unitId, quantity) > 0);
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
	
	public int[] getUnitsInSettlement(int settlementId){
		int[] units = db.getAllIds("unit");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int unit: units){
			if(getDistanceFromSettlement(unit, settlementId) <= DominionSettings.unitGarrisonRange)
				list.add(new Integer(unit));
		}
		int[] sendUnits = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        sendUnits[i++] = n;
	    }
		return sendUnits;
	}
	
	public boolean setCommandX(int commandId, double xCoord){
		return plugin.getDBHandler().update("command", "xcoord", xCoord, "command_id", commandId);
	}
	
	public boolean setCommandZ(int commandId, double zCoord){
		return plugin.getDBHandler().update("command", "zcoord", zCoord, "command_id", commandId);
	}
	
	public boolean grantSettlementExperience(int settlementId, double experience, String weaponType){
		int[] units = getUnitsInSettlement(settlementId);
		boolean success = true;
		String unitClass = "";
		for(int unit: units){
			unitClass = getClass(unit);
			if(getWeaponClass(unitClass).equalsIgnoreCase(weaponType) && 
					getUnitType(unitClass).equalsIgnoreCase("land") &&
					isFriendlyToObject(unit, settlementId, "settlement"))
				if(!grantExperience(unit, experience))
					success = false;
		}
		return success;
	}
	
	public boolean grantExperience(int unitId, double experience){
		experience += getExperience(unitId);
		return db.update("unit", "experience", experience, "unit_id", unitId);
	}
	
	public boolean isFriendlyToObject(int unitId, int objectId, String object){
		// - This will check friendliness status in the future.
		return true;
	}
	
	public boolean isAffectedByLighthouse(int unitId){
		BuildingManager bm = plugin.getBuildingManager();
		int[] lighthouses = bm.getBuildingIds("lighthouse");
		double range=0, lighthouseX=0, lighthouseZ=0;
		for(int lighthouse: lighthouses){
			range = bm.getLevel(lighthouse) * bm.getLighthouse().range;
			lighthouseX = bm.getX(lighthouse);
			lighthouseZ = bm.getZ(lighthouse);
			if(isFriendlyToObject(unitId, lighthouse, "building") && 
					withinRange(unitId, range, lighthouseX, lighthouseZ))
				return true;
		}
		return false;
	}
	
	public double getHealRate(int unitId){
		double healRate = baseHealPercentage;
		String classType = getClass(unitId);
		if(isSeaUnit(classType)){
			Unit ship = getUnit(classType);
			BuildingManager bm = plugin.getBuildingManager();
			
			int[] dockyards = bm.getBuildingIds("dockyard");
			int[] shipyards = bm.getBuildingIds("shipyard");
			double dockyardRange = bm.getDockyard().range, shipyardRange = bm.getShipyard().range, 
					dockyardRepair=0, shipyardRepair=0, xCoord, zCoord;
			boolean atDockyard = false, atShipyard = false;
			for(int dockyard: dockyards){
				xCoord = bm.getX(dockyard);
				zCoord = bm.getZ(dockyard);
				if(withinRange(unitId, dockyardRange, xCoord, zCoord) && 
						isFriendlyToObject(unitId, dockyard, "building"))
					atDockyard = true;
			}
			for(int shipyard: shipyards){
				xCoord = bm.getX(shipyard);
				zCoord = bm.getZ(shipyard);
				if(withinRange(unitId, shipyardRange, xCoord, zCoord) && 
						isFriendlyToObject(unitId, shipyard, "building"))
					atShipyard = true;
			}
			if(ship.civilian){
				if(bm.getDockyard().repairCivilian && atDockyard == true){
					if(ship.size.equalsIgnoreCase("small")){
						dockyardRepair = bm.getDockyard().smallRepairRate;
					} else if (ship.size.equalsIgnoreCase("medium")){
						dockyardRepair = bm.getDockyard().mediumRepairRate;
					} else if (ship.size.equalsIgnoreCase("large")){
						dockyardRepair = bm.getDockyard().largeRepairRate;
					}
				}
				if(bm.getShipyard().repairCivilian && atShipyard == true){
					if(ship.size.equalsIgnoreCase("small")){
						shipyardRepair = bm.getShipyard().smallRepairRate;
					} else if (ship.size.equalsIgnoreCase("medium")){
						shipyardRepair = bm.getShipyard().mediumRepairRate;
					} else if (ship.size.equalsIgnoreCase("large")){
						shipyardRepair = bm.getShipyard().largeRepairRate;
					}
				}
				if(dockyardRepair > shipyardRepair)
					healRate = dockyardRepair;
				else
					healRate = shipyardRepair;
			} else {
				if(bm.getDockyard().repairWar && atDockyard == true){
					if(ship.size.equalsIgnoreCase("small")){
						dockyardRepair = bm.getDockyard().smallRepairRate;
					} else if (ship.size.equalsIgnoreCase("medium")){
						dockyardRepair = bm.getDockyard().mediumRepairRate;
					} else if (ship.size.equalsIgnoreCase("large")){
						dockyardRepair = bm.getDockyard().largeRepairRate;
					}
				}
				if(bm.getShipyard().repairWar && atShipyard == true){
					if(ship.size.equalsIgnoreCase("small")){
						shipyardRepair = bm.getShipyard().smallRepairRate;
					} else if (ship.size.equalsIgnoreCase("medium")){
						shipyardRepair = bm.getShipyard().mediumRepairRate;
					} else if (ship.size.equalsIgnoreCase("large")){
						shipyardRepair = bm.getShipyard().largeRepairRate;
					}
				}
				if(dockyardRepair > shipyardRepair)
					healRate = dockyardRepair;
				else
					healRate = shipyardRepair;
			}
		} else if(isGarrisoned(unitId))
			healRate = DominionSettings.unitGarrisonHeal;
		return healRate;
	}
	
	public boolean isGarrisoned(int unitId){
		int[] settlements = db.getAllIds("settlement");
		for(int settlement: settlements){
			if(getDistanceFromSettlement(unitId, settlement) <= DominionSettings.unitGarrisonRange)
				return true;
		}
		return false;
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
		if(ownerId == playerId || plugin.getPermissionManager().hasPermission(playerId, "see", ownerId))
			return true;
		// - Check Invisibility
		SpellManager sm = plugin.getSpellManager();
		if(sm.getActiveSpells(unitId, "unit", "unit_invisibility").length > 0){
			double unitX = getUnitX(unitId), unitZ = getUnitZ(unitId);
			for(int spell: sm.getAllSpells("aoe_reveal_invisible_units")){
				if(sm.isWithinAreaOfEffect(unitX, unitZ, spell))
					return true;
			}
			return false;
		}
		// - Check Vision
		for(int unit: getAllPlayerUnits(playerId)){
			if(withinVisionRange(unit, unitId))
				return true;
		}
		// - Fog of War
		return false;
	}
		
	public boolean isUnit(String unit){
		String name = config.getString("units." + unit + ".name");
		return unit.equalsIgnoreCase(name);
	}
	
	public boolean isLandUnit(String unit){
		String type = config.getString("units." + unit + ".type");
		if(type.equalsIgnoreCase("land"))
				return true;
		return false;
	}
	
	public boolean isSeaUnit(String unit){
		String type = config.getString("units." + unit + ".type");
		if(type.equalsIgnoreCase("sea"))
				return true;
		return false;
	}
	
	public String getUnitType(String unit){
		return config.getString("units." + unit + ".type");
	}
	
	public String getWeaponClass(String unit){
		return config.getString("units." + unit + ".weapon_class");
	}
	
	public Unit getUnit(String unit){
		Unit unitData = new Unit();
		unitData.name = config.getString("units." + unit + ".name");
		unitData.type = config.getString("units." + unit + ".type");
		unitData.speed = config.getDouble("units." + unit + ".speed");
		unitData.health = config.getDouble("units." + unit + ".health");
		unitData.offense = config.getDouble("units." + unit + ".offense");
		unitData.defense = config.getDouble("units." + unit + ".defense");
		unitData.range = config.getDouble("units." + unit + ".range");
		unitData.foodConsumption = config.getDouble("units." + unit + ".food_consumption");
		unitData.upkeep = config.getDouble("units." + unit + ".upkeep");
		unitData.buildCost = config.getDouble("units." + unit + ".build_cost");
		unitData.trainingTime = config.getInt("units." + unit + ".training_time");
		unitData.capacity = config.getDouble("units." + unit + ".capacity");
		unitData.size = config.getString("units." + unit + ".size");
		unitData.civilian = config.getBoolean("units." + unit + ".civilian");
		unitData.material1 = config.getString("units." + unit + ".material_1.type");
		unitData.material2 = config.getString("units." + unit + ".material_2.type");
		unitData.material1Quantity = config.getDouble("units." + unit + ".material_1.quantity");
		unitData.material2Quantity = config.getDouble("units." + unit + ".material_2.quantity");

		return unitData;
	}
	
	public String getClass(int unitId){
		return db.getSingleColumnString("unit", "class", unitId, "unit_id");
	}
	
	public int getOwner(int unitId){
		return db.getSingleColumnInt("unit", "owner", unitId, "unit_id");
	}
	
	public double getHealth(int unitId){
		return db.getSingleColumnDouble("unit", "health", unitId, "unit_id");
	}
	
	public double getUnitX(int unitId){
		return db.getSingleColumnDouble("unit", "xcoord", unitId, "unit_id");
	}
	
	public double getUnitZ(int unitId){
		return db.getSingleColumnDouble("unit", "zcoord", unitId, "unit_id");
	}
	
	public double getExperience(int unitId){
		return db.getSingleColumnDouble("unit", "experience", unitId, "unit_id");
	}
	
	public int getTargetId(int unitId){
		int targetId = -1;
		int commandId = getCurrentCommandId(unitId);
		if(commandId == -1)
			return targetId;
		return db.getSingleColumnInt("command", "target_id", commandId, "command_id");
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

	public double getWeight(int unitId){
		int[] items = plugin.getItemManager().getHeldItemIds(unitId);
		double weight = 0;
		for(int item: items){
			weight += plugin.getItemManager().getWeight(item);
		}
		return weight;
	}
	
	public double getWeightRemaining(int unitId){
		double weight = getWeight(unitId);
		double capacity = getCapacity(unitId);
		return capacity - weight;
	}
	
	public double getCapacity(int unitId){
		return getUnit(getClass(unitId)).capacity;
	}
	
	public boolean withinRange(int unitId, int targetId){
		double targetX = getUnitX(targetId);
		double targetZ = getUnitZ(targetId);
		double range = getUnit(getClass(unitId)).range;
		return withinRange(unitId, range, targetX, targetZ);
	}
	
	public boolean withinVisionRange(int unitId, int targetId){
		double targetX = getUnitX(targetId);
		double targetZ = getUnitZ(targetId);
		double range = getUnit(getClass(unitId)).vision;
		return withinRange(unitId, range, targetX, targetZ);
	}
	
	public boolean withinRange(int unitId, double range, double xcoord, double zcoord){
		double unitX = getUnitX(unitId);
		double unitZ = getUnitZ(unitId);
		if(Math.abs(unitX - xcoord) <= range && Math.abs(unitZ - zcoord) <= range)
			return true;
		return false;
	}
	
	public int[] getAllPlayerUnits(int playerId){
		return db.getSpecificIds("unit", playerId, "owner_id");
	}
	
	public String outputUnitData(){
		String output = "", name = "", type = "", mat1 = "", mat2 = "";
		double speed = 0, health = 0, offense = 0, defense = 0, range = 0, foodConsumption = 0, upkeep = 0, 
				buildCost = 0, trainingTime = 0, capacity = 0, mat1Num = 0, mat2Num = 0;
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
			output += "\n§a--------------------------§r \n";
		}
		if(output == "")
			output = "No units are currently available.";
		return output;
	}
	
	public double getBaseHealPercentage(){
		return baseHealPercentage;
	}
	
	public String[] getLandUnitTypes(){
		ArrayList<String> stringList = new ArrayList<String>();
		String name = "", type = "";
		ConfigurationSection section = config.getConfigurationSection("units");
		for(String unit: section.getKeys(false)){
			name = config.getString("units." + unit + ".name");
			type = config.getString("units." + unit + ".type");
			if(type.equalsIgnoreCase("land"))
				stringList.add(name);
		}
		String landUnitTypes[] = new String[stringList.size()];
		landUnitTypes = stringList.toArray(landUnitTypes);
		return landUnitTypes;
	}
	
	public String[] getSeaUnitTypes(){
		ArrayList<String> stringList = new ArrayList<String>();
		String name = "", type = "";
		ConfigurationSection section = config.getConfigurationSection("units");
		for(String unit: section.getKeys(false)){
			name = config.getString("units." + unit + ".name");
			type = config.getString("units." + unit + ".type");
			if(type.equalsIgnoreCase("sea"))
				stringList.add(name);
		}
		String seaUnitTypes[] = new String[stringList.size()];
		seaUnitTypes = stringList.toArray(seaUnitTypes);
		return seaUnitTypes;
	}
}
