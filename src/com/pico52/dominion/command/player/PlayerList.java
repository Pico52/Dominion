package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerList</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerList extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "list".
 */
public class PlayerList extends PlayerSubCommand{

	/** 
	 * <b>PlayerList</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerList({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerList} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerList(Dominion instance) {
		super(instance, "/dominion list [kingdom/settlement/player/building/trade]");
	}

	/** 
	 * <b>execute</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean execute({@link CommandSender} sender, {@link String}[] args)
	 * <br>
	 * <br>
	 * Manages the list sub-command to send a player a message indicating the names and ids of 
	 * all existing objects in the specified table.
	 * @param sender - The sender of the command and the recipient of the message.
	 * @param args - The arguments the player provided.
	 * @return The sucess of the execution of this command.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 0){	// - They only specified "list" but gave nothing to list.
			sender.sendMessage(plugin.getLogPrefix() + "Outputs all ids and names of a set of objects.");
			sender.sendMessage(plugin.getLogPrefix() + "Usage: " + getUsage());
			return true;
		}
		// - There will at least be an argument here.  Hopefully a table name.
		String entity = args[0];
		ResultSet results = plugin.getDBHandler().getAllTableData(entity, "*");
		String allData = "";
		String middleData = "";
		String entity_id = entity + "_id";
		int columnCount = 1;
		try{
			while(results.next()){
				middleData += "�aId#: �f" + results.getInt(entity_id) + "  ";
				try{
					String name = results.getString("name");
					if(name != null){
						middleData += "�aName: �f" + name + "  ";
						columnCount++;
					}
				}catch (SQLException ex){}
				try{
					String classification = results.getString("class");
					if(classification != null){
						middleData += "�aClass: �f" + classification;
						columnCount++;
					}
				}catch (SQLException ex){}
				middleData += "\n";
			}
			results.getStatement().close();
		}catch (SQLException ex){
			sender.sendMessage("There was an error communicating with the database.");
			ex.printStackTrace();
			return false;
		}
		allData = "�a";
		String containment = "=";
		for(int i=0; i<columnCount;i++)
			containment += "===";
		allData += containment + entity.toUpperCase() + containment + "�r\n";
		if(middleData == "")
			middleData += "There are no objects in " + entity + ".\n";
		allData += middleData + "�a" + containment;
		for(int i=0; i<entity.length();i++)
			allData += "=";
		allData += containment + "�r";
		sender.sendMessage(allData);
		
		return true;
	}

}
