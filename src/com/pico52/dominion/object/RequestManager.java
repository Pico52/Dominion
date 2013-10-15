package com.pico52.dominion.object;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pico52.dominion.Dominion;
import com.pico52.dominion.DominionSettings;

/** 
 * <b>RequestManager</b><br>
 * <br>
 * &nbsp;&nbsp;public class RequestManager extends {@link DominionObjectManager}
 * <br>
 * <br>
 * Controller for requests.
 */
public class RequestManager extends DominionObjectManager{

	/** 
	 * <b>RequestManager</b><br>
	 * <br>
	 * &nbsp;&nbsp;public RequestManager()
	 * <br>
	 * <br>
	 * The constructor clause for the {@link RequestManager} class.
	 * @param instance - The {@link Dominion} plugin this manager will be running on.
	 */
	public RequestManager(Dominion plugin) {
		super(plugin);
	}
	
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, int objectId, double xcoord, double zcoord){
		return createRequest(ownerId, targetId, toAdmin, level, request, "",objectId, 0, xcoord, zcoord);
	}
	
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, String objectName, int objectId, double xcoord, double zcoord){
		return createRequest(ownerId, targetId, toAdmin, level, request, objectName, objectId, 0, xcoord, zcoord);
	}
	
	public boolean createRequest(int ownerId, int targetId, boolean toAdmin, int level, String request, String objectName, int objectId, int targetObjectId, double xcoord, double zcoord){
		return db.createRequest(ownerId, targetId, toAdmin, level, request, objectName, objectId, targetObjectId, xcoord, zcoord);
	}
	
	public boolean acceptRequest(int requestId){
		String requestType = getRequestType(requestId);
		boolean success = false;
		if(isBuildRequest(requestType))
			success = createBuilding(requestId);
		else if (requestType.equalsIgnoreCase("kingdom_invite"))
			success = kingdomTransfer(requestId, "invite");
		else if (requestType.equalsIgnoreCase("kingdom_request"))
			success = kingdomTransfer(requestId, "request");
		else if (requestType.equalsIgnoreCase("liege_invite"))
			success = liegeTransfer(requestId, "invite");
		else if (requestType.equalsIgnoreCase("liege_request"))
			success = liegeTransfer(requestId, "request");
		else if(requestType.equalsIgnoreCase("found_kingdom"))
			success = foundKingdom(requestId);
		else if(requestType.equalsIgnoreCase("found_settlement"))
			success = foundSettlement(requestId);
		else if (requestType.equalsIgnoreCase("trade"))
			success = createTrade(requestId);
		if(success)
			success = removeRequest(requestId);
		if(success){
			String message = "Request #" + requestId + " \"" + requestType + "\" + has been accepted by ";
			if(isToAdmin(requestId))
				message += "an administrator.";
			else
				message += db.getPlayerName(getToId(requestId)) + ".";
			plugin.sendMessage(db.getPlayerName(getSenderId(requestId)), message);
		}
		return success;
	}
	
	private boolean createBuilding(int requestId){
		ResultSet request = plugin.getDBHandler().getTableData("request", requestId, "*", "request_id");
		int ownerId=0, level=0, objectId=0;
		double xCoord=0, zCoord=0;
		String requestType = "";
		try{
			if(request.next()){
				ownerId = request.getInt("owner_id");
				level = request.getInt("level");
				objectId = request.getInt("object_id");
				xCoord = request.getDouble("xcoord");
				zCoord = request.getDouble("zcoord");
				requestType = request.getString("request");
			}
			request.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		String classification = requestType.replace("build_", "");
		
		return plugin.getBuildingManager().createBuilding(objectId, ownerId, classification, level, xCoord, zCoord);
	}
	
	private boolean kingdomTransfer(int requestId, String inviteOrRequest){
		int toId = getToId(requestId), senderId = getSenderId(requestId), kingdomId = 0;
		if(inviteOrRequest.equalsIgnoreCase("invite")){
			kingdomId = db.getSingleColumnInt("player", "kingdom_id", senderId, "player_id");
			return db.update("player", "kingdom_id", kingdomId, "player_id", toId);
		} else if (inviteOrRequest.equalsIgnoreCase("request")){
			kingdomId = db.getSingleColumnInt("player", "kingdom_id", toId, "player_id");
			return db.update("player", "kingdom_id", kingdomId, "player_id", senderId);
		}
		return false;
	}
	
	private boolean liegeTransfer(int requestId, String inviteOrRequest){
		int toId = getToId(requestId), senderId = getSenderId(requestId), liegeId = 0;
		if(inviteOrRequest.equalsIgnoreCase("invite")){
			liegeId = db.getSingleColumnInt("player", "owner_id", senderId, "player_id");
			return db.update("player", "owner_id", liegeId, "player_id", toId);
		} else if (inviteOrRequest.equalsIgnoreCase("request")){
			liegeId = db.getSingleColumnInt("player", "owner_id", toId, "player_id");
			return db.update("player", "owner_id", liegeId, "player_id", senderId);
		}
		return false;
	}
	
	private boolean foundKingdom(int requestId){
		ResultSet request = plugin.getDBHandler().getTableData("request", requestId, "*", "request_id");
		int monarch=0;
		String name = "";
		try{
			if(request.next()){
				monarch = request.getInt("owner_id");
				name = request.getString("object_name");
			}
			request.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return db.createKingdom(name, monarch, "", "");
	}
	
	private boolean foundSettlement(int requestId){
		ResultSet request = plugin.getDBHandler().getTableData("request", requestId, "*", "request_id");
		int ownerId=0;
		double xCoord=0, zCoord=0;
		String name = "";
		try{
			if(request.next()){
				ownerId = request.getInt("owner_id");
				xCoord = request.getDouble("xcoord");
				zCoord = request.getDouble("zcoord");
				name = request.getString("object_name");
			}
			request.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return false;
		}
		return plugin.getSettlementManager().createSettlement(name, ownerId, "", "town", xCoord, zCoord);
	}
	
	private boolean createTrade(int requestId){
		int settlement1 = getObjectId(requestId), settlement2 = getTargetObjectId(requestId);
		return plugin.getTradeManager().createTrade(settlement1, settlement2);
	}
	
	/** 
	 * <b>denyRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean denyRequest(int requestId)
	 * <br>
	 * <br>
	 * Denies a request by sending messages to players involved that the request was denied and also removes the request from the database.  
	 * This method should be used if a player is attempting to remove the request rather than the removeRequest method.
	 * @param requestId - The id of the associated request.
	 * @return The success of the execution of this command.
	 */
	public boolean declineRequest(int requestId){
		// - We'll figure out who to send messages to and what to send.
		// - An e-mail system might be required.
		return removeRequest(requestId);
	}
	
	/** 
	 * <b>removeRequest</b><br>
	 * <br>
	 * &nbsp;&nbsp;public boolean removeRequest(int requestId)
	 * <br>
	 * <br>
	 * Removes a request from the database.  Use the denyRequest method if a player is issuing this.
	 * @param requestId - The id of the associated request.
	 * @return The success of the execution of this command.
	 */
	public boolean removeRequest(int requestId){
		return db.remove("request", requestId);
	}
	
	public String getRequestOutput(int requestId){
		ResultSet request = plugin.getDBHandler().getTableData("request", requestId, "*", "request_id");
		int ownerId=0, targetId=0, toAdmin=0, level=0, objectId=0, targetObjectId=0;
		double xCoord=0, zCoord=0;
		String requestType = "", objectName = "";
		boolean exists = false;
		try{
			if(request.next()){
				exists = true;
				ownerId = request.getInt("owner_id");
				targetId = request.getInt("target_id");
				toAdmin = request.getInt("to_admin");
				level = request.getInt("level");
				objectId = request.getInt("object_id");
				targetObjectId = request.getInt("target_object_id");
				xCoord = request.getDouble("xcoord");
				zCoord = request.getDouble("zcoord");
				requestType = request.getString("request");
				objectName = request.getString("object_name");
			}
			request.getStatement().close();
		} catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
		if(!exists)
			return "No request found.";
		String sender = db.getPlayerName(ownerId), 
				toPlayer = db.getPlayerName(targetId), 
				output = "§aId:§f " + requestId + "  §aFrom:§f " + sender;
		if(toAdmin == 1)
			output += "  §aTo:§f Admins";
		else
			output += "  §aTo:§f " + toPlayer;
		output += "  §aRequest:§f " + requestType;
		if(objectName != null && objectName != "")
			output += "  §aObject Name:§f " + objectName;
		if(isBuildRequest(requestType)){
			output += "  §aLevel:§f " + level + "  §aX:§f " + xCoord + "  §aZ:§f " + zCoord;
			return output;
		}
		if(objectId > 0 || targetObjectId > 0)
			output += " §aObject Id:§f " + objectId + "  §aTarget Object Id:§f " + targetObjectId;
		return output;
	}
	
	public boolean harassableRequestExistsAlready(int ownerId, int targetId, String request){
		boolean repeat = false;
		if(isHarassable(request)){
			ResultSet reqs = db.getTableData("request", "request_id", "owner_id=" + ownerId + " AND target_id=" + targetId + " AND request=\'" + request + "\'");
			try{
				if(reqs.next())
					repeat = true;
				reqs.getStatement().close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return repeat;
	}
	
	private boolean isHarassable(String request){
		for(String harrassable: DominionSettings.harrassableRequests){
			if(request.equalsIgnoreCase(harrassable))
				return true;
		}
		return false;
	}
	
	//---Request Types---//
	public boolean isBuildRequest(String request){
		if(!request.startsWith("build_"))
			return false;
		String building = request.replace("build_", "");
		return plugin.getBuildingManager().isBuilding(building);
	}
	
	public boolean isRequestType(String request){
		if(isBuildRequest(request) || 
				request.equalsIgnoreCase("kingdom_invite") || 
				request.equalsIgnoreCase("kingdom_request") || 
				request.equalsIgnoreCase("liege_invite") || 
				request.equalsIgnoreCase("liege_request") || 
				request.equalsIgnoreCase("found_kingdom") || 
				request.equalsIgnoreCase("found_settlement") || 
				request.equalsIgnoreCase("trade"))
			return true;
		return false;
	}
	
	public boolean goesToAdmins(String request){
		if(isBuildRequest(request) || 
				request.equalsIgnoreCase("found_kingdom") || 
				request.equalsIgnoreCase("found_settlement"))
			return true;
		return false;
	}
	
	//---Multiple Requests---//
	public int[] getRequestsFrom(int playerId){
		return  db.getSpecificIds("request", playerId, "owner_id");
	}
	
	public int[] getRequestsTo(int playerId){
		return  db.getSpecificIds("request", playerId, "target_id");
	}
	
	public int[] getRequestsToAdmins(){
		return  db.getSpecificIds("request", 1, "to_admin");
	}
	
	//---Request Data---//
	public int getSenderId(int requestId){
		return db.getSingleColumnInt("request", "owner_id", requestId, "request_id");
	}
	
	public int getToId(int requestId){
		return db.getSingleColumnInt("request", "target_id", requestId, "request_id");
	}
	
	public int getLevel(int requestId){
		return db.getSingleColumnInt("request", "level", requestId, "request_id");
	}
	
	public boolean isToAdmin(int requestId){
		int toAdmin = db.getSingleColumnInt("request", "to_admin", requestId, "request_id");
		if(toAdmin == 1)
			return true;
		else
			return false;
	}
	
	public String getRequestType(int requestId){
		return db.getSingleColumnString("request", "request", requestId, "request_id");
	}
	
	public String getObjectName(int requestId){
		return db.getSingleColumnString("request", "object_name", requestId, "request_id");
	}
	
	public int getObjectId(int requestId){
		return db.getSingleColumnInt("request", "object_id", requestId, "request_id");
	}
	
	public int getTargetObjectId(int requestId){
		return db.getSingleColumnInt("request", "target_object_id", requestId, "request_id");
	}
	
	public double getXCoord(int requestId){
		return db.getSingleColumnDouble("request", "xcoord", requestId, "request_id");
	}
	
	public double getZCoord(int requestId){
		return db.getSingleColumnDouble("request", "zcoord", requestId, "request_id");
	}
	
	public int getTimeStamp(int requestId){
		return db.getSingleColumnInt("request", "timestamp", requestId, "request_id");
	}
}
