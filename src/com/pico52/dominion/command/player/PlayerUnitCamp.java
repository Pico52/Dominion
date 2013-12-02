package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

public class PlayerUnitCamp extends PlayerSubCommand{

	public PlayerUnitCamp(Dominion instance) {
		super(instance, "/d camp [unit id]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!DominionSettings.unitsActive){
			sender.sendMessage(logPrefix + "Units are currently not available on this server.");
			return true;
		}
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Orders a unit to camp.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int unitId = 0;
		try{
			unitId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  \"" + args[0] + "\" is not a number.");
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
		if(!plugin.getUnitManager().commandToCamp(unitId)){
			sender.sendMessage(logPrefix + "Failed to order the unit to camp.");
		}
		return true;
	}

}
