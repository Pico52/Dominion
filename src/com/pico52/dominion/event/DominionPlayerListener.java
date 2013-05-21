package com.pico52.dominion.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.pico52.dominion.Dominion;

/** 
 * <b>DominionPlayerListener</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerSubCommand implements {@link Listener}
 * <br>
 * <br>
 * The event listener for all events involving a player.
 */
public class DominionPlayerListener implements Listener{
	private static Dominion plugin;
	
	/** 
	 * <b>DominionPlayerListener</b><br>
	 * <br>
	 * &nbsp;&nbsp;public DominionPlayerListener({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link DominionPlayerListener} class.
	 * @param instance - The {@link Dominion} plugin this listener will be running on.
	 */
	public DominionPlayerListener(Dominion instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		if(!plugin.getDBHandler().playerExists(event.getPlayer().getName()))
			plugin.getDBHandler().createPlayer(event.getPlayer().getName());
	}
}
