package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminSetOwner</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminSetOwner extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "setowner".
 */
public class AdminSetOwner extends AdminSubCommand{

	/** 
	 * <b>AdminSetOwner</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSetOwner({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminSetOwner} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminSetOwner(Dominion instance) {
		super(instance, "/ad setowner [kingdom/settlement/building/player] [kingdom/settlement/player/building id] [player name]");
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
			sender.sendMessage(logPrefix + "Sets the monarch, lord, or liege of a kingdom, settlement, building, or player to a player.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String task = args[0];
		if(args.length == 1){
			if(task.equalsIgnoreCase("kingdom")){
				sender.sendMessage(logPrefix + "Usage: /ad setowner kingdom [kingdom name] [player name]");
				return true;
			}
			if(task.equalsIgnoreCase("settlement")){
				sender.sendMessage(logPrefix + "Usage: /ad setowner settlement [settlement name] [player name]");
				return true;
			}
			if(task.equalsIgnoreCase("player")){
				sender.sendMessage(logPrefix + "Usage: /ad setowner player [player name] [liege name]");
				return true;
			}
			if(task.equalsIgnoreCase("building")){
				sender.sendMessage(logPrefix + "Usage: /ad setowner building [building id] [player name]");
				return true;
			}
			sender.sendMessage(logPrefix + "Usage: " + usage);
			
			return true;
		}
		String entity = args[1];
		if(args.length == 2){
			if(task.equalsIgnoreCase("kingdom")){
				if(db.kingdomExists(entity)){
					sender.sendMessage(logPrefix + "You need to select a player.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner kingdom " + entity + " [player name]");
				}else{
					sender.sendMessage(logPrefix + "\"" + entity + "\" is not a valid kingdom.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner kingdom [kingdom name] [player name]");
				}
				return true;
			}
			if(task.equalsIgnoreCase("settlement")){
				if(db.settlementExists(entity)){
					sender.sendMessage(logPrefix + "You need to select a player.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner settlement " + entity + " [player name]");
				}else{
					sender.sendMessage(logPrefix + "\"" + entity + "\" is not a valid settlement.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner settlement [settlement name] [player name]");
				}
				return true;
			}
			if(task.equalsIgnoreCase("player")){
				if(db.playerExists(entity)){
					sender.sendMessage(logPrefix + "You need to select a player to be the liege of " + entity + ".");
					sender.sendMessage(logPrefix + "Usage: /ad setowner player " + entity + " [liege name]");
				}else{
					sender.sendMessage(logPrefix + "\"" + entity + "\" is not a valid player.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner player [player name] [liege name]");
				}
				return true;
			}
			if(task.equalsIgnoreCase("building")){
				int buildingId = -1;
				try{
					buildingId = Integer.parseInt(args[1]);
				} catch (NumberFormatException ex){
					sender.sendMessage(logPrefix + "The building id you provided \"" + args[1] + "\" is not a number.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner building [building id] [player name]");
					return true;
				}
				if(db.thingExists(buildingId, "building")){
					sender.sendMessage(logPrefix + "You need to select a player to be the owner of building " + buildingId + ".");
					sender.sendMessage(logPrefix + "Usage: /ad setowner building [building id] [player name]");
				}else{
					sender.sendMessage(logPrefix + "The building id you provided does not exist.");
					sender.sendMessage(logPrefix + "Usage: /ad setowner building [building id] [player name]");
				}
				return true;
			}
			sender.sendMessage(logPrefix + "You did not select \"kingdom\", \"settlement\", \"building\" or \"player\" after using the setowner command.");
			sender.sendMessage(logPrefix + "Usage: /ad setowner [kingdom/settlement/player] [kingdom/settlement/player name] [player name]");
			return true;
		}
		// - All of the information should theoretically be here by this point.
		String player = args[2];
		int playerId;
		if((playerId = db.getPlayerId(player)) == -1){
			sender.sendMessage(logPrefix + "The player \"" + player + "\" you selected does not exist.");
			return true;
		}
		
		//---KINGDOM---//
		if(task.equalsIgnoreCase("kingdom")){
			if(!db.kingdomExists(entity)){
				sender.sendMessage(logPrefix + "The kingdom \"" + entity + "\" does not exist.");
				return true;
			}
			if(db.update("kingdom", "owner_id", playerId, "kingdom_id", db.getKingdomId(entity)))
				sender.sendMessage(logPrefix + player + " is now the monarch of " + entity + ".");
			else
				sender.sendMessage(logPrefix + "Failed to make " + player + " the monarch of " + entity + ".");			
			return true;
		}
		
		//---SETTLEMENT---//
		if(task.equalsIgnoreCase("settlement")){
			if(!db.settlementExists(entity)){
				sender.sendMessage(logPrefix + "The settlement \"" + entity + "\" does not exist.");
				return true;
			}
			if(db.update("settlement", "owner_id", playerId, "settlement_id", db.getSettlementId(entity)))
				sender.sendMessage(logPrefix + player + " is now the lord of " + entity + ".");
			else
				sender.sendMessage(logPrefix + "Failed to make " + player + " the lord of " + entity + ".");				
			return true;
		}
		
		//---BUILDING---//
		if(task.equalsIgnoreCase("building")){
			int buildingId = -1;
			try{
				buildingId = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "The building id you provided \"" + args[1] + "\" is not a number.");
				sender.sendMessage(logPrefix + "Usage: /ad setowner building [building id] [player name]");
				return true;
			}
			if(!db.thingExists(buildingId, "building")){
				sender.sendMessage(logPrefix + "The building id you provided does not exist.");
				return true;
			}
			if(db.update("building", "owner_id", playerId, "building_id", buildingId))
				sender.sendMessage(logPrefix + player + " is now the owner of building " + buildingId + ".");
			else
				sender.sendMessage(logPrefix + "Failed to make " + player + " the owner of building " + buildingId + ".");
			
			return true;
		}
		
		//---PLAYER---//
		if(task.equalsIgnoreCase("player")){
			if(!db.playerExists(entity)){
				sender.sendMessage(logPrefix + "The player \"" + entity + "\" does not exist.");
				return true;
			}
			if(!db.playerExists(player)){
				sender.sendMessage(logPrefix + "The player \"" + player + "\" does not exist.");
				return true;
			}
			if(db.update("player", "owner_id", playerId, "player_id", db.getPlayerId(entity)))
				sender.sendMessage(logPrefix + player + " is now the liege lord of " + entity + ".");
			else
				sender.sendMessage(logPrefix + "Failed to make " + player + " the liege lord of " + entity + ".");
			return true;
		}
		
		//--- DEFAULT---//
		sender.sendMessage(logPrefix + "Something wasn't input properly.");
		sender.sendMessage(logPrefix + "Usage: " + usage);
		
		return true;
	}

}
