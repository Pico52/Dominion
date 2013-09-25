package com.pico52.dominion.command.player;

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
			sender.sendMessage(logPrefix + "You must be online in order to withdraw anything.");
			return true;
		}
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "Usage: " + usage);
			sender.sendMessage(logPrefix + "Please issue the command again specifying the material and amount to be withdrawn.");
			return true;
		}
		if (args.length == 2){
			sender.sendMessage(logPrefix + "Usage: " + usage);
			sender.sendMessage(logPrefix + "Please issue the command again specifying the amount to be withdrawn.");
			return true;
		}
		String settlement = args[0], material = args[1].toLowerCase();
		double withdrawAmount = 0;
		try{
			withdrawAmount = Double.parseDouble(args[2]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  " + args[2] + " is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "Settlement: " + settlement + " does not exist.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(Material.matchMaterial(material) == null){
			sender.sendMessage(logPrefix + "The material \"" + material + "\" is not a material.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int playerId = db.getPlayerId(sender.getName()), settlementId = db.getSettlementId(settlement);
		if(!plugin.getSettlementManager().isOwner(playerId, settlementId)){
			sender.sendMessage(logPrefix + "You must be the lord of this settlement in order to withdraw from its resources.");
			return true;
		}
		double currentMat = plugin.getSettlementManager().getMaterial(settlementId, material);
		double newMat = currentMat - withdrawAmount;
			if(newMat < 0)
				withdrawAmount = currentMat;
		plugin.getLogger().info(logPrefix + "Withdraw amount: " + withdrawAmount + ".  Material: " + material.toUpperCase());
		HashMap<Integer, ItemStack> remainderMap = player.getInventory().addItem(new ItemStack(Material.matchMaterial(material.toUpperCase()),(int) withdrawAmount));
		double remainder = 0;
		double actualWithdraw = (int) withdrawAmount;
		if(remainderMap != null){
			for(int i=0;remainderMap.containsKey(i);i++){
				remainder += remainderMap.get(i).getAmount();
			}
			actualWithdraw = withdrawAmount - remainder;
		}
		sender.sendMessage(logPrefix + "Total withdraw: " + actualWithdraw + ".  Remainder not withdrawn: " + remainder);
		if(plugin.getSettlementManager().subtractMaterial(settlementId, material, actualWithdraw)){
			sender.sendMessage(logPrefix + "Withdraw successful!");
			plugin.getLogger().info(logPrefix + "Withdraw successful for command sender: " + sender.getName());
			return true;
		}else{
			sender.sendMessage(logPrefix + "Withdraw failed to subtract resources from the settlement.");
			plugin.getLogger().info(logPrefix + "Withdraw failed to subtract resources from the settlement.");
			return true;
		}
	}
}
