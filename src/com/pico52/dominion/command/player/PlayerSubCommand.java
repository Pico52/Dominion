package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.db.DominionDatabaseHandler;

/** 
 * <b>PlayerSubCommand</b><br>
 * <br>
 * &nbsp;&nbsp;public abstract class PlayerSubCommand
 * <br>
 * <br>
 * The abstract class for all player sub-commands.
 */
public abstract class PlayerSubCommand {
	
	protected static Dominion plugin;
	protected DominionDatabaseHandler db;
	protected String usage, logPrefix;
	
	/** 
	 * <b>PlayerSubCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerSubCommand({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerSubCommand} abstract class.
	 * @param instance - The {@link Dominion} plugin this sub-command executor will be running on.
	 */
	protected PlayerSubCommand(Dominion instance, String usage){
		plugin = instance;
		db = plugin.getDBHandler();
		this.usage = usage;
		logPrefix = plugin.getLogPrefix();
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the execution of the sub-command.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	public abstract boolean execute(CommandSender sender, String[] args);
	
	/** 
	 * <b>getUsage</b><br>
	 * <br>
	 * &nbsp;&nbsp;public {@link String} getUsage()
	 * <br>
	 * <br>
	 * Gets the usage of the command.
	 * @return The usage of the command.
	 */
	public String getUsage(){
		return usage;
	}
}
