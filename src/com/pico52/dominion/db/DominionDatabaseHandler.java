package com.pico52.dominion.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.pico52.dominion.Dominion;

/** 
 * <b>DominionDatabaseHandler</b><br>
 * <br>
 * &nbsp;&nbsp;public class DominionDatabaseHandler extends {@link SQLite}
 * <br>
 * <br>
 * The main file for all Dominion-based database transactions.
 */
public class DominionDatabaseHandler extends SQLite{
//===================Default database setup.===================//
	private static String[] settlementColumns = 
		{"settlement_id", "name", "owner_id", "kingdom_id", "biome", "xcoord", "zcoord", "class", "wall", "mana", "population", "wealth", "food", "wood", "cobblestone", "stone", "sand", 
		"gravel", "dirt", "iron_ingot", "iron_ore", "emerald", "emerald_ore", "gold_ingot", "gold_ore", "flint", "feather", "lapis_ore", "diamond", "obsidian", "netherrack", "nether_brick", 
		"redstone", "brick", "glowstone", "clay", "coal", "wool", "leather", "arrow", "string", "armor", "weapon", "snow", "recruit", "prisoner"};
	private static String[] settlementDims = 
		{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] buildingColumns = {"building_id", "settlement_id", "owner_id", "class", "resource", "level", "xcoord", "zcoord", "employed"};
	private static String[] buildingDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "TEXT", "INT DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "INT DEFAULT 0"};
	private static String[] tradeColumns = {"trade_id", "settlement1_id", "settlement2_id", "income1", "income2"};
	private static String[] tradeDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] kingdomColumns = {"kingdom_id", "name", "owner_id", "primarycolor", "secondarycolor"};
	private static String[] kingdomDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT UNIQUE", "TEXT", "TEXT", "TEXT"};
	private static String[] playerColumns = {"player_id", "name", "owner_id", "kingdom_id"};
	private static String[] playerDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT UNIQUE", "INT DEFAULT 0", "INT DEFAULT 0"};
	private static String[] unitColumns = {"unit_id", "owner_id", "settlement_id", "class", "xcoord", "zcoord", "health", "experience", "real"};
	private static String[] unitDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "INT DEFAULT 0", 
		"INT DEFAULT 1"};
	private static String[] commandColumns = {"command_id", "unit_id", "command", "target_id", "xcoord", "zcoord"};
	private static String[] commandDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "TEXT", "INT DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] itemColumns = {"item_id", "unit_id", "type", "quantity"};
	private static String[] itemDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0"};
	private static String[] spellColumns = {"spell_id", "settlement_id", "owner_id", "object_id", "object", "class", "power", "duration", "area_of_effect", "xcoord", "zcoord"};
	private static String[] spellDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] productionColumns = {"production_id", "settlement_id", "owner_id", "class", "duration"};
	private static String[] productionDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "INT DEFAULT 0"};
	private static String[] permissionColumns = {"permission_id", "owner_id", "grantee_id", "node", "refer_id"};
	private static String[] permissionDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "INT DEFAULT 0"};
	private static String[] requestColumns = {"request_id", "owner_id", "target_id", "to_admin", "level", "request", "object_name", "object_id", "target_object_id", "xcoord", "zcoord", "timestamp"};
	private static String[] requestDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "INT DEFAULT 0", "INT DEFAULT 1", "TEXT", "TEXT", "INT DEFAULT 0", "INT DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "INTEGER DEFAULT 0"};
//===================Database Setup Complete===================//
			
	private static Dominion plugin;

//--- CONSTRUCTOR AND DEFAULTS ---//
	/** 
	 * <b>DominionDatabaseHandler</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionDatabaseHandler({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionDatabaseHandler} class.
	 * @param instance - The {@link Dominion} plugin this handler will be running on.
	 */
	public DominionDatabaseHandler(Dominion instance, Logger log, String prefix, String name, String location) {
		super(log, prefix, name, location);
		plugin = instance;
	}
	
	/** 
	 * <b>setDefaultTables</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean setDefaultTables()
	 * <br>
	 * <br>
	 * Checks to ensure all default tables exist.  If they do not, they will be created.
	 * @return The sucess of the execution of this command.
	 */
	public boolean setDefaultTables(){
		if(!checkTable("settlement")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the settlement table..");
			createTable("settlement", settlementColumns, settlementDims);
		}
		if(!checkTable("building")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the building table..");
			createTable("building", buildingColumns, buildingDims);
		}
		if(!checkTable("trade")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the trade table..");
			createTable("trade", tradeColumns, tradeDims);
		}
		if(!checkTable("kingdom")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the kingdom table..");
			createTable("kingdom", kingdomColumns, kingdomDims);
		}
		if(!checkTable("player")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the player table..");
			createTable("player", playerColumns, playerDims);
		}
		if(!checkTable("unit")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the unit table..");
			createTable("unit", unitColumns, unitDims);
		}
		if(!checkTable("command")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the command table..");
			createTable("command", commandColumns,commandDims);
		}
		if(!checkTable("spell")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the spell table..");
			createTable("spell", spellColumns, spellDims);
		}
		if(!checkTable("item")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the item table..");
			createTable("item", itemColumns, itemDims);
		}
		if(!checkTable("production")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the production table..");
			createTable("production", productionColumns, productionDims);
		}
		if(!checkTable("permission")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the permission table..");
			createTable("permission", permissionColumns, permissionDims);
		}
		if(!checkTable("request")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the request table..");
			createTable("request", requestColumns, requestDims);
		}
		// - All tables should now exist.
		if(checkTable("settlement") & checkTable("building") & checkTable("trade") & checkTable("kingdom") & checkTable("player") & 
			checkTable("unit") & checkTable("command") & checkTable("spell") & checkTable("item") & checkTable("production") & checkTable("permission") & checkTable("request"))
			return setDefaultColumns();
		return false;
	}
	
	/** 
	 * <b>setDefaultColumns</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean setDefaultColumns()
	 * <br>
	 * <br>
	 * Checks to ensure all default columns exist within their respective tables.  If they do not, they will be created.
	 * @return The sucess of the execution of this command.
	 */
	public boolean setDefaultColumns(){
		String column = "";
		for(int i=0; i < settlementColumns.length;i++){
			column = settlementColumns[i];
			if(!checkColumn("settlement", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("settlement", column, settlementDims[i]);
			}
		}
		for(int i=0; i < buildingColumns.length;i++){
			column = buildingColumns[i];
			if(!checkColumn("building", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("building", column, buildingDims[i]);
			}
		}
		for(int i=0; i < tradeColumns.length;i++){
			column = tradeColumns[i];
			if(!checkColumn("trade", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("trade", column, tradeDims[i]);
			}
		}
		for(int i=0; i < kingdomColumns.length;i++){
			column = kingdomColumns[i];
			if(!checkColumn("kingdom", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("kingdom", column, kingdomDims[i]);
			}
		}
		for(int i=0; i < playerColumns.length;i++){
			column = playerColumns[i];
			if(!checkColumn("player", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("player", column, playerDims[i]);
			}
		}
		for(int i=0; i < unitColumns.length;i++){
			column = unitColumns[i];
			if(!checkColumn("unit", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("unit", column, unitDims[i]);
			}
		}
		for(int i=0; i < commandColumns.length;i++){
			column = commandColumns[i];
			if(!checkColumn("command", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("command", column, commandDims[i]);
			}
		}
		for(int i=0; i < itemColumns.length;i++){
			column = itemColumns[i];
			if(!checkColumn("item", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("item", column, itemDims[i]);
			}
		}
		for(int i=0; i < spellColumns.length;i++){
			column = spellColumns[i];
			if(!checkColumn("spell", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("spell", column, spellDims[i]);
			}
		}
		for(int i=0; i < productionColumns.length;i++){
			column = productionColumns[i];
			if(!checkColumn("production", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("production", column, productionDims[i]);
			}
		}
		for(int i=0; i < permissionColumns.length;i++){
			column = permissionColumns[i];
			if(!checkColumn("permission", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("permission", column, permissionDims[i]);
			}
		}
		for(int i=0; i < requestColumns.length;i++){
			column = requestColumns[i];
			if(!checkColumn("request", column)){
				plugin.getLogger().info("Must create the " + column + " column..");
				createColumn("request", column, requestDims[i]);
			}
		}
		
		return true;
	}

//--- FUNCTIONALITY ---///
	/** 
	 * <b>subtractMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean subtractMaterial({@link String} settlement, {@link String} material, double amount)
	 * <br>
	 * <br>
	 * Subtracts material from a settlement's storage.
	 * @param settlement - The settlement to subtract from.
	 * @param material - The material to subtract.
	 * @param amount - The amount of the material to subtract.
	 * @return The sucess of the execution of this command.
	 */
	public boolean subtractMaterial(String settlement, String material, double amount){
		return operator(settlement, material, amount, "subtract");
	}
	
	/** 
	 * <b>subtractMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean subtractMaterial(int settlement, {@link String} material, double amount)
	 * <br>
	 * <br>
	 * Subtracts material from a settlement's storage.
	 * @param settlement_id - The settlement id to subtract from.
	 * @param material - The material to subtract.
	 * @param amount - The amount of the material to subtract.
	 * @return The sucess of the execution of this command.
	 */
	public boolean subtractMaterial(int settlement_id, String material, double amount){
		return operator(settlement_id, material, amount, "subtract");
	}
	
	/** 
	 * <b>addMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean addMaterial(int settlement_id, {@link String} material, double amount)
	 * <br>
	 * <br>
	 * Deposits material into the settlement's storage.
	 * @param settlement_id - The settlement id to deposit to.
	 * @param material - The material to deposit.
	 * @param amount - The amount of the material to deposit.
	 * @return The sucess of the execution of this command.
	 */
	public boolean addMaterial(int settlement_id, String material, double amount){
		return operator(settlement_id, material, amount, "add");
	}
	
	/** 
	 * <b>addMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean addMaterial({@link String} settlement, {@link String} material, double amount)
	 * <br>
	 * <br>
	 * Deposits material into the settlement's storage.
	 * @param settlement - The settlement to deposit to.
	 * @param material - The material to deposit.
	 * @param amount - The amount of the material to deposit.
	 * @return The sucess of the execution of this command.
	 */
	public boolean addMaterial(String settlement, String material, double amount){
		return operator(settlement, material, amount, "add");
	}
	
	/** 
	 * <b>operator</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean operator({@link String} settlement, {@link String} material, double amount, String operator)
	 * <br>
	 * <br>
	 * Deposits or subtracts material into or from a settlement's storage.  This method is simply for convenience 
	 * of non-repetitive code.
	 * @param settlement - The settlement to reference.
	 * @param material - The material to reference.
	 * @param amount - The amount of the material to reference.
	 * @param operator - The operation to perform on this task.
	 * @return The sucess of the execution of this command.
	 */
	private boolean operator(String settlement, String material, double amount, String operator){		
		return operator(getSettlementId(settlement), material, amount, operator);
	}
	
	/** 
	 * <b>operator</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean operator({@link String} settlement, {@link String} material, double amount, String operator)
	 * <br>
	 * <br>
	 * Deposits or subtracts material into or from a settlement's storage.  This method is simply for convenience 
	 * of non-repetitive code.
	 * @param settlement_id - The settlement to reference.
	 * @param material - The material to reference.
	 * @param amount - The amount of the material to reference.
	 * @param operator - The operation to perform on this task.
	 * @return The sucess of the execution of this command.
	 */
	private boolean operator(int settlement_id, String material, double amount, String operator){
		material = material.toLowerCase();
		if(material == null){
			writeError(plugin.getLogPrefix() + "The material was not specified while trying to subtract a material from a settlement.", true);
			return false;
		}
		if(amount == 0)
			return true;
		ResultSet settlementData = getSettlementData(settlement_id, material);
		try{
			settlementData.next();
			double currentMat = settlementData.getDouble(material);
			settlementData.getStatement().close();
			String query = "";
			if(operator.equalsIgnoreCase("add"))
				query = "UPDATE settlement SET " + material + "=" + (currentMat + amount) + " WHERE settlement_id=\'" + settlement_id + "\'";
			else if(operator.equalsIgnoreCase("subtract"))
				query = "UPDATE settlement SET " + material + "=" + (currentMat - amount) + " WHERE settlement_id=\'" + settlement_id + "\'";
			return queryWithResult(query);
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		} catch (NullPointerException ex){
			plugin.getLogger().info("Null pointer exception while performing an operation with material.");
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/** 
	 * <b>setMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean setMaterial(String settlement, String material, int amount)
	 * <br>
	 * <br>
	 * Sets material in the settlement's storage to an exact value.
	 * @param settlement - The settlement to reference.
	 * @param material - The material to reference.
	 * @param amount - The amount of the material to reference.
	 * @return The sucess of the execution of this command.
	 */
	public boolean setMaterial(String settlement, String material, int amount){
		material = material.toLowerCase();
		if(settlement == null | material == null){
			writeError(plugin.getLogPrefix() + "A value was not specified while trying to subtract a material from the settlement.", true);
			return false;
		}
		if(!settlementExists(settlement)){
			writeError(plugin.getLogPrefix() + "The settlement provided does not exist.", true);
			return false;
		}
		String query = "UPDATE settlement SET " + material + "=" + amount + " WHERE name=\'" + settlement + "\'";
		return queryWithResult(query);
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update({@link String} table, {@link String} column, {@link String} value, {@link String} id_name, int id)
	 * <br>
	 * <br>
	 * Updates a table column to a value.
	 * @param table - The table being referenced.
	 * @param column - The column being referenced.
	 * @param value - The value to change this row's column to.
	 * @param id_name - The name of the column used to identify the row.
	 * @param id - The id being referenced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean update(String table, String column, String value, String id_name, int id){
		table = table.toLowerCase();
		column = column.toLowerCase();
		if(table.equalsIgnoreCase("kingdom")){
			if(!kingdomExists(getKingdomName(id))){
				writeError(plugin.getLogPrefix() + "The kingdom does not exist.", true);
				return false;
			}
		}
		if(table.equalsIgnoreCase("settlement")){
			if(!settlementExists(getSettlementName(id))){
				writeError(plugin.getLogPrefix() + "The settlement does not exist.", true);
				return false;
			}
		}
		String query = "UPDATE " + table + " SET " + column + "=\'" + value + "\' WHERE " + id_name + "=" + id;
		return queryWithResult(query);
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update({@link String} table, {@link String} column, int value, {@link String} id_name, int id)
	 * <br>
	 * <br>
	 * Updates a table column to a value.
	 * @param table - The table being referenced.
	 * @param column - The column being referenced.
	 * @param value - The value to change this row's column to.
	 * @param id_name - The name of the column used to identify the row.
	 * @param id - The id being referenced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean update(String table, String column, int value, String id_name, int id){
		table = table.toLowerCase();
		column = column.toLowerCase();
		// - Technically, these checks aren't necessary, but I rather stop it here in case someone uses it wrong.
		if(table.equalsIgnoreCase("kingdom")){
			if(!kingdomExists(getKingdomName(id))){
				writeError(plugin.getLogPrefix() + "The kingdom does not exist.", true);
				return false;
			}
		}
		if(table.equalsIgnoreCase("settlement")){
			if(!settlementExists(getSettlementName(id))){
				writeError(plugin.getLogPrefix() + "The settlement does not exist.", true);
				return false;
			}
		}
		String query = "UPDATE " + table + " SET " + column + "=" + value + " WHERE " + id_name + "=" + id;
		return queryWithResult(query);
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update({@link String} table, {@link String} column, double value, {@link String} id_name, int id)
	 * <br>
	 * <br>
	 * Updates a table column to a value.
	 * @param table - The table being referenced.
	 * @param column - The column being referenced.
	 * @param value - The value to change this row's column to.
	 * @param id_name - The name of the column used to identify the row.
	 * @param id - The id being referenced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean update(String table, String column, double value, String id_name, int id){
		table = table.toLowerCase();
		column = column.toLowerCase();
		// - Technically, these checks aren't necessary, but I rather stop it here in case someone uses it wrong.
		if(table.equalsIgnoreCase("kingdom")){
			if(!kingdomExists(getKingdomName(id))){
				writeError(plugin.getLogPrefix() + "The kingdom does not exist.", true);
				return false;
			}
		}
		if(table.equalsIgnoreCase("settlement")){
			if(!settlementExists(getSettlementName(id))){
				writeError(plugin.getLogPrefix() + "The settlement does not exist.", true);
				return false;
			}
		}
		String query = "UPDATE " + table + " SET " + column + "=" + value + " WHERE " + id_name + "=" + id;
		return queryWithResult(query);
	}
	
	/** 
	 * <b>update</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean update({@link String} table, {@link String} column, {@link String} value, {@link String} id_name, {@link String} id)
	 * <br>
	 * <br>
	 * Updates a table column to a value.
	 * @param table - The table being referenced.
	 * @param column - The column being referenced.
	 * @param value - The value to change this row's column to.
	 * @param id_name - The name of the column used to identify the row.
	 * @param id - The id being referenced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean update(String table, String column, String value, String id_name, String id){
		table = table.toLowerCase();
		column = column.toLowerCase();
		String query = "UPDATE " + table + " SET " + column + "=\'" + value + "\' WHERE " + id_name + "=\'" + id + "'";
		return queryWithResult(query);
	}
	
	/** 
	 * <b>remove</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean remove({@link String} entity, int id)
	 * <br>
	 * <br>
	 * 
	 * @param entity - The entity type.  Should be a table name.
	 * @param id - The id number of the entity to be removed.
	 * @return The sucess of the execution of this command.
	 */
	public boolean remove(String entity, int id){
		String query = "DELETE FROM " + entity + " WHERE " + entity + "_id" + "=" + id;
		return queryWithResult(query);
	}

	/** 
	 * <b>createKingdom</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createKingdom(String name)
	 * <br>
	 * <br>
	 * Creates a kingdom in the database with default values and the specified name.
	 * @param name - The name of the new kingdom.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createKingdom(String name){
		return createKingdom(name, 0, "", "");
	}
	/** 
	 * <b>createKingdom</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createKingdom(String name, int monarch, String primaryColor, String secondaryColor)
	 * <br>
	 * <br>
	 * Creates a kingdom in the database with all values specified.
	 * @param name - The name of the new kingdom.
	 * @param monarch - The player_id of the ruler of this kingdom.
	 * @param primaryColor - The primary color attributed to this kingdom.
	 * @param secondaryColor - The secondary color attributed to this kingdom.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createKingdom(String name, int monarch, String primaryColor, String secondaryColor){
		if(name == "" | name == null)
			return false;
		String query = "INSERT INTO kingdom(name,owner_id,primarycolor,secondarycolor) VALUES (\'" + name + "\'," + monarch + ",\'" + primaryColor + "\',\'" + secondaryColor + "\')";
		return queryWithResult(query);
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
		String query = "INSERT INTO settlement(name,owner_id,biome,class,xcoord,zcoord) VALUES (\'" + name + "\'," + ownerId + ",\'" + biome + "\',\'" + classification + "\'," + xcoord + "," + zcoord +")";
		return queryWithResult(query);
	}

	/** 
	 * <b>createPlayer</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createPlayer(String name)
	 * <br>
	 * <br>
	 * Creates a player in the database with default values and the specified name.
	 * @param name - The name of the new player.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createPlayer(String name){
		return createPlayer(name, 0);
	}
	
	/** 
	 * <b>createPlayer</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createPlayer(String name, String liege)
	 * <br>
	 * <br>
	 * Creates a player in the database with specified values.
	 * @param name - The name of the new player.
	 * @param liege - The player_id of the liege lord of this player.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createPlayer(String name, int liege){
		if(name == "" | name == null)
			return false;
		String query = "INSERT INTO player(name, owner_id) VALUES (\'" + name + "\'," + liege + ")";
		if(queryWithResult(query)){
			plugin.getLogger().info("Player \"" + name + "\" has been added to the player table.");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuilding(int settlementId, int owner, {@link String} classification, int level, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlementId - The settlement id this building will be associated with.
	 * @param owner - The id of the player that will own this building.
	 * @param classification - The classification of the building.
	 * @param level - The starting level of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(int settlementId, int owner, String classification, int level, double xcoord, double zcoord){
		String query = "INSERT INTO building(settlement_id, owner_id, class, level, xcoord, zcoord) VALUES (" + settlementId + "," + owner + ",\'" + classification + "\'," + level + "," + xcoord +"," + zcoord +")";
		if(queryWithResult(query)){
			plugin.getLogger().info("A new " + classification + " building has been created in " + settlementId + " at x-" + xcoord + " z-" + zcoord + ".");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createProduction({@link String} settlement, {@link String} classification, int ownerId)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.  The duration of the production will be automatically calculated and 
	 * the owner will be considered the current owner of the settlement.
	 * @param settlement - The settlement this unit is being built in.
	 * @param classification - The classification of the unit being produced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createProduction(String settlement, String classification){
		int ownerId = getOwnerId("settlement", getSettlementId(settlement));
		return createProduction(settlement, classification, ownerId);
	}
	
	/** 
	 * <b>createProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createProduction({@link String} settlement, {@link String} classification, int ownerId)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.  The duration of the production will be automatically calculated.
	 * @param settlement - The settlement this unit is being built in.
	 * @param classification - The classification of the unit being produced.
	 * @param ownerId - The id of the player that will own this unit upon its completion.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createProduction(String settlement, String classification, int ownerId){
		int duration = plugin.getUnitManager().getUnit(classification).getTrainingTime();
		return createProduction(settlement, classification, ownerId, duration);
	}
	
	/** 
	 * <b>createProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createProduction({@link String} settlement, {@link String} classification, int ownerId, int duration)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.
	 * @param settlement - The settlement this unit is being built in.
	 * @param classification - The classification of the unit being produced.
	 * @param ownerId - The id of the player that will own this unit upon its completion.
	 * @param duration - The number of unit ticks that this production will take to complete.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createProduction(String settlement, String classification, int ownerId, int duration){
		int settlementId = getSettlementId(settlement);
		String query = "INSERT INTO production(settlement_id, owner_id, class, duration) VALUES (" + settlementId + "," + ownerId + ",\'" + classification + "\'," + duration + ")";
		if(queryWithResult(query)){
			plugin.getLogger().info("A new " + classification + " unit is being built in " + settlement + ", owned by " + getPlayerName(ownerId) + ", and will take " + duration + " ticks to complete.");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createCommand(int unitId, {@link String} command)
	 * <br>
	 * <br>
	 * Creates a command for a unit in the database with default values.
	 * @param unitId - The id of the unit.
	 * @param command - The type of command.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createCommand(int unitId, String command){
		return createCommand(unitId, command, 0, 0, 0);
	}
	
	/** 
	 * <b>createCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createCommand(int unitId, {@link String} command, int targetId)
	 * <br>
	 * <br>
	 * Creates a command for a unit in the database with specified values.
	 * @param unitId - The id of the unit.
	 * @param command - The type of command.
	 * @param targetId - The id of the afflicted item, if any.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createCommand(int unitId, String command, int targetId){
		return createCommand(unitId, command, targetId, 0, 0);
	}
	
	/** 
	 * <b>createCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createCommand(int unitId, {@link String} command, double xCoord, double zCoord))
	 * <br>
	 * <br>
	 * Creates a command for a unit in the database with specified values.
	 * @param unitId - The id of the unit.
	 * @param command - The type of command.
	 * @param xCoord - The x coordinate this command may be referencing.
	 * @param zCoord - The z coordinate this command may be referencing.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createCommand(int unitId, String command, double xCoord, double zCoord){
		return createCommand(unitId, command, 0, xCoord, zCoord);
	}
	
	/** 
	 * <b>createCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createCommand(int unitId, {@link String} command, int targetId, double xCoord, double zCoord)
	 * <br>
	 * <br>
	 * Creates a command for a unit in the database with specified values.
	 * @param unitId - The id of the unit.
	 * @param command - The type of command.
	 * @param targetId - The id of the afflicted item, if any.
	 * @param xCoord - The x coordinate this command may be referencing.
	 * @param zCoord - The z coordinate this command may be referencing.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createCommand(int unitId, String command, int targetId, double xCoord, double zCoord){
		String query = "INSERT INTO command(unit_id, command, target_id, xcoord, zcoord) VALUES (" + unitId + ",\"" + command + "\"," + targetId + "," + xCoord + "," + zCoord + ")";
		if(queryWithResult(query)){
			if(command.equalsIgnoreCase("move"))
				plugin.getLogger().info("A new move command has been issued:  Unit #" + unitId + " to x:" + xCoord + " z:" + zCoord);
			else if (command.equalsIgnoreCase("attack"))
				plugin.getLogger().info("A new attack command has been issued:  Unit #" + unitId + " is to attack unit #" + targetId);
			else
				plugin.getLogger().info("A new " + command + " command has been issued.");
			return true;
		} else {
			plugin.getLogger().info("Failed to create a command.");
			return false;
		}
	}
	
	/** 
	 * <b>createUnit</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createUnit({@link String} settlement, {@link String} classification, int ownerId, double xcoord, double zcoord, double health, int experience)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.
	 * @param settlement - The settlement this unit is being built in.
	 * @param classification - The classification of the unit.
	 * @param ownerId - The id of the player that will own this unit upon its completion.
	 * @param xcoord - The x coordinate the unit will be placed upon.
	 * @param zcoord - The z coordinate the unit will be placed upon.
	 * @param health - The starting health this unit will have.
	 * @param experience - The starting experience this unit will have.
	 * @param real - True if the unit is real; false if it is not.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createUnit(String settlement, String classification, int ownerId, double xcoord, double zcoord, double health, int experience, boolean real){
		int settlementId = getSettlementId(settlement);
		return createUnit(settlementId, classification, ownerId, xcoord, zcoord, health, experience, real);
	}
	
	/** 
	 * <b>createUnit</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createUnit(int settlementId, {@link String} classification, int ownerId, double xcoord, double zcoord, double health, int experience)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.
	 * @param settlementId - The settlement this unit is being built in.
	 * @param classification - The classification of the unit.
	 * @param ownerId - The id of the player that will own this unit upon its completion.
	 * @param xcoord - The x coordinate the unit will be placed upon.
	 * @param zcoord - The z coordinate the unit will be placed upon.
	 * @param health - The starting health this unit will have.
	 * @param experience - The starting experience this unit will have.
	 * @param real - True if the unit is real; false if it is not.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createUnit(int settlementId, String classification, int ownerId, double xcoord, double zcoord, double health, int experience, boolean real){
		int isReal = 1;
		if(!real)
			isReal = 0;
		String query = "INSERT INTO unit(owner_id, settlement_id, class, xcoord, zcoord, health, experience, real) VALUES (" + ownerId + "," + settlementId + ",\'" + 
	classification + "\'," + xcoord + "," + zcoord + "," + health + "," + experience + ", " + isReal + ")";
		if(queryWithResult(query)){
			plugin.getLogger().info("A new " + classification + " owned by " + getPlayerName(ownerId) + " has been created at x:" + xcoord + " z:" + zcoord + " with " + health + " health.");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createItem</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createItem(int unitId, {@link String} type, double quantity)
	 * <br>
	 * <br>
	 * Creates a production in the database with specified values.
	 * @param unitId - The id of the unit this item will reside upon.
	 * @param type - The type of the item.
	 * @param quantity - The quantity of this item.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createItem(int unitId, String type, double quantity){
		String query = "INSERT INTO item(unit_id, type, quantity) VALUES (" + unitId + ", \'" + type + "\', " + quantity + ")";
		if(queryWithResult(query)){
			plugin.getLogger().info("A new " + type + " x(" + quantity + ") item has been created on unit #" + unitId + ".");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createSpell</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createSpell(int settlementId, int casterId, int targetId, {@link String} object, {@link String} effect, double power, double duration)
	 * <br>
	 * <br>
	 * Creates a spell in the database with specified values and default values for area of effect, xcoord, and zcoord.
	 * @param settlementId - The id of the settlement casting the spell.
	 * @param casterid - The player id casting the spell.
	 * @param targetId - The id of the target entity for the spell to affect.
	 * @param object - The type of entity (kingdom, settlement, building, player, unit, etc.)
	 * @param effect - The type of spell effect this is.
	 * @param duration - The number of spell ticks this spell will last.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createSpell(int settlementId, int casterId, int targetId, String object, String effect, double power, double duration){
		return createSpell(settlementId, casterId, targetId, object, effect, power, duration, 0, 0, 0);
	}
	
	/** 
	 * <b>createSpell</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createSpell(int settlementId, int casterId, int targetId, {@link String} object, {@link String} effect, double power, double duration)
	 * <br>
	 * <br>
	 * Creates a spell in the database with specified values.
	 * @param settlementId - The id of the settlement casting the spell.
	 * @param casterid - The player id casting the spell.
	 * @param targetId - The id of the target entity for the spell to affect.
	 * @param object - The type of entity (kingdom, settlement, building, player, unit, etc.)
	 * @param effect - The type of spell effect this is.
	 * @param duration - The number of spell ticks this spell will last.
	 * @param areaOfEffect - The radius distance this spell's range will affect from its target location.
	 * @param xCoord - The x-coordinate the spell will be cast upon.
	 * @param zCoord - The z-coordinate the spell will be cast upon.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createSpell(int settlementId, int casterId, int targetId, String object, String effect, double power, double duration, double areaOfEffect, double xCoord, double zCoord) {
		String query = "INSERT INTO spell(settlement_id, owner_id, object_id, object, class, power, duration, area_of_effect, xcoord, zcoord) VALUES (" + settlementId + ", " + casterId + ", " + targetId + 
				", \'" + object + "\', \'" + effect + "\', " + power + ", "+ duration + ", " + areaOfEffect +", " + xCoord + ", " + zCoord + ")";
		if(queryWithResult(query)){
			String target = object + " #" + targetId;
			if(object.equalsIgnoreCase("settlement"))
				target = getSettlementName(targetId);
			else if (object.equalsIgnoreCase("player"))
				target = getPlayerName(targetId);
			else if (object.equalsIgnoreCase("kingdom"))
				target = getKingdomName(targetId);
			plugin.getLogger().info(getPlayerName(casterId) + " has cast a new " + effect + " spell from " + getSettlementName(settlementId) + ", lasting " + duration + " ticks and targeting " + target + ".");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createTrade</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createTrade(int settlement1Id, int settlement2Id, double income1, double income2);)
	 * <br>
	 * <br>
	 * Creates a trade in the database with specified values.
	 * @param settlement1Id - The id of the first settlement.
	 * @param settlement2Id - The id of the second settlement.
	 * @param income1 - The income value settlement 1 will receive for this trade.
	 * @param income2 - The income value settlement 2 will receive for this trade.
	 * @return True if the trade has been created; false if it has not.
	 */
	public boolean createTrade(int settlement1Id, int settlement2Id, double income1, double income2){
		String query = "INSERT INTO trade(settlement1_id, settlement2_id, income1, income2) VALUES (" + settlement1Id + ", " + settlement2Id + ", " + income1 + ", " + income2 + ")";
		if(queryWithResult(query)){
			String settlement1 = getSettlementName(settlement1Id), settlement2 = getSettlementName(settlement2Id);
			plugin.getLogger().info("Trade has been initiated between " + settlement1 + " and " + settlement2 + " with a trade value of " + income1 + " and " + income2 + " respectively.");
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createPermission</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createPermission(int ownerId, int granteeId, {@link String} node, int referId)
	 * <br>
	 * <br>
	 * Creates a permission in the database with specified values.
	 * @param ownerId - The id of the player granting the permission.
	 * @param granteeId - The Id of the player being given the permission.
	 * @param node - The permission node being granted.
	 * @param referId - The Id of the object being referenced if the node is referencing an object.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createPermission(int ownerId, int granteeId, String node, int referId){
		String query = "INSERT INTO permission(owner_id, grantee_id, node, refer_id) VALUES (" + ownerId + ", " + granteeId + ", \'" + node + "\', " + referId + ")";
		if(queryWithResult(query)){
			String owner = getPlayerName(ownerId),
					grantee = getPlayerName(granteeId), 
					message = owner + " granted \"" + node + "\" permission ";
			if(referId > 0)
				message += "for object " + referId + " ";
			message += "to " + grantee + ".";
			plugin.getLogger().info(message);
			return true;
		}
		return false;
	}
	
	/** 
	 * <b>createRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, {@link String} request, {@link String} objectName, int objectId, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a request in the database with specified values.
	 * @param ownerId - The id of the player that started this request.
	 * @param targetId - The id of the player this request is being sent to.
	 * @param toAdmin - True if this request is to all admins; false if it is not.
	 * @param level - For building requests, the alleged level of the building.
	 * @param request - The type of request.
	 * @param objectName - The name of the object to be added.
	 * @param objectId - The associated id of the object being referenced if one is being referenced.
	 * @param targetObjectId - The associated id of the target object being referenced if one is being referenced.
	 * @param xcoord - The x-coordinate of the object being referenced if one is being referenced.
	 * @param zcoord - The z-coordinate of the object being referenced if one is being referenced.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, String objectName, int objectId, int targetObjectId, double xcoord, double zcoord){
		int toAdmins = 0;
		if(toAdmin)
			toAdmins = 1;
		long now = new Date().getTime();
		String query = "INSERT INTO request(owner_id, target_id, to_admin, level, request, object_name, object_id, target_object_id, xcoord, zcoord, timestamp) VALUES (" + ownerId + 
				", " + targetId + ", " + toAdmins + ", " + level + ", \'" + request + "\', \'" + objectName + "\', " +objectId + ", " + targetObjectId + ", " + xcoord + ", " + zcoord + ", " + now + ")";
		if(queryWithResult(query)){
			String owner = getPlayerName(ownerId),
					target = getPlayerName(targetId);
			if(toAdmin)
				target = "admins";
			String message = owner + " has requested \"" + request + "\" to " + target + ".  Object Name: " + objectName + "  Object Id: " + objectId + "  Target Object Id: " + targetObjectId + 
					"  Level: " + level + "  x: " + xcoord + "  z:" + zcoord;
			plugin.getLogger().info(message);
			return true;
		}
		return false;
	}
//--- CHECK EXISTENCE OF OBJECT ---//
	/** 
	 * <b>playerExists</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean playerExists(String name)
	 * <br>
	 * <br>
	 * Checks to see if a player exists.
	 * @param name - The name of the player.
	 * @return True if the player exists; false if they do not.
	 */
	public boolean playerExists(String name){
		return thingExists(name, "player");
	}
	
	/** 
	 * <b>settlementExists</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean settlementExists(String name)
	 * <br>
	 * <br>
	 * Checks to see if a settlement exists.
	 * @param name - The name of the settlement.
	 * @return True if the settlement exists; false if it does not.
	 */
	public boolean settlementExists(String name){
		return thingExists(name, "settlement");
	}
	
	/** 
	 * <b>kingdomExists</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean kingdomExists(String name)
	 * <br>
	 * <br>
	 * Checks to see if a kingdom exists.
	 * @param name - The name of the kingdom.
	 * @return True if the kingdom exists; false if it does not.
	 */
	public boolean kingdomExists(String name){
		return thingExists(name, "kingdom");
	}
	
	/** 
	 * <b>thingExists</b><br>
	 * <br>
	 * &nbsp;&nbsp;private boolean thingExists({@link String} name, {@link String} table)
	 * <br>
	 * <br>
	 * Checks to see if a thing exists in a table using its name to identify it.
	 * @param name - The name of the thing.
	 * @param table - The name of the database table to check.
	 * @return True if the thing exists; false if it does not.
	 */
	private boolean thingExists(String name, String table){
		Statement statement = null;
		if(name == "" | name == null){
			writeError(plugin.getLogPrefix() + "Check failed to find the " + table + " due to the name not being specified.", true);
			return false;
		}
		try{
			statement = connection.createStatement();
			String query = "SELECT name FROM " + table + " WHERE name=\'" + name + "\'";
			ResultSet results = statement.executeQuery(query);
			if (results.next()){
				statement.close();
				return true;
			}
			statement.close();
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		return false;
	}
	
	/** 
	 * <b>thingExists</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean thingExists(int id, {@link String} table)
	 * <br>
	 * <br>
	 * Checks to see if a thing exists in a table using its name to identify it.
	 * @param name - The name of the thing.
	 * @param table - The name of the database table to check.
	 * @return True if the thing exists; false if it does not.
	 */
	public boolean thingExists(int id, String table){
		boolean success = false;
		String identifier = table + "_id";
		ResultSet entity = getTableData(table, identifier, identifier + "=" + id);
		try{
			if(entity.next())
				success = true;
			entity.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error while attempting to check if a " + table + " existed.");
			ex.printStackTrace();
		}
		return success;
	}

//--- ACCESSORS ---//
//--- Getting data from a table ---//
	/** 
	 * <b>getKingdomData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getKingdomData(int kingdom_id, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the kingdom table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param kingdom_id - The ID of the kingdom.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getKingdomData(int kingdom_id, String column){
		return getTableData("kingdom", kingdom_id, column, "kingdom_id");
	}
	
	/** 
	 * <b>getKingdomData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getKingdomData({@link String} name, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the kingdom table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param name - The name of the kingdom.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getKingdomData(String name, String column){
		return getTableData("kingdom", getKingdomId(name), column, "kingdom_id");
	}
	
	/** 
	 * <b>getSettlementData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getSettlementData(int settlement_id, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the settlement table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param settlement_id - The ID of the settlement.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getSettlementData(int settlement_id, String column){
		return getTableData("settlement", settlement_id, column, "settlement_id");
	}
	
	/** 
	 * <b>getSettlementData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getSettlementData({@link String} name, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the settlement table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param name - The name of the settlement.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getSettlementData(String name, String column){
		return getTableData("settlement", getSettlementId(name), column, "settlement_id");
	}
	
	/** 
	 * <b>getPlayerData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getPlayerData(int player_id, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the player table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param player_id - The ID of the player.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getPlayerData(int player_id, String column){
		return getTableData("player", player_id, column, "player_id");
	}
	
	/** 
	 * <b>getPlayerData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getPlayerData({@link String} name, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the player table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param name - The name of the player.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getPlayerData(String name, String column){
		return getTableData("player", getPlayerId(name), column, "player_id");
	}
	
	/** 
	 * <b>getBuildingData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getBuildingData(int id, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the building table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param id - The id of the building.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getBuildingData(int id, String column){
		return getTableData("building", id, column, "building_id");
	}
	
	/** 
	 * <b>getUnitData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getUnitData(int id, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from the unit table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param id - The id of the unit.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getUnitData(int id, String column){
		return getTableData("unit", id, column, "unit_id");
	}
	
	/** 
	 * <b>getTableData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getTableData({@link String} table, int id, {@link String} column, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets the column data from a table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param table - The name of the table to reference.
	 * @param id - The id number of the thing you want to find.
	 * @param column - The column to reference.
	 * @param idName - The name of the identifying column.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getTableData(String table, int id, String column, String idName){
		String query = "SELECT " + column + " FROM " + table + " WHERE " + idName + "=" + id;
		return  querySelect(query);
	}
	
	/** 
	 * <b>getTableData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getTableData({@link String} table, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from a table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getTableData(String table, String column){
		String query = "SELECT " + column + " FROM " + table;
		return  querySelect(query);
	}
	
	/** 
	 * <b>getTableData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getTableData({@link String} table, {@link String} column, {@link String} where)
	 * <br>
	 * <br>
	 * Gets the column data from a table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @param where - The exact SQL where clause without the "where" part.  "settlement_id=1" for example.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getTableData(String table, String column, String where){
		String query = "SELECT " + column + " FROM " + table + " WHERE " + where;
		return  querySelect(query);
	}
	
	/** 
	 * <b>getSingleColumnInt</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSingleColumnInt({@link String} table, {@link String} column, int id, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets one entry from one column in a table.  Do not use "*".
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @param id - The id of the object.
	 * @param idName - The column that the identifier is referring to.
	 * @return The results if there are any.  0 if there are not.
	 */
	public int getSingleColumnInt(String table, String column, int id, String idName){
		if(column.equalsIgnoreCase("*"))
			return 0;
		int returnInt = 0;
		ResultSet request = getTableData(table, id, column, idName);
		try{
			if(request.next())
				returnInt = request.getInt(column);
			request.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the " + column + " of a " + table + ".");
			ex.printStackTrace();
		}
		return returnInt;
	}
	
	/** 
	 * <b>getSingleColumnDouble</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSingleColumnDouble({@link String} table, {@link String} column, int id, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets one entry from one column in a table.  Do not use "*".
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @param id - The id of the object.
	 * @param idName - The column that the identifier is referring to.
	 * @return The results if there are any.  0 if there are not.
	 */
	public double getSingleColumnDouble(String table, String column, int id, String idName){
		if(column.equalsIgnoreCase("*"))
			return 0;
		double returnDouble = 0;
		ResultSet request = getTableData(table, id, column, idName);
		try{
			if(request.next())
				returnDouble = request.getDouble(column);
			request.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the " + column + " of a " + table + ".");
			ex.printStackTrace();
		}
		return returnDouble;
	}
	
	/** 
	 * <b>getSingleColumnString</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSingleColumnString({@link String} table, {@link String} column, int id, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets one entry from one column in a table.  Do not use "*".
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @param id - The id of the object.
	 * @param idName - The column that the identifier is referring to.
	 * @return The results if there are any.  Null if there are not.
	 */
	public String getSingleColumnString(String table, String column, int id, String idName){
		if(column.equalsIgnoreCase("*"))
			return null;
		String returnString = null;
		ResultSet request = getTableData(table, id, column, idName);
		try{
			if(request.next())
				returnString = request.getString(column);
			request.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error attempting to get the " + column + " of a " + table + ".");
			ex.printStackTrace();
		}
		return returnString;
	}
	
	/** 
	 * <b>getAllIds</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int[] getAllIds({@link String} table)
	 * <br>
	 * <br>
	 * @param table - The name of the table to reference.
	 * @return The ids if there are any.  Null if there are not.
	 */
	public int[] getAllIds(String table){
		String identifier = table + "_id";
		ResultSet entity = getTableData(table, identifier);
		ArrayList<Integer> list = new ArrayList<Integer>();
		try{
			while(entity.next()){
				list.add(new Integer(entity.getInt(identifier)));
			}
			entity.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ids = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        ids[i++] = n;
	    }
		
		return ids;
	}
	
	public int[] getSpecificIds(String table, int id, String column){
		String identifier = table + "_id";
		ResultSet entity = this.getTableData(table, id, identifier, column);
		ArrayList<Integer> list = new ArrayList<Integer>();
		try{
			while(entity.next()){
				list.add(new Integer(entity.getInt(identifier)));
			}
			entity.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ids = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        ids[i++] = n;
	    }
		
		return ids;
	}
	
	public int[] getSpecificIds(String table, int id, String column, int[] set){
		String identifier = table + "_id";
		ResultSet entity = this.getTableData(table, id, identifier, column);
		ArrayList<Integer> list = new ArrayList<Integer>();
		int identity = 0;
		try{
			while(entity.next()){
				identity = entity.getInt(identifier);
				for(int i: set){
					if(identity == i)
						list.add(new Integer(identity));
				}
			}
			entity.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ids = new int[list.size()];
		int i = 0;
	    for (Integer n : list) {
	        ids[i++] = n;
	    }
		
		return ids;
	}
	
//--- Getting names from an id ---//
	/** 
	 * <b>getKingdomName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getKingdomName(int k_id)
	 * <br>
	 * <br>
	 * Gets the name of a kingdom via referencing the id number.
	 * @param k_id - The id of the kingdom.
	 * @return The kingdom name if there is one.  Null if there is not.
	 */
	public String getKingdomName(int k_id){
		return getThingName(k_id, "kingdom", "kingdom_id");
	}
	/** 
	 * <b>getSettlementName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getSettlementName(int s_id)
	 * <br>
	 * <br>
	 * Gets the name of a settlement via referencing the id number.
	 * @param s_id - The id of the settlement.
	 * @return The settlement name if there is one.  Null if there is not.
	 */
	public String getSettlementName(int s_id){
		return getThingName(s_id, "settlement", "settlement_id");
	}
	/** 
	 * <b>getPlayerName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getPlayerName(int p_id)
	 * <br>
	 * <br>
	 * Gets the name of a player via referencing the id number.
	 * @param p_id - The id of the player.
	 * @return The player name if there is one.  Null if there is not.
	 */
	public String getPlayerName(int p_id){
		return getThingName(p_id, "player", "player_id");
	}
	/** 
	 * <b>getThingName</b><br>
	 * <br>
	 * &nbsp;&nbsp;private {@link String} getThingName(int  id, {@link String} table, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets the name of a thing via referencing the id number.
	 * @param id - The id of the thing.
	 * @param table - The name of the table to reference.
	 * @param idName - The name of the identifying column.
	 * @return The thing's name if there is one.  Null if there is not.
	 */
	private String getThingName(int id, String table, String idName){
		String query = "SELECT name FROM " + table + " WHERE " + idName + "=\'" + id + "\'";
		ResultSet results = querySelect(query);
		try{
			if(results.next()){
				String name = results.getString("name");
				results.getStatement().close();
				return name;
			}
			results.getStatement().close();
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		return null;
	}

//--- Getting ids from a name ---//
	/** 
	 * <b>getKingdomId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getKingdomId({@link String} name)
	 * <br>
	 * <br>
	 * Gets the id of a kingdom via referencing its name.
	 * @param name - The name of the kingdom.
	 * @return The id number if there is one.  -1 if there is not.
	 */
	public int getKingdomId(String name){
		return getThingId(name, "kingdom", "kingdom_id");
	}
	
	/** 
	 * <b>getSettlementId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getSettlmentId({@link String} name)
	 * <br>
	 * <br>
	 * Gets the id of a settlement via referencing its name.
	 * @param name - The name of the settlement.
	 * @return The id number if there is one.  -1 if there is not.
	 */
	public int getSettlementId(String name){
		return getThingId(name, "settlement", "settlement_id");
	}
	
	/** 
	 * <b>getPlayerId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getPlayerId({@link String} name)
	 * <br>
	 * <br>
	 * Gets the id of a player via referencing its name.
	 * @param name - The name of the player.
	 * @return The id number if there is one.  -1 if there is not.
	 */
	public int getPlayerId(String name){
		return getThingId(name, "player", "player_id");
	}
	
	/** 
	 * <b>getThingId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getThingId({@link String} name, {@link String} table, {@link String} idName)
	 * <br>
	 * <br>
	 * Gets the id of a thing via referencing its name.
	 * @param name - The name of the thing.
	 * @param table - The name of the table to reference.
	 * @param idName - The name of the column to select.
	 * @return The id number if there is one.  -1 if there is not.
	 */
	private int getThingId(String name, String table, String idName){
		String query = "SELECT " + idName + " FROM " + table + " WHERE name=\'" + name +"'";
		ResultSet results = querySelect(query);
		try {
			if(results.next()){
				int id = results.getInt(idName);
				results.getStatement().close();
				return id;
			}
			results.getStatement().close();
		} catch (SQLException ex) {
			writeError(ex.getMessage(), true);
		}
		return -1;
	}
	
	/**
	 * <b>getLiegeName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getLiegeName({@link String} name)
	 * <br>
	 * <br>
	 * Gets the name of the liege player of the selected player.
	 * @param name - The player to find the liege lord for.
	 * @return The name of the liege lord if there is one.  Null if there is not.
	 */
	public String getLiegeName(String name){
		String query = "SELECT name FROM player WHERE name=\'" + name +"'";
		ResultSet results = querySelect(query);
		try{
			if(results.next()){
				int liege = results.getInt("owner_id");
				results.getStatement().close();
				return getPlayerName(liege);
			}
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		return null;
	}
	
	/**
	 * <b>getLiegeId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getLiegeId({@link String} name)
	 * <br>
	 * <br>
	 * Gets the id of the liege player of the selected player.
	 * @param name - The player to find the liege lord for.
	 * @return The id of the liege lord if there is one. -1 if there is not.
	 */
	public int getLiegeId(String name){
		String query = "SELECT name FROM player WHERE name=\'" + name +"'";
		ResultSet results = querySelect(query);
		try{
			if(results.next()){
				int liege = results.getInt("owner_id");
				results.getStatement().close();
				return liege;
			}
		} catch (SQLException ex){
			plugin.getLogger().info("Error while attempting to retrieve an owner id of a player.");
			ex.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * <b>getOwnerName</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getOwnername({@link String} table, int id)
	 * <br>
	 * <br>
	 * @param entity - The entity being accessed.
	 * @param id - The row identifier.
	 * @return The name of the owner of this entity if there is one.
	 */
	public String getOwnerName(String entity, int id){
		return getPlayerName(getOwnerId(entity, id));
	}
	
	/**
	 * <b>getOwnerId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getOwnerId({@link String} table, int id)
	 * <br>
	 * <br>
	 * Gets the id of the player that owns this entity.
	 * @param entity - The entity being accessed.
	 * @param id - The row identifier.
	 * @return The id of the owner of this entity if there is one. -1 if there is not.
	 */
	public int getOwnerId(String entity, int id){
		if(entity == null | entity == "" | id <= 0)
			return -1;
		String query = "SELECT owner_id FROM " + entity + " WHERE " + entity + "_id=" + id;
		ResultSet results = querySelect(query);
		try{
			if(results.next()){
				int ownerId = results.getInt("owner_id");
				results.getStatement().close();
				return ownerId;
			}
		} catch (SQLException ex){
			plugin.getLogger().info("Error while attempting to retrieve an owner id.");
			ex.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * <b>getNewestId</b><br>
	 * <br>
	 * &nbsp;&nbsp;public int getNewestId({@link String} table)
	 * <br>
	 * <br>
	 * @param table - The table in the database.
	 * @return The highest id in the table.
	 */
	public int getNewestId(String table){
		if(table == null | table == "")
			return -1;
		String tableIdName = table + "_id";
		String query = "SELECT " + tableIdName + " FROM " + table;
		ResultSet results = querySelect(query);
		int newestId = -1, thisId = 0;
		try{
			while(results.next()){
				thisId = results.getInt(tableIdName);
				if(thisId > newestId)
					newestId = thisId;
			}
			results.getStatement().close();
		} catch (SQLException ex){
			plugin.getLogger().info("Error while attempting to retrieve the highest id in a table.");
			ex.printStackTrace();
		}
		return newestId;
	}
}
