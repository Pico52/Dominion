package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminRemove</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminRemove extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "remove".
 */
public class AdminRemove extends AdminSubCommand{
	
	/** 
	 * <b>AdminRemove</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminRemove({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminRemove} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminRemove(Dominion instance) {
		super(instance, "/ad remove [entity] [id]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin remove sub-command to remove a building or structure.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Removes an entity.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(plugin.getLogPrefix() + "You need to provide the id number of the object you want to remove.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		String entity = args[0];
		int id = Integer.parseInt(args[1]);
		if(plugin.getDBHandler().remove(entity, id)){
			sender.sendMessage(plugin.getLogPrefix() + "Successfully removed the " + entity + "!");
			plugin.getLogger().info("Successfully removed a " + entity + ".");
		} else {
			sender.sendMessage(plugin.getLogPrefix() + "Failed to destroy the " + entity + "!");
			plugin.getLogger().info("Failed to remove a " + entity + ".");
		}
		return true;
	}
}
