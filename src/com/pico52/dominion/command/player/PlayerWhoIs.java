package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerWhoIs</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerWhoIs extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "WhoIs".
 */
public class PlayerWhoIs extends PlayerSubCommand{

	/** 
	 * <b>PlayerWhoIs</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerWhoIs({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerWhoIs} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerWhoIs(Dominion instance) {
		super(instance, "/d whois [player name]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the whois sub-command to display information about a player.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Displays information about a player.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String player = args[0];
		if(!db.playerExists(player)){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" can not be found.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		sender.sendMessage("§a=======================================§f\n" + plugin.getPlayerManager().outputData(player) + "\n§a=======================================§f");
		return true;
	}

}
