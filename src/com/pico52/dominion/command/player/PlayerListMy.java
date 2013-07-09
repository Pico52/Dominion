package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerListMy</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerListMy extends {@link PlayerSubCommand}
 * <br>
 * <br>
 * The class file for the player sub-command "list".
 */
public class PlayerListMy extends PlayerSubCommand{

	/** 
	 * <b>PlayerListMy</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerListMy({@link Dominion} instance)
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerList} class.
	 * @param instance - The {@link Dominion} plugin this command executor will be running on.
	 */
	public PlayerListMy(Dominion instance) {
		super(instance, "/d listmy [kingdom/settlement/player/building/trade/unit]");
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
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Outputs all ids and names of a set of objects that you own.");
			sender.sendMessage(logPrefix + "Usage: " + usage);
			return true;
		}
		String entity = args[0];
		int ownerId = db.getPlayerId(sender.getName());
		ResultSet results;
		if(entity.equalsIgnoreCase("trade")){
			// - To do:  Make it find only the trades that the player is a part of.
			results = db.getTableData(entity, "*");
		} else if (entity.equalsIgnoreCase("command")){
			// - To do:  Make it find only the commands for units that are owned by this player.
			results = db.getTableData(entity, "*");
		} else 
			results = db.getTableData(entity, "*", "owner_id=" + ownerId);
		String allData = "";
		String middleData = "";
		String entity_id = entity + "_id";
		int columnCount = 1;
		boolean isUnit = false, isCommand = false, isItem = false, hasName = false, hasDuration = false, hasClass = false, hasType = false;
		try{
			while(results.next()){
				middleData += "§aId#: §f" + results.getInt(entity_id) + "  ";
				try{
					String name = results.getString("name");
					if(name != null){
						middleData += "§aName: §f" + name + "  ";
						hasName = true;
					}
				}catch (SQLException ex){}
				try{
					String type = results.getString("type");
					middleData += "§aType: §f" + type + "  ";
					hasType = true;
				} catch (SQLException ex){}
				try{
					String classification = results.getString("class");
					if(classification != null){
						middleData += "§aClass: §f" + classification + "  ";
						hasClass = true;
						if(plugin.getUnitManager().isUnit(classification)){
							middleData += "§aHealth: §f" + results.getDouble("health") + "  ";
							middleData += "§aX: §f" + results.getDouble("xcoord") + "  ";
							middleData += "§aZ: §f" + results.getDouble("zcoord") + "  ";
							isUnit = true;
						}
					}
				}catch (SQLException ex){}
				try{
					int duration = results.getInt("duration");
					middleData += "§aDuration: §f" + duration;
					hasDuration = true;
				} catch (SQLException ex){}
				try{
					String command = results.getString("command");
					int unitId = results.getInt("unit_id");
					int afflictId = results.getInt("target_id");
					double xCoord = results.getDouble("xcoord");
					double zCoord = results.getDouble("zcoord");
					middleData += "§aCommand: §f" + command + "  ";
					middleData += "§aUnit #: §f" + unitId + "  ";
					middleData += "§aTarget Id: §f" + afflictId + "  ";
					middleData += "§aX: §f" + xCoord + "  ";
					middleData += "§aZ: §f" + zCoord + "  ";
					isCommand = true;
				} catch (SQLException ex){}
				try{
					int unitId = results.getInt("unit_id");
					double quantity = results.getDouble("quantity");
					middleData += "§aUnit Id: §f" + unitId + "  ";
					middleData += "§aQuantity: §f" + quantity + "  ";
					isItem = true;
				} catch (SQLException ex){}
				middleData += "\n";
			}
			results.getStatement().close();
		}catch (SQLException ex){
			sender.sendMessage("There was an error communicating with the database.");
			ex.printStackTrace();
			return false;
		}
		allData = "§a";
		String containment = "=";
		if(isUnit)
			columnCount += 3;
		if(isCommand)
			columnCount += 4;
		if(isItem)
			columnCount += 2;
		if(hasName)
			columnCount++;
		if(hasDuration)
			columnCount++;
		if(hasClass)
			columnCount++;
		if(hasType)
			columnCount++;
		for(int i=0; i<columnCount;i++)
			containment += "===";
		allData += containment + entity.toUpperCase() + containment + "§r\n";
		if(middleData == "")
			middleData += "There are no objects in " + entity + ".\n";
		allData += middleData + "§a" + containment;
		for(int i=0; i<entity.length();i++)
			allData += "=";
		allData += containment + "§r";
		sender.sendMessage(allData);
		
		return true;
	}

}