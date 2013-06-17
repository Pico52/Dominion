package com.pico52.dominion.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.command.player.PlayerData;
import com.pico52.dominion.command.player.PlayerDeposit;
import com.pico52.dominion.command.player.PlayerDestroy;
import com.pico52.dominion.command.player.PlayerEmploy;
import com.pico52.dominion.command.player.PlayerEmployment;
import com.pico52.dominion.command.player.PlayerInfo;
import com.pico52.dominion.command.player.PlayerList;
import com.pico52.dominion.command.player.PlayerProduction;
import com.pico52.dominion.command.player.PlayerStorage;
import com.pico52.dominion.command.player.PlayerWithdraw;

/** 
 * <b>PlayerCommand</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerCommand implements {@link CommandExecutor}
 * <br>
 * <br>
 * The command executor for all player commands.
 */
public class PlayerCommand implements CommandExecutor{
	
	private static Dominion plugin;
	private static PlayerInfo playerInfo;
	private static PlayerStorage playerStorage;
	private static PlayerWithdraw playerWithdraw;
	private static PlayerDeposit playerDeposit;
	private static PlayerList playerList;
	private static PlayerDestroy playerDestroy;
	private static PlayerEmploy playerEmploy;
	private static PlayerEmployment playerEmployment;
	private static PlayerData playerData;
	private static PlayerProduction playerProduction;
	
	/** 
	 * <b>PlayerCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerCommand({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerCommand} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerCommand(Dominion instance){
		plugin = instance;
		
		playerInfo = new PlayerInfo(plugin);
		playerStorage = new PlayerStorage(plugin);
		playerWithdraw = new PlayerWithdraw(plugin);
		playerDeposit = new PlayerDeposit(plugin);
		playerList = new PlayerList(plugin);
		playerDestroy = new PlayerDestroy(plugin);
		playerEmploy = new PlayerEmploy(plugin);
		playerEmployment = new PlayerEmployment(plugin);
		playerData = new PlayerData(plugin);
		playerProduction = new PlayerProduction(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		/* Default view
		 * View the information of the default settlement.
		 */
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Usage:  /dominion [info / storage / withdraw / deposit / list / destroy / employ / employment / data / production]");
			return true;
		}
		String subCommand = args[0];
		// - Get rid of the first argument.  We know what it is.
		List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		arguments.remove(0);
		args = arguments.toArray(new String[arguments.size()]);
		
		//--- SUB-COMMAND EXECUTORS ---//
		if (subCommand.equalsIgnoreCase("info") | subCommand.equalsIgnoreCase("view") | subCommand.equalsIgnoreCase("i")){
			return playerInfo.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("storage") | subCommand.equalsIgnoreCase("stores") | subCommand.equalsIgnoreCase("materials")
				| subCommand.equalsIgnoreCase("mats") | subCommand.equalsIgnoreCase("s")){
			return playerStorage.execute(sender,args);
		}
		if (subCommand.equalsIgnoreCase("withdraw") | subCommand.equalsIgnoreCase("wd") | subCommand.equalsIgnoreCase("w")){
			return playerWithdraw.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("deposit") | subCommand.equalsIgnoreCase("d")){
			return playerDeposit.execute(sender,args);
		}
		if(subCommand.equalsIgnoreCase("list") | subCommand.equalsIgnoreCase("l")){
			return playerList.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("destroy") | subCommand.equalsIgnoreCase("dismantle") | subCommand.equalsIgnoreCase("burn")){
			return playerDestroy.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("employ")){
			return playerEmploy.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("employment")){
			return playerEmployment.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("data")){
			return playerData.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("production") | subCommand.equalsIgnoreCase("prod") | subCommand.equalsIgnoreCase("p")){
			return playerProduction.execute(sender, args);
		}
		
		return false;
	}
}
