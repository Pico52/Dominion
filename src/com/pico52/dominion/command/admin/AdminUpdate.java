package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminUpdate</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminUpdate extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "update".
 */
public class AdminUpdate extends AdminSubCommand{

	/** 
	 * <b>AdminUpdate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminUpdate({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminUpdate} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminUpdate(Dominion instance) {
		super(instance, "/admindominion update [table name] [column name] [value] [where identity] [identifier]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin update sub-command to update a column in a table specifically.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Updates a column in a table at a specified row.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(logPrefix + "You must provide a column, value, column to identify by, and an identifier.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(logPrefix + "You must provide a value, column to identify by, and an identifier.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 3){
			sender.sendMessage(logPrefix + "You must provide a column to identify by and an identifier.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 4){
			sender.sendMessage(logPrefix + "You must provide an identifier.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String table = args[0];
		String column = args[1];
		String value = args[2];
		String identifyBy = args[3];
		String identifier = args[4];
		
		if(db.update(table, column, value, identifyBy, identifier)){
			sender.sendMessage(logPrefix + "Successfully updated the " + column + " column in the " + table+ " table to " + value + " where " + identifyBy + "=" + identifier);
			plugin.getLogger().info("Successfully updated the " + column + " column in the " + table+ " table to " + value + " where " + identifyBy + "=" + identifier);
		}else{
			sender.sendMessage(logPrefix + "Failed to update the " + column + " column in the " + table+ " table to " + value + " where " + identifyBy + "=" + identifier);
			plugin.getLogger().info("Failed to update the " + column + " column in the " + table+ " table to " + value + " where " + identifyBy + "=" + identifier);
		}
		return true;
	}

}
