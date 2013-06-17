package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerEmployment</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerEmployment extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "employment".
 */
public class PlayerEmployment extends PlayerSubCommand{

	/** 
	 * <b>PlayerEmployment</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerEmployment({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerEmployment} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerEmployment(Dominion instance) {
		super(instance, "/dominion employment [settlement name]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the employment sub-command to deposit materials into a specified settlement's storage.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "employment" but gave no settlement.
			sender.sendMessage(plugin.getLogPrefix() + "Outputs employment information about a settlement.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "The settlement \"" + settlement + "\" does not exist.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int settlementId = plugin.getDBHandler().getSettlementId(settlement);
		int ownerId = plugin.getDBHandler().getOwnerId("settlement", settlementId);
		if(ownerId != plugin.getDBHandler().getPlayerId(sender.getName())){
			sender.sendMessage(plugin.getLogPrefix() + "You are not the owner of " + settlement + ".");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		ResultSet results = plugin.getDBHandler().getAllTableData("building", "*", "settlement_id=" + settlementId);
		int totalEmployed = 0, workers = 0, employed = 0;
		String allData = "§a======" + settlement + "======§r\n";
		try {
			while(results.next()){
				String classType = results.getString("class");
				workers = plugin.getBuildingManager().getWorkers(classType);
				employed = results.getInt("employed");
				if(workers <= 0 & employed <= 0) // - No reason to bog down the results with buildings that don't need employees.
					continue;
				allData += "§aClass: §f" + classType + "  ";
				allData += "§aResource: §f" + results.getString("resource") + "  ";
				allData += "§aEmployed: §f" + employed + "/" + workers * results.getInt("level") + "\n";
				totalEmployed += results.getInt("employed");
			}
			results.getStatement().close();
		} catch (SQLException ex) {
			sender.sendMessage("There was an error communicating with the database.");
			ex.printStackTrace();
			return false;
		}
		allData += "§aTotal Employed:§f " + totalEmployed + "\n";
		allData += "§a======";
		for(int i=0; i<settlement.length();i++)
			allData += "=";
		allData += "======§r";
		sender.sendMessage(allData);
		return true;
	}

}
