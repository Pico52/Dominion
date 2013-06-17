package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.datasheet.ProductionSheet;

/** 
 * <b>PlayerProduction</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerProduction extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "production".
 */
public class PlayerProduction extends PlayerSubCommand{

	/** 
	 * <b>PlayerProduction</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerProduction({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerProduction} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerProduction(Dominion instance) {
		super(instance, "/dominion [p/prod/production] [settlement name]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the production sub-command to send a player a message indicating the materials and 
	 * items being produced in a settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "mats" but gave no settlement.
			sender.sendMessage(plugin.getLogPrefix() + "Outputs materials production of a settlement.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// There will at least be an argument here, hopefully a settlement name.
		String settlement = args[0];
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "No such kingdom \"" + settlement + "\".");
			return true;
		}
		ProductionSheet results = plugin.getBuildingManager().getProductions(settlement);
		String allData = "�a======" + settlement + "======�f\n";
		allData += "�aMana: �f" + results.mana + "  ";
		allData += "�aPopulation: �f" + results.population + "\n";
		allData += "�aWealth: �f" + results.wealth + "  ";
		allData += "�aFood: �f" + results.food + "\n";
		allData += "�aWood: �f" + results.wood + "  ";
		allData += "�aCobble: �f" + results.cobblestone + "\n";
		allData += "�aStone: �f" + results.stone + "  ";
		allData += "�aSand: �f" + results.sand + "\n";
		allData += "�aGravel: �f" + results.gravel + "  ";
		allData += "�aDirt: �f" + results.dirt + "\n";
		allData += "�aIron: �f" + results.ironIngot + "  ";
		allData += "�aIron Ore: �f" + results.ironOre + "\n";
		allData += "�aGold: �f" + results.goldIngot + "  ";
		allData += "�aGold Ore: �f" + results.goldOre + "\n";
		allData += "�aEmerald: �f" + results.emerald + "  ";
		allData += "�aEmerald Ore: �f" + results.emeraldOre + "\n";
		allData += "�aFlint: �f" + results.flint + "  ";
		allData += "�aFeather: �f" + results.feather + "\n";
		allData += "�aLapis Ore: �f" + results.lapisOre + "  ";
		allData += "�aDiamond: �f" + results.diamond + "\n";
		allData += "�aObsidian: �f" + results.obsidian + "  ";
		allData += "�aNetherrack: �f" + results.netherrack + "\n";
		allData += "�aNether brick: �f" + results.netherBrick + "  ";
		allData += "�aRedstone: �f" + results.redstone + "\n";
		allData += "�aGlowstone: �f" + results.glowstone + "  ";
		allData += "�aBrick: �f" + results.brick + "\n";
		allData += "�aClay: �f" + results.clay + "  ";
		allData += "�aCoal: �f" + results.coal + "\n";
		allData += "�aWool: �f" + results.wool + "  ";
		allData += "�aLeather: �f" + results.leather + "\n";
		allData += "�aArrows: �f" + results.arrow + "  ";
		allData += "�aRecruit: �f" + results.recruit + "\n";
		allData += "�aWeapon: �f" + results.weapon + "  ";
		allData += "�aArmor: �f" + results.armor + "\n";
		allData += "�aSnow: �f" + results.snow + "\n";
		allData += "�a======";
		for(int i=0; i<settlement.length();i++)
			allData += "=";
		allData += "======�r";
		sender.sendMessage(allData);

		return true;
	}
}
