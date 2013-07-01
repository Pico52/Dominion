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
			sender.sendMessage(plugin.getLogPrefix()+ "Orders a unit to attack another unit.");
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
		if(args.length == 1){
			sender.sendMessage(plugin.getLogPrefix()+ "You must provide the id of the target unit.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int targetId = 0;
		try{
			targetId = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex){
			sender.sendMessage(plugin.getLogPrefix() + "Incorrect input.  \"" + args[1] + "\" is not a number.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(!plugin.getUnitManager().commandToAttack(unitId, targetId)){
			sender.sendMessage(plugin.getLogPrefix() + "Failed to order the unit to attack.");
		}
		
		return true;
	}

}
