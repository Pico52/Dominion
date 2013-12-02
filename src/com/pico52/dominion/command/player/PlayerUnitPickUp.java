package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

public class PlayerUnitPickUp extends PlayerSubCommand{

	public PlayerUnitPickUp(Dominion instance) {
		super(instance, "/d pickup [unit id] [material] (optional)[quantity]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!DominionSettings.unitsActive){
			sender.sendMessage(logPrefix + "Units are currently not available on this server.");
			return true;
		}
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Picks up an item within range.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int unitId = 0;
		try{
			unitId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input. \"" + args[0] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int ownerId = db.getOwnerId("unit", unitId);
		int playerId = db.getPlayerId(sender.getName());
		if(ownerId != playerId){
			sender.sendMessage(logPrefix + "You must be the owner of this unit to tell it what to do.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must specify the material, and you may optionally specify a quantity.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String material = args[1];
		double quantity = 0;
		boolean usingQuantity = false;
		if(args.length > 2){
			try{
				quantity = Double.parseDouble(args[2]);
				usingQuantity = true;
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "Incorrect input. \"" + args[2] + "\" is not a number.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
		}
		boolean success = false;
		if(usingQuantity)
			success = plugin.getUnitManager().pickUpItem(unitId, material, quantity);
		else
			success = plugin.getUnitManager().pickUpItem(unitId, material);
			
		if(success)
			sender.sendMessage(logPrefix + "Unit #" + unitId + " has picked up the " + material + ".");
		else
			sender.sendMessage(logPrefix + "Failed to pick up " + material + " for unit #" + unitId + ".");
		
		return true;
	}

}
