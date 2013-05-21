package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerWithdraw</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerWithdraw extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "withdraw".
 */
public class PlayerWithdraw extends PlayerSubCommand{
	
	/** 
	 * <b>PlayerWithdraw</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerWithdraw({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerWithdraw} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerWithdraw(Dominion instance){
		super(instance, "/dominion withdraw [settlement name] [material] [amount]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the withdraw sub-command to withdraw materials from the stores of a settlement.  The 
	 * player must have space for the materials.  Only the materials that can hold and the settlement 
	 * has enough of will be given to the player and taken from the settlement's storage.
	 * @param sender - The sender of the command and the player to receive the materials.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if(!player.isOnline()){  // - The player must be online for this to work.
			sender.sendMessage(plugin.getLogPrefix() + "You must be online in order to withdraw anything.");
			return true;
		}
		if(args.length == 0 ){	// - They only specified "withdraw" but gave no settlement.  Tell them how to use the command.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){  // - They may have specified the settlement, but they didn't say what to withdraw or how much.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the material and amount to be withdrawn.");
			return true;
		}
		if (args.length == 2){ // - They may have forgotten to put the amount to withdraw.
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			sender.sendMessage(plugin.getLogPrefix() + "Please issue the command again specifying the amount to be withdrawn.");
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String settlement = args[0];
		String material = args[1].toLowerCase();
		int withdrawAmount = Integer.parseInt(args[2]);
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
		ResultSet lord = plugin.getDBHandler().getSettlementData(settlement, "lord_id");
		try {
			if(lord.next()){
				if(plugin.getDBHandler().getPlayerName(lord.getInt("lord_id")) != sender.getName()){
					lord.getStatement().close();
					sender.sendMessage(plugin.getLogPrefix() + "You must be the lord of this settlement in order to withdraw from its resources.");
					return true;
				}
			} else {
				lord.getStatement().close();
				sender.sendMessage(plugin.getLogPrefix() + "Cannot find the lord you are looking for.");
				plugin.getLogger().info("Can't find the lord of this settlement.");
				return true;
			}
			// By this point, the lord matches the sender, so they are authorized to withdraw.
			ResultSet info = plugin.getDBHandler().getSettlementData(settlement, material);
			info.next();
			int currentMat = info.getInt(material);
			info.getStatement().close();
			int newMat = currentMat - withdrawAmount;
			if(newMat < 0)  // - Check to see if there's that much to be withdrawn.
				withdrawAmount = currentMat;
			plugin.getLogger().info(plugin.getLogPrefix() + "Withdraw amount: " + withdrawAmount + ".  Material: " + material.toUpperCase());
			HashMap<Integer, ItemStack> remainderMap = player.getInventory().addItem(new ItemStack(Material.matchMaterial(material.toUpperCase()),withdrawAmount));
			int remainder = 0;
			int actualWithdraw = withdrawAmount;
			if(remainderMap != null){
				for(int i=0;remainderMap.containsKey(i);i++){
					remainder += remainderMap.get(i).getAmount();  // - Summing up the total items that were not added.
				}
				actualWithdraw = withdrawAmount - remainder;
			}
			sender.sendMessage(plugin.getLogPrefix() + "Total withdraw: " + actualWithdraw + ".  Remainder not withdrawn: " + remainder);
			// - Subtract the material from the database.
			if(plugin.getDBHandler().subtract(settlement, material.toLowerCase(), actualWithdraw)){
				sender.sendMessage(plugin.getLogPrefix() + "Withdraw successful!");
				plugin.getLogger().info(plugin.getLogPrefix() + "Withdraw successful for command sender: " + sender.getName());
				return true;
			}else{
				sender.sendMessage(plugin.getLogPrefix() + "Withdraw failed to subtract resources from the settlement.");
				plugin.getLogger().info(plugin.getLogPrefix() + "Withdraw failed to subtract resources from the settlement.");
				return true;
			}
		}catch(SQLException ex){
			sender.sendMessage(plugin.getLogPrefix() + "An error occured when attempting to find your materials.  Please make sure that \"" + material + "\" is a material.");
			plugin.getLogger().info(plugin.getLogPrefix() + "An error occured when attempting to find materials information.  Material specified: " + material);
		}
		return true;
	}
}
