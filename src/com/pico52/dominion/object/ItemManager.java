package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pico52.dominion.Dominion;

/** 
 * <b>ItemManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class ItemManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for items.
 */
public class ItemManager extends DominionObjectManager{
	private UnitManager unitManager;

	/** 
	 * <b>ItemManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public ItemManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link ItemManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public ItemManager(Dominion plugin) {
		super(plugin);
		unitManager = plugin.getUnitManager();
	}
	
	public boolean giveItemToUnit(int itemId, int unitId){
		double quantity = getItemQuantity(itemId);
		return giveItemToUnit(itemId, unitId, quantity);
	}
	
	public boolean giveItemToUnit(int itemId, int unitId, double quantity){
		double itemQuantity = getItemQuantity(itemId);
		if(quantity > itemQuantity)
			return false;
		String type = getItemType(itemId);
		if(!giveItemToUnit(type, quantity, unitId))
			return false;
		return subtractQuantity(itemId, quantity);
	}
	
	public boolean giveItemToUnit(String type, double quantity, int unitId){
		ResultSet items = db.getTableData("item", unitId, "*", "unit_id");
		String thisType="";
		int itemId=0;
		double thisQuantity=0;
		boolean merged = false;
		try{
			while(items.next()){
				thisType = items.getString("type");
				if(type.equalsIgnoreCase(thisType)){
					itemId = items.getInt("item_id");
					thisQuantity = items.getDouble("quantity");
					quantity += thisQuantity;
					merged = true;
				}
			}
			items.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		if(merged){
			return db.update("item", "quantity", quantity, "item_id", itemId);
		} else {
			if(createItem(type, quantity, unitId) != -1)
				return true;
			else
				return false;
		}
	}
	
	public boolean giveItemToSettlement(int settlementId, int itemId){
		double quantity = getItemQuantity(itemId);
		return giveItemToSettlement(settlementId, itemId, quantity);
	}
	
	public boolean giveItemToSettlement(int settlementId, int itemId, double quantity){
		double itemQuantity = getItemQuantity(itemId);
		if(quantity > itemQuantity)
			return false;
		String type = getItemType(itemId);
		if(!plugin.getSettlementManager().addMaterial(settlementId, type, quantity))
			return false;
		return subtractQuantity(itemId, quantity);
	}
	
	public boolean dropItem(int itemId){
		double itemQuantity = getItemQuantity(itemId);
		return dropItem(itemId, itemQuantity);
	}
	
	public boolean dropItem(int itemId, double quantity){
		String type = getItemType(itemId);
		int holderId = getHolderId(itemId);
		double xCoord = plugin.getUnitManager().getUnitX(holderId);
		double zCoord = plugin.getUnitManager().getUnitZ(holderId);
		if(!dropItem(type, quantity, xCoord, zCoord))
			return false;
		return subtractQuantity(itemId, quantity);
	}
	
	public boolean dropItem(String type, double quantity, double xCoord, double zCoord){
		int wagonId = unitManager.createUnit(0, 0, "wagon");
		if(wagonId == -1)
			return false;
		if(!unitManager.moveUnit(wagonId, xCoord, zCoord))
			return false;
		int newItemId = createItem(type, quantity, wagonId);
		if(newItemId == -1)
			return false;
		return true;
	}
	
	public boolean dropAllItems(int unitId){
		boolean success = true;
		int[] items = getHeldItemIds(unitId);
		if(isUnitEmpty(unitId))
			return true;
		for(int item: items){
			if(!dropItem(item))
				success = false;
		}
		return success;
	}
	
	public int createItem(String type, double quantity){
		return createItem(type, quantity, 0);
	}
	
	public int createItem(String type, double quantity, int unitId){
		boolean makeItem = db.createItem(unitId, type, quantity);
		if(makeItem){
			return db.getNewestId("item");
		} else return -1;
	}

	public boolean pickUpItem(int itemId, int unitId){
		int holderId = getHolderId(itemId);
		if(holderId == unitId)
			return true;
		return db.update("item", "unit_id", unitId, "item_id", itemId);
	}
	
	public boolean destroyItem(int itemId){
		int holderId = getHolderId(itemId);
		if(!db.remove("item", itemId))
			return false;
		if(plugin.getUnitManager().getClass(holderId).equalsIgnoreCase("wagon"))
			if(isUnitEmpty(holderId))
				return plugin.getUnitManager().kill(holderId,"disband");
		return true;
	}
	
	public boolean destroyAllItemsOnUnit(int unitId){
		int[] items = getHeldItemIds(unitId);
		boolean success = true;
		for(int item: items){
			if(!destroyItem(item))
				success = false;
		}
		return success;
	}
	
	public boolean addQuantity(int itemId, double quantity){
		double currentQuantity = getItemQuantity(itemId);
		double newQuantity = currentQuantity + quantity;
		return db.update("item", "quantity", newQuantity, "item_id", itemId);
	}
	
	public boolean subtractQuantity(int itemId, double quantity){
		double currentQuantity = getItemQuantity(itemId);
		double newQuantity = currentQuantity - quantity;
		if(newQuantity <= 0)
			return destroyItem(itemId);
		else
			return db.update("item", "quantity", newQuantity, "item_id", itemId);
	}
	
	public boolean isUnitEmpty(int unitId){
		int[] heldItems = getHeldItemIds(unitId);
		if(heldItems.length == 0 | heldItems == null)
			return true;
		else
			return false;
	}
	
	public int[] getHeldItemIds(int unitId){
		ArrayList<Integer> itemIds = new ArrayList<Integer>();
		ResultSet items = db.getTableData("item", unitId, "item_id", "unit_id");
		try{
			while(items.next()){
				itemIds.add(items.getInt("item_id"));
			}
			items.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] heldItems = new int[itemIds.size()];
		int i = 0;
	    for (Integer n : itemIds) {
	        heldItems[i++] = n;
	    }
		return heldItems;
	}
	
	public String getItemType(int itemId){
		String itemType = "";
		ResultSet item = db.getTableData("item", itemId, "type", "item_id");
		try{
			if(item.next())
				itemType = item.getString("type");
			item.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the type of an item.");
			ex.printStackTrace();
		}
		return itemType;
	}
	
	public double getItemQuantity(int itemId){
		double quantity = -1;
		ResultSet item = db.getTableData("item", itemId, "quantity", "item_id");
		try{
			if(item.next())
				quantity = item.getDouble("quantity");
			item.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the quantity of an item.");
			ex.printStackTrace();
		}
		return quantity;
	}
	
	public int getHolderId(int itemId){
		int holderId = -1;
		ResultSet item = db.getTableData("item", itemId, "unit_id", "item_id");
		try{
			if(item.next())
				holderId = item.getInt("unit_id");
			item.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the holder id of an item.");
			ex.printStackTrace();
		}
		return holderId;
	}
}
