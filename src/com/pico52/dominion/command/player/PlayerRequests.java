package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerRequests</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerRequest extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "requests".
 */
public class PlayerRequests extends PlayerSubCommand{

	/** 
	 * <b>PlayerRequests</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerRequests({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerRequests} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerRequests(Dominion instance) {
		super(instance, "/d requests");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the requests sub-command to display active requests to this player.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		// - No check for arguments needed here.  Just output all requests to and from this player.
		return true;
	}
}