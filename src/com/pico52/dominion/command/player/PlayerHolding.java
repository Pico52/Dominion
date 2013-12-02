package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

public class PlayerHolding extends PlayerSubCommand{

	public PlayerHolding(Dominion instance) {
		super(instance, "/d holding [unit id]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!DominionSettings.unitsActive){
			sender.sendMessage(logPrefix + "Units are currently not available on this server.");
			return true;
		}
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Displays what a unit is holding.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int unitId = 0;
		try{
			unitId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  " + args[0] + " is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int[] heldItems = plugin.getItemManager().getHeldItemIds(unitId);
		String allData = "";
		String middleData = "";
		double weight = 0;
		double itemWeight = 0;
		for(int item: heldItems){
			itemWeight = plugin.getItemManager().getWeight(item);
			middleData += "§aId #: §f" + item + "  ";
			middleData += "§aItem: §f" + plugin.getItemManager().getItemQuantity(item) + " " + plugin.getItemManager().getItemType(item) + "  ";
			middleData += "§aWeight: §f" + itemWeight + "\n";
			weight += itemWeight;
		}
		if(middleData == "")
			middleData = "This unit is not holding anything.\n";
		allData += "§a============ITEMS============§r \n";
		allData += middleData;
		allData += "§aTotal Weight: §r" + weight + "/" + plugin.getUnitManager().getCapacity(unitId) + "\n";
		allData += "§a=============================§r";
		sender.sendMessage(allData);
		
		return true;
	}

}
