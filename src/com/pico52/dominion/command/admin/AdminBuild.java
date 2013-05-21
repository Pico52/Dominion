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
		super(instance, "");
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
		sender.sendMessage("This command will construct a new building or structure for the specified settlement.");
		
		return true;
	}
}
