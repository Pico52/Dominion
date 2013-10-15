package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminCreate</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminCreate extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the administrator sub-command "create".
 */
public class AdminCreate extends AdminSubCommand{
	
	/** 
	 * <b>AdminCreate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminCreate({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminCreate} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminCreate(Dominion instance){
		super(instance, "/ad create [kingdom/settlement] [kingdom/settlement name]");
	}
	
	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender,{@link String} args[])
	 * <br>
	 * <br>
	 * Manages the create sub-command to create a kingdom or settlement with a given name if 
	 * all values were properly provided.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Creates a kingdom or settlement.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String entity = args[0];
		if(args.length == 1){
			if(entity.equalsIgnoreCase("kingdom")){
				sender.sendMessage(logPrefix + "Usage: /ad create kingdom [kingdom name]");
			}
			if(entity.equalsIgnoreCase("settlement")){
				sender.sendMessage(logPrefix + "Usage: /ad create settlement [settlement name]");
			}
			return true;
		}
		String entityName = args[1];
		if(entity.equalsIgnoreCase("kingdom")){
			if(db.kingdomExists(entityName)){
				sender.sendMessage(logPrefix + "The kingdom \"" + entityName + "\" already exists.");
				return true;
			}
			if(db.createKingdom(entityName)){
				sender.sendMessage(logPrefix + "The new kingdom \"" + entityName + "\" has been created!");
				plugin.getLogger().info(logPrefix + "The new kingdom \"" + entityName + "\" has been created!");
			}else{
				sender.sendMessage(logPrefix + "The kingdom \"" + entityName + "\" could not be created.");
				plugin.getLogger().info(logPrefix + "The kingdom \"" + entityName + "\" could not be created.");
			}
			return true;
		}
		if(entity.equalsIgnoreCase("settlement")){
			if(db.settlementExists(entityName)){
				sender.sendMessage(logPrefix + "The settlement \"" + entityName + "\" already exists.");
				return true;
			}
			if(plugin.getSettlementManager().createSettlement(entityName)){
				sender.sendMessage(logPrefix + "The new settlement \"" + entityName + "\" has been created!");
				plugin.getLogger().info(logPrefix + "The new settlement \"" + entityName + "\" has been created!");
			}else{
				sender.sendMessage(logPrefix + "The settlement \"" + entityName + "\" could not be created.");
				plugin.getLogger().info(logPrefix + "The settlement \"" + entityName + "\" could not be created.");
			}
			return true;
		}
		sender.sendMessage(logPrefix + "Usage: " + usage);
		return true;
	}
}
