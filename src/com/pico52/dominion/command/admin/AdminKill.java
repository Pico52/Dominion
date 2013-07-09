package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class AdminKill extends AdminSubCommand{

	public AdminKill(Dominion instance) {
		super(instance, "/ad kill [unit id] (optional)[to unitId]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Kills a unit or multiple units within a range of ids.");
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
		int endUnitId = 0;
		boolean usingRange = false;
		if(args.length > 1){
			try{
				endUnitId = Integer.parseInt(args[1]);
				usingRange = true;
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "Incorrect input. \"" + args[1] + "\" is not a number.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
			if(endUnitId < unitId){
				sender.sendMessage(logPrefix + "The ending unit id must be greater than or equal to the starting unit id.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
		}
		if(usingRange){
			for(int id=unitId; id <= endUnitId; id++){
				if(plugin.getUnitManager().kill(id, "admin"))
					sender.sendMessage(logPrefix + "Successfully killed unit #" + id + ".");
				else
					sender.sendMessage(logPrefix + "Failed to kill unit #" + id + ".");
			}
		} else if(plugin.getUnitManager().kill(unitId, "admin"))
				sender.sendMessage(logPrefix + "Successfully killed unit #" + unitId + ".");
			else
				sender.sendMessage(logPrefix + "Failed to kill unit #" + unitId + ".");
		
		return true;
	}
}
