package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class PlayerUnitAttack extends PlayerSubCommand{

	public PlayerUnitAttack(Dominion instance) {
		super(instance, "/d attack [attacker id] [target id]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix+ "Orders a unit to attack another unit.");
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
		if(args.length == 1){
			sender.sendMessage(logPrefix+ "You must provide the id of the target unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int targetId = 0;
		try{
			targetId = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  \"" + args[1] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getUnitManager().commandToAttack(unitId, targetId)){
			sender.sendMessage(logPrefix + "Failed to order the unit to attack.");
		}
		
		return true;
	}

}
