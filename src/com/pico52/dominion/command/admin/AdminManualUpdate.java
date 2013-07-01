package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>AdminManualUpdate</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminManualUPdate extends {@link AdminSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "manualupdate".
 */
public class AdminManualUpdate extends AdminSubCommand{
	
	/** 
	 * <b>AdminManualUpdate</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminManualUpdate({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminManualUpdate} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminManualUpdate(Dominion instance) {
		super(instance, "/ad manualupdate");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the admin manualupdate sub-command to force Dominion to advance a building tick.
	 * @param sender - The sender of the command.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(plugin.getSettlementManager().updateAll()){
			sender.sendMessage("Successfully updated all settlements.");
		} else {
			sender.sendMessage("At least one of the settlements has not been updated.");
		}
		return true;
	}

}
