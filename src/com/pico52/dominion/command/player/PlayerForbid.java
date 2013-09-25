package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerForbid</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerForbid extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "forbid".
 */
public class PlayerForbid extends PlayerSubCommand{

	/** 
	 * <b>PlayerForbid</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerForbid({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerPermit} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerForbid(Dominion instance) {
		super(instance, "/d forbid [player] [permission] [settlement]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the permit sub-command to forbid a player from doing/using something in a settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Forbids a permission from being added to a player.");
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
				!plugin.getPermissionManager().hasPermission(playerId, "forbid_all", settlementId) && 
				!plugin.getPermissionManager().hasPermission(playerId, "forbid_" + permission, settlementId)){
			sender.sendMessage(logPrefix + "You are not permitted to forbid this permission in " + settlement + ".");
			return true;
		}
		if(plugin.getPermissionManager().createPermission(db.getPlayerId(sender.getName()), playerId, "forbidden_" + permission, settlementId))
			sender.sendMessage(logPrefix + player + " has been forbidden to be granted the " + permission + " permission.");
		else
			sender.sendMessage(logPrefix + "Failed to forbid the \"" + permission + "\" permission for " + player + ".");
		
		return true;
	}
}