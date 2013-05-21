package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminForce</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminForce extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "force".
 */
public class AdminForce extends AdminSubCommand{

	/** 
	 * <b>AdminForce</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminForce({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminForce} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminForce(Dominion instance) {
		super(instance, "/admindominion force [kingdom/settlement/player] [kingdom/settlement/player name] [player name]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin force sub-command to force a player into the ownership role of a settlement or kingdom.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Forces the monarch, lord, or liege of a kingdom, settlement, or player to a player.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		String task = args[0];
		if(args.length == 1){
			if(task.equalsIgnoreCase("kingdom")){
				sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force kingdom [kingdom name] [player name]");
				return true;
			}
			if(task.equalsIgnoreCase("settlement")){
				sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force settlement [settlement name] [player name]");
				return true;
			}
			if(task.equalsIgnoreCase("player")){
				sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force player [player name] [liege name]");
				return true;
			}
			sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force [kingdom/settlement/player] [kingdom/settlement/player name] [player name]");
			
			return true;
		}
		String entity = args[1];
		if(args.length == 2){
			if(task.equalsIgnoreCase("kingdom")){
				if(plugin.getDBHandler().kingdomExists(entity)){
					sender.sendMessage(plugin.getLogPrefix() + "You need to select a player.");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force kingdom " + entity + " [player name]");
				}else{
					sender.sendMessage(plugin.getLogPrefix() + "\"" + entity + "\" is not a valid kingdom.");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force kingdom [kingdom name] [player name]");
				}
				return true;
			}
			if(task.equalsIgnoreCase("settlement")){
				if(plugin.getDBHandler().settlementExists(entity)){
					sender.sendMessage(plugin.getLogPrefix() + "You need to select a player.");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force settlement " + entity + " [player name]");
				}else{
					sender.sendMessage(plugin.getLogPrefix() + "\"" + entity + "\" is not a valid settlement.");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force settlement [settlement name] [player name]");
				}
				return true;
			}
			if(task.equalsIgnoreCase("player")){
				if(plugin.getDBHandler().playerExists(entity)){
					sender.sendMessage(plugin.getLogPrefix() + "You need to select a player to be the liege of " + entity + ".");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force player " + entity + " [liege name]");
				}else{
					sender.sendMessage(plugin.getLogPrefix() + "\"" + entity + "\" is not a valid player.");
					sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force player [player name] [liege name]");
				}
				return true;
			}
			sender.sendMessage(plugin.getLogPrefix() + "You did not select \"kingdom\" or \"settlement\" or \"player\" after using force.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: /admindominion force [kingdom/settlement/player] [kingdom/settlement/player name] [player name]");
			return true;
		}
		// - All of the information should theoretically be here by this point.
		String player = args[2];
		int playerId;
		if((playerId = plugin.getDBHandler().getPlayerId(player)) == -1){
			sender.sendMessage(plugin.getLogPrefix() + "The player \"" + player + "\" you selected does not exist.");
			return true;
		}
		
		//---KINGDOM---//
		if(task.equalsIgnoreCase("kingdom")){
			if(!plugin.getDBHandler().kingdomExists(entity)){
				sender.sendMessage(plugin.getLogPrefix() + "The kingdom \"" + entity + "\" does not exist.");
				return true;
			}
			if(plugin.getDBHandler().update("kingdom", "monarch_id", playerId, "kingdom_id", plugin.getDBHandler().getKingdomId(entity)))
				sender.sendMessage(plugin.getLogPrefix() + player + " is now the monarch of " + entity + ".");
			else
				sender.sendMessage(plugin.getLogPrefix() + "Failed to make " + player + " the monarch of " + entity + ".");			
			return true;
		}
		
		//---SETTLEMENT---//
		if(task.equalsIgnoreCase("settlement")){
			if(!plugin.getDBHandler().settlementExists(entity)){
				sender.sendMessage(plugin.getLogPrefix() + "The settlement \"" + entity + "\" does not exist.");
				return true;
			}
			if(plugin.getDBHandler().update("settlement", "lord_id", playerId, "settlement_id", plugin.getDBHandler().getSettlementId(entity)))
				sender.sendMessage(plugin.getLogPrefix() + player + " is now the lord of " + entity + ".");
			else
				sender.sendMessage(plugin.getLogPrefix() + "Failed to make " + player + " the lord of " + entity + ".");				
			return true;
		}
		
		//---PLAYER---//
		if(task.equalsIgnoreCase("player")){
			if(!plugin.getDBHandler().playerExists(entity)){
				sender.sendMessage(plugin.getLogPrefix() + "The player \"" + entity + "\" does not exist.");
				return true;
			}
			if(!plugin.getDBHandler().playerExists(player)){
				sender.sendMessage(plugin.getLogPrefix() + "The player \"" + player + "\" does not exist.");
				return true;
			}
			if(plugin.getDBHandler().update("player", "liege_id", playerId, "player_id", plugin.getDBHandler().getPlayerId(entity)))
				sender.sendMessage(plugin.getLogPrefix() + player + " is now the liege lord of " + entity + ".");
			else
				sender.sendMessage(plugin.getLogPrefix() + "Failed to make " + player + " the liege lord of " + entity + ".");
			return true;
		}
		
		//--- DEFAULT---//
		sender.sendMessage(plugin.getLogPrefix() + "Something wasn't input properly.");
		sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
		
		return true;
	}

}
