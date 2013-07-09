package com.pico52.dominion.command.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

public class PlayerGiveToUnit extends PlayerSubCommand{

	public PlayerGiveToUnit(Dominion instance) {
		super(instance, "/d givetounit [settlement name] [unit id] [material] [quantity]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Gives materials from a settlement to a unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String settlement = args[0];
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "No such settlement \"" + settlement + "\".");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getSettlementManager().isOwner(db.getPlayerId(sender.getName()), settlement)){
			sender.sendMessage(logPrefix + "You do not have the right to manage resources in this settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must specify a unit id, the type of material, and the quantity of the material.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int unitId = 0;
		try{
			unitId = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "\"" + args[1] + "\" is not a proper id number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(logPrefix + "You must specify the type of material and its quantity.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String material = args[2].toLowerCase();
		if(Material.matchMaterial(material) == null & !material.equalsIgnoreCase("food") & !material.equalsIgnoreCase("wealth") & 
				!material.equalsIgnoreCase("prisoner") & !material.equalsIgnoreCase("weapon") & !material.equalsIgnoreCase("armor")){
			sender.sendMessage(logPrefix + "The material \"" + material + "\" is not a giveable material.");
			return true;
		}
		if(args.length == 3){
			sender.sendMessage(logPrefix + "You must specify the quantity of the material.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		double quantity = 0;
		try{
			quantity = Double.parseDouble(args[3]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "\"" + args[3] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getSettlementManager().hasMaterial(settlement, material, quantity)){
			sender.sendMessage(logPrefix + settlement + " does not have enough " + material + " to perform this task.");
			return true;
		}
		int settlementId = db.getSettlementId(settlement);
		double unitX = plugin.getUnitManager().getUnitX(unitId);
		double unitZ = plugin.getUnitManager().getUnitZ(unitId);
		double settlementX = plugin.getSettlementManager().getX(settlementId);
		double settlementZ = plugin.getSettlementManager().getZ(settlementId);
		if(Math.abs(unitX - settlementX) > DominionSettings.unitPickUpFromSettlementRange | Math.abs(unitZ - settlementZ) > DominionSettings.unitPickUpFromSettlementRange){
			sender.sendMessage(logPrefix + "The unit is too far from " + settlement + " to pick up the item.");
			return true;
		}
		if(!plugin.getItemManager().giveItemToUnit(material, quantity, unitId)){
			sender.sendMessage(logPrefix + "Failed to give " + material + " x(" + quantity + ") to the unit.");
			return true;
		}
		if(plugin.getSettlementManager().subtractMaterial(settlementId, material, quantity)){
			sender.sendMessage(logPrefix + "Successfully gave " + quantity + " " + material + " to unit #" + unitId + ".");
			return true;
		} else {
			sender.sendMessage(logPrefix + "Failed to subtract the " + material + " x(" + quantity + ") from " + settlement + ".");
			return true;
		}
	}

}
