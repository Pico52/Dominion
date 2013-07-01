package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class PlayerUnitCamp extends PlayerSubCommand{

	public PlayerUnitCamp(Dominion instance) {
		super(instance, "/d camp [unit id]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Orders a unit to camp.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int unitId = 0;
		try{
			unitId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(plugin.getLogPrefix() + "Incorrect input.  \"" + args[0] + "\" is not a number.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int ownerId = plugin.getDBHandler().getOwnerId("unit", unitId);
		int playerId = plugin.getDBHandler().getPlayerId(sender.getName());
		if(ownerId != playerId){
			sender.sendMessage(plugin.getLogPrefix() + "You must be the owner of this unit to tell it what to do.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(!plugin.getUnitManager().commandToCamp(unitId)){
			sender.sendMessage(plugin.getLogPrefix() + "Failed to order the unit to camp.");
		}
		return true;
	}

}
