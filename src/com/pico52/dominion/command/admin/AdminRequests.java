package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.object.RequestManager;

/** 
 * <b>AdminRequests</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminRequest extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the admin sub-command "requests".
 */
public class AdminRequests extends AdminSubCommand{

	/** 
	 * <b>AdminRequests</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminRequests({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminRequests} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminRequests(Dominion instance) {
		super(instance, "/d requests (Request id) (Accept/Reject)");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the requests sub-command to display active requests to admins.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		RequestManager rm = plugin.getRequestManager();
		int playerId = db.getPlayerId(sender.getName());
		if(args.length == 0){
			String message = "§a==========Requests==========§r \n";
			int[] requestsToAdmins = rm.getRequestsToAdmins();
			if(requestsToAdmins.length == 0)
				message += "There are no active requests for administrators.\n";
			else {
				for(int request: requestsToAdmins)
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
		if(!db.thingExists(requestId, "request")){
			sender.sendMessage(logPrefix + "Request #" + requestId + " has not been found.");
			return true;
		}
		if(args.length == 1){
			if(playerId != rm.getToId(requestId) && playerId != rm.getSenderId(requestId) && !rm.isToAdmin(requestId)){
				sender.sendMessage(logPrefix + "§4Note:  You are not involved in this request.§f");
			}
			sender.sendMessage(logPrefix + rm.getRequestOutput(requestId));
			return true;
		}
		String response = args[1];
		if(response.equalsIgnoreCase("accept") || response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
			if(rm.acceptRequest(requestId))
				sender.sendMessage(logPrefix + "Request " + requestId + " has been accepted.");
			else
				sender.sendMessage(logPrefix + "Failed to accept request " + requestId + ".");
			return true;
		}
		if(response.equalsIgnoreCase("reject") || response.equalsIgnoreCase("decline") || response.equalsIgnoreCase("deny") || response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")){
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