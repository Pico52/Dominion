package com.pico52.dominion.command.player;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

public class PlayerUnitDrop extends PlayerSubCommand{

	public PlayerUnitDrop(Dominion instance) {
		super(instance, "/d drop [unit id] [item id] (optional)[quantity]");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		return true;
	}

}
