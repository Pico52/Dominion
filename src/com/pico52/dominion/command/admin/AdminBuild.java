package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminBuild</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminBuild extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "build".
 */
public class AdminBuild extends AdminSubCommand{
	
	/** 
	 * <b>AdminBuild</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminBuild({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminBuild} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminBuild(Dominion instance) {
		super(instance, "/ad build [settlement] [class]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin build sub-command to create a building or structure.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "build" but gave no other arguments.
			sender.sendMessage(logPrefix + "Constructs a new building at your location.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide a class type for this building.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String settlement = args[0];
		String classification = args[1];
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + settlement + " is not a valid settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(plugin.getBuildingManager().createBuilding(db.getSettlementId(settlement), classification)){
			int newBuildingId = db.getNewestId("building");
			sender.sendMessage(logPrefix + "Successfully created a " + classification + " id#: " + newBuildingId + " for " + settlement + "!");
			plugin.getLogger().info("Successfully created a " + classification + " for " + settlement + "!");
		} else {
			sender.sendMessage(logPrefix + "Failed to create the " + classification + " for " + settlement + ".");
			plugin.getLogger().info("Failed to create the " + classification + " for " + settlement + ".");
		}
		
		return true;
	}
}
