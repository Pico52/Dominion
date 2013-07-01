package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class PlayerUnitMove extends PlayerSubCommand{

	public PlayerUnitMove(Dominion instance) {
		super(instance, "/d move [unit id] [x coordinate] [z coordinate]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Orders a unit to move to a location.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(plugin.getLogPrefix() + "You must specify the x and z coordinates.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(plugin.getLogPrefix() + "You must specify the z coordinate.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int unitId = 0;
		double xCoord = 0, zCoord = 0;
		try{
			unitId = Integer.parseInt(args[0]);
			xCoord = Double.parseDouble(args[1]);
			zCoord = Double.parseDouble(args[2]);
		} catch (NumberFormatException ex){
			sender.sendMessage(plugin.getLogPrefix() + "Incorrect input.  All input must be numbers.  The unit id must be an integer.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int ownerId = plugin.getDBHandler().getOwnerId("unit", unitId);
		int playerId = plugin.getDBHandler().getPlayerId(sender.getName());
		if(ownerId != playerId){
			sender.sendMessage(plugin.getLogPrefix() + "You must be the owner of this unit in order to give it an order.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(!plugin.getUnitManager().commandToMove(unitId, xCoord, zCoord)){
			sender.sendMessage(plugin.getLogPrefix() + "Failed to order the unit to move.");
			plugin.getLogger().info("Failed to order unit #" + unitId + " to move.");
		}
		
		return true;
	}

}
