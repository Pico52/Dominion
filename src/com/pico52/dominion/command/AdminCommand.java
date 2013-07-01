package com.pico52.dominion.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.command.admin.AdminAdd;
import com.pico52.dominion.command.admin.AdminBuild;
import com.pico52.dominion.command.admin.AdminCreate;
import com.pico52.dominion.command.admin.AdminForce;
import com.pico52.dominion.command.admin.AdminManualUpdate;
import com.pico52.dominion.command.admin.AdminRemove;
import com.pico52.dominion.command.admin.AdminSetBiome;
import com.pico52.dominion.command.admin.AdminSetMaterial;
import com.pico52.dominion.command.admin.AdminSubtract;
import com.pico52.dominion.command.admin.AdminUpdate;

/** 
 * <b>AdminCommand</b><br>
 * <br>
 * &nbsp;&nbsp;public class AdminCommand implements {@link CommandExecutor}
 * <br>
 * <br>
 * The command executor for all administrator commands.
 */
public class AdminCommand implements CommandExecutor{
	
	private static Dominion plugin;
	private static AdminSetMaterial adminSet;
	private static AdminAdd adminAdd;
	private static AdminSubtract adminSubtract;
	private static AdminBuild adminBuild;
	private static AdminCreate adminCreate;
	private static AdminRemove adminRemove;
	private static AdminForce adminForce;
	private static AdminUpdate adminUpdate;
	private static AdminManualUpdate adminManualUpdate;
	private static AdminSetBiome adminSetBiome;
	
	/** 
	 * <b>AdminCommand</b><br>
	 * <br>
	 * &nbsp;&nbsp;public AdminCommand({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link AdminCommand} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public AdminCommand(Dominion instance){
		plugin = instance;
		
		//--- Sub-command executors ---//
		adminSet 		= new AdminSetMaterial(plugin);
		adminAdd 		= new AdminAdd(plugin);
		adminSubtract = new AdminSubtract(plugin);
		adminBuild 		= new AdminBuild(plugin);
		adminCreate 	= new AdminCreate(plugin);
		adminRemove	= new AdminRemove(plugin);
		adminForce 	= new AdminForce(plugin);
		adminUpdate	= new AdminUpdate(plugin);
		adminManualUpdate = new AdminManualUpdate(plugin);
		adminSetBiome = new AdminSetBiome(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!sender.hasPermission("admin") || !sender.isOp()){
			sender.sendMessage(plugin.getLogPrefix() + "You must have the administrator permission to use this command.");
			return false;
		}
		if(args.length == 0){
			sender.sendMessage("The primary controller for all administrator commands.");
			sender.sendMessage("Usage:  /admindominion [set / add / subtract / build / create / remove / force / setbiome / update / manualupdate]");
			return true;
		}
		String subCommand = args[0];
		// - Get rid of the first argument.  We know what it is.
		List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		arguments.remove(0);
		args = arguments.toArray(new String[arguments.size()]);
		
		//--- SUB-COMMAND EXECUTORS ---//
		if (subCommand.equalsIgnoreCase("set") | subCommand.equalsIgnoreCase("setmaterial") | subCommand.equalsIgnoreCase("sm")){
			return adminSet.execute(sender,  args);
		}
		if (subCommand.equalsIgnoreCase("add") | subCommand.equalsIgnoreCase("give") | subCommand.equalsIgnoreCase("grant")){
			return adminAdd.execute(sender,args);
		}
		if (subCommand.equalsIgnoreCase("subtract") | subCommand.equalsIgnoreCase("take")){
			return adminSubtract.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("build") | subCommand.equalsIgnoreCase("construct")){
			return adminBuild.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("create") | subCommand.equalsIgnoreCase("found")){
			return adminCreate.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("remove") | subCommand.equalsIgnoreCase("delete") | subCommand.equalsIgnoreCase("destroy")){
			return adminRemove.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("force") | subCommand.equalsIgnoreCase("owner") | subCommand.equalsIgnoreCase("setowner")){
			return adminForce.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("update") | subCommand.equalsIgnoreCase("change")){
			return adminUpdate.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("manualupdate") | subCommand.equalsIgnoreCase("mu")){
			return adminManualUpdate.execute(sender, args);
		}
		if(subCommand.equalsIgnoreCase("setbiome") | subCommand.equalsIgnoreCase("sb")){
			return adminSetBiome.execute(sender, args);
		}
		
		return false;
	}
}
