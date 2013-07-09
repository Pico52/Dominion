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
			sender.sendMessage(logPrefix + "Orders a unit to move to a location.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must specify the x and z coordinates.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(logPrefix + "You must specify the z coordinate.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int unitId = 0;
		double xCoord = 0, zCoord = 0;
		try{
			unitId = Integer.parseInt(args[0]);
			xCoord = Double.parseDouble(args[1]);
			zCoord = Double.parseDouble(args[2]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  All input must be numbers.  The unit id must be an integer.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int ownerId = db.getOwnerId("unit", unitId);
		int playerId = db.getPlayerId(sender.getName());
		if(ownerId != playerId){
			sender.sendMessage(logPrefix + "You must be the owner of this unit in order to give it an order.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getUnitManager().commandToMove(unitId, xCoord, zCoord)){
			sender.sendMessage(logPrefix + "Failed to order the unit to move.");
			plugin.getLogger().info("Failed to order unit #" + unitId + " to move.");
		}
		
		return true;
	}

}
