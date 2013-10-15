package com.pico52.dominion.command.player;

import org.bukkit.Location;
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
		super(instance, "/d build [building type] [settlement] (level)");
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
		int level = 1;
		if(args.length > 2){
			try{
				level = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "Incorrect input.  " + args[2] + " is not a proper level.  Levels must be integers.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
		}
		Location loc = plugin.getServer().getPlayer(sender.getName()).getLocation();
		double xcoord = loc.getBlockX();
		double zcoord = loc.getBlockZ();

		if(plugin.getRequestManager().createRequest(playerId, 0, true, level, "build_" + building, "", settlementId, 0, xcoord, zcoord))
			sender.sendMessage(logPrefix + "Successfully requested a level " + level + " " + building + " to be created in " + settlement + " at your current location.");
		else
			sender.sendMessage(logPrefix + "Failed to create your building request.");
		return true;
	}
}
