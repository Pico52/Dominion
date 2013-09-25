package com.pico52.dominion.event;

import org.bukkit.entity.Player;
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
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		//---Register new players---//
		if(!plugin.getDBHandler().playerExists(playerName))
			plugin.getDBHandler().createPlayer(playerName);
		
		//---Handle admin requests---//
		if(event.getPlayer().isOp()){
			int[] adminRequests = plugin.getRequestManager().getRequestsToAdmins();
			String message = plugin.getLogPrefix() + "You have " + adminRequests.length + " admin requests from other players active.";
			if(adminRequests.length > 0)
				message += "  Please use the \"/ad requests\" command to view the requests.";
			player.sendMessage(message);
		}
		
		//---Handle other requests---//
		int playerId = plugin.getDBHandler().getPlayerId(playerName);
		int[] playerRequests = plugin.getRequestManager().getRequestsTo(playerId);
		String message = plugin.getLogPrefix() + "You have " + playerRequests.length + " player requests from other players active.";
		if(playerRequests.length > 0)
			message += "  Please use the \"/d requests\" command to view the requests.";
		player.sendMessage(message);
	}
}
