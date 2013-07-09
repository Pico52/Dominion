package com.pico52.dominion.command.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminSetMaterial</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminSetMaterial extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "set".
 */
public class AdminSetMaterial extends AdminSubCommand{

	/** 
	 * <b>AdminSetMaterial</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminSetMaterial({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminSetMaterial} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminSetMaterial(Dominion instance) {
		super(instance, "/ad setmaterial [settlement name] [material] [amount]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin set sub-command to set a material to an exact value in a settlement.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		if(args.length == 1){  // - They may have specified the settlement, but they didn't say what to set or how much.
			sender.sendMessage(logPrefix + "Usage: " + usage);
			sender.sendMessage(logPrefix + "Please issue the command again specifying the material and amount to be set.");
			return true;
		}
		if (args.length == 2){ // - They may have forgotten to put the amount to set.
			sender.sendMessage(logPrefix + "Usage: " + usage);
			sender.sendMessage(logPrefix + "Please issue the command again specifying the amount to set to.");
			return true;
		}
		// - Setting names to the arguments for easy readability.
		String settlement = args[0];
		String material = args[1].toLowerCase();
		int amount = 0;
		try{
			amount = Integer.parseInt(args[2]);
		} catch (NumberFormatException ex){
			sender.sendMessage(logPrefix + "\"" + args[2] + "\" is not a number.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		
		// - Make sure the settlement is legitimate
		if(!db.settlementExists(settlement)){
			sender.sendMessage(logPrefix + "Settlement: \"" + settlement + "\" does not exist.  (Case-sensitive)");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - Make sure the material type is legitimate.
		if(Material.matchMaterial(material) == null){
			sender.sendMessage(logPrefix + "The material \"" + material + "\" is not a material.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		// - Add the material to the database.
		if(db.setMaterial(settlement, material, amount))
			sender.sendMessage(logPrefix + "Successfully set " + amount + " " + material + " in " + settlement + ".");
		else
			sender.sendMessage(logPrefix + "Failed to set " + amount + " " + material + " in " + settlement + ".");
		
		return true;
	}
}
