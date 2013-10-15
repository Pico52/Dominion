package com.pico52.dominion.command.player;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerFound</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerFound extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "found".
 */
public class PlayerFound extends PlayerSubCommand{

	/** 
	 * <b>PlayerFound</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerFound({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerFound} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerFound(Dominion instance) {
		super(instance, "/d found [kingdom/settlement] [kingdom/settlement name]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the found sub-command to request a kingdom or settlement to be founded.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Founds a new kingdom or settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String entity = args[0];
		if(args.length == 1){
			if(entity.equalsIgnoreCase("kingdom")){
				sender.sendMessage(logPrefix + "Usage: /ad found kingdom [kingdom name]");
			}
			if(entity.equalsIgnoreCase("settlement")){
				sender.sendMessage(logPrefix + "Usage: /ad found settlement [settlement name]");
			}
			return true;
		}
		String playerName = sender.getName(), entityName = args[1];
		int ownerId = db.getPlayerId(playerName);
		Location loc = plugin.getServer().getPlayer(sender.getName()).getLocation();
		double xcoord = loc.getX(), zcoord = loc.getZ();
		if(entity.equalsIgnoreCase("kingdom")){
			if(db.kingdomExists(entityName)){
				sender.sendMessage(logPrefix + "The kingdom \"" + entityName + "\" already exists.");
				return true;
			}
			if(plugin.getRequestManager().createRequest(ownerId, 0, true, 1, "found_kingdom", entityName, 0, xcoord, zcoord)){
				sender.sendMessage(logPrefix + "The new kingdom \"" + entityName + "\" has been requested!");
				plugin.getLogger().info(logPrefix + "The new kingdom \"" + entityName + "\" has been requested!");
			}else{
				sender.sendMessage(logPrefix + "The kingdom \"" + entityName + "\" could not be requested.");
				plugin.getLogger().info(logPrefix + "The kingdom \"" + entityName + "\" could not be requested.");
			}
			return true;
		}
		if(entity.equalsIgnoreCase("settlement")){
			if(db.settlementExists(entityName)){
				sender.sendMessage(logPrefix + "The settlement \"" + entityName + "\" already exists.");
				return true;
			}
			if(plugin.getRequestManager().createRequest(ownerId, 0, true, 1, "found_settlement", entityName, 0, xcoord, zcoord)){
				sender.sendMessage(logPrefix + "The new settlement \"" + entityName + "\" has been requested!");
				plugin.getLogger().info(logPrefix + "The new settlement \"" + entityName + "\" has been requested.");
			}else{
				sender.sendMessage(logPrefix + "The settlement \"" + entityName + "\" could not be requested.");
				plugin.getLogger().info(logPrefix + "The settlement \"" + entityName + "\" could not be requested.");
			}
			return true;
		}
		sender.sendMessage(logPrefix + "Usage: " + usage);
		return true;
	}

}
