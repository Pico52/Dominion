package com.pico52.dominion.command.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminSubtract</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminSubtract extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "subtract".
 */
public class AdminSubtract extends AdminSubCommand{

	/** 
	 * <b>AdminSubtract</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSubtract({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminSubtract} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminSubtract(Dominion instance) {
		super(instance, "/admindominion set [settlement name] [material] [amount]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>f
	 * <br>
	 * Manages the admin subtract sub-command to remove materials from a settlement's storage.  Unlike the player 
	 * withdraw command, this will not give the command sender any resources.  It will also not ensure positive values.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){	// - They only specified "subtract" but gave no settlement.  Tell them how to use the command.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){  // - They may have specified the settlement, but they didn't say what to subtract or how much.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the material and amount to be subtracted.");
			return true;
		}
		if (args.length == 2){ // - They may have forgotten to put the amount to withdraw.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the amount to subtract.");
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String settlement = args[0];
		String material = args[1].toLowerCase();
		int amount = Integer.parseInt(args[2]);
		if(!plugin.getDBHandler().settlementExists(settlement)){ // - Make sure this settlement is legitimate.
			sender.sendMessage(plugin.getLogPrefix() + "Settlement: " + settlement + " does not exist.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - Make sure the material type is legitimate.
		if(Material.matchMaterial(material) == null){
			sender.sendMessage(plugin.getLogPrefix() + "The material \"" + material + "\" is not a material.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - Remember, admins don't need to be the lord to issue this command, so we will continue.
		// - Admins can also subtract the resource to make a negative value, so we will still continue.
		// - Subtract the material from the database.
		if(plugin.getDBHandler().subtract(settlement, material, amount)){
			sender.sendMessage(plugin.getLogPrefix() + "Successfully subtracted " + amount + " " + material + " from " + settlement + "!");
			plugin.getLogger().info(plugin.getLogPrefix() + "Successful subtraction for command sender: " + sender.getName());
			return true;
		}else{
			sender.sendMessage(plugin.getLogPrefix() + "Failed to subtract " + amount + " " + material + " from " + settlement + ".");
			plugin.getLogger().info(plugin.getLogPrefix() + "Failed to subtract resources from the settlement.");
			return true;
		}
	}
}
