package com.pico52.dominion;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.pico52.dominion.command.AdminCommand;
import com.pico52.dominion.command.PlayerCommand;
import com.pico52.dominion.db.DominionDatabaseHandler;
import com.pico52.dominion.event.DominionPlayerListener;

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
	//*****Class Variables*****//
	public Logger log = Logger.getLogger("Minecraft");
	private  final static String logPrefix = "§a[Dominion]§r "; // Prefix to go in front of all log entries
	private File pFolder = new File("plugins" + File.separator + "Dominion"); // Folder to store plugin settings file and database
	private DominionDatabaseHandler dbHandler = null; // SQLite handler
	private HashMap<String, Integer> commandUsers = new HashMap<String, Integer>(); //Stores info about people using commands	
	private DominionPlayerListener playerEvent;
	
	@Override
	public void onEnable(){
		log.info(logPrefix + "has been enabled!");	// - Letting the console know productions has started.
		dbHandler = new DominionDatabaseHandler(this, log, logPrefix, "Dominion", pFolder.getPath());	// - Starting up the database connection.
		dbHandler.getConnection();  // - Open up the connection.
		dbHandler.setDefaultTables();
		
		playerEvent = new DominionPlayerListener(this);
		getServer().getPluginManager().registerEvents(playerEvent, this);

		getCommand("dominion").setExecutor(new PlayerCommand(this));
		getCommand("admindominion").setExecutor(new AdminCommand(this));
	}
	
	@Override
	public void onDisable(){
		dbHandler.close();	// Close the database connection.
		log.info(logPrefix + "has been disabled."); // Let the console know the plugin has been shut down.
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
}
