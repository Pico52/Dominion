package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminSubCommand</b><br>
 * <br>
 * &nbsp;&nbsp;public abstract class AdminSubCommand
 * <br>
 * <br>
 * The abstract class for all admin sub-commands.
 */
public abstract class AdminSubCommand {
	
	protected static Dominion plugin;
	private static String usageMessage;
	
	/** 
	 * <b>AdminSubCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSubCommand({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerSubCommand} abstract class.
	 * @param instance - The {@link Dominion} plugin this sub-command executor will be running on.
	 */
	protected AdminSubCommand(Dominion instance, String usage){
		plugin = instance;
		usageMessage = usage;
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
	 * @return The usage of the command.
	 */
	public String getUsage(){
		return usageMessage;
	}
}