package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerCancel</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerCancel extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "cancel".
 */
public class PlayerCancel extends PlayerSubCommand{

	/** 
	 * <b>PlayerCancel</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerCancel({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerCancel} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerCancel(Dominion instance) {
		super(instance, "/d cancel [trade_id]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the cancel sub-command to cancel a trade.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Cancels an active trade agreement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int tradeId = 0;
		try{
			tradeId = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "Incorrect input.  " + args[0] + " is not a trade id.  Ids must be integers.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int settlement1Id = plugin.getTradeManager().getSettlement1(tradeId);
		int settlement2Id = plugin.getTradeManager().getSettlement2(tradeId);
		int settlement1Owner = db.getOwnerId("settlement", settlement1Id);
		int settlement2Owner = db.getOwnerId("settlement", settlement2Id);
		int playerId = db.getPlayerId(sender.getName());
		if(playerId != settlement1Owner && 
				playerId != settlement2Owner && 
				!plugin.getPermissionManager().hasPermission(playerId, "trade", settlement1Id) && 
				!plugin.getPermissionManager().hasPermission(playerId, "trade", settlement2Id)){
			sender.sendMessage(logPrefix + "You do not have the authority to cancel this trade.");
			return true;
		}
		if(plugin.getTradeManager().cancelTrade(tradeId))
			sender.sendMessage(logPrefix + "Successfully canceled trade #" + tradeId + "!");
		else
			sender.sendMessage(logPrefix + "Failed to cancel trade #" + tradeId + ".");
		return true;
	}

}
