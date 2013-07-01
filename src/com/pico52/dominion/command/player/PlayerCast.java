package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerCast</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerCast extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "cast".
 */
public class PlayerCast extends PlayerSubCommand{

	/** 
	 * <b>PlayerCast</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerCast({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerCast} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerCast(Dominion instance) {
		super(instance, "/d cast [settlement name] [spell name] [target]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the cast sub-command to cast a spell.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Commands a settlement to cast a spell on a target.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		String settlement = args[0];
		if(!plugin.getDBHandler().settlementExists(settlement)){
			sender.sendMessage(plugin.getLogPrefix() + "No such settlement \"" + settlement + "\".");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 1){
			sender.sendMessage(plugin.getLogPrefix() + "You must specify a spell and its target id.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		String spell = args[1];
		if(!plugin.getSpellManager().isSpell(spell)){
			sender.sendMessage(plugin.getLogPrefix() + "\"" + spell + "\" is not a spell.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		if(args.length == 2){
			sender.sendMessage(plugin.getLogPrefix() + "You must specify a target id.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int targetId = 0;
		try{
			targetId = Integer.parseInt(args[2]);
		} catch (NumberFormatException ex){
			sender.sendMessage(plugin.getLogPrefix() + "\"" + args[2] + "\" is not a number.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		int settlementId = plugin.getDBHandler().getSettlementId(settlement);
		int casterId = plugin.getDBHandler().getPlayerId(sender.getName());
		if(!plugin.getSpellManager().castSpell(settlementId, casterId, spell, targetId))
			sender.sendMessage(plugin.getLogPrefix() + "Failed to cast the spell.");
		
		return true;
	}
}
