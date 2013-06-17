package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerInfo</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerInfo extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "info".
 */
public class PlayerInfo extends PlayerSubCommand{
	
	/** 
	 * <b>PlayerInfo</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerInfo({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerInfo} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerInfo(Dominion instance){
		super(instance, "/dominion [info/view] [settlement name]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the info sub-command to send a player a message indicating general information 
	 * about a specified settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "info" but gave no settlement.
			sender.sendMessage(plugin.getLogPrefix() + "Outputs general information about a settlement.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "No such settlement \"" + settlement + "\".");
			return true;
		}
		ResultSet results = plugin.getDBHandler().getSettlementData(settlement, "*");
		String allData = "§a======" + settlement + "======§r\n";
		try {
			if(results.next()){
				allData += "§aLord: §f" + plugin.getDBHandler().getPlayerName(results.getInt("lord_id")) 		+ "\n";
				allData += "§aKingdom: §f" + plugin.getDBHandler().getKingdomName(results.getInt("kingdom_id")) + "\n";
				allData += "§aBiome: §f" + results.getString("biome") 	+ "\n";
				allData += "§aClass: §f" + results.getString("class") + "\n";
				allData += "§aBase Defense: §f" + (int) results.getDouble("wall") + "\n";
				allData += "§aX-coord: §f" + results.getDouble("xcoord") + "  ";
				allData += "§aZ-coord: §f" + results.getDouble("zcoord") + "\n";
				allData += "§aMana: §f" + (int) results.getDouble("mana") + "/" + plugin.getSettlementManager().getMaxMana(settlement) + "\n";
				allData += "§aPopulation: §f" + (int) results.getDouble("population") + "\n";
				allData += "§aRecruits: §f" + (int) results.getDouble("recruit") + "\n";
				allData += "§aPrisoners: §f" + (int) results.getDouble("prisoner") + "\n";
				allData += "§a======";
				for(int i=0; i<settlement.length();i++)
					allData += "=";
				allData += "======§r";
				sender.sendMessage(allData);
			}
			results.getStatement().close();
		} catch (SQLException ex) {
			sender.sendMessage("There was an error communicating with the database.");
			ex.printStackTrace();
			return false;
		}

		return true;
	}
}
