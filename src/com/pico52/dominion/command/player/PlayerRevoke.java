package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerRevoke</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerRevoke extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "revoke".
 */
public class PlayerRevoke extends PlayerSubCommand{

	/** 
	 * <b>PlayerRevoke</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerRevoke({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerRevoke} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerRevoke(Dominion instance) {
		super(instance, "/d revoke [player] [permission] [settlement]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the revoke sub-command to revoke a permission from a player for a settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Removes a permission from a player.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide a permission and the name of the settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(logPrefix + "You must provide the name of the settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String player = args[0], permission = args[1], settlement = args[2];
		if(!db.playerExists(player)){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" does not exist.");
			return true;
		}
		if(!plugin.getPermissionManager().isNode(permission)){
			sender.sendMessage(logPrefix + "\"" + permission + "\" is not a proper permission.");
			return true;
		}
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "The settlement \"" + settlement + "\" does not exist.");
			return true;
		}
		int playerId = db.getPlayerId(player), settlementId = db.getSettlementId(settlement);
		if(!plugin.getSettlementManager().isOwner(player, settlement) && 
				!plugin.getPermissionManager().hasPermission(playerId, "revoke_all", settlementId) && 
				!plugin.getPermissionManager().hasPermission(playerId, "revoke_" + permission, settlementId)){
			sender.sendMessage(logPrefix + "You are not permitted to revoke this permission in " + settlement + ".");
			return true;
		}
		if(plugin.getPermissionManager().removePermission(player, permission, settlementId))
			sender.sendMessage(logPrefix + player + " has been revoked the " + permission + " permission.");
		else
			sender.sendMessage(logPrefix + "Failed to revoke the \"" + permission + "\" permission from " + player + ".");
		
		return true;
	}
}