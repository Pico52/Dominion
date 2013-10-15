package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.object.RequestManager;

/** 
 * <b>PlayerRequests</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerRequest extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "requests".
 */
public class PlayerRequests extends PlayerSubCommand{

	/** 
	 * <b>PlayerRequests</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerRequests({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerRequests} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerRequests(Dominion instance) {
		super(instance, "/d requests (Request id) (Accept/Reject)");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the requests sub-command to display active requests to this player.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		RequestManager rm = plugin.getRequestManager();
		int playerId = db.getPlayerId(sender.getName());
		if(args.length == 0){
			String message = "§a==========Requests==========§f\n";
			int[] requestsToUser = rm.getRequestsTo(playerId);
			if(requestsToUser.length == 0)
				message += "There are no active requests for you.\n";
			else {
				for(int request: requestsToUser)
					message += rm.getRequestOutput(request) + "\n";
			}
			message += "§a==========================§f";
			sender.sendMessage(message);
			return true;
		}
		int requestId = 0;
		try{
			requestId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  \"" + args[0] + "\" is not an id number.  The number must be an integer.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			if(playerId != rm.getToId(requestId) && playerId != rm.getSenderId(requestId)){
				sender.sendMessage(logPrefix + "You cannot view a request that you are not a part of.");
				return true;
			}
			sender.sendMessage(logPrefix + rm.getRequestOutput(requestId));
			return true;
		}
		String response = args[1];
		if(response.equalsIgnoreCase("cancel") || response.equalsIgnoreCase("delete") || response.equalsIgnoreCase("remove")){
			if(playerId != rm.getSenderId(requestId)){
				sender.sendMessage(logPrefix + "You cannot cancel a request that you did not issue.");
				return true;
			}
			if(rm.removeRequest(requestId))
				sender.sendMessage(logPrefix + "Request " + requestId + " has been canceled.");
			else
				sender.sendMessage(logPrefix + "Failed to cancel request " + requestId + ".");
			return true;
		}
		if(response.equalsIgnoreCase("accept") || response.equalsIgnoreCase("yes")){
			if(playerId != rm.getToId(requestId)){
				sender.sendMessage(logPrefix + "You cannot accept a request for someone else.");
				return true;
			}
			if(rm.acceptRequest(requestId))
				sender.sendMessage(logPrefix + "Request " + requestId + " has been accepted.");
			else
				sender.sendMessage(logPrefix + "Failed to accept request " + requestId + ".");
			return true;
		}
		if(response.equalsIgnoreCase("reject") || response.equalsIgnoreCase("decline") || response.equalsIgnoreCase("deny") || response.equalsIgnoreCase("no")){
			if(playerId != rm.getToId(requestId)){
				sender.sendMessage(logPrefix + "You cannot decline a request for someone else.");
				return true;
			}
			if(rm.declineRequest(requestId))
				sender.sendMessage(logPrefix + "Request " + requestId + " has been declined.");
			else
				sender.sendMessage(logPrefix + "Failed to decline request " + requestId + ".");
			return true;
		}
		sender.sendMessage(logPrefix + "Failed to manage the request.\n" + logPrefix + "Usage: " + usage);
		return true;
	}
}