package com.pico52.dominion.command.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminAdd</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminAdd extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "add".
 */
public class AdminAdd extends AdminSubCommand{
	
	/** 
	 * <b>AdminAdd</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminAdd({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminAdd} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminAdd(Dominion instance) {
		super(instance, "/admindominion add [settlement name] [material name] [amount]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin add sub-command to deposit materials into a settlement's storage.  Unlike the player 
	 * deposit command, this command does not require the sender to have the resources on-hand.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){  // - They may have specified the settlement, but they didn't say what to add or how much.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the material and amount to be added.");
			return true;
		}
		if (args.length == 2){ // - They may have forgotten to put the amount to add.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the amount to add.");
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String settlement = args[0];
		String material = args[1].toLowerCase();
		int amount = Integer.parseInt(args[2]);
		
		// - Make sure the settlement is legitimate
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "Settlement: \"" + settlement + "\" does not exist.  (Case-sensitive)");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - Make sure the material type is legitimate.
		if(Material.matchMaterial(material) == null){
			sender.sendMessage(plugin.getLogPrefix() + "The material \"" + material + "\" is not a material.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - Add the material to the database.
		if(plugin.getDBHandler().add(settlement, material, amount))
			sender.sendMessage(plugin.getLogPrefix() + "Successfully added " + amount + " " + material + " to " + settlement + ".");
		else
			sender.sendMessage(plugin.getLogPrefix() + "Failed to add " + amount + " " + material + " to " + settlement + ".");
		
		return true;
	}
}
