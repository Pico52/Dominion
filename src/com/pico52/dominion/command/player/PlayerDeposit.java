package com.pico52.dominion.command.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerDeposit</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerDeposit extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "deposit".
 */
public class PlayerDeposit extends PlayerSubCommand{
	
	/** 
	 * <b>PlayerDeposit</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerDeposit({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerDeposit} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerDeposit(Dominion instance){
		super(instance, "/dominion deposit [settlement name] [material] [amount]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the deposit sub-command to deposit materials into a specified settlement's storage.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if(!player.isOnline()){  // - The player must be online for this to work.
			sender.sendMessage(plugin.getLogPrefix() + "You must be online in order to deposit anything.");
			return true;
		}
		if(args.length == 0 ){	// - They only specified "deposit" but gave no settlement.  Tell them how to use the command.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){  // - They may have specified the settlement, but they didn't say what to deposit or how much.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the material and amount to be deposited.");
			return true;
		}
		if (args.length == 2){ // - They may have forgotten to put the amount to deposit.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the amount to be deposited.");
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String settlement = args[0];
		String material = args[1].toLowerCase();
		int depositAmount = Integer.parseInt(args[2]);
		// - Make sure the settlement is legitimate
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "Settlement: \"" + settlement + "\" does not exist.  (Case-sensitive)");
			return true;
		}
		// - Make sure the material type is legitimate.
		if(Material.matchMaterial(material) == null){
			sender.sendMessage(plugin.getLogPrefix() + "The material \"" + material + "\" is not a material.");
			return true;
		}
		// - Remove the materials from the player's inventory.
		int actualCount = 0;
		ItemStack[] currentItems = player.getInventory().getContents();
		for(ItemStack stack: currentItems){
			if(stack == null)
				continue;
			if(stack.getType().equals(Material.matchMaterial(material))){
				actualCount += stack.getAmount();
				player.getInventory().remove(stack);
			}
		}
		int notDeposited = depositAmount - actualCount;
		depositAmount = actualCount;
		// - Add the material to the database.
		if(plugin.getDBHandler().add(settlement, material, depositAmount))
			sender.sendMessage(plugin.getLogPrefix() + "Successfully deposited " + depositAmount + " " + material + " with " + notDeposited + " not deposited.");
		else
			sender.sendMessage(plugin.getLogPrefix() + "Failed to deposit.");
		
		return true;
	}
}
