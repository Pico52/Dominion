package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.command.player.PlayerSubCommand;

/** 
 * <b>AdminSquelch</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminSquelch extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the admin sub-command "Squelch".
 */
public class AdminSquelch extends PlayerSubCommand{

	/** 
	 * <b>AdminSqueltch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSquelch({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminSquelch} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminSquelch(Dominion instance) {
		super(instance, "/ad squelch [player name] [request type]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender,{@link String} args[])
	 * <br>
	 * <br>
	 * Manages the squelch sub-command to keep a player from using a certain request.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Prevents a player from requesting a certain type of request.  " +
					"You may also use \"build\" to prevent all build requests or \"all\" for all requests in general.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String player = args[0];
		if(!db.playerExists(player)){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" can not be found.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must indicate the request type that is to be squelched.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String requestType = args[1];
		if(!plugin.getSqueltchManager().isSquelchType(requestType)){
			sender.sendMessage(logPrefix + "The request type \"" + requestType + "\" is not a valid squelch type.");
			return true;
		}
		int playerId = plugin.getPlayerManager().getPlayerId(player);
		if(plugin.getSqueltchManager().createSquelch(playerId, requestType))
			sender.sendMessage(logPrefix + "Successfully squelched " + player + "\'s ability to use the request(s): " + requestType + ".");
		else
			sender.sendMessage(logPrefix + "Failed to squeltch the player.");
		return true;
	}
}
