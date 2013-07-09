package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerEmploy</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerEmploy extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "employ".
 */
public class PlayerEmploy extends PlayerSubCommand{

	/** 
	 * <b>PlayerEmploy</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerEmploy({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerEmploy} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerEmploy(Dominion instance) {
		super(instance, "/dominion employ [# to have employed] [building id]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the employ sub-command to deposit materials into a specified settlement's storage.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "employment" but gave no settlement.
			sender.sendMessage(logPrefix + "Sets a number of employees to work in a building.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int employ = 0;
		try{
			employ = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  \"" + args[0] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must specify the building id.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int building = 0;
		try{
			building = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  " + args[1] + " is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int ownerId = db.getOwnerId("building", building);
		if(ownerId != db.getPlayerId(sender.getName())){
			sender.sendMessage(logPrefix + "You are not the owner of that building.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String classification = "";
		int settlementId = 0, employed = 0, level = 0;
		double population = 0;
		ResultSet buildingData = db.getBuildingData(building, "*");
		try{
			buildingData.next();
			classification = buildingData.getString("class");
			settlementId = buildingData.getInt("settlement_id");
			employed = buildingData.getInt("employed");
			level = buildingData.getInt("level");
			buildingData.getStatement().close();
			ResultSet settlementData = db.getSettlementData(settlementId, "*");
			settlementData.next();
			population = settlementData.getDouble("population");
			settlementData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int totalEmployed = plugin.getSettlementManager().getCurrentlyEmployed(settlementId);
		if(employ + (totalEmployed - employed) > population){
			sender.sendMessage(logPrefix + "Failed to employ " + employ + " citizens to building id #" + building + " due to not having enough population.");
			plugin.getLogger().info("Failed to employ " + employ + " citizens to building id #" + building + " due to not having enough population.");
			return true;
		}
		int maxWorkers = plugin.getBuildingManager().getWorkers(classification);
		if(employ > maxWorkers * level){
			sender.sendMessage(logPrefix + "Failed to employ " + employ + " citizens to building id #" + building + " because the building has a maximum worker capacity of " + 
					maxWorkers * level + " at level " + level + ".");
			plugin.getLogger().info("Failed to employ " + employ + " citizens to building id #" + building + " because the building has a maximum worker capacity of " + maxWorkers * level + 
					" at level " + level + ".");
			return true;
		}
		if(db.update("building", "employed", employ, "building_id", building)){
			sender.sendMessage(logPrefix + employ + " employees are now working at building id #" + building + ".");
			plugin.getLogger().info(employ + " employees are now working at building id #" + building + ".");
		} else {
			sender.sendMessage(logPrefix + "Failed to update the employment at building id #" + building + ".");
			plugin.getLogger().info("Failed to update the employment at building id #" + building + ".");
		}
		return true;
	}

}
