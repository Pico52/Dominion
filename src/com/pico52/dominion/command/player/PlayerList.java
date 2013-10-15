package com.pico52.dominion.command.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.object.SpellManager;

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
		if(args.length == 0){
			sender.sendMessage(logPrefix + "Outputs all ids and names of a set of objects.");
			sender.sendMessage(logPrefix + "Usage: " + getUsage());
			return true;
		}
		String entity = args[0];
		ResultSet results = db.getTableData(entity, "*");
		String allData = "", middleData = "", entity_id = entity + "_id";
		int columnCount = 1, entityId = 0, casterId = db.getPlayerId(sender.getName());
		boolean isUnit = false, isCommand = false, isItem = false, isTrade = false, 
				hasName = false, hasDuration = false, hasClass = false, hasType = false;
		try{
			while(results.next()){
				// - For the future, make this whole section flow better.
				entityId = results.getInt(entity_id);
				middleData += "§aId#: §f" + entityId + "  ";
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
							if(!plugin.getUnitManager().isVisible(entityId, casterId)){
								middleData += "\n";
								continue;
							}
							middleData += "§aHealth: §f" + results.getDouble("health") + "  ";
							double unitX = results.getDouble("xcoord"), unitZ = results.getDouble("zcoord");
							middleData += "§aX: §f" + unitX + "  ";
							middleData += "§aZ: §f" + unitZ + "  ";
							if(!plugin.getUnitManager().isReal(entityId)){
								if(casterId != db.getOwnerId("unit", entityId)){
									SpellManager sm = plugin.getSpellManager();
									for(int spell: sm.getAllSpells("aoe_reveal_fake_units")){
										if(sm.isWithinAreaOfEffect(unitX, unitZ, spell) & sm.getCasterId(spell) == casterId)
											middleData += "§aFAKE§f  ";
									}
								} else {
									middleData += "§aFAKE§f  ";
								}
							}
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
					int afflictId = results.getInt("afflict_id");
					double xCoord = results.getDouble("xcoord");
					double zCoord = results.getDouble("zcoord");
					middleData += "§aCommand: §f" + command + "  ";
					middleData += "§aUnit #: §f" + unitId + "  ";
					middleData += "§aAfflict Id: §f" + afflictId + "  ";
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
				try{
					int settlement1 = results.getInt("settlement1_id");
					int settlement2 = results.getInt("settlement2_id");
					double value1 = results.getDouble("income1");
					double value2 = results.getDouble("income2");
					middleData += "§aSettlement 1:§f " + db.getSettlementName(settlement1) + "(+" + value1 + ")  ";
					middleData += "§aSettlement 2:§f " + db.getSettlementName(settlement2) + "(+" + value2 + ")  ";
					isTrade = true;
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
		if(isTrade)
			columnCount += 4;
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
