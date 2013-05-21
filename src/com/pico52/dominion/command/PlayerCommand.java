package com.pico52.dominion.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.command.player.PlayerDeposit;
import com.pico52.dominion.command.player.PlayerInfo;
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
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		String command = "[PLAYER_COMMAND]" + sender.getName() + ":/" + commandLabel + " ";
		for(String arg: args){
			command += arg + " ";
		}
		plugin.getLogger().info(command);
		
		/* Default view
		 * View the information of the default settlement.
		 */
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Usage:  /dominion [info / storage / withdraw / deposit]");
			return true;
		}
		String subCommand = args[0];
		// - Get rid of the first argument.  We know what it is.
		List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		arguments.remove(0);
		args = arguments.toArray(new String[arguments.size()]);
		
		//--- SUB-COMMAND EXECUTORS ---//
		if (subCommand.equalsIgnoreCase("info") | subCommand.equalsIgnoreCase("view")){
			return playerInfo.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("storage") | subCommand.equalsIgnoreCase("stores") | subCommand.equalsIgnoreCase("materials") | subCommand.equalsIgnoreCase("mats")){
			return playerStorage.execute(sender,args);
		}
		if (subCommand.equalsIgnoreCase("withdraw") | subCommand.equalsIgnoreCase("wd") | subCommand.equalsIgnoreCase("w")){
			return playerWithdraw.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("deposit")){
			return playerDeposit.execute(sender,args);
		}
		
		return false;
	}
}
