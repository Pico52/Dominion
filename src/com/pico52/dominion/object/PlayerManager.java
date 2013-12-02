package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pico52.dominion.Dominion;

/** 
 * <b>PlayerManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class PlayerManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for players.
 */
public class PlayerManager extends DominionObjectManager{

	/** 
	 * <b>PlayerManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public PlayerManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link PlayerManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public PlayerManager(Dominion plugin) {
		super(plugin);
	}
	
	public String outputData(String playerName){
		return outputData(getPlayerId(playerName));
	}
	
	public String outputData(int playerId){
		int kingdomId = 0, ownerId = 0;
		String name = "", kingdomName = "", ownerName = "";
		ResultSet playerData = db.getPlayerData(playerId, "*");
		try{
			if(playerData.next()){
				name = playerData.getString("name");
				kingdomId = playerData.getInt("kingdom_id");
				ownerId = playerData.getInt("owner_id");
			}
			playerData.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		kingdomName = db.getKingdomName(kingdomId);
		ownerName = db.getPlayerName(ownerId);
		String output = "§aId:§f " + playerId + "  §aName§f: " + name + "  §aKingdom:§f " + kingdomName + "  §aLiege:§f " + ownerName;
		try {
			int[] settlements = getOwnedSettlements(playerId);
			int[] buildings = getOwnedBuildings(playerId);
			int[] players = getOwnedPlayers(playerId);
			if(settlements.length > 0){
				output += "\n§aSettlements:§f " + db.getSettlementName(settlements[0]);
				for(int i=1; i < settlements.length; i++){
					output += ", " + db.getSettlementName(settlements[i]);
				}
			}
			if(buildings.length > 0){
				output += "\n§aBuildings:§f " + buildings.length;
			}
			if(players.length > 0){
				output += "\n§aVassals:§f " + getPlayerName(players[0]);
				for(int i=1; i < players.length; i++){
					output += ", " + getPlayerName(players[i]);
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return output;
	}
	
	//===MUTATORS===//
	
	public boolean setKingdom(int playerId, int kingdomId){
		return db.update("player", "kingdom_id", kingdomId, "player_id", playerId);
	}
	
	public boolean setOwner(int playerId, int ownerId){
		return db.update("player", "owner_id", ownerId, "player_id", playerId);
	}
	
	//===ACCESSORS===//
	public int getPlayerId(String playerName){
		return db.getPlayerId(playerName);
	}
	
	public String getPlayerName(int playerId){
		return db.getPlayerName(playerId);
	}
	
	public int getOwnerId(int playerId){
		return db.getOwnerId("player", playerId);
	}
	
	public String getOwnerName(int playerId){
		return db.getOwnerName("player", playerId);
	}
	
	public int getKingdomId(int playerId){
		return db.getSingleColumnInt("player", "kingdom_id", playerId, "player_id");
	}
	
	public String getKingdomName(int playerId){
		return db.getKingdomName(getKingdomId(playerId));
	}
	
	public int[] getOwnedSettlements(int playerId){
		ArrayList<Integer> settlementIds = new ArrayList<Integer>();
		ResultSet settlements = db.getTableData("settlement", playerId, "settlement_id", "owner_id");
		try{
			while(settlements.next()){
				settlementIds.add(settlements.getInt("settlement_id"));
			}
			settlements.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ownedSettlements = new int[settlementIds.size()];
		int i = 0;
	    for (Integer n : settlementIds) {
	        ownedSettlements[i++] = n;
	    }
	    return ownedSettlements;
	}
	
	public int[] getOwnedBuildings(int playerId){
		ArrayList<Integer> buildingIds = new ArrayList<Integer>();
		ResultSet buildings = db.getTableData("building", playerId, "building_id", "owner_id");
		try{
			while(buildings.next()){
				buildingIds.add(buildings.getInt("building_id"));
			}
			buildings.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ownedBuildings = new int[buildingIds.size()];
		int i = 0;
	    for (Integer n : buildingIds) {
	        ownedBuildings[i++] = n;
	    }
	    return ownedBuildings;
	}
	
	public int[] getOwnedPlayers(int playerId){
		ArrayList<Integer> playerIds = new ArrayList<Integer>();
		ResultSet players = db.getTableData("player", playerId, "player_id", "owner_id");
		try{
			while(players.next()){
				playerIds.add(players.getInt("player_id"));
			}
			players.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		int[] ownedPlayers = new int[playerIds.size()];
		int i = 0;
	    for (Integer n : playerIds) {
	        ownedPlayers[i++] = n;
	    }
	    return ownedPlayers;
	}
}
