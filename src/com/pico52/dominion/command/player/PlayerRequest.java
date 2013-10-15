package com.pico52.dominion.command.player;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerRequest</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerRequest extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "request".
 */
public class PlayerRequest extends PlayerSubCommand{

	/** 
	 * <b>PlayerRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerRequest({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerRequest} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerRequest(Dominion instance) {
		super(instance, "/d request [kingdom_invite / kingdom_request / liege_invite / liege_request] [player]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the request sub-command to request something from someone.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Sends a request to a player to join your/their kingdom or to become your/their liege.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide the player's name that you are trying to request.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String request = args[0], player = args[1];
		if(!db.playerExists(player)){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" does not exist.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(request.equalsIgnoreCase("trade")){
			sender.sendMessage(logPrefix + "To initiate a trade, please use the \"/d trade\" command.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if (plugin.getRequestManager().isBuildRequest(request)){
			sender.sendMessage(logPrefix + "To initiate a build request, please use the \"/d build\" command.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if (request.equalsIgnoreCase("found_kingdom") || request.equalsIgnoreCase("found_settlement")){
			sender.sendMessage(logPrefix + "To initiate a found request, please use the \"/d found\" command.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!plugin.getRequestManager().isRequestType(request)){
			sender.sendMessage(logPrefix + "The request \"" + request + "\" is not a proper request type.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int requesterId = db.getPlayerId(sender.getName()), targetId = db.getPlayerId(player), objectId = 0;
		if(plugin.getRequestManager().harassableRequestExistsAlready(requesterId, targetId, request)){
			sender.sendMessage(logPrefix + "You already have an active request of that type for " + player + ".");
			return true;
		}
		boolean toAdmin = false;
		if(plugin.getRequestManager().goesToAdmins(request))
			toAdmin = true;
		Location loc = plugin.getServer().getPlayer(sender.getName()).getLocation();
		double xcoord = loc.getBlockX();
		double zcoord = loc.getBlockZ();
		
		if(plugin.getRequestManager().createRequest(requesterId, targetId, toAdmin, 1, request, objectId, xcoord, zcoord))
			sender.sendMessage(logPrefix + "Successfully created your request.");
		else
			sender.sendMessage(logPrefix + "Failed to send your request.");
		return true;
	}
}