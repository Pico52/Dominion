package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerTrade</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerTrade extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "trade".
 */
public class PlayerTrade extends PlayerSubCommand{

	/** 
	 * <b>PlayerTrade</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerTrade({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerTrade} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerTrade(Dominion instance) {
		super(instance, "/d trade [from settlement] [target settlement]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the trade sub-command to request a trade between settlements.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0 ){
			sender.sendMessage(logPrefix + "Sends a request to a settlement to initiate passive trading.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide the name of the settlement you are trying to trade with.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String settlement = args[0], targetSettlement = args[1];
		if(settlement == targetSettlement){
			sender.sendMessage(logPrefix + "Settlements cannot trade with themselves.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "The settlement \"" + settlement + "\" does not exist.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(!db.settlementExists(targetSettlement)){
			sender.sendMessage(logPrefix + "The settlement \"" + targetSettlement + "\" does not exist.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int requesterId = db.getPlayerId(sender.getName()), settlementId = db.getSettlementId(settlement);
		if(!plugin.getSettlementManager().isOwner(requesterId, settlementId) && 
				!plugin.getPermissionManager().hasPermission(requesterId, "trade", settlementId)){
			sender.sendMessage(logPrefix + "You do not have permission to initiate trade for " + settlement + ".");
			return true;
		}
		int targetId = db.getSettlementId(targetSettlement), targetOwnerId  = db.getOwnerId("settlement",  targetId);
		if(targetOwnerId == 0){
			sender.sendMessage(logPrefix + "There is currently no owner of " + settlement + ".  The trade cannot be made.");
			return true;
		}
		if(plugin.getTradeManager().getNumberOfActiveTradesBetween(settlementId, targetId) > 0){
			sender.sendMessage(logPrefix + "These settlements already have an active trade agreement.  Multiple trade agreements will be available in a later version of Dominion.");
			return true;
		}
		double xcoord = plugin.getSettlementManager().getX(targetId), zcoord = plugin.getSettlementManager().getZ(targetId);
		
		if(plugin.getRequestManager().createRequest(requesterId, targetOwnerId, false, 1, "trade", "", settlementId, targetId, xcoord, zcoord))
			sender.sendMessage(logPrefix + "Successfully requested a trade between " + settlement + " and " + targetSettlement + ".");
		else
			sender.sendMessage(logPrefix + "Failed to send your trade request.");
		return true;
	}
}