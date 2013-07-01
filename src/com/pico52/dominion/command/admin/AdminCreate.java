package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminCreate</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminCreate extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the administrator sub-command "create".
 */
public class AdminCreate extends AdminSubCommand{
	
	/** 
	 * <b>AdminCreate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminCreate({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminCreate} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminCreate(Dominion instance){
		super(instance, "/ad create [kingdom/settlement] [kingdom/settlement name]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender,{@link String} args[])
	 * <br>
	 * <br>
	 * Manages the create sub-command to create a kingdom or settlement with a given name if 
	 * all values were properly provided.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Creates a kingdom or settlement.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("kingdom")){
				sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion create kingdom [kingdom name]");
			}
			if(args[0].equalsIgnoreCase("settlement")){
				sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion create settlement [settlement name]");
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("kingdom")){
			if(plugin.getDBHandler().createKingdom(args[1])){
				sender.sendMessage(plugin.getLogPrefix() + "The new kingdom \"" + args[1] + "\" has been created!");
				plugin.getLogger().info(plugin.getLogPrefix() + "The new kingdom \"" + args[1] + "\" has been created!");
			}else{
				sender.sendMessage(plugin.getLogPrefix() + "The kingdom \"" + args[1] + "\" could not be created.");
				plugin.getLogger().info(plugin.getLogPrefix() + "The kingdom \"" + args[1] + "\" could not be created");
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("settlement")){
			if(plugin.getDBHandler().createSettlement(args[1])){
				sender.sendMessage(plugin.getLogPrefix() + "The new settlement \"" + args[1] + "\" has been created!");
				plugin.getLogger().info(plugin.getLogPrefix() + "The new settlement \"" + args[1] + "\" has been created!");
			}else{
				sender.sendMessage(plugin.getLogPrefix() + "The settlement \"" + args[1] + "\" could not be created.");
				plugin.getLogger().info(plugin.getLogPrefix() + "The settlement \"" + args[1] + "\" could not be created");
			}
			return true;
		}
		
		return false;
	}
}
