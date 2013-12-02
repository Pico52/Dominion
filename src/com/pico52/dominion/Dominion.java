package com.pico52.dominion;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.pico52.dominion.command.AdminCommand;
import com.pico52.dominion.command.PlayerCommand;
import com.pico52.dominion.datasheet.BiomeData;
import com.pico52.dominion.db.DominionDatabaseHandler;
import com.pico52.dominion.event.DominionPlayerListener;
import com.pico52.dominion.object.BuildingManager;
import com.pico52.dominion.object.ItemManager;
import com.pico52.dominion.object.PermissionManager;
import com.pico52.dominion.object.PlayerManager;
import com.pico52.dominion.object.RequestManager;
import com.pico52.dominion.object.SettlementManager;
import com.pico52.dominion.object.SpellManager;
import com.pico52.dominion.object.SquelchManager;
import com.pico52.dominion.object.TradeManager;
import com.pico52.dominion.object.UnitManager;
import com.pico52.dominion.task.TaskManager;

/**
 * <b>Dominion plugin for Bukkit</b><br>
 * <br>
 * &nbsp;&nbsp;public final class Dominion extends {@link JavaPlugin}
 * <br>
 * <br>
 * <b>Website:</b>  http://dev.bukkit.org/server-mods/dominion/<br>
 * <br>
 * <b>Created:</b> Thursday, May 16, 2013, 17:47:49 PM<br>
 * 
 * @author Pico52
 */
public final class Dominion extends JavaPlugin{
	
	public Logger log = Logger.getLogger("Minecraft");
	private  final static String logPrefix = "§a[Dominion]§r ";
	private File pFolder = new File("plugins" + File.separator + "Dominion");
	private DominionDatabaseHandler dbHandler = null;
	private HashMap<String, Integer> commandUsers = new HashMap<String, Integer>();
	private DominionPlayerListener playerEvent;
	private BuildingManager buildingManager;
	private SettlementManager settlementManager;
	private PlayerManager playerManager;
	private UnitManager unitManager;
	private BiomeData biomeData;
	private TaskManager taskManager;
	private SpellManager spellManager;
	private ItemManager itemManager;
	private TradeManager tradeManager;
	private PermissionManager permissionManager;
	private RequestManager requestManager;
	private SquelchManager squelchManager;
	
	@Override
	public void onEnable(){
		log.info(logPrefix + "has been enabled!");
		saveDefaultConfig();
		DominionSettings.onEnable(this);
		dbHandler = new DominionDatabaseHandler(this, log, logPrefix, "Dominion", pFolder.getPath());
		dbHandler.getConnection();
		dbHandler.setDefaultTables();
		buildingManager = new BuildingManager(this);
		settlementManager = new SettlementManager(this);
		playerManager = new PlayerManager(this);
		unitManager = new UnitManager(this);
		spellManager = new SpellManager(this);
		itemManager = new ItemManager(this);
		tradeManager = new TradeManager(this);
		permissionManager = new PermissionManager(this);
		requestManager = new RequestManager(this);
		squelchManager = new SquelchManager(this);
		biomeData = new BiomeData();
		
		playerEvent = new DominionPlayerListener(this);
		getServer().getPluginManager().registerEvents(playerEvent, this);

		getCommand("dominion").setExecutor(new PlayerCommand(this));
		getCommand("admindominion").setExecutor(new AdminCommand(this));
		
		taskManager = new TaskManager(this);
		taskManager.startTimers();
	}
	
	@Override
	public void onDisable(){
		dbHandler.close();	// Close the database connection.
		taskManager.onDisable();
		log.info(logPrefix + "has been disabled."); // Let the console know the plugin has been shut down.
	}
	
	/** 
	 * <b>sendMessage</b><br>
	 * <br>
	 * &nbsp;&nbsp;public void sendMessage({@link String} playerName, {@link String} message)
	 * <br>
	 * <br>
	 * @param playerName - The name of the player.
	 * @param message - The message to send.
	 */
	public void sendMessage(String playerName, String message){
		if(isPlayerOnline(playerName))
			getServer().getPlayer(playerName).sendMessage(message);
	}
	
	/** 
	 * <b>isPlayerOnline</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean isPlayerOnline({@link String} player)
	 * <br>
	 * <br>
	 * @param player - The name of the player.
	 * @return True if the player is online.  False if they are not.
	 */
	public boolean isPlayerOnline(String player){
		try{
			if(player == null)
				return false;
		} catch (NullPointerException ex){
			// - This just means it's not a player but is most likely a sort of NPC controller.
			return false;
		}
		for(Player players: getServer().getOnlinePlayers()){
			if(player.equalsIgnoreCase(players.getName()))
				return true;
		}
		return false;
	}

	//--Accessors--//
	/** 
	 * <b>getDBHandler</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link DominionDatabaseHandler} getDBHandler()
	 * <br>
	 * <br>
	 * @return The primary database handler for the Dominion plugin.
	 */
	public DominionDatabaseHandler getDBHandler(){
		return dbHandler;
	}
	
	/** 
	 * <b>getLogPrefix</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getLogPrefix()
	 * <br>
	 * <br>
	 * @return The constant log prefix to go before every output statement.
	 */
	public String getLogPrefix(){
		return logPrefix;
	}
	
	/** 
	 * <b>getPFolder</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link File} getPFolder()
	 * <br>
	 * <br>
	 * @return The plugin file's main directory.
	 */
	public File getPFolder(){
		return pFolder;
	}
	
	/** 
	 * <b>getCommandUsers</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link HashMap}<{@link String}, {@link Integer}> getCommandUsers()
	 * <br>
	 * <br>
	 * @return The command users of this plugin.
	 */
	public HashMap<String, Integer> getCommandUsers(){
		return commandUsers;
	}
	
	/** 
	 * <b>getBuildingManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link BuildingManager} getBuildingManager()
	 * <br>
	 * <br>
	 * @return The manager controlling buildings.
	 */
	public BuildingManager getBuildingManager(){
		return buildingManager;
	}
	
	/** 
	 * <b>getSettlementManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link SettlementManager} getSettlementManager()
	 * <br>
	 * <br>
	 * @return The manager controlling settlements.
	 */
	public SettlementManager getSettlementManager(){
		return settlementManager;
	}
	
	/** 
	 * <b>getPlayerManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link PlayerManager} getPlayerManager()
	 * <br>
	 * <br>
	 * @return The manager controlling players.
	 */
	public PlayerManager getPlayerManager(){
		return playerManager;
	}
	
	/** 
	 * <b>getUnitManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link UnitManager} getUnitManager()
	 * <br>
	 * <br>
	 * @return The manager controlling units.
	 */
	public UnitManager getUnitManager(){
		return unitManager;
	}
	
	/** 
	 * <b>getSpellManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link SpellManager} getSpellManager()
	 * <br>
	 * <br>
	 * @return The manager controlling spells.
	 */
	public SpellManager getSpellManager(){
		return spellManager;
	}
	
	/** 
	 * <b>getItemManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link ItemManager} getItemManager()
	 * <br>
	 * <br>
	 * @return The manager controlling items.
	 */
	public ItemManager getItemManager(){
		return itemManager;
	}
	
	/** 
	 * <b>getTradeManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link TradeManager} getTradeManager()
	 * <br>
	 * <br>
	 * @return The manager controlling trade.
	 */
	public TradeManager getTradeManager(){
		return tradeManager;
	}
	
	/** 
	 * <b>getPermissionManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link PermissionManager} getPermissionManager()
	 * <br>
	 * <br>
	 * @return The manager controlling permissions.
	 */
	public PermissionManager getPermissionManager(){
		return permissionManager;
	}
	
	/** 
	 * <b>getRequestManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link RequestManager} getRequestManager()
	 * <br>
	 * <br>
	 * @return The manager controlling requests.
	 */
	public RequestManager getRequestManager(){
		return requestManager;
	}
	
	/** 
	 * <b>getSqueltchManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link SquelchManager} getSqueltchManager()
	 * <br>
	 * <br>
	 * @return The manager controlling squeltches.
	 */
	public SquelchManager getSqueltchManager(){
		return squelchManager;
	}
	
	/** 
	 * <b>getBiomeData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link BiomeData} getBiomeData()
	 * <br>
	 * <br>
	 * @return The data sheet for biome bonuses and penalties.
	 */
	public BiomeData getBiomeData(){
		return biomeData;
	}
}
