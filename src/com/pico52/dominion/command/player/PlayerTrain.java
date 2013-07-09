package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerTrain</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerTrain extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "train".
 */
public class PlayerTrain extends PlayerSubCommand{

	/** 
	 * <b>PlayerTrain</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerTrain({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerTrain} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerTrain(Dominion instance){
		super(instance, "/d train [settlement name] [unit type]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the train sub-command to train a new unit.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "train" but gave no other arguments.
			sender.sendMessage(logPrefix + "Trains a new unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must specify a unit type.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String settlement = args[0];
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "The input provided: \"" + settlement + "\" is not a settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String unit = args[1];
		if(!plugin.getUnitManager().isUnit(unit)){
			sender.sendMessage(logPrefix + "The input provided: \"" + unit + "\" is not a unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - In the future, we'll check if the user has the permission to do this rather than only checking if they're the owner of the settlement.
		int settlementOwner = db.getOwnerId("settlement", db.getSettlementId(settlement));
		if(db.getPlayerId(sender.getName()) != settlementOwner){
			sender.sendMessage(logPrefix + "You must be the owner of the settlement in order to train anything there.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int foodReq = plugin.getUnitManager().getUnit(unit).getFoodConsumption() * 10;
		if(plugin.getSettlementManager().getMaterial(db.getSettlementId(settlement), "food") < foodReq){
			sender.sendMessage(logPrefix + "This unit requires " + foodReq + " food to be built.  " + settlement + " does not have enough food to build the unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int wealthReq = plugin.getUnitManager().getUnit(unit).getBuildCost();
		if(plugin.getSettlementManager().getMaterial(db.getSettlementId(settlement), "wealth") < wealthReq){
			sender.sendMessage(logPrefix + "This unit requires " + wealthReq + " wealth to be built.  " + settlement + " does not have enough wealth to build the unit.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(plugin.getUnitManager().startProduction(settlement, unit)){
			int trainingTime = plugin.getUnitManager().getUnit(unit).getTrainingTime();
			sender.sendMessage(logPrefix + "Successfully training a new " + unit + " in " + settlement +" that will be finished in " + trainingTime + " unit ticks.");
		} else {
			sender.sendMessage(logPrefix + "Failed to train the new unit: \"" + unit + "\"");
			plugin.getLogger().info("Failed to train a unit: \"" + unit + "\"");
		}
		return true;
	}
}
