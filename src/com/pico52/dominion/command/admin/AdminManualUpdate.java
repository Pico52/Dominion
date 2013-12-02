package com.pico52.dominion.command.admin;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.task.SpellTask;
import com.pico52.dominion.task.UnitTask;

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
		super(instance, "/ad manualupdate [settlement / spell / unit] (# of updates)");
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
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Advances the timer tick for all settlements, units, or spells.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		int totalRuns = 1;
		boolean success = true;
		if(args.length >= 2){
			try{
				totalRuns = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex){
				sender.sendMessage(logPrefix + "\"" + args[1] + "\" is not a proper integer (no decimals).");
				sender.sendMessage(logPrefix + "Usage: " + usage);
				return true;
			}
		}
		String task = args[0];
		if(task.equalsIgnoreCase("settlement") | task.equalsIgnoreCase("settlements")){
			sender.sendMessage(logPrefix + "Starting a manual update to advance settlements " + totalRuns + " time(s).");
			for(int runs = totalRuns; runs > 0; runs--){
				if(!plugin.getSettlementManager().updateAll())
					success = false;
			}
			if(success)
				sender.sendMessage(logPrefix + "Successfully updated all settlements " + totalRuns + " time(s).");
			else
				sender.sendMessage(logPrefix + "At least one of the settlements has not been updated.");
			return true;
		} else if (task.equalsIgnoreCase("unit") | task.equalsIgnoreCase("units")){
			sender.sendMessage(logPrefix + "Starting a manual update to advance units " + totalRuns + " time(s).");
			for(int runs = totalRuns; runs > 0; runs--){
				new UnitTask(plugin).run();
			}
			sender.sendMessage(logPrefix + "Manually updated the units tick by " + totalRuns + ".");
			return true;
		} else if (task.equalsIgnoreCase("spell") | task.equalsIgnoreCase("spells")){
			sender.sendMessage(logPrefix + "Starting a manual update to advance spells " + totalRuns + " time(s).");
			for(int runs = totalRuns; runs > 0; runs--){
				new SpellTask(plugin).run();
			}
			sender.sendMessage(logPrefix + "Manually updated the spells tick by " + totalRuns + ".");
			return true;
		} else {
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
	}
}
