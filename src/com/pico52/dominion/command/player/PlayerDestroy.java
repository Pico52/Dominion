package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerDestroy</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerDestroy extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "destroy".
 */
public class PlayerDestroy extends PlayerSubCommand{

	/** 
	 * <b>PlayerDestroy</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerDestroy({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerDestroy} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerDestroy(Dominion instance) {
		super(instance, "/d destroy [building id]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the destroy sub-command to destroy an entity so long as the player is the owner of the entity.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Destroys a building so long as you are the owner of it.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - There should be an argument by this point.  Hopefully it's a building Id.
		int building = 0;
		try{
			building = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  " + args[0] + " is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int playerId;
		if((playerId = db.getPlayerId(sender.getName())) == -1){
			sender.sendMessage(logPrefix + "Could not find your player Id!");
			return true;
		}
		int settlementId = plugin.getBuildingManager().getSettlementId(building);
		if(db.getOwnerId("building", building) != playerId && 
				!plugin.getSettlementManager().isOwner(playerId, settlementId) && 
				!plugin.getPermissionManager().hasPermission(playerId, "destroy", settlementId)){
			sender.sendMessage(logPrefix + "You do not have permission to destroy this building!");
			return true;
		}
		if(db.remove("building", building)){
			sender.sendMessage(logPrefix + "Successfully destroyed the building!");
			plugin.getLogger().info("Successfully removed a building.");
		} else {
			sender.sendMessage(logPrefix + "Failed to destroy the building.");
			plugin.getLogger().info("Failed to remove a building.");
		}
		return true;
	}

}
