package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerStorage</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerStorage extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "storage".
 */
public class PlayerStorage extends PlayerSubCommand{
	
	/** 
	 * <b>PlayerStorage</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerStorage({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerStorage} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerStorage(Dominion instance){
		super(instance, "/dominion [storage/info/stores/materials/mats] [settlement name]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the storage sub-command to ultimately send the player a message indicating materials storage information 
	 * about a specified settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "mats" but gave no settlement.
			sender.sendMessage(plugin.getLogPrefix() + "Outputs materials inventory of a settlement.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		ResultSet results = plugin.getDBHandler().getSettlementData(settlement, "*");
		try {
			if(!plugin.getDBHandler().settlementExists(settlement)){
				sender.sendMessage(plugin.getLogPrefix() + "No such kingdom \"" + settlement + "\".");
				return true;
			}
			String allData = "§a======" + settlement + "======§f\n";
			while(results.next()){
				allData += "§aFood: §f" + results.getInt("food") + "\n";
				allData += "§aWood: §f" + results.getInt("wood") + "  ";
				allData += "§aCobble: §f" + results.getInt("cobblestone") + "\n";
				allData += "§aStone: §f" + results.getInt("stone") + "  ";
				allData += "§aSand: §f" + results.getInt("sand") + "\n";
				allData += "§aGravel: §f" + results.getInt("gravel") + "  ";
				allData += "§aDirt: §f" + results.getInt("dirt") + "\n";
				allData += "§aIron: §f" + results.getInt("iron") + "  ";
				allData += "§aIron Ore: §f" + results.getInt("iron_ore") + "\n";
				allData += "§aGold: §f" + results.getInt("gold") + "  ";
				allData += "§aGold Ore: §f" + results.getInt("gold_ore") + "\n";
				allData += "§aFlint: §f" + results.getInt("flint") + "  ";
				allData += "§aFeather: §f" + results.getInt("feather") + "\n";
				allData += "§aLapis Ore: §f" + results.getInt("lapis_ore") + "  ";
				allData += "§aDiamond: §f" + results.getInt("diamond") + "\n";
				allData += "§aObsidian: §f" + results.getInt("obsidian") + "  ";
				allData += "§aNetherrack: §f" + results.getInt("netherrack") + "\n";
				allData += "§aNether brick: §f" + results.getInt("nether_brick") + "  ";
				allData += "§aRedstone: §f" + results.getInt("redstone") + "\n";
				allData += "§aBrick: §f" + results.getInt("brick") + "  ";
				allData += "§aClay: §f" + results.getInt("clay") + "\n";
				allData += "§aCoal: §f" + results.getInt("coal") + "  ";
				allData += "§aWool: §f" + results.getInt("wool") + "\n";
				allData += "§aLeather: §f" + results.getInt("leather") + "  ";
				allData += "§aArrows: §f" + results.getInt("arrow") + "\n";
				allData += "§aArmor: §f" + results.getInt("armor") + "  ";
				allData += "§aSnow: §f" + results.getInt("snow") + "\n";
				allData += "§a======";
				for(int i=0; i<settlement.length();i++)
					allData += "=";
				allData += "======§r";
				sender.sendMessage(allData);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return true;
	}
}
