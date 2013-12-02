package com.pico52.dominion.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.command.player.PlayerBuild;
import com.pico52.dominion.command.player.PlayerCancel;
import com.pico52.dominion.command.player.PlayerCast;
import com.pico52.dominion.command.player.PlayerData;
import com.pico52.dominion.command.player.PlayerDeposit;
import com.pico52.dominion.command.player.PlayerDestroy;
import com.pico52.dominion.command.player.PlayerEmploy;
import com.pico52.dominion.command.player.PlayerEmployment;
import com.pico52.dominion.command.player.PlayerForbid;
import com.pico52.dominion.command.player.PlayerFound;
import com.pico52.dominion.command.player.PlayerGiveToUnit;
import com.pico52.dominion.command.player.PlayerHolding;
import com.pico52.dominion.command.player.PlayerInfo;
import com.pico52.dominion.command.player.PlayerList;
import com.pico52.dominion.command.player.PlayerListMy;
import com.pico52.dominion.command.player.PlayerPermit;
import com.pico52.dominion.command.player.PlayerProduction;
import com.pico52.dominion.command.player.PlayerRequest;
import com.pico52.dominion.command.player.PlayerRequests;
import com.pico52.dominion.command.player.PlayerRevoke;
import com.pico52.dominion.command.player.PlayerStorage;
import com.pico52.dominion.command.player.PlayerTrade;
import com.pico52.dominion.command.player.PlayerTrain;
import com.pico52.dominion.command.player.PlayerUnitAttack;
import com.pico52.dominion.command.player.PlayerUnitCamp;
import com.pico52.dominion.command.player.PlayerUnitDisband;
import com.pico52.dominion.command.player.PlayerUnitDrop;
import com.pico52.dominion.command.player.PlayerUnitMove;
import com.pico52.dominion.command.player.PlayerUnitPickUp;
import com.pico52.dominion.command.player.PlayerWhoIs;
import com.pico52.dominion.command.player.PlayerWithdraw;

/** 
 * <b>PlayerCommand</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerCommand implements {@link CommandExecutor}
 * <br>
 * <br>
 * The command executor for all player commands.
 */
public class PlayerCommand implements CommandExecutor{
	
	private static Dominion plugin;
	private static PlayerInfo playerInfo;
	private static PlayerStorage playerStorage;
	private static PlayerWithdraw playerWithdraw;
	private static PlayerDeposit playerDeposit;
	private static PlayerList playerList;
	private static PlayerDestroy playerDestroy;
	private static PlayerEmploy playerEmploy;
	private static PlayerEmployment playerEmployment;
	private static PlayerData playerData;
	private static PlayerProduction playerProduction;
	private static PlayerTrain playerTrain;
	private static PlayerUnitMove playerUnitMove;
	private static PlayerUnitAttack playerUnitAttack;
	private static PlayerUnitCamp playerUnitCamp;
	private static PlayerUnitDrop playerUnitDrop;
	private static PlayerUnitDisband playerUnitDisband;
	private static PlayerListMy playerListMy;
	private static PlayerCast playerCast;
	private static PlayerGiveToUnit playerGiveToUnit;
	private static PlayerHolding playerHolding;
	private static PlayerUnitPickUp playerUnitPickUp;
	private static PlayerBuild playerBuild;
	private static PlayerPermit playerPermit;
	private static PlayerRevoke playerRevoke;
	private static PlayerForbid playerForbid;
	private static PlayerRequest playerRequest;
	private static PlayerRequests playerRequests;
	private static PlayerTrade playerTrade;
	private static PlayerCancel playerCancel;
	private static PlayerFound playerFound;
	private static PlayerWhoIs playerWhoIs;
	
	/** 
	 * <b>PlayerCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerCommand({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerCommand} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerCommand(Dominion instance){
		plugin = instance;
		
		playerInfo = new PlayerInfo(plugin);
		playerStorage = new PlayerStorage(plugin);
		playerWithdraw = new PlayerWithdraw(plugin);
		playerDeposit = new PlayerDeposit(plugin);
		playerList = new PlayerList(plugin);
		playerDestroy = new PlayerDestroy(plugin);
		playerEmploy = new PlayerEmploy(plugin);
		playerEmployment = new PlayerEmployment(plugin);
		playerData = new PlayerData(plugin);
		playerProduction = new PlayerProduction(plugin);
		playerTrain = new PlayerTrain(plugin);
		playerUnitMove = new PlayerUnitMove(plugin);
		playerUnitAttack = new PlayerUnitAttack(plugin);
		playerUnitCamp = new PlayerUnitCamp(plugin);
		playerUnitDrop = new PlayerUnitDrop(plugin);
		playerUnitDisband = new PlayerUnitDisband(plugin);
		playerListMy = new PlayerListMy(plugin);
		playerCast = new PlayerCast(plugin);
		playerGiveToUnit = new PlayerGiveToUnit(plugin);
		playerHolding = new PlayerHolding(plugin);
		playerUnitPickUp = new PlayerUnitPickUp(plugin);
		playerBuild = new PlayerBuild(plugin);
		playerPermit = new PlayerPermit(plugin);
		playerRevoke = new PlayerRevoke(plugin);
		playerForbid = new PlayerForbid(plugin);
		playerRequest = new PlayerRequest(plugin);
		playerRequests = new PlayerRequests(plugin);
		playerTrade = new PlayerTrade(plugin);
		playerCancel = new PlayerCancel(plugin);
		playerFound = new PlayerFound(plugin);
		playerWhoIs = new PlayerWhoIs(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		/* Default view
		 * View the information of the default settlement.
		 */
		if(args.length == 0){
			sender.sendMessage(plugin.getLogPrefix() + "Usage:  /dominion [info / storage / withdraw / deposit / list / listmy / destroy / employ / employment / data / request / permit / production / train / attack / move / camp / drop / pickup / holding / disband]");
			return true;
		}
		String subCommand = args[0];
		// - Get rid of the first argument.  We know what it is.
		List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		arguments.remove(0);
		args = arguments.toArray(new String[arguments.size()]);
		
		//--- SUB-COMMAND EXECUTORS ---//
		if (subCommand.equalsIgnoreCase("info") || subCommand.equalsIgnoreCase("view") || subCommand.equalsIgnoreCase("i")){
			return playerInfo.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("storage") || subCommand.equalsIgnoreCase("stores") || subCommand.equalsIgnoreCase("materials")
				|| subCommand.equalsIgnoreCase("mats") || subCommand.equalsIgnoreCase("s")){
			return playerStorage.execute(sender,args);
		}
		if (subCommand.equalsIgnoreCase("withdraw") || subCommand.equalsIgnoreCase("wd") || subCommand.equalsIgnoreCase("w")){
			return playerWithdraw.execute(sender, args);
		}
		if (subCommand.equalsIgnoreCase("deposit") || subCommand.equalsIgnoreCase("d")){
			return playerDeposit.execute(sender,args);
		}
		if(subCommand.equalsIgnoreCase("list") || subCommand.equalsIgnoreCase("l")){
			return playerList.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("destroy") || subCommand.equalsIgnoreCase("dismantle") || subCommand.equalsIgnoreCase("burn")){
			return playerDestroy.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("employ")){
			return playerEmploy.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("employment")){
			return playerEmployment.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("data")){
			return playerData.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("production") || subCommand.equalsIgnoreCase("prod") || subCommand.equalsIgnoreCase("p")){
			return playerProduction.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("train") || subCommand.equalsIgnoreCase("t")){
			return playerTrain.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("attack") || subCommand.equalsIgnoreCase("assault") || subCommand.equalsIgnoreCase("a")){
			return playerUnitAttack.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("move") || subCommand.equalsIgnoreCase("m")){
			return playerUnitMove.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("drop")){
			return playerUnitDrop.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("camp") || subCommand.equalsIgnoreCase("c")){
			return playerUnitCamp.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("disband")){
			return playerUnitDisband.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("listmy") || subCommand.equalsIgnoreCase("lm")){
			return playerListMy.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("cast")){
			return playerCast.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("give") || subCommand.equalsIgnoreCase("givetounit") || subCommand.equalsIgnoreCase("gtu")){
			return playerGiveToUnit.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("holding") || subCommand.equalsIgnoreCase("carrying")){
			return playerHolding.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("pickup") || subCommand.equalsIgnoreCase("take") || subCommand.equalsIgnoreCase("grab") || subCommand.equalsIgnoreCase("pu")){
			return playerUnitPickUp.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("build") || subCommand.equalsIgnoreCase("construct") || subCommand.equalsIgnoreCase("b")){
			return playerBuild.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("permit") || subCommand.equalsIgnoreCase("grant")){
			return playerPermit.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("revoke") || subCommand.equalsIgnoreCase("re")){
			return playerRevoke.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("forbid") || subCommand.equalsIgnoreCase("prohibit")){
			return playerForbid.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("request") || subCommand.equalsIgnoreCase("req") || subCommand.equalsIgnoreCase("r")){
			return playerRequest.execute(sender, args);	
		}
		if(subCommand.equalsIgnoreCase("requests") || subCommand.equalsIgnoreCase("reqs")){
			return playerRequests.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("trade") || subCommand.equalsIgnoreCase("barter")){
			return playerTrade.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("cancel")){
			return playerCancel.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("found")){
			return playerFound.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("whois") || subCommand.equalsIgnoreCase("who_is") || subCommand.equalsIgnoreCase("who") || subCommand.equalsIgnoreCase("wi")){
			return playerWhoIs.execute(sender, args);
		}
		
		return false;
	}
}
