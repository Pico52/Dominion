package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

public class PlayerUnitDrop extends PlayerSubCommand{

	public PlayerUnitDrop(Dominion instance) {
		super(instance, "/d drop [unit id] [item id] (optional)[quantity]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!DominionSettings.unitsActive){
			sender.sendMessage(logPrefix + "Units are currently not available on this server.");
			return true;
		}
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Drops an item where the unit is standing.");
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
			sender.sendMessage(logPrefix + "You must provide the item id and optionally the quantity to drop.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int itemId = 0;
		try{
			itemId = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input. \"" + args[1] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		double quantity = 0;
		boolean usingQuantity = false;
		if(args.length > 2){
			try{
				usingQuantity = true;
				System.out.println(usingQuantity);
				quantity = Double.parseDouble(args[2]);
				if(quantity < 0){
					sender.sendMessage(logPrefix + "You cannot drop a negative value.");
					sender.sendMessage(logPrefix + "Usage: " + usage);
					return true;
				}
				if(quantity == 0){
					sender.sendMessage(logPrefix + "You cannot drop nothing.");
					sender.sendMessage(logPrefix + "Usage: " + usage);
					return true;
				}
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "Incorrect input. \"" + args[2] + "\" is not a number.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				System.out.print(ex.getMessage());
				return true;
			}
		}
		boolean success = false;
		if(usingQuantity){
			double itemQuantity = plugin.getItemManager().getItemQuantity(itemId);
			if(quantity > itemQuantity){
				sender.sendMessage(logPrefix + "The item only has " + itemQuantity + ".  Dropping all of the item instead..");
				quantity = itemQuantity;
			}
			success = plugin.getUnitManager().dropItem(unitId, itemId, quantity);
		} else
			success = plugin.getUnitManager().dropItem(unitId, itemId);
			
		if(success)
			sender.sendMessage(logPrefix + "Unit #" + unitId + " dropped the item #" + itemId + ".");
		else
			sender.sendMessage(logPrefix + "Failed to drop item #" + itemId + " from unit #" + unitId + "'s inventory.");
		
		return true;
	}

}
