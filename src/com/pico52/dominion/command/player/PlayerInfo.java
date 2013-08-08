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
			sender.sendMessage(logPrefix + "Outputs general information about a settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "No such settlement \"" + settlement + "\".");
			return true;
		}
		ResultSet results = db.getSettlementData(settlement, "*");
		String allData = "§a======" + settlement + "======§r\n";
		try {
			if(results.next()){
				int settlementId = results.getInt("settlement_id");
				allData += "§aLord: §f" + db.getPlayerName(results.getInt("owner_id")) 		+ "\n";
				allData += "§aKingdom: §f" + db.getKingdomName(results.getInt("kingdom_id")) + "\n";
				allData += "§aBiome: §f" + results.getString("biome") 	+ "\n";
				allData += "§aClass: §f" + results.getString("class") + "\n";
				allData += "§aWalls: §f" + (int) results.getDouble("wall") + "\n";
				allData += "§aDefense: §f" + (int) plugin.getSettlementManager().getDefense(settlementId) + "\n";
				allData += "§aX-coord: §f" + results.getDouble("xcoord") + "  ";
				allData += "§aZ-coord: §f" + results.getDouble("zcoord") + "\n";
				allData += "§aSpell Power: §f" + plugin.getSettlementManager().getSpellPower(settlementId) + "\n";
				allData += "§aMana: §f" + (int) results.getDouble("mana") + "/" + plugin.getSettlementManager().getMaxMana(settlementId) + "\n";
				allData += "§aPopulation: §f" + (int) results.getDouble("population") + "/" + plugin.getSettlementManager().getMaxPopulation(settlementId) + "\n";
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
