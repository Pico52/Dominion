package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerPermit</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerPermit extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "permit".
 */
public class PlayerPermit extends PlayerSubCommand{

	/** 
	 * <b>PlayerPermit</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerPermit({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerPermit} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerPermit(Dominion instance) {
		super(instance, "/d permit [player] [permission] [settlement]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the permit sub-command to permit a player to do/use something in a settlement.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Permits a player to perform/use specific functions of a settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide a permission node and the name of the settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String player = args[0], permission = args[1];
		if(!db.playerExists(player)){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" does not exist.");
			return true;
		}
		if(!plugin.getPermissionManager().isNode(permission)){
			sender.sendMessage(logPrefix + "\"" + permission + "\" is not a proper permission.");
			return true;
		}
		int playerId = db.getPlayerId(player), referenceId = 0;
		if(plugin.getPermissionManager().getReference(permission).equalsIgnoreCase("settlement")){
			if(args.length == 2){
				sender.sendMessage(logPrefix + "You must provide the name of the settlement.");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
			String settlement = args[2];
			if(!db.settlementExists(settlement)){
				sender.sendMessage(logPrefix + "The settlement \"" + settlement + "\" does not exist.");
				return true;
			}
			referenceId = db.getSettlementId(settlement);
			if(!plugin.getSettlementManager().isOwner(player, settlement) && 
				!plugin.getPermissionManager().hasPermission(playerId, "permit_all", referenceId) && 
				!plugin.getPermissionManager().hasPermission(playerId, "permit_" + permission, referenceId)){
					sender.sendMessage(logPrefix + "You are not permitted to grant this permission in " + settlement + ".");
				return true;
			}
			if(plugin.getPermissionManager().isForbidden(playerId, permission, referenceId)){
				if(!plugin.getSettlementManager().isOwner(playerId, settlement)){
					sender.sendMessage(logPrefix + player + " is forbidden from being granted the " + permission + " permission.\n" + 
							logPrefix + "You must either first remove the prohibition from this player or you must be the owner of the settlement to grant this privilege.");
					return true;
				} else {
					sender.sendMessage(logPrefix + "NOTICE:  The player you are granting this permission has been deemed forbidden from using it.");
				}
			}
		} else if(plugin.getPermissionManager().getReference(permission).equalsIgnoreCase("player")){
			// - Players always have permission to refer to themselves.
			// - Players cannot be forbidden from referring to themselves.
			referenceId = playerId;
		}
		if(plugin.getPermissionManager().createPermission(db.getPlayerId(sender.getName()), playerId, permission, referenceId))
			sender.sendMessage(logPrefix + player + " has been granted the \"" + permission + "\" permission.");
		else
			sender.sendMessage(logPrefix + "Failed to grant the \"" + permission + "\" permission to " + player + ".");
		
		return true;
	}
}