package com.pico52.dominion.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		{"settlement_id", "name", "lord_id", "kingdom_id", "biome", "xcoord", "zcoord", "class", "wall", "mana", "population", "wealth", "food", "wood", "cobblestone", "stone", "sand", 
		"gravel", "dirt", "iron_ingot", "iron_ore", "emerald", "emerald_ore", "gold_ingot", "gold_ore", "flint", "feather", "lapis_ore", "diamond", "obsidian", "netherrack", "nether_brick", "redstone", "brick", 
		"glowstone", "clay", "coal", "wool", "leather", "arrow", "armor", "weapon", "snow", "recruit", "prisoner"};
	private static String[] settlementDims = 
		{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] buildingColumns = {"building_id", "settlement_id", "owner_id", "class", "resource", "level", "xcoord", "zcoord", "employed"};
	private static String[] buildingDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "TEXT", "INT DEFAULT 0", "DOUBLE DEFAULT 0", 
		"DOUBLE DEFAULT 0", "INT DEFAULT 0"};
	private static String[] tradeColumns = {"trade_id", "settlement1_id", "settlement2_id", "income"};
	private static String[] tradeDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "DOUBLE DEFAULT 0"};
	private static String[] kingdomColumns = {"kingdom_id", "name", "monarch_id", "primarycolor", "secondarycolor"};
	private static String[] kingdomDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT UNIQUE", "TEXT", "TEXT", "TEXT"};
	private static String[] playerColumns = {"player_id", "name", "liege_id"};
	private static String[] playerDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT UNIQUE", "INT DEFAULT 0"};
	private static String[] unitColumns = {"unit_id", "owner_id", "settlement_id", "class", "xcoord", "zcoord", "experience"};
	private static String[] unitDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0", "INT DEFAULT 0"};
	private static String[] spellColumns = {"spell_id", "caster_id", "object_id", "object", "class", "power", "duration"};
	private static String[] spellDims = {"INTEGER PRIMARY KEY AUTOINCREMENT", "INT DEFAULT 0", "INT DEFAULT 0", "TEXT", "TEXT", "DOUBLE DEFAULT 0", "DOUBLE DEFAULT 0"};
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
		if(!checkTable("spell")){
			plugin.getLogger().info(plugin.getLogPrefix() + "Must create the spell table..");
			createTable("spell", spellColumns, spellDims);
		}
		// - All tables should now exist.
		if(checkTable("settlement") & checkTable("building") & checkTable("trade") & checkTable("kingdom") & checkTable("player") & checkTable("unit") & checkTable("spell"))
			return true;
		return false;
	}

//--- FUNCTIONALITY ---///
	/** 
	 * <b>subtract</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean subtract(String settlement, String material, int amount)
	 * <br>
	 * <br>
	 * Subtracts material from a settlement's storage.
	 * @param settlement - The settlement to subtract from.
	 * @param material - The material to subtract.
	 * @param amount - The amount of the material to subtract.
	 * @return The sucess of the execution of this command.
	 */
	public boolean subtract(String settlement, String material, int amount){
		return operator(settlement, material, amount, "subtract");
	}
	/** 
	 * <b>add</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean add(String settlement, String material, int amount)
	 * <br>
	 * <br>
	 * Deposits material into the settlement's storage.
	 * @param settlement - The settlement to deposit to.
	 * @param material - The material to deposit.
	 * @param amount - The amount of the material to deposit.
	 * @return The sucess of the execution of this command.
	 */
	public boolean add(String settlement, String material, int amount){
		return operator(settlement, material, amount, "add");
	}
	/** 
	 * <b>operator</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean operator(String settlement, String material, int amount, String operator)
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
	private boolean operator(String settlement, String material, int amount, String operator){
		material = material.toLowerCase();
		if(settlement == null | material == null | amount == 0){
			writeError(plugin.getLogPrefix() + "A value was not specified while trying to subtract a material from the settlement.", true);
			return false;
		}
		ResultSet settlementData = getSettlementData(settlement, material);
		try{
			settlementData.next();
			int currentMat = settlementData.getInt(material);
			settlementData.getStatement().close(); // - Always make sure to close the statement after using the Result Set.  This frees up the database.
			String query = "";
			if(operator == "add")
				query = "UPDATE settlement SET " + material + "=" + (currentMat + amount) + " WHERE name=\'" + settlement + "\'";
			else if(operator == "subtract")
				query = "UPDATE settlement SET " + material + "=" + (currentMat - amount) + " WHERE name=\'" + settlement + "\'";
			return queryWithResult(query);
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		
		return false;
	}
	
	/** 
	 * <b>set</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean set(String settlement, String material, int amount)
	 * <br>
	 * <br>
	 * Sets material in the settlement's storage to an exact value.
	 * @param settlement - The settlement to reference.
	 * @param material - The material to reference.
	 * @param amount - The amount of the material to reference.
	 * @return The sucess of the execution of this command.
	 */
	public boolean set(String settlement, String material, int amount){
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
	
//--- CREATE A KINGDOM ---//
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
		String query = "INSERT INTO kingdom(name,monarch_id,primarycolor,secondarycolor) VALUES (\'" + name + "\'," + monarch + ",\'" + primaryColor + "\',\'" + secondaryColor + "\')";
		return queryWithResult(query);
	}
	
//--- CREATE A SETTLEMENT ---//
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
		if(name == "" | name == null | settlementExists(name))
			return false;
		String query = "INSERT INTO settlement(name,biome,class) VALUES (\'" + name + "\',\'none\',\'town\')";
		return queryWithResult(query);
	}
	
//--- CREATE A PLAYER ---//
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
		String query = "INSERT INTO player(name, liege_id) VALUES (\'" + name + "\'," + liege + ")";
		if(queryWithResult(query)){
			plugin.getLogger().info("Player \"" + name + "\" has been added to the player table.");
			return true;
		}
		return false;
	}	
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuildingr({@link String} settlement, {@link String} classification)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlement - The settlement this building will be associated with
	 * @param classification - The classification of the building.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(String settlement, String classification){
		return createBuilding(settlement, 0,classification, 0, 0);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuildingr({@link String} settlement, {@link String} classification, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlement - The settlement this building will be associated with
	 * @param classification - The classification of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(String settlement, String classification, double xcoord, double zcoord){
		return createBuilding(settlement, 0,classification, xcoord, zcoord);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuildingr({@link String} settlement, {@link String} owner, {@link String} classification)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlement - The settlement this building will be associated with
	 * @param owner - The player that will own this building.
	 * @param classification - The classification of the building.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(String settlement, String owner, String classification){
		return createBuilding(settlement, getPlayerId(owner),classification, 0, 0);
	}
	
	/** 
	 * <b>createBuilding</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean createBuildingr({@link String} settlement, int owner, {@link String} classification, double xcoord, double zcoord)
	 * <br>
	 * <br>
	 * Creates a building in the database with specified values.  Uses default values for unspecified variables.
	 * @param settlement - The settlement this building will be associated with.
	 * @param owner - The id of the player that will own this building.
	 * @param classification - The classification of the building.
	 * @param xcoord - The x coordinate this building is located on.
	 * @param zcoord - The z coordinate this building is located on.
	 * @return The sucess of the execution of this command.
	 */
	public boolean createBuilding(String settlement, int owner, String classification, double xcoord, double zcoord){
		int settlementId = getSettlementId(settlement);
		String query = "INSERT INTO building(settlement_id, owner_id, class, xcoord, zcoord) VALUES (" + settlementId + "," + owner + ",\'" + classification + "\'," + xcoord +"," + zcoord +")";
		if(queryWithResult(query)){
			plugin.getLogger().info("A new " + classification + " building has been created in " + settlement + " at x-" + xcoord + " z-" + zcoord + ".");
			return true;
		}
		return false;
	}	
	//building_id", "settlement_id", "owner_id", "class", "resource", "level", "employed", "bonus
	
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
	 * &nbsp;&nbsp;public boolean thingExists(String name, String table)
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
	 * <b>getAllTableData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getAllTableData({@link String} table, {@link String} column)
	 * <br>
	 * <br>
	 * Gets the column data from a table.  Use "*" to retrieve all data.  Make sure to issue 
	 * .getStatement().close(); on your {@link ResultSet} object in order to free space on the database.  
	 * Otherwise, a database locked exception may occur.
	 * @param table - The name of the table to reference.
	 * @param column - The column to reference.
	 * @return The results if there are any.  Null if there are not.
	 */
	public ResultSet getAllTableData(String table, String column){
		String query = "SELECT " + column + " FROM " + table;
		return  querySelect(query);
	}
	
	/** 
	 * <b>getAllTableData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ResultSet} getAllTableData({@link String} table, {@link String} column, {@link String} where)
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
	public ResultSet getAllTableData(String table, String column, String where){
		String query = "SELECT " + column + " FROM " + table + " WHERE " + where;
		return  querySelect(query);
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
				int liege = results.getInt("liege_id");
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
				int liege = results.getInt("liege_id");
				results.getStatement().close();
				return liege;
			}
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		return -1;
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
		if(entity == null | entity == "" | id <= 0){
			return -1;
		}
		String identifyBy = entity + "_id";
		String owner = "";
		if(entity.equalsIgnoreCase("building"))
			owner = "owner_id";
		else if(entity.equalsIgnoreCase("settlement"))
			owner = "lord_id";
		else if(entity.equalsIgnoreCase("kingdom"))
			owner = "monarch_id";
		if(owner == "")
			return -1;
		
		String query = "SELECT " + owner + " FROM " + entity + " WHERE " + identifyBy + "=" + id;
		ResultSet results = querySelect(query);
		try{
			if(results.next()){
				int ownerId = results.getInt(owner);
				results.getStatement().close();
				return ownerId;
			}
		} catch (SQLException ex){
			writeError(ex.getMessage(), true);
		}
		return -1;
	}
}
