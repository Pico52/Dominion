package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class PlayerUnitDisband extends PlayerSubCommand{

	public PlayerUnitDisband(Dominion instance) {
		super(instance, "/d disband [unit id]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Disbands a unit.");
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
			sender.sendMessage(logPrefix + "You must be the owner of this unit in order to disband it.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getUnitManager().kill(unitId, "disband")){
			sender.sendMessage(logPrefix + "Failed to disband the unit.");
		}
		
		return true;
	}

}
