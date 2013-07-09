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
			sender.sendMessage(logPrefix + "Outputs materials inventory of a settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		ResultSet results = db.getSettlementData(settlement, "*");
		try {
			if(!db.settlementExists(settlement)){
				sender.sendMessage(logPrefix + "No such kingdom \"" + settlement + "\".");
				return true;
			}
			String allData = "§a======" + settlement + "======§f\n";
			while(results.next()){
				allData += "§aFood: §f" + (int) results.getDouble("food") + "  ";
				allData += "§aWealth: §f" + (int) results.getDouble("wealth") + "\n";
				allData += "§aWood: §f" + (int) results.getDouble("wood") + "  ";
				allData += "§aCobble: §f" + (int) results.getDouble("cobblestone") + "\n";
				allData += "§aStone: §f" + (int) results.getDouble("stone") + "  ";
				allData += "§aSand: §f" + (int) results.getDouble("sand") + "\n";
				allData += "§aGravel: §f" + (int) results.getDouble("gravel") + "  ";
				allData += "§aDirt: §f" + (int) results.getDouble("dirt") + "\n";
				allData += "§aIron: §f" + (int) results.getDouble("iron_ingot") + "  ";
				allData += "§aIron Ore: §f" + (int) results.getDouble("iron_ore") + "\n";
				allData += "§aGold: §f" + (int) results.getDouble("gold_ingot") + "  ";
				allData += "§aGold Ore: §f" + (int) results.getDouble("gold_ore") + "\n";
				allData += "§aEmerald: §f" + (int) results.getDouble("emerald") + "  ";
				allData += "§aEmerald Ore: §f" + (int) results.getDouble("emerald_ore") + "\n";
				allData += "§aFlint: §f" + (int) results.getDouble("flint") + "  ";
				allData += "§aFeather: §f" + (int) results.getDouble("feather") + "\n";
				allData += "§aLapis Ore: §f" + (int) results.getDouble("lapis_ore") + "  ";
				allData += "§aDiamond: §f" + (int) results.getDouble("diamond") + "\n";
				allData += "§aObsidian: §f" + (int) results.getDouble("obsidian") + "  ";
				allData += "§aNetherrack: §f" + (int) results.getDouble("netherrack") + "\n";
				allData += "§aNether Brick: §f" + (int) results.getDouble("nether_brick") + "  ";
				allData += "§aRedstone: §f" + (int) results.getDouble("redstone") + "\n";
				allData += "§aGlowstone: §f" + (int) results.getDouble("glowstone") + "  ";
				allData += "§aBrick: §f" + (int) results.getDouble("brick") + "\n";
				allData += "§aClay: §f" + (int) results.getDouble("clay") + "  ";
				allData += "§aCoal: §f" + (int) results.getDouble("coal") + "\n";
				allData += "§aWool: §f" + (int) results.getDouble("wool") + "  ";
				allData += "§aLeather: §f" + (int) results.getDouble("leather") + "\n";
				allData += "§aArrows: §f" + (int) results.getDouble("arrow") + "  ";
				allData += "§aWeapon: §f" + (int) results.getDouble("weapon") + "\n";
				allData += "§aArmor: §f" + (int) results.getDouble("armor") + "  ";
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
