package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminUnsquelch</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminUnsquelch extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the admin sub-command "Unsquelch".
 */
public class AdminUnsquelch extends AdminSubCommand{

	/** 
	 * <b>AdminUnsqueltch</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSquelch({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminUnsquelch} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminUnsquelch(Dominion instance) {
		super(instance, "/ad unsquelch [player name] [request type / \"all\"]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender,{@link String} args[])
	 * <br>
	 * <br>
	 * Manages the unsquelch sub-command to allow a player to use a request type again.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Allows a player to  make a certain request again.  " +
					"You may also use \"build\" to allow all build requests or \"all\" for all requests in general.");
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
			sender.sendMessage(logPrefix + "You must indicate the request type that is to be unsquelched.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String requestType = args[1];
		if(!plugin.getSqueltchManager().isSquelchType(requestType)){
			sender.sendMessage(logPrefix + "The request type \"" + requestType + "\" is not a valid squelch type.");
			return true;
		}
		int playerId = plugin.getPlayerManager().getPlayerId(player);
		boolean success = false;
		if(requestType.equalsIgnoreCase("all"))
			success = plugin.getSqueltchManager().removeAllSquelches(playerId);
		else
			success = plugin.getSqueltchManager().removeSquelch(playerId, requestType);
		if(success)
			sender.sendMessage(logPrefix + "Successfully unsquelched " + player + "\'s ability to use the request(s): " + requestType + ".");
		else
			sender.sendMessage(logPrefix + "Failed to unsqueltch the player.");
		return true;
	}

}
