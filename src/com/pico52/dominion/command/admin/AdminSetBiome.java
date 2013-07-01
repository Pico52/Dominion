package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminSetBiome</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminSetBiome extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "setbiome".
 */
public class AdminSetBiome extends AdminSubCommand{

	/** 
	 * <b>AdminSetBiome</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSetBiome({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminSetBiome} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminSetBiome(Dominion instance) {
		super(instance, "/ad setbiome [settlement name] [biome]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin setbiome sub-command to set the biome of a settlement to a specified value.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Updates a settlement biome.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(plugin.getLogPrefix() + "You must specify the biome.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		String settlement = args[0];
		String biome = args[1].toLowerCase();
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "Settlement: \"" + settlement + "\" does not exist.  (Case-sensitive)");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(!plugin.getBiomeData().isBiome(biome)){
			sender.sendMessage(plugin.getLogPrefix() + "The input \"" + biome + "\" is not a biome.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(plugin.getSettlementManager().setBiome(settlement, biome))
			sender.sendMessage(plugin.getLogPrefix() + "Successfully set the biome to " + biome + " in " + settlement + ".");
		else
			sender.sendMessage(plugin.getLogPrefix() + "Failed to set the biome to " + biome + " in " + settlement + ".");
		
		return true;
	}
}