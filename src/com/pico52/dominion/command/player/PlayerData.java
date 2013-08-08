package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerData</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerData extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "data".
 */
public class PlayerData extends PlayerSubCommand{
	
	/** 
	 * <b>PlayerData</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerData({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerData} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerData(Dominion instance){
		super(instance, "/dominion data [biome] [building] [unit] [spell]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the data sub-command to send a player a message indicating general game information.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "data" but gave no indication of what data they want.
			sender.sendMessage(logPrefix + "Outputs general information about the current game.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String entity = args[0].toLowerCase();
		String topBar = "§a======";
		String middleData = "";
		String title = "";
		if(entity.equalsIgnoreCase("biome") | entity.equalsIgnoreCase("biomes")){
			title = "Biome Data";
			middleData = plugin.getBiomeData().outputData();
		} else if(entity.equalsIgnoreCase("building") | entity.equalsIgnoreCase("buildings")){
			title = "Building Data";
			middleData += "Building data is not currently working.";
		} else if(entity.equalsIgnoreCase("unit") | entity.equalsIgnoreCase("units")){
			title = "Unit Data";
			middleData = plugin.getUnitManager().outputUnitData();
		} else if(entity.equalsIgnoreCase("spell") | entity.equalsIgnoreCase("spells")){
			title = "Spell Data";
			middleData = plugin.getSpellManager().outputSpellData();
		}  else {
			title = "No Data";
			middleData += "No data available.";
		}
		topBar += title + "======§r\n";
		String bottomBar = "§a======";
			for(int i=0; i<title.length();i++)
				bottomBar += "=";
		bottomBar += "======§r";
		String allData = topBar + middleData + "\n" + bottomBar;
		sender.sendMessage(allData);

		return true;
	}
}