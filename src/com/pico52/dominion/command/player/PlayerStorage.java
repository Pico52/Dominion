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
				allData += "§aFood: §f" + (int) results.getDouble("food") + "  ";
				allData += "§aWood: §f" + (int) results.getDouble("wood") + "\n";
				allData += "§aCobble: §f" + (int) results.getDouble("cobblestone") + "  ";
				allData += "§aStone: §f" + (int) results.getDouble("stone") + "\n";
				allData += "§aSand: §f" + (int) results.getDouble("sand") + "  ";
				allData += "§aGravel: §f" + (int) results.getDouble("gravel") + "\n";
				allData += "§aDirt: §f" + (int) results.getDouble("dirt") + "  ";
				allData += "§aIron: §f" + (int) results.getDouble("iron_ingot") + "\n";
				allData += "§aIron Ore: §f" + (int) results.getDouble("iron_ore") + "  ";
				allData += "§aGold: §f" + (int) results.getDouble("gold_ingot") + "\n";
				allData += "§aGold Ore: §f" + (int) results.getDouble("gold_ore") + "  ";
				allData += "§aEmerald: §f" + (int) results.getDouble("emerald") + "\n";
				allData += "§aEmerald Ore: §f" + (int) results.getDouble("emerald_ore") + "  ";
				allData += "§aFlint: §f" + (int) results.getDouble("flint") + "\n";
				allData += "§aFeather: §f" + (int) results.getDouble("feather") + "  ";
				allData += "§aLapis Ore: §f" + (int) results.getDouble("lapis_ore") + "\n";
				allData += "§aDiamond: §f" + (int) results.getDouble("diamond") + "  ";
				allData += "§aObsidian: §f" + (int) results.getDouble("obsidian") + "\n";
				allData += "§aNetherrack: §f" + (int) results.getDouble("netherrack") + "  ";
				allData += "§aNether Brick: §f" + (int) results.getDouble("nether_brick") + "\n";
				allData += "§aRedstone: §f" + (int) results.getDouble("redstone") + "  ";
				allData += "§aGlowstone: §f" + (int) results.getDouble("glowstone") + "\n";
				allData += "§aBrick: §f" + (int) results.getDouble("brick") + "  ";
				allData += "§aClay: §f" + (int) results.getDouble("clay") + "\n";
				allData += "§aCoal: §f" + (int) results.getDouble("coal") + "  ";
				allData += "§aWool: §f" + (int) results.getDouble("wool") + "\n";
				allData += "§aLeather: §f" + (int) results.getDouble("leather") + "  ";
				allData += "§aArrows: §f" + (int) results.getDouble("arrow") + "\n";
				allData += "§aWeapon: §f" + (int) results.getDouble("weapon") + "  ";
				allData += "§aArmor: §f" + (int) results.getDouble("armor") + "\n";
				allData += "§aSnow: §f" + (int) results.getDouble("snow") + "\n";
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
