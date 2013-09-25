package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerBuild</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerBuild extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "build".
 */
public class PlayerBuild extends PlayerSubCommand{

	/** 
	 * <b>PlayerBuild</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerBuild({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerBuild} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerBuild(Dominion instance) {
		super(instance, "/d build [building type] [settlement] (optional)[level]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the build sub-command to request the addition of a building.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Requests an admin to approve a building at the current location of the specified type and level.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide the name of the settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String building = args[0];
		if(!plugin.getBuildingManager().isBuilding(building)){
			sender.sendMessage(logPrefix + "\"" + building + "\" is not a building.\nTry using the \"/d data building\" command for the list of buildings.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String settlement = args[1];
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "The settlement \"" + settlement + "\" does not exist.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String player = sender.getName();
		int playerId = db.getPlayerId(player), settlementId = db.getSettlementId(settlement);
		if(!plugin.getSettlementManager().isOwner(playerId, settlementId) && 
				!plugin.getPermissionManager().hasPermission(playerId, "build", settlementId)){
			sender.sendMessage(logPrefix + "You do not have permission to build in this settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		sender.sendMessage(logPrefix + "The requests system is still under development.\n" + logPrefix + "No build request has been sent.");
		return true;
	}
}
